/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.manager;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiString;
import sun.misc.HexDumpEncoder;

/**
 *
 * @author yuu@akron
 */
public class StringByteConverter {

    //htmlencode
    private static String HtmlUnicodeencode(String s, int radix) {
        StringBuilder sb = new StringBuilder("");
        char ch[] = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (Character.getNumericValue(ch[i]) >= 1) {
                sb.append(ch[i]);
            } else if (Character.isWhitespace(ch[i])) {
                sb.append(ch[i]);
            } else {
                sb.append("&#");
                if (radix == 16) {
                    sb.append("x");
                }
                sb.append(Integer.toString(ch[i], radix));
                sb.append(";");
            }
        }
        return sb.toString();
    }

    //htmldecode
    public static String HtmlUnicodedecode(String encoded_str, int radix) {
        String retStr = "";
//        char[] chararray;
        encoded_str = encoded_str.replaceAll("\\&amp\\;", "&");
        encoded_str = encoded_str.replaceAll("\\&quot\\;", "\"");
        encoded_str = encoded_str.replaceAll("\\&lt\\;", "<");
        encoded_str = encoded_str.replaceAll("\\&gt\\;", ">");
        encoded_str = encoded_str.replaceAll("\\&nbsp\\;", " ");
        for (int i = 0; i < encoded_str.length(); i++) {
            if (encoded_str.charAt(i) != '&') {
                retStr += encoded_str.charAt(i);
                continue;
            }
            int index = encoded_str.indexOf(";", i);
            if (index < 0) {
                continue;
            }
            String str2 = encoded_str.substring(i, index);
            str2 = str2.replaceAll("\\&", "");
            str2 = str2.replaceAll("\\#", "");
            int utf16_code = Integer.parseInt(str2, radix);
            byte[] char_set = new byte[2];
            char_set[0] = (byte) (utf16_code >> 8);
            char_set[1] = (byte) (utf16_code & 255);
            try {
                String new_str = new String(char_set, "utf-16");
                retStr += new_str;
            } catch (Exception e) {
                System.out.println("HtmlUnicodedecode:"+e);
            }

            i = index;
        }
        return retStr;
    }
    
            //htmlencode
    private static String HtmlUnicodeencode(byte[] s, int radix) {
        StringBuilder sb = new StringBuilder("");
//        char ch[] = s.toCharArray();
        for (int i = 0; i < s.length; i++) {
            if (Character.getNumericValue(s[i]) >= 1) {
                sb.append(s[i]);
            } else if (Character.isWhitespace(s[i])) {
                sb.append(s[i]);
            } else {
                sb.append("&#");
                if (radix == 16) {
                    sb.append("x");
                }
                sb.append(Integer.toString(s[i], radix));
                sb.append(";");
            }
        }
        return sb.toString();
    }
    
    public static String BytetoString(byte[] str) {
        try {
            if (JKaiUI.getKaiEngineVersion()==null) {
                return new String(str);
            }
            if (JKaiUI.getKaiEngineVersion().equals("7.4.18")) {
                return new String(str, "Windows-31j");
            }
            if (JKaiUI.getKaiEngineVersion().equals("7.4.22")) {
                return new String(str, "utf-8");
            }
        } catch (Exception e) {
            System.out.println("bytetostring:" + e);
        }
        return new String(str);
    }   

    public static byte[] StringtoByte(String str) {
        
        try {
            if (JKaiUI.getKaiEngineVersion()==null) {
                return str.getBytes();
            }
            if (JKaiUI.getKaiEngineVersion().equals("7.4.18")) {
                return str.getBytes("Windows-31j");
            }
            if (JKaiUI.getKaiEngineVersion().equals("7.4.22")) {
                return str.getBytes("utf-8");
            }
        } catch (Exception e) {
            System.out.println("stringtobyte:" + e);
        }
        return str.getBytes();
    }
    
    public static String BytetoStringforPM(byte[] str, String user) {
        
        try {
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("JKaiUI(7.4.18)")) {
                return new String(str, "Windows-31j");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("JKaiUI(7.4.22)")) {
                return new String(str, "utf-8");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("WebUI(7.4.18)")) {
                String tmp = new String(str, "Windows-31j");
                String[] tmpstr = tmp.split(";");
                String returnstr = tmpstr[0] + ";" + tmpstr[1] + ";" + HtmlUnicodedecode(tmpstr[2].replaceAll("\2", ";"), 10) + ";";
                return returnstr;
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("WebUI(7.4.22)")) {
                String tmp = new String(str, "utf-8");
                String[] tmpstr = tmp.split(";");
                String returnstr = tmpstr[0] + ";" + tmpstr[1] + ";" + HtmlUnicodedecode(tmpstr[2].replaceAll("\2", ";"), 10) + ";";
                return returnstr;
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("GUI(7.4.18)")) {
                return new String(str, "Windows-31j");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("GUI(7.4.22)")) {
                return new String(str, "utf-8");
            }
        } catch (Exception e) {
            System.out.println("bytetostringforpm:" + e);
        }
        return new String(str);
    }

    public static byte[] StringtoByteforPM(String str, String user) {

        try {
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("JKaiUI(7.4.18)")) {
                return str.getBytes("Windows-31j");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("JKaiUI(7.4.22)")) {
                return str.getBytes("utf-8");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("WebUI(7.4.18)")) {
                String[] tmp = str.split(";");
                String sendstr = tmp[0] + ";" + tmp[1] + ";" + new KaiString(HtmlUnicodeencode(tmp[2], 10)).toString() + ";";
                return sendstr.getBytes("Windows-31j");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("WebUI(7.4.22)")) {
                String[] tmp = str.split(";");
                String returnstr = tmp[0] + ";" + tmp[1] + ";" + new KaiString(HtmlUnicodeencode(tmp[2], 10)).toString() + ";";
                return returnstr.getBytes("utf-8");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("GUI(7.4.18)")) {
                return str.getBytes("Windows-31j");
            }
            if (ChatManager.getInstance().getSelectedEncoding(user).equals("GUI(7.4.22)")) {
                return str.getBytes("utf-8");
            }
        } catch (Exception e) {
            System.out.println("stringtobyeforpm:" + e);
        }
        return str.getBytes();
    }
    
    public static void main(String[] args) {
        System.out.println("StringByteConverter test");
        HexDumpEncoder enc = new HexDumpEncoder();
        
        try {
            String tmp = "あ";
            System.out.println("utf-16:" + enc.encode(tmp.getBytes("utf-16")));
            System.out.println("windows-31j:" + enc.encode(tmp.getBytes("Windows-31j")));
            System.out.println("utf-8:" + enc.encode(tmp.getBytes("utf-8")));
            System.out.println("sjis:" + enc.encode(tmp.getBytes("SJIS")));
            System.out.println("htmlunicode windows-31j:" + enc.encode(HtmlUnicodeencode(tmp, 10).getBytes("windows-31j")));
            System.out.println("htmlunicode utf-8:" + enc.encode(HtmlUnicodeencode(tmp, 10).getBytes("utf-8")));
            
            byte[] str = tmp.getBytes("windows-31j");
            System.out.println(HtmlUnicodedecode(new String(str, "windows-31j"), 10));
            str = tmp.getBytes("utf-8");
            System.out.println(HtmlUnicodedecode(new String(str, "utf-8"), 10));
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
