package Algorithms;

public class FormatHTMLSafe {
    public static String format(String str) {
        if (str == null) return null;

        String newstr="";

        for(int i=0;i<str.length();i++)
            if(str.charAt(i)=='\'')
                newstr = newstr + "&#39;";
            else if (str.charAt(i)=='\"')
                newstr = newstr + "&#34;";
            else if(str.charAt(i)=='&')
                newstr = newstr + "&#38;";
            else if(str.charAt(i)=='<')
                newstr = newstr + "&#60;";
            else if(str.charAt(i)=='>')
                newstr = newstr + "&#62;";
            else
                newstr= newstr + String.copyValueOf(str.toCharArray(),i,1);

        return newstr;
    }
}
