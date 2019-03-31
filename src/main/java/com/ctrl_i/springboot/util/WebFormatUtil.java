package com.ctrl_i.springboot.util;

/**
 * 普通格式与web文字格式替换
 * Created by zekdot on 2017/9/23.
 */
public class WebFormatUtil {
    /**
     * 将普通格式转换为web格式
     *
     * @param str
     * @return
     */
    public static String txtToHtml(String str) {
        String newStr = str.replaceAll("&", "&amp;")
                .replaceAll(" ", "&nbsp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\r\n", "<br>")
                .replaceAll("\n", "<br>");
        return newStr;
    }

    /**
     * 将web格式转换为普通格式
     *
     * @param str
     * @return
     */
    public static String htmlToTxt(String str) {
        String newStr = str.replace("\r\n", "<br>")
                .replaceAll("&gt", ">")
                .replaceAll("&lt", "<")
                .replaceAll("&nbsp", " ")
                .replaceAll("&amp", "&");
        //TODO 如果要彻底转换为普通模式应该过滤到所有其他的html标签，可以使用正则表达式
        return newStr;
    }

}