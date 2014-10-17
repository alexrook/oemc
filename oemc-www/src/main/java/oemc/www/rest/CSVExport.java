package oemc.www.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("exp")
public class CSVExport {

    @Context
    private UriInfo context;

    private final String[] badSymvols = {",", ";"};

    public CSVExport() {
    }

    @Path("csv")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String expCSV(String content) {
        JSONArray a = new JSONArray(content);

        return jsonArrayToCSV(a, ",", null);
    }

    private String jsonArrayToCSV(JSONArray a, String delimitier, String lineSeparator) {

        delimitier = ((delimitier != null) && (delimitier.length() > 0))
                ? delimitier : ",";

        lineSeparator = ((lineSeparator != null) && (lineSeparator.length() > 0))
                ? lineSeparator : System.lineSeparator();

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            buf.append(jsonObjToCSVRow(a.get(i), delimitier));
            buf.append(lineSeparator);
        }

        return buf.toString();
    }
 
    private String jsonObjToCSVRow(Object val, String delimiter) {
        StringBuilder buf = new StringBuilder();
        if (val instanceof JSONObject) {
            for (Object key : ((JSONObject) val).keySet()) {

                buf.append(((JSONObject) val).get((String) key).toString());
                buf.append(delimiter);
            }
            buf.deleteCharAt(buf.length() - 1);
        } else if ((val instanceof JSONArray)) {
            for (int i = 0; i < ((JSONArray) val).length(); i++) {

                buf.append(((JSONArray) val).get(i));
                buf.append(delimiter);
            }
            buf.deleteCharAt(buf.length() - 1);
        } else {
            buf.append(val.toString());
        }
       return buf.toString();
    }
}
