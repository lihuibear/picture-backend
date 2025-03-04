package com.lihui.picturebackend.utils;

import java.awt.*;

/**
 * 颜色转换工具类
 */
public class ColorTransformUtils {

    private ColorTransformUtils() {
        // 工具类不需要实例化
    }

    /**
     * 获取标准颜色（将数据万象的 5 位色值转为 6 位）
     *
     * @param color
     * @return
     */
    public static String getStandardColor(String color) {
        // 去掉开头0x
        String hex = color.startsWith("0x") ? color.substring(2) : color;
        // 获取其余长度
        int length = hex.length();
        String[] parts = new String[3]; // 分别存储R, G, B部分
        int currentIndex = 0;

        for (int i = 0; i < 3; i++) {
            if (currentIndex >= length) {
                parts[i] = "";
                continue;
            }
            int remaining = length - currentIndex;
            if (i < 2) { // 处理R和G部分，尽量取两位
                if (remaining >= 2) {
                    parts[i] = hex.substring(currentIndex, currentIndex + 2);
                    currentIndex += 2;
                } else {
                    parts[i] = hex.substring(currentIndex, currentIndex + 1);
                    currentIndex += 1;
                }
            } else { // 处理B部分，取剩余所有字符
                parts[i] = hex.substring(currentIndex);
                currentIndex = length;
            }
        }

        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                result.append("00");
            } else if (part.length() == 1) {
                result.append(part.charAt(0)).append('0');
            } else {
                result.append(part);
            }
        }
        String standardHex = result.toString().substring(0, 6); // 确保总长度是6位
        return "0x" + standardHex;
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(getStandardColor("0xc000")); // 预期输出0xc00000
        System.out.println(getStandardColor("0xff"));    // 预期输出0xff0000
        System.out.println(getStandardColor("0x00"));    // 预期输出0x000000
        System.out.println(getStandardColor("0xf0f"));   // 预期输出0xf00ff0
        System.out.println(getStandardColor("0x0"));     // 预期输出0x000000
        System.out.println(getStandardColor("0xc00"));   // 预期输出0xc00000
    }

}