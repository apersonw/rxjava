package org.rxjava.third.weixin.cp.bean;

/**
 * 性别枚举
 */
public enum Gender {
    /**
     * 男
     */
    MALE("男", "1"),
    /**
     * 女
     */
    FEMALE("女", "2");

    private String genderName;
    private String code;

    Gender(String genderName, String code) {
        this.genderName = genderName;
        this.code = code;
    }

    public String getGenderName() {
        return this.genderName;
    }

    public String getCode() {
        return this.code;
    }

    public static Gender fromCode(String code) {
        if ("1".equals(code)) {
            return Gender.MALE;
        }
        if ("2".equals(code)) {
            return Gender.FEMALE;
        }

        return null;
    }
}
