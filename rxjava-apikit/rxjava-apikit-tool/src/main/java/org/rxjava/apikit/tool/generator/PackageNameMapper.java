package org.rxjava.apikit.tool.generator;

/**
 * @author happy
 * 包名Mapper
 */
public interface PackageNameMapper {
    String apply(String sourceRootPackage, String packageName);
}
