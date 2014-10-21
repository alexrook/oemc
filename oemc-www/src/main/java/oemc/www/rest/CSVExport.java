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
import java.util.regex.*;
import javax.ws.rs.HeaderParam;
import oemc.utils.csv.FieldSeparatorEnum;
import oemc.utils.csv.LineSeparatorEnum;
import oemc.utils.json.Json2CSV;

@Path("exp")
public class CSVExport {

    @Context
    private UriInfo context;

   

   
    public CSVExport() {
    }

    @Path("csv")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String expCSV(@HeaderParam("X-LineSeparator") String lineSeparator,
            @HeaderParam("User-Agent") String useragent,
            @HeaderParam("X-FieldSeparator") String fieldSeparator,
            String content) {

        Json2CSV converter = new Json2CSV((lineSeparator != null) ? LineSeparatorEnum.get(lineSeparator) : LineSeparatorEnum.get(useragent),
                (fieldSeparator != null) ? FieldSeparatorEnum.get(fieldSeparator) : FieldSeparatorEnum.OTHER);
        JSONArray a = new JSONArray(content);

       return converter.export(a);
    }

 
    
    public static void main(String args[]) {

        String s = System.lineSeparator();
        System.out.println(s);
        Pattern p = Pattern.compile("(\\s)|([,;'\"])");

        Matcher m = p.matcher("sdcsdcsdc'sdc;");
        System.out.println("find=" + m.find());
        System.out.println("groupCount=" + m.groupCount());
        System.out.println("group=" + m.group());
        System.out.println("group1=" + m.group(1));
        System.out.println("group2=" + m.group(2));
        System.out.println("matches=" + m.matches());

        System.out.println("find=" + m.find());
        System.out.println("groupCount=" + m.groupCount());
        System.out.println("group=" + m.group());
        System.out.println("group1=" + m.group(1));
        System.out.println("group=" + m.group(2));
        System.out.println("matches=" + m.matches());

        System.out.println("find=" + m.find());
        System.out.println("groupCount=" + m.groupCount());
        System.out.println("group=" + m.group());
        System.out.println("group1=" + m.group(1));
        System.out.println("group=" + m.group(2));
        System.out.println("matches=" + m.matches());
    }
}
