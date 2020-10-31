package org.rxjava.apikit.plugin;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.plugin.logging.Log;

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

    public static boolean commit(String dir, String commit, Log log) {
        StringBuffer sb = new StringBuffer();
        StringBuffer errSb = new StringBuffer();
        int r = CommandUtils.exec("git commit -m \"" + StringEscapeUtils.escapeXSI(commit) + "\"", dir, sb, errSb);
        if (r != 0) {
            //nothing to commit, working tree clean
            if (sb.indexOf("nothing") > -1) {
                return false;
            }
            throw new RuntimeException("exec result not zero");
        }
        return true;
    }

    public static void push(String dir, String branch) {
        CommandUtils.exec("git push -u origin " + branch, dir);
    }

    public static void main(String[] args) {
        clone(
                "https://codeup.aliyun.com/5eb8536c38076f00011bd2ac/rxjava/rxjava-api-example.git",
                "master", "./rxjava-api-example"
        );
    }
}
