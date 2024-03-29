package org.rxjava.common.core.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

/**
 * @author happy 2019-06-20 14:41
 */
public class FileRenameUtils {
    public static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    public static void main(String[] args) {

        Path source = Paths.get("/Users/happy/DeskTop/img");
        Path dist = Paths.get("/Users/happy/DeskTop/img_py");
        handler(source, dist, "@", "Select");
    }

    private static void handler(Path source, Path dist, String split, String suffix) {
        try {
            if (Files.isDirectory(source)) {
                Files.list(source).forEach(child -> {
                    Path fileName = child.getFileName();
                    String pinyinName = toPinyin(fileName.getFileName().toString());
                    if (StringUtils.isNoneEmpty(split, suffix)) {
                        pinyinName = pinyinName.replace(split, suffix + split);
                    }
                    handler(child, dist.resolve(pinyinName));
                });
            } else {
                String pinyinName = toPinyin(dist.getFileName().toString()) + suffix;
                Files.createDirectories(dist.getParent());
                Files.copy(source, dist.resolveSibling(pinyinName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void handler(Path source, Path dist) {
        handler(source, dist, "", "");
    }

    public static String toPinyin(String name) {
        return name.chars().mapToObj((int r) -> {
            try {
                char ch = (char) r;
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(ch, FORMAT);
                if (ArrayUtils.isEmpty(pinyins)) {
                    return Character.toString(ch);
                } else {
                    String pinyin = pinyins[0];
                    return pinyin.substring(0, 1).toUpperCase() +
                            (pinyin.length() > 1 ? pinyin.substring(1) : "");
                }
            } catch (BadHanyuPinyinOutputFormatCombination ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.joining());
    }
}
