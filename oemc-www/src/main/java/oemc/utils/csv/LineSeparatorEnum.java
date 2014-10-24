package oemc.utils.csv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author moroz
 */
public enum LineSeparatorEnum {

    DOS("\r\n"), UNIX("\n"), 
    SYSTEM(System.getProperty("line.separator"));
    //SYSTEM(System.lineSeparator()); since java 7

    public static final Pattern unixP = Pattern.compile("([Ll]inux)|([Uu]nix)|([Ff]reebsd)|([Aa]ndroid)|([Mm]ac)");
    public static final Pattern dosP = Pattern.compile("([Ww]indows)|([Dd]os)");

    private final String separator;

    public String getSeparator() {
        return separator;
    }

    LineSeparatorEnum(String separator) {
        this.separator=separator;
    }
    
   
    public static LineSeparatorEnum get(String val) {
        Matcher m = unixP.matcher(val);
        if (m.find()) {
            return UNIX;
        }
        m = dosP.matcher(val);
        if (m.find()) {
            return DOS;
        }
        return SYSTEM;
    }

}
