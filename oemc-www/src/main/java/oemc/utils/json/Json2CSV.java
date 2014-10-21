package oemc.utils.json;

import java.util.regex.Pattern;
import oemc.utils.csv.FieldSeparatorEnum;
import oemc.utils.csv.LineSeparatorEnum;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author moroz
 */
public class Json2CSV {

    private final Pattern hSymbols = Pattern.compile("(\\s)|([;,\"'])");

    private LineSeparatorEnum lineS = LineSeparatorEnum.SYSTEM;
    private FieldSeparatorEnum fieldS = FieldSeparatorEnum.OTHER;

    private boolean useStrict = true;
    private final String[] escapeCandidates = {"\"", "'", "`", "#"};
    private String fieldEscaper = escapeCandidates[0];

    public Json2CSV(LineSeparatorEnum lineS, FieldSeparatorEnum fieldS) {
        this.lineS = lineS;
        this.fieldS = fieldS;
        setFieldEscaper();
    }

    private void setFieldEscaper() {
        for (String candidate : escapeCandidates) {
            if (!(fieldS.getSeparator().contains(candidate))) {
                this.fieldEscaper = candidate;
                break;
            }
        }
    }

    public String export(JSONArray a) {

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            buf.append(objToCSVRow(a.get(i)));
            buf.append(lineS.getSeparator());
        }

        return buf.toString();
    }

    public String convert2Row(JSONObject val) {
        StringBuilder buf = new StringBuilder();

        for (Object key : val.keySet()) {
            buf.append(hideChars(
                    val.get((String) key).toString()));
            buf.append(fieldS.getSeparator());
        }

        buf.deleteCharAt(buf.length() - 1);
        return buf.toString();
    }

    public String convert2Row(JSONArray val) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {

            buf.append(hideChars(
                    val.get(i).toString()));
            buf.append(fieldS.getSeparator());
        }
        buf.deleteCharAt(buf.length() - 1);
        return buf.toString();
    }

    private String objToCSVRow(Object val) {

        if (val instanceof JSONObject) {
            return convert2Row((JSONObject) val);
        } else if ((val instanceof JSONArray)) {
            return convert2Row((JSONArray) val);
        } else {
            return hideChars(val.toString());
        }

    }

    private String hideChars(String field) {
        if (useStrict) {
            //TODO
        } else {
            //спрятать символы равные сепаратору полей в поле
            field = field.replaceAll(fieldS.getPattern(), hide(fieldS.getSeparator()));

            //спрятать все поле если содержит line-breaks
            if (field.contains(lineS.getSeparator())) {
                field = hide(field);
            }
        }

        return field;
    }

    private String hide(String val) {
        return (new StringBuilder(this.fieldEscaper).
                append(val).
                append(this.fieldEscaper))
                .toString();
    }

    public boolean isUseStrict() {
        return useStrict;
    }

    public void setUseStrict(boolean useStrict) {
        this.useStrict = useStrict;
    }

}
