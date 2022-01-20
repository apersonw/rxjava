package top.rxjava.apikit.tool.generator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import top.rxjava.apikit.tool.Context;
import top.rxjava.apikit.tool.generator.impl.DefaultClassNameMapper;
import top.rxjava.apikit.tool.generator.impl.DefaultPackageNameMapper;
import top.rxjava.apikit.tool.generator.impl.PatternNameMaper;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.info.ParamClassInfo;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author happy
 * 抽象类生成器
 */
@Setter
@Getter
@ToString
@Slf4j
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
     * 参数类信息构建包装器列表
     */
    protected List<BuilderWrapper<EnumParamClassInfo>> builderEnumWrappers;
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

                    String distName = classNameMapper.apply(nameSet, paramClassInfo.getPackageName(), paramClassInfo.getClassName());
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
     * 初始化枚举参数类包装器
     */
    private List<BuilderWrapper<EnumParamClassInfo>> initEnumParamClassWrapper() {
        Collection<EnumParamClassInfo> enumParamClassInfos = context.getEnumParamClassInfos();
        String sourceRootPackage = context.getRootPackage();
        Map<String, Set<String>> names = new HashMap<>();
        Map<String, BuilderWrapper<EnumParamClassInfo>> enumParamClassInfoMap = new HashMap<>();
        List<BuilderWrapper<EnumParamClassInfo>> builderWrappers = enumParamClassInfos.stream()
                .map(classTypeInfo -> {
                    String sourcePackage = classTypeInfo.getPackageName();

                    //组成完整包路径
                    String distPackage = packageNameMapper.apply(sourceRootPackage, sourcePackage);
                    Set<String> nameSet = names.computeIfAbsent(distPackage, k -> new HashSet<>());

                    String distName = classNameMapper.apply(nameSet, classTypeInfo.getPackageName(), classTypeInfo.getClassName());
                    nameSet.add(distName);

                    return createEnumParamClassWarpper(classTypeInfo, distPackage, distName);
                })
                .collect(Collectors.toList());
        builderWrappers.forEach(item -> enumParamClassInfoMap.put(item.getSourceFullName(), item));
        context.setEnumParamClassWrapperMap(enumParamClassInfoMap);
        return builderWrappers;
    }

    /**
     * 生成Api文件
     */
    @Override
    public void generate(Context context) throws Exception {
        this.context = context;
        this.builderWrappers = initParamClassWrapper();
        this.builderEnumWrappers = initEnumParamClassWrapper();

        //通过api类信息生成api
        Collection<ApiClassInfo> values = context.getApiClassInfoMultimap().values();
        for (ApiClassInfo apiInfo : values) {
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

        //通过EnumParamInfo类信息生成参数类信息
        for (BuilderWrapper<EnumParamClassInfo> builderWrapper : builderEnumWrappers) {
            try {
                generateEnumParamFile(builderWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        generateBaseFile();
    }

    public abstract void generateBaseFile() throws Exception;

    public abstract void generateApiFile(ApiClassInfo apiInfo) throws Exception;

    protected abstract BuilderWrapper<ParamClassInfo> createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String distName);

    protected abstract BuilderWrapper<EnumParamClassInfo> createEnumParamClassWarpper(EnumParamClassInfo paramClassInfo, String distPack, String distName);

    public abstract void generateParamFile(BuilderWrapper<ParamClassInfo> builderWrapper) throws Exception;

    public abstract void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> builderWrapper) throws Exception;

}
