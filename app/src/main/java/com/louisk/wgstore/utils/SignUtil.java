package com.louisk.wgstore.utils;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class SignUtil {

  public static String toMD5(String param) throws Exception {
    StringBuffer buf = new StringBuffer();
    //生成实现指定摘要算法的 MessageDigest 对象。
    MessageDigest md = MessageDigest.getInstance("MD5");
    //使用指定的字节数组更新摘要。
    md.update(param.getBytes());
    //通过执行诸如填充之类的最终操作完成哈希计算。
    byte b[] = md.digest();
    //生成具体的md5密码到buf数组
    int i;
    for (int offset = 0; offset < b.length; offset++) {
      i = b[offset];
      if (i < 0)
        i += 256;
      if (i < 16)
        buf.append("0");
      buf.append(Integer.toHexString(i));
    }
    return buf.toString();
  }
  /**
   * 字符串 转UTF-8  转码
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String toUtf8(String str) {
    String result = null;
    try {
      result = new String(str.getBytes("UTF-8"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 字符串base64转码
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String toBase64(String str) {
    if (str == null){
      return null;
    }
    String result=Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
    return result;
  }

  public static String toString(String str){
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0, length = str.length(); i < length; i++) {
      char c = str.charAt(i);
      if (c <= '\u001f' || c >= '\u007f') {
        stringBuffer.append(String.format("\\u%04x", (int) c));
      } else {
        stringBuffer.append(c);
      }
    }
    str = stringBuffer.toString();
    return str;
  }
}
