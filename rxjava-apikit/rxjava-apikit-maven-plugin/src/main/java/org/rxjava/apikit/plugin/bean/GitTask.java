package org.rxjava.apikit.plugin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author happy
 */
@Getter
@Setter
public class GitTask extends AbstractTask {
    private String url;
    private String user;
    private String password;
    private String authorEmail = "apikit@rxjava.org";
    private String authorName = "apikit";
    private Task task;
    private String branch = "master";
    private String outPath;
    private List<String> deleteUris;
}
