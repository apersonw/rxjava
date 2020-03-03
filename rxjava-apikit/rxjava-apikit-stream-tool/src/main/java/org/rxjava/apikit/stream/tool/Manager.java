package org.rxjava.apikit.stream.tool;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.apikit.stream.tool.build.ApidocBuild;
import org.rxjava.apikit.stream.tool.scan.ApikitScanFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * api工具工厂
 */
@Setter
@Getter
public class Manager implements Serializable {
    private Context context;

    private Manager() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private Manager(Context context) {
        setContext(context);
    }

    private static class LazyHolder {
        private static Manager lazy(Context context){
            return new Manager(context);
        }
    }

    /**
     * @param sourcePath  待分析源码src/main/java的绝对路径
     * @param packagePath 待分析的包路径
     * @return Manager
     */
    public static Manager getInstance(String sourcePath, String packagePath) {
        Context context = new Context();
        context.setSrcMainJavaPath(sourcePath);
        context.setAnalysePackage(packagePath);
        return LazyHolder.lazy(context);
    }

    public Mono<String> start() {
        String analysePackage = context.getAnalysePackage();
        String srcMainJavaPath = context.getSrcMainJavaPath();
        return ApikitScanFactory.getInstance(context).scan().collectList()
                .flatMap(controllerInfos -> new ApidocBuild().build()
                        .subscriberContext(context -> context.put("controllerInfos", controllerInfos))
                ).subscriberContext(context -> context
                        .put("apikitContext", getContext())
                );
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy(context);
    }
}
