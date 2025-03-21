package com.xiaqi.filetransfer.core;

public class ConvertUnitUtil {
    public static String convertUnitStr(long size) {
        final int KB = 1024;
        final int MB = KB * 1024;
        final int GB = MB * 1024;
        StringBuilder builder = new StringBuilder();
        if (size >= GB) {
            double sizeGB = (double) size / (double) GB;
            builder.append(String.format("%.2f", sizeGB)).append("GB");
        } else if (size >= MB) {
            double sizeMB = (double) size / (double) MB;
            builder.append(String.format("%.2f", sizeMB)).append("MB");
        } else if (size >= KB) {
            double sizeKB = (double) size / (double) KB;
            builder.append(String.format("%.2f", sizeKB)).append("KB");
        } else {
            builder.append(size).append("B");
        }
        return builder.toString();
    }
}
