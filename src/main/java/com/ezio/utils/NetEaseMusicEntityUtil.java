package com.ezio.utils;

import com.alibaba.fastjson.JSON;
import com.ezio.entity.NetEaseMusicEntity;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.*;

public class NetEaseMusicEntityUtil {
    /**
     * buU9L(["爱心", "女孩", "惊恐", "大笑"])的值
     */
    public static String g = "0CoJUm6Qyw8W8jud";
    /**
     * buU9L(["流泪", "强"])的值
     */
    public static String b = "010001";
    /**
     * buU9L(Rg4k.md)的值
     */
    public static String c = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
    /**
     * 随机生成长度为16的字符串
     */
    public static String i = createRandomStr(16);


    /**
     * 随机生成长度i的字符串
     *
     * @param size
     * @return
     */
    public static String createRandomStr(int size) {
        String keys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String key = "";
        for (int i = 0; i < size; i++) {
            int pos = (int) Math.floor(Math.random() * keys.length());
            key = key + String.valueOf(keys.charAt(pos));
        }
        return key;
    }

    /**
     * 随机生成USERAGENT
     *
     * @return
     */
    public static String createRandomUSERAGENTS() {
        String[] USER_AGENTS = {
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
                "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
                "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
                "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
                "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5"
        };
        Random rand = new Random();
        int num = rand.nextInt(USER_AGENTS.length);
        return USER_AGENTS[num];
    }

    /**
     * 截取长度
     *
     * @param str
     * @param size
     * @return
     */
    public static String zfill(String str, int size) {
        while (str.length() < size) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * aes加密
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String sSrc, String sKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        //此处使用BASE64做转码。
        return new Base64().encodeToString(encrypted);
    }

    /**
     * rsa加密算法
     *
     * @param text
     * @param pubKey
     * @param modulus
     * @return
     */
    public static String rsaEncrypt(String text, String pubKey, String modulus) {
        List<String> list = Arrays.asList(text.split(""));
        Collections.reverse(list);
        String _text = StringUtils.join(list, "");
        BigInteger biText = new BigInteger(1, _text.getBytes());
        BigInteger biEx = new BigInteger(1, hexToBytes(pubKey));
        BigInteger biMod = new BigInteger(1, hexToBytes(modulus));
        String biRet = biText.modPow(biEx, biMod).toString(16);
        return zfill(biRet, 256);
    }

    /**
     * 十六进制,字节
     *
     * @param hexString 十六进制字符串
     * @return {@link byte[]}
     */
    private static byte[] hexToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        String hexDigits = "0123456789abcdef";
        for (int i = 0; i < length; i++) {
            int pos = i * 2; // 两个字符对应一个byte
            int h = hexDigits.indexOf(hexChars[pos]) << 4; // 注1
            int l = hexDigits.indexOf(hexChars[pos + 1]); // 注2
            if (l == -1) { // 非16进制字符
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }

    /**
     * 得到参数
     *
     * @param musicId 音乐id
     * @return {@link String}
     */
    public static String getParams(String musicId) throws Exception {
        String encText = "{\"ids\":\"[" + musicId + "]\",\"level\":\"standard\",\"encodeType\":\"aac\",\"csrf_token\":\"\"}\"";
        System.out.println(aesEncrypt(encText, g));
        return aesEncrypt(aesEncrypt(encText, g), i);
    }

    /**
     * 获取网易云音乐实体
     *
     * @param musicId 音乐id
     * @return {@link NetEaseMusicEntity}* @throws Exception 异常
     */
    public static NetEaseMusicEntity getNetEaseMusicEntity(String musicId) throws Exception {
        Map formdata = new HashMap<>(2);
        formdata.put("params", getParams("1378055568"));
        formdata.put("encSecKey", rsaEncrypt(i, b, c));
        Map header = new HashMap<>(3);
        header.put("User-Agent", createRandomUSERAGENTS());
        header.put("referer", "https://music.163.com");
        header.put("origin", "https://music.163.com/");
        header.put("accept", "*/*");
        String s = HttpUtils.httpPostRequest("https://music.163.com/weapi/song/enhance/player/url/v1?csrf_token=", header, formdata);
        return JSON.parseObject(s, NetEaseMusicEntity.class);
    }
}
