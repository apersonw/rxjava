package org.rxjava.apikit.tool.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class GitUtils {
    public static void clone(String url, String branch, String dir, Log log) {
        int r = ExecUtils.exec("git clone -b " + branch + "  " + StringEscapeUtils.escapeXSI(url) + " " + StringEscapeUtils.escapeXSI(dir), log);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }
    }

    public static int getVersion(String dir, Log log) {
        StringBuffer sb = new StringBuffer();
        int r = ExecUtils.exec("git rev-list --all --count", log, dir, sb, null);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }
        sb.trimToSize();
        return NumberUtils.toInt(sb.toString());
    }

    public static void setNameAndEmail(String dir, String name, String email, Log log) {
        int r = ExecUtils.exec("git config user.name \"" + StringEscapeUtils.escapeXSI(name) + "\"", log, dir);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }

        r = ExecUtils.exec("git config user.email \"" + StringEscapeUtils.escapeXSI(email) + "\"", log, dir);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }
    }

    public static void add(String dir, Log log) {
        int r = ExecUtils.exec("git add .", log, dir);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }
    }

    public static boolean commit(String dir, String commit, Log log) {
        StringBuffer sb = new StringBuffer();
        StringBuffer errSb = new StringBuffer();
        int r = ExecUtils.exec("git commit -m \"" + StringEscapeUtils.escapeXSI(commit) + "\"", log, dir, sb, errSb);
        if (r != 0) {
            //nothing to commit, working tree clean
            if (sb.indexOf("nothing") > -1) {
                return false;
            }
            throw new RuntimeException("exec result not zero");
        }
        return true;
    }

    public static void push(String dir, String branch, Log log) {
        int r = ExecUtils.exec("git push -u origin " + branch, log, dir);
        if (r != 0) {
            throw new RuntimeException("exec result not zero");
        }
    }

    public static void main(String[] args) {
        SystemStreamLog log = new SystemStreamLog();
        clone(
                "https://code.aliyun.com/rxjava/rxjava-api-example.git",
                "master", "./temp",
                log
        );

        System.out.println(getVersion("./temp", log));
    }
}
