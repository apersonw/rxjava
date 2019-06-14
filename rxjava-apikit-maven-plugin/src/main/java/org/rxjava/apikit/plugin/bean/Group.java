package org.rxjava.apikit.plugin.bean;

import lombok.*;

import java.util.List;

/**
 * @author happy
 */
@Getter
@Setter
@ToString
public class Group {
    private List<Task> tasks;
    private String rootPackage;
    private String apiType;
    public Group() {
    }
    public Group(List<Task> tasks, String rootPackage,String apiType) {
        this.tasks = tasks;
        this.rootPackage = rootPackage;
        this.apiType = apiType;
    }
}
