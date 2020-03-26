package com.housaiying.qingting.home;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String s = "英语jo<em>i</em>n <em>i</em>n 三年级上册课本听力";
        Pattern pattern = Pattern.compile("<em>(.+?)</em>");
        Matcher matcher = pattern.matcher(s);
//        System.out.println(matcher.find());
        System.out.println(matcher.groupCount());
     /*   System.out.println(matcher.group(1));

        System.out.println(matcher.find());
        System.out.println(matcher.group(1));*/

       /* System.out.println(s.indexOf("<em>"));
        System.out.println(s.indexOf("</em>"));
        StringBuilder stringBuilder=new StringBuilder(s);
        stringBuilder.replace(stringBuilder.indexOf("<em>"),stringBuilder.indexOf("<em>")+4,"");
        stringBuilder.replace(stringBuilder.indexOf("</em>"),stringBuilder.indexOf("</em>")+5,"");
        System.out.println(stringBuilder);*/
    }

    @Test
    public void convertNum() {
        int num = 1110;
        if (num < 1000) {
            System.out.println(num);
        } else {
            String dy1000 = String.valueOf(num / 1000);
            String xy1000 = String.valueOf(num % 1000 / 100);

            System.out.println(dy1000 + "." + xy1000 + "k");
        }
    }
}