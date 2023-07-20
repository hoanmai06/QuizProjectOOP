package Algorithms;

public class FormatHTMLSafe {
    public static String format(String str) {
        if (str == null) return null;

        StringBuilder newstr= new StringBuilder();

        for(int i=0;i<str.length();i++)
            if(str.charAt(i)=='\'')
                newstr.append("&#39;");
            else if (str.charAt(i)=='\"')
                newstr.append("&#34;");
            else if(str.charAt(i)=='&')
                newstr.append("&#38;");
            else if(str.charAt(i)=='<')
                newstr.append("&#60;");
            else if(str.charAt(i)=='>')
                newstr.append("&#62;");
            else
                newstr.append(String.copyValueOf(str.toCharArray(), i, 1));

        return newstr.toString();
    }
}
