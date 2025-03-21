package com.xiaqi.filetransfer.core;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class FileHashUtil {
    public static String getHash(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            int readSize = 10 * 1024 * 1024;
            long remaining = file.length();
            while (remaining > 0) {
                if (remaining < readSize) {
                    readSize = (int) remaining;
                }
                MappedByteBuffer byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, file.length() - remaining, readSize);
                remaining -= readSize;
                digest.update(byteBuffer);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
