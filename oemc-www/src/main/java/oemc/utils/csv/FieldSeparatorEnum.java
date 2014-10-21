package oemc.utils.csv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author moroz
 */
public enum FieldSeparatorEnum {

    TAB("\t"), SPACE("\u0020"), OTHER(",");

    public static final Pattern tab = Pattern.compile("(\t)|(tab)");
    public static final Pattern space = Pattern.compile("(\u0020)|(space)");

    private String val;

    public String getSeparator() {
        return val;
    }

    public String getPattern() {
        return Pattern.quote(this.getSeparator());
    }

    private FieldSeparatorEnum(String val) {
        this.val = val;
    }

    public static FieldSeparatorEnum get(String val) {
        Matcher m = tab.matcher(val);
        if (m.find()) {
            return TAB;
        }
        m = space.matcher(val);
        if (m.find()) {
            return SPACE;
        }

        OTHER.val = val;

        return OTHER;
    }
;
}
