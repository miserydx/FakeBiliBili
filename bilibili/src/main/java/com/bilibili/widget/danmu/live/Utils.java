package com.bilibili.widget.danmu.live;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by czp on 17-5-25.
 */
class Utils {
    private static byte[][] splitBytes(byte[] bytes, int n) {
        int lineCount = bytes.length % n == 0 ? bytes.length / n : bytes.length / n + 1;
        byte[][] result = new byte[lineCount][];
        int to;
        for (int line = 1; line <= lineCount; line++) {
            if (line != lineCount) {
                to = line * n;
            } else {
                to = bytes.length;
            }
            result[line - 1] = Arrays.copyOfRange(bytes, (line - 1) * n, to);
        }
        return result;
    }

    static void printBytes(byte[] bytes) {
        byte[][] data = splitBytes(bytes, 16);
        byte[] currentRow;
        char c;
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%08x  ", i * 16);
            currentRow = data[i];
            for (int j = 0; j < currentRow.length; j++) {
                System.out.printf("%02x ", currentRow[j]);
                if (j == 7) {
                    System.out.print(" ");
                }
            }
            if (currentRow.length < 16) {
                for (int k = 0; k < (48 - currentRow.length * 2 - (currentRow.length - 1)); k++) {
                    System.out.print(" ");
                }
            }
            System.out.print(" ");
            for (int j = 0; j < currentRow.length; j++) {
                if (currentRow[j] < ' ') {
                    c = '.';
                } else {
                    c = (char) currentRow[j];
                }
                System.out.print(c);
                if (j == 7) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    static ScriptEntity resolveScriptPartInHTML(URL url) throws IOException, IllegalArgumentException {
        ScriptEntity scriptEntity = new ScriptEntity();
        String scriptText = Jsoup.parse(url, 10000).head().select("script").last().data();
        Matcher matcher;
        //得到 ROOMID
        matcher = Pattern.compile("var ROOMID = (\\d+);").matcher(scriptText);
        if (matcher.find()) {
            scriptEntity.roomId = Integer.valueOf(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Invalid URL");
        }
        //得到 DANMU_RND
        matcher = Pattern.compile("var DANMU_RND = (\\d+);").matcher(scriptText);
        if (matcher.find()) {
            scriptEntity.random = Long.valueOf(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Invalid URL");
        }
        //得到 ROOMURL
        matcher = Pattern.compile("var ROOMURL = (\\d+);").matcher(scriptText);
        if (matcher.find()) {
            scriptEntity.roomURL = Integer.valueOf(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Invalid URL");
        }
        return scriptEntity;
    }
}
