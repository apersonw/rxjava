package org.rxjava.third.weixin.cp.bean.taskcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务卡片按钮
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardButton {
    private String key;
    private String name;
    private String replaceName;
    private String color;
    private Boolean bold;
}
