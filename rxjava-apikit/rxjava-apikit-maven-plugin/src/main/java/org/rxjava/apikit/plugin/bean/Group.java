package org.rxjava.apikit.plugin.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author happy
 * 任务分组
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private List<Task> tasks;
    private String rootPackage;
    private String apiType;
    private boolean reactive = true;
}
