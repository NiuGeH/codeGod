package com.springbootjpa.codeGod.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/*****************************************************
 * AES加密解密工具
 ****************************************************/


public class AesUtils {


    private static final Logger logger = LoggerFactory.getLogger(AesUtils.class); //log日志

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";    //"算法/模式/补码方式"

    /*****************************************************
     * AES加密
     * @param content 加密内容
     * @param key 加密密码，由字母或数字组成
    此方法使用AES-128-ECB加密模式，key需要为16位
    加密解密key必须相同，如：abcd1234abcd1234
     * @return 加密密文
     ****************************************************/

    public  String enCode(String content, String key) {
        if (key == null || "".equals(key)) {
            logger.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            logger.info("key长度不是16位！");
            return null;
        }
        try {
            byte[] raw = key.getBytes();  //获得密码的字节数组
            SecretKeySpec skey = new SecretKeySpec(raw, "AES"); //根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM);  //根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.ENCRYPT_MODE, skey); //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte [] byte_content = content.getBytes("utf-8"); //获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] encode_content = cipher.doFinal(byte_content); //密码器加密数据
            return Base64.encodeBase64String(encode_content); //将加密后的数据转换为字符串返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*****************************************************
     * AES解密
     * @param content 加密密文
     * @param key 加密密码,由字母或数字组成
    此方法使用AES-128-ECB加密模式，key需要为16位
    加密解密key必须相同
     * @return 解密明文
     ****************************************************/

    public  String deCode(String content, String key) {
        if (key == null || "".equals(key)) {
            logger.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            logger.info("key长度不是16位！");
            return null;
        }
        try {
            byte[] raw = key.getBytes();  //获得密码的字节数组
            SecretKeySpec skey = new SecretKeySpec(raw, "AES"); //根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM);  //根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.DECRYPT_MODE, skey); //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte [] encode_content = Base64.decodeBase64(content); //把密文字符串转回密文字节数组
            byte [] byte_content = cipher.doFinal(encode_content); //密码器解密数据
            return new String(byte_content,"utf-8"); //将解密后的数据转换为字符串返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        AesUtils aesUtils = new AesUtils();
        //加密
        String mi97JSJfjwIDAQAB = aesUtils.enCode("{'page':'1','rows':'5'}", "MI97JSJfjwIDAQAB");
        System.out.println(aesUtils.enCode("{'page':'1','rows':'5','validationCode':'0','siginEnd':'0'}", "MI97JSJfjwIDAQAB"));
        System.out.println(mi97JSJfjwIDAQAB);
        //解密
        System.out.println(aesUtils.deCode(
                "z8xf5ljf/xo0Eq9uhK8nZE01ZbNbBBcJCk6fbEKsp9TSuXYyOpzfy5shjkZGWpRi"
                ,"MI97JSJfjwIDAQAB"));

        byte[] bytes = DigestUtils.md5Digest("123456".getBytes());
        String s = new String(bytes);
        System.out.println(s);
    }

}
