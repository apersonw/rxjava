package org.rxjava.apikit.tool.generator.impl;

import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.*;
import org.rxjava.apikit.tool.generator.AbstractGenerator;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.generator.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.eclipse.jgit.awtui.AwtCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * @author happy
 */
@Setter
@Getter
public class GitGenerator implements Generator {
    private static final Logger log = LoggerFactory.getLogger(GitGenerator.class);

    private AbstractGenerator generator;
    private String gitUser;
    private String gitPassword;

    /**
     * 对应git  user.name
     */
    private String gitName;
    /**
     * 对应git  user.email
     */
    private String gitEmail;

    private String gitUrl;
    private String gitBranch = "master";
    private String outPath;
    private List<String> deleteUris;
    private String commitTemplate = "更新SDK版本:%s";

    @Override
    public void generate(Context context) throws Exception {
        Path tempDir = Files.createTempDirectory("apikit-git");

        //账号信息配置
        CredentialsProvider cp;
        if (StringUtils.isEmpty(gitUser)) {
            cp = new ChainingCredentialsProvider(new NetRCCredentialsProvider(), new AwtCredentialsProvider());
        } else {
            cp = new UsernamePasswordCredentialsProvider(gitUser, gitPassword);
        }
        log.info("开始 git clone");

        //git clone --depth=1
        try (Git git = Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(tempDir.toFile())
                .setBranch(gitBranch)
                .setCredentialsProvider(cp)
                .call()) {

            log.info("git clone 成功");
            log.info("git临时目录:{}", tempDir.toAbsolutePath());

            File src = new File(tempDir.toFile(), outPath);
            generator.setOutPath(src.getAbsolutePath());

            if (deleteUris != null && !deleteUris.isEmpty()) {
                for (String deleteUri : deleteUris) {
                    File delFile = new File(src, deleteUri);
                    log.info("清理目录:{}", delFile.getAbsolutePath());
                    FileUtils.deleteDirectory(delFile);
                }
            }
            int version = getVersion(git);
            log.info("开始执行生成器");
            generator.setVersion(Integer.toString(version + 1));
            generator.generate(context);

            DirCache dirCache = git.add().addFilepattern(".").call();
            log.info("添加文件: getEntryCount: {},dirCache:{}", dirCache.getEntryCount(), dirCache);

            Set<String> missing = git.status().call().getMissing();

            if (!missing.isEmpty()) {
                RmCommand rm = git.rm();
                for (String missingFile : missing) {
                    rm.addFilepattern(missingFile);
                }
                DirCache rmCache = rm.call();

                log.info("删除文件: getEntryCount: {},dirCache:{}", rmCache.getEntryCount(), rmCache);
            }

            Status status = git.status().call();

            boolean isChange = status.getAdded().size() > 0 ||
                    status.getChanged().size() > 0 ||
                    status.getMissing().size() > 0 ||
                    status.getAdded().size() > 0 ||
                    status.getModified().size() > 0 ||
                    status.getRemoved().size() > 0;
            if (!isChange) {
                log.info("未有改变，不提交:{}", status);
                return;
            }

            version = getVersion(git);
            String message = String.format(commitTemplate, Integer.toString(version));

            log.info("开始提交！");
            RevCommit revCommit = git.commit().setAuthor(gitName, gitEmail).setMessage(message).call();
            log.info("提交结果:{}", revCommit);

            log.info("开始push:{}", revCommit);


            Iterable<PushResult> pushResults = git.push().setCredentialsProvider(cp).call();

            log.info("push结果:{}", Iterables.toString(pushResults));

        } finally {
            //清理临时文件夹
//            FileUtils.deleteDirectory(tempDir.toFile());
        }
    }

    private int getVersion(Git git) throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
        LogCommand log = git.log();
        List<Ref> refs = git.branchList().call();
        Ref curRef = null;
        String branch = "refs/heads/" + this.gitBranch;
        for (Ref ref : refs) {
            if (ref.getName().equals(branch)) {
                curRef = ref;
                break;
            }
        }
        if (curRef == null) {
            throw new RuntimeException("错误的分支:" + gitBranch);
        }

        log.add(curRef.getObjectId());

        Iterable<RevCommit> logs = log.call();
        int version = 0;
        for (RevCommit rev : logs) {
            version++;
        }
        return version;
    }

    @Override
    public String getOutPath() {
        return null;
    }

    @Override
    public void setVersion(String version) {

    }

    public static void main(String[] args) throws IOException, GitAPIException {
        //测试能否克隆仓库
        CredentialsProvider cp;
        cp = new ChainingCredentialsProvider(new NetRCCredentialsProvider(), new AwtCredentialsProvider());

        Path tempDir = Files.createTempDirectory("apikit-git");
        try (Git git = Git.cloneRepository()
                .setURI("git@code.aliyun.com:fenglin/fenglin-api-javademo.git")
                .setDirectory(tempDir.toFile())
                .setBranch("master")
                .setCredentialsProvider(cp)
                .call()) {
            System.out.println("结束");
        }
    }
}
