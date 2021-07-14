package com.example.myapplication;

public class MyTools {
  public static String UnicodeToString(String UnicodeStr) {
    StringBuffer stringBuffer = new StringBuffer();
    String text = "";
    for (int i = 1; i < UnicodeStr.length(); i++) {
      if ('\\' == UnicodeStr.charAt(i)
              && 'u' == UnicodeStr.charAt(i + 1)) {
        Character c = (char) Integer.parseInt(UnicodeStr.substring(1 + 2, i + 6), 16);
        stringBuffer.append(c);
        i += 5;
      } else {
        stringBuffer.append(UnicodeStr.charAt(i));
      }
    }
    return stringBuffer.toString();
  }
  private static String StringToUnicode(String val) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < val.length(); i++) {
      int code = val.codePointAt(i);
      if (code < 128) {
        sb.append(String.format("%c", code));
      } else {
        sb.append(String.format("\\u%04x", code));
      }
    }
    return sb.toString();
  }
}
