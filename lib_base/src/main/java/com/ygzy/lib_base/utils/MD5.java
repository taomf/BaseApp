package com.ygzy.lib_base.utils;

import java.security.MessageDigest;

/**
 * 说明：MD5工具类
 */

public final class MD5 {

    private MD5() {
    }

    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHasnString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtils.INSTANCE.e(content + "转MD5异常");
        }
        return null;
    }

    public static String getMD5Num(String content, int count) {
        try {
            if (count<=0) {
                MyLogUtils.INSTANCE.e("加密次数必须大于0");
            }else {
                String md5 = content;
                for (int i = 0; i < count; i++) {
                    md5 = getMD5(md5);
                }
                return md5;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtils.INSTANCE.e(content + "转MD5异常");
        }
        return null;
    }

    private static String getHasnString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
}

