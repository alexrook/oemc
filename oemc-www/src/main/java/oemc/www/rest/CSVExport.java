package oemc.www.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
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

    /**
     * Converts JSON to CSV
     * @param content - an json content
     * @param lineSeparator - separator lines for CSV-result files 
     *       if not specified ,user agent uses to determine the OS and line-separator
     *       accept special values like 'dos', 'unix', 'linux' ...
     *       default: line-separator for server OS 
     * @param useragent - User-Agent heder (automaticaly fills by browser)
     * @param fieldSeparator - fields separator for CSV-result files 
     *        accept special values: 'space', 'tab'
     *       default: comma
     * @param exportType - specific values ​​for the type of export
     *          'strict' - based on http://www.creativyst.com/Doc/Articles/CSV/CSV01.htm
     *          'custom' - fields that contain space,line-separator or field-seapator characters 
     *                       surounded by double-quotes (or next special charcter see Json2CSV.escapeCandidates)
     *          'simple' - in fields space,line-separator or field-seapator characters
     *                     surounded by double-quotes 
     *          
     * 
     * @return CSV file
     */
    @Path("csv")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String expCSV(@HeaderParam("X-LineSeparator") String lineSeparator,
            @HeaderParam("User-Agent") String useragent,
            @HeaderParam("X-FieldSeparator") String fieldSeparator,
             @HeaderParam("X-ExportType") String exportType,
            String content) {

        Json2CSV converter = new Json2CSV((lineSeparator != null) ? LineSeparatorEnum.get(lineSeparator) : LineSeparatorEnum.get(useragent),
                (fieldSeparator != null) ? FieldSeparatorEnum.get(fieldSeparator) : FieldSeparatorEnum.OTHER);
        
        converter.setExportType(exportType);
        
        JSONArray a = new JSONArray(content);

       return converter.export(a);
    }
    
}
