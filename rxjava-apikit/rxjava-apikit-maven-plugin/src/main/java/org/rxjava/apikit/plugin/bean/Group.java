package org.rxjava.apikit.plugin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author happy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private List<Task> tasks;
    private String rootPackage;
    private String apiType;
}
