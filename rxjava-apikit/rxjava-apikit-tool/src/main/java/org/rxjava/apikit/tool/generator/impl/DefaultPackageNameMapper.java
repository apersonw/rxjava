package org.rxjava.apikit.tool.generator.impl;

import org.rxjava.apikit.tool.generator.PackageNameMapper;

/**
 * @author happy
 * 默认的包名Mapper
 */
public class DefaultPackageNameMapper implements PackageNameMapper {
    @Override
    public String apply(String sourceRootPackage, String packageName) {
        boolean isInPack = packageName.startsWith(sourceRootPackage);
        if (isInPack) {
            String dist = packageName.substring(sourceRootPackage.length());
            if(dist.startsWith(".")){
                return dist.substring(1);
            }
            return dist;
        }else{
            int index = packageName.lastIndexOf(".");
            if(index>-1){
                String dist = packageName.substring(index, packageName.length());
                if(dist.startsWith(".")){
                    return dist.substring(1);
                }
                return dist;
            }
        }
        return "core";
    }
}
