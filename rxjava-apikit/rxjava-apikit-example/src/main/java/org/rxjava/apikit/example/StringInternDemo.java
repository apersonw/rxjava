package org.rxjava.apikit.example;

/**
 * @author happy 2019-05-11 22:25
 */
public class StringInternDemo {
    public static void main(String[] args) {
        String s = new String("1");
        s.intern();

        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        //intern是检查当前字符串是否在常量池，若是没有，则将当前字符串值存放到常量池中
        s3.intern();

        String s4 = "11";
        System.out.println(s3 == s4);
    }
}
