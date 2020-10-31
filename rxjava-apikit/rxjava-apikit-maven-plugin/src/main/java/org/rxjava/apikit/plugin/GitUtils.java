package org.rxjava.apikit.plugin;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Git帮助类
 */
public class GitUtils {
    public static void clone(String url, String branch, String dir) {
        CommandUtils.exec("git clone -b " + branch + "  " + StringEscapeUtils.escapeXSI(url) + " " + StringEscapeUtils.escapeXSI(dir));
    }

    public static int getVersion(String dir) {
        StringBuffer sb = new StringBuffer();
        CommandUtils.exec("git rev-list --all --count", dir, sb, null);
        sb.trimToSize();
        return NumberUtils.toInt(sb.toString());
    }

    public static void setNameAndEmail(String dir, String name, String email) {
        CommandUtils.exec("git config user.name \"" + StringEscapeUtils.escapeXSI(name) + "\"", dir);
        CommandUtils.exec("git config user.email \"" + StringEscapeUtils.escapeXSI(email) + "\"", dir);
    }

    public static void add(String dir) {
        CommandUtils.exec("git add .", dir);
    }

    public static boolean commit(String dir, String message) {
        StringBuffer sb = new StringBuffer();
        StringBuffer errSb = new StringBuffer();
        CommandUtils.exec("git commit -m \"" + StringEscapeUtils.escapeXSI(message) + "\"", dir, sb, errSb);
        //nothing to commit, working tree clean
        return sb.indexOf("nothing") < 0;
    }

    public static void push(String dir, String branch) {
        CommandUtils.exec("git push -u origin " + branch, dir);
    }

    public static void main(String[] args) {
        //clone(
        //        "https://codeup.aliyun.com/5eb8536c38076f00011bd2ac/rxjava/rxjava-api-example.git",
        //        "master", "./rxjava-api-example"
        //);
        setNameAndEmail("./rxjava-api-example", "happy", "185408868@qq.com");
        add("./rxjava-api-example");
        commit("./rxjava-api-example", "测试提交");
        push("./rxjava-api-example","master");
    }
}
