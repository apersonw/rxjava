package top.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy
 */
@Getter
@Setter
public class AnnotationInfo {
    private ClassTypeInfo typeInfo;

    private List<String> args = new ArrayList<>();
}
