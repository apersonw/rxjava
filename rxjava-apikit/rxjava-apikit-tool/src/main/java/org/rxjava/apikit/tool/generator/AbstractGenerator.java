package org.rxjava.apikit.tool.generator;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.apikit.tool.generator.impl.DefaultClassNameMapper;
import org.rxjava.apikit.tool.generator.impl.DefaultPackageNameMapper;
import org.rxjava.apikit.tool.generator.impl.PatternNameMaper;
import org.rxjava.apikit.tool.info.ApiClass;
import org.rxjava.apikit.tool.info.ParamClassInfo;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;
import reactor.core.publisher.Flux;

import java.util.*;

/**
 * @author happy
 * 抽象类生成器
 */
@Setter
@Getter
public abstract class AbstractGenerator implements Generator {
    /**
     * 上下文
     */
    protected Context context;
    /**
     * 输出路径
     */
    protected String outPath;
    /**
     * 输出的根包路径
     */
    protected String outRootPackage;
    /**
     * 微服务Id
     */
    protected String serviceId;
    /**
     * 微服务接口类型
     */
    protected String apiType;
    /**
     * 版本号
     */
    protected String version;
    /**
     * api命名
     */
    protected NameMaper apiNameMaper = new PatternNameMaper("(?<name>.*)Controller", "${name}Api");
    /**
     * 参数类信息构建包装器列表
     */
    protected List<BuilderWrapper<ParamClassInfo>> builderWrappers;
    /**
     * 消息名称maper
     */
    protected ClassNameMapper classNameMapper = new DefaultClassNameMapper();
    /**
     * 消息包名称maper
     */
    protected PackageNameMapper packageNameMapper = new DefaultPackageNameMapper();

    /**
     * 初始化参数类包装器
     */
    private List<BuilderWrapper<ParamClassInfo>> initParamClassWrapper() {
        Collection<ParamClassInfo> paramClassInfos = context.getParamClassInfos();
        String sourceRootPackage = context.getRootPackage();

        Map<String, Set<String>> names = new HashMap<>();
        Map<String, BuilderWrapper<ParamClassInfo>> paramClassInfoMap = new HashMap<>();

        List<BuilderWrapper<ParamClassInfo>> builderWrappers = Flux
                .fromIterable(paramClassInfos)
                .map(paramClassInfo -> {
                    String sourcePackage = paramClassInfo.getPackageName();

                    //组成完整包路径
                    String distPackage = packageNameMapper.apply(sourceRootPackage, sourcePackage);
                    Set<String> nameSet = names.computeIfAbsent(distPackage, k -> new HashSet<>());

                    String distName = classNameMapper.apply(nameSet, paramClassInfo.getPackageName(), paramClassInfo.getName());
                    nameSet.add(distName);

                    return createParamClassWarpper(paramClassInfo, distPackage, distName);
                })
                .collectList()
                .block();

        Objects.requireNonNull(builderWrappers).forEach(item -> paramClassInfoMap.put(item.getSourceFullName(), item));

        context.setParamClassWrapperMap(paramClassInfoMap);
        return builderWrappers;
    }

    /**
     * 生成Api文件
     */
    @Override
    public void generate(Context context) throws Exception {
        this.context = context;
        this.builderWrappers = initParamClassWrapper();

        //通过api类信息生成api
        for (ApiClass apiInfo : context.apis.getValues()) {
            try {
                generateApiFile(apiInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //通过param类信息生成参数类信息
        for (BuilderWrapper<ParamClassInfo> builderWrapper : builderWrappers) {
            try {
                generateParamFile(builderWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        generateBaseFile();
    }

    public abstract void generateBaseFile() throws Exception;

    public abstract void generateApiFile(ApiClass apiInfo) throws Exception;

    protected abstract BuilderWrapper<ParamClassInfo> createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String distName);

    public abstract void generateParamFile(BuilderWrapper<ParamClassInfo> builderWrapper) throws Exception;
}
