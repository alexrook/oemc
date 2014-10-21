package oemc.utils.json;

import java.util.regex.Matcher;
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

    private final Pattern badSymbols = Pattern.compile("(\\s)|([;,\"'])");

    private LineSeparatorEnum lineS = LineSeparatorEnum.SYSTEM;
    private FieldSeparatorEnum fieldS = FieldSeparatorEnum.OTHER;

    private boolean useStrict = true;

    public Json2CSV(LineSeparatorEnum lineS, FieldSeparatorEnum fieldS) {
        this.lineS = lineS;
        this.fieldS = fieldS;

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
        for (Object key : ((JSONObject) val).keySet()) {

            
            buf.append(hideChars(
                    ((JSONObject) val).get((String) key).toString()));
            buf.append(fieldS.getSeparator());
        }
        buf.deleteCharAt(buf.length() - 1);
        return buf.toString();
    }

    public String convert2Row(JSONArray val) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < ((JSONArray) val).length(); i++) {

            buf.append(hideChars(
                    ((JSONArray) val).get(i).toString()));
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

    private String hideChars(String row) {
        //TODO
        return row;
    }

    public boolean isUseStrict() {
        return useStrict;
    }

    public void setUseStrict(boolean useStrict) {
        this.useStrict = useStrict;
    }

}
