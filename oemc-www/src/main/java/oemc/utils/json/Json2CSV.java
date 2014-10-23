package oemc.utils.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oemc.utils.csv.FieldSeparatorEnum;
import oemc.utils.csv.LineSeparatorEnum;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author moroz
 */
public class Json2CSV {

    public static enum ExportType {

        SIMPLE, STRICT, CUSTOM;

        private static final Pattern custom = Pattern.compile("[Cc]ustom"),
                strict = Pattern.compile("[Ss]trict");

        public static ExportType get(String val) {
            if (val != null) {
                Matcher m = custom.matcher(val);
                if (m.find()) {
                    return CUSTOM;
                }
                m = strict.matcher(val);
                if (m.find()) {
                    return STRICT;
                }
            }
            return SIMPLE;
        }

    }

    private LineSeparatorEnum lineS = LineSeparatorEnum.SYSTEM;
    private FieldSeparatorEnum fieldS = FieldSeparatorEnum.OTHER;

    private ExportType exportType = ExportType.SIMPLE;
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

        switch (exportType) {

            case STRICT: {
                //based on : http://www.creativyst.com/Doc/Articles/CSV/CSV01.htm
                StringBuilder buf = new StringBuilder();
                field = field.trim();//TODO: only left trim here
                if ((field.contains("\""))
                        || (field.contains(lineS.getSeparator()))
                        || (field.contains(fieldS.getSeparator()))
                        || (Pattern.compile("\\s").matcher(field).find())) {

                    buf.append(field.replaceAll("\"", "\"\""));
                    field = hide(buf.toString());
                }

                break;
            }

            case CUSTOM: {
                field = field.trim();//TODO: only left trim here
                if ((field.contains(lineS.getSeparator()))
                        || (field.contains(fieldS.getSeparator()))
                        || (Pattern.compile("\\s").matcher(field).find())) {

                    field = hide(field);
                }

                break;

            }
            case SIMPLE: {
                //спрятать символы равные сепаратору полей в поле
                field = field.replaceAll(fieldS.getPattern(), hide(fieldS.getSeparator()));
                //спрятать все поле если содержит line-breaks
                if (field.contains(lineS.getSeparator())) {
                    field = hide(field);
                }
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

    public ExportType getExportType() {
        return exportType;
    }

    public void setExportType(String type) {
        this.exportType = ExportType.get(type);
    }

}
