/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.testingScripts;

import LabPLANET.utilities.LPHashMap;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class LPTestingOutFormat {
    
    public static final String TESTING_FILES_PATH = "/LabPLANET-API/testingRepository"; //\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\";
    public static final String TESTING_FILES_FIELD_SEPARATOR=";";
    public static final String TESTING_USER="labplanet";
    public static final String TESTING_PW="avecesllegaelmomento";
    public static final String MSG_DB_CON_ERROR="<th>Error connecting to the database</th>";       

    public static final String FILEHEADER_NUM_HEADER_LINES_TAG_NAME="NUMHEADERLINES";
    public static final String FILEHEADER_NUM_TABLES_TAG_NAME="NUMTABLES"; 
    public static final String FILEHEADER_TABLE_NAME_TAG_NAME="TABLE";

    public static final String FILEHEADER_NUM_ARGUMENTS="NUMARGUMENTS";
    public static final String FILEHEADER_NUM_EVALUATION_ARGUMENTS="NUMEVALUATIONARGUMENTS";
    public static final String FILEHEADER_EVALUATION_POSITION="EVALUATIONPOSITION";
    
    public static final String BUNDLE_FILE_NAME="parameter.config.labtimus";

    public static final String TST_BOOLEANMATCH =ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_booleanMatch");
    public static final String TST_BOOLEANUNMATCH =ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_booleanUnMatch");
    public static final String TST_BOOLEANUNDEFINED=ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_booleanUndefined");
    public static final String TST_ERRORCODEMATCH =ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_errorCodeMatch");
    public static final String TST_ERRORCODEUNMATCH =ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_errorCodeUnMatch");
    public static final String TST_ERRORCODEUNDEFINED=ResourceBundle.getBundle(BUNDLE_FILE_NAME).getString("labPLANET_errorCodeUndefined");

    public static HttpServletResponse responsePreparation(HttpServletResponse response){
        response.setCharacterEncoding(LPPlatform.LAB_ENCODER_UTF8);

        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
        String frontendUrl = prop.getString("frontend_url");

        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setHeader("Access-Control-Allow-Methods", "GET");          
        response.setContentType("text/html;charset=UTF-8");
        return response;
    }      
    
    public static String htmlStart(){        return "<html>";    }
    public static String htmlEnd(){        return "</html>";    }
    public static String bodyStart(){        return "<body>";    }
    public static String bodyEnd(){        return "</body>";    }

    public static String tableStart(){        return "<table>";    }
    public static String tableEnd(){        return "</table>";    }
    public static String headerStart(){        return "<th>";    }
    public static String headerEnd(){        return "</th>";    }
    public static String rowStart(){        return "<tr>";    }
    public static String rowEnd(){        return "</tr>";    }
    public static String fieldStart(){        return "<td>";    }
    public static String fieldEnd(){        return "</td>";    }

    public static String headerAddField(String field){
        String content="";
        content = content+"<th>"+LPNulls.replaceNull((String) field)+"</th>";           
        return content;
    }
    
    public static String headerAddFields(String[] fields){
        String content="";
        for (Object fld: fields){
            content = content+"<th>"+LPNulls.replaceNull(fld)+"</th>";           
        }
        return content;
    }
    
    public static String[] addUATColumns(String[] fields, Integer numEvaluationArguments){
        String[] newFields = new String[]{"Test #"};
        newFields=LPArray.addValueToArray1D(newFields, fields);
        if (numEvaluationArguments>0){
            newFields=LPArray.addValueToArray1D(newFields, "Syntaxis");
            newFields=LPArray.addValueToArray1D(newFields, "Code");
            newFields=LPArray.addValueToArray1D(newFields, "Evaluation");
        }
        return newFields;
    }


    public static String rowAddField(String field){
        String content="";
        content = content+fieldStart()+LPNulls.replaceNull((String) field)+fieldEnd();           
        return content;
    }

    public static String rowAddFields(Object[] fields){
        String content="";
        for (Object field: fields){
            if (field==null){
                content = content+fieldStart()+""+fieldEnd();           
            }else{
                content = content+fieldStart()+LPNulls.replaceNull(field.toString())+fieldEnd();           
            }
        }
        return content;
    }

    /**
     *
     * @param csvPathName
     * @param fileContent
     * @throws java.io.IOException
     */
    public static void createLogFile(String csvPathName, String fileContent) throws IOException{
        csvPathName = csvPathName.replace(".txt", ".html");            
        File file = new File(csvPathName);
            try (FileWriter fileWriter = new FileWriter(file)) {
                if (file.exists()){file.delete();}
                file.createNewFile();
                //Files.deleteIfExists(file.toPath());
                fileWriter.write(fileContent);
                fileWriter.flush();
            } 
    }
    
    public static String getHtmlStyleHeader(String servletName) {
        String fileContent = "";
        fileContent = fileContent + "<!DOCTYPE html>" + "";
        fileContent = fileContent + "<html>" + "";
        fileContent = fileContent + "<head>" + "";
        fileContent = fileContent + "<style>";
        ResourceBundle prop = ResourceBundle.getBundle(BUNDLE_FILE_NAME);
        fileContent = fileContent + prop.getString("testingTableStyle1");
        fileContent = fileContent + prop.getString("testingTableStyle2");
        fileContent = fileContent + prop.getString("testingTableStyle3");
        fileContent = fileContent + prop.getString("testingTableStyle4");
        fileContent = fileContent + prop.getString("testingTableStyle5");
        fileContent = fileContent + "</style>";
        fileContent = fileContent + "<title>Servlet " + servletName + "</title>" + "";
        fileContent = fileContent + "</head>" + "";
        fileContent = fileContent + "<body>" + "\n";
        fileContent = fileContent + "<h1>Servlet TestingUnitConversion at " + servletName + "</h1>" + "";
        fileContent = fileContent + "</body>" + "";
        fileContent = fileContent + "</html>" + "";
        fileContent = fileContent + "<table id=\"scriptTable\">";
        return fileContent;
    }
    
    
    public static HashMap<String, Object>  getCSVHeader(String[][] csvContent){
        HashMap<String, Object> fieldsRequired = new HashMap();   
        fieldsRequired.put(FILEHEADER_NUM_HEADER_LINES_TAG_NAME, "");   fieldsRequired.put(FILEHEADER_NUM_TABLES_TAG_NAME, "");   
        fieldsRequired.put(FILEHEADER_NUM_EVALUATION_ARGUMENTS, "");   
        //fieldsRequired.put(FILEHEADER_NUM_ARGUMENTS, "");  //fieldsRequired.put(FILEHEADER_EVALUATION_POSITION, "");           

        HashMap<String, Object> hm = new HashMap();   
        
        Integer maxHeaderLines=25;
        if (csvContent.length<maxHeaderLines){maxHeaderLines=csvContent.length-1;}
        Integer iLineParsed = 0;
        Boolean continueParsing=true;        
        while (continueParsing){
            String getLineKey = LPNulls.replaceNull(csvContent[iLineParsed][0]).toUpperCase();
            String getLineValue = LPNulls.replaceNull(csvContent[iLineParsed][1]);
            if (fieldsRequired.containsKey(getLineKey)){
                if (FILEHEADER_NUM_HEADER_LINES_TAG_NAME.equalsIgnoreCase(getLineKey)){
                    maxHeaderLines=Integer.parseInt(getLineValue);
                }
                if (FILEHEADER_NUM_TABLES_TAG_NAME.equalsIgnoreCase(getLineKey)){
                    Integer numTbls=Integer.parseInt(getLineValue);
                    for (int iNumTbls=1; iNumTbls<=numTbls; iNumTbls++){
                        fieldsRequired.put(FILEHEADER_TABLE_NAME_TAG_NAME+String.valueOf(iNumTbls), "");
                    }
                }
                hm.put(getLineKey, getLineValue);
                fieldsRequired.remove(getLineKey);
            }                
            if (iLineParsed>=maxHeaderLines){continueParsing=false;}
            iLineParsed++;
        }
        if (!fieldsRequired.isEmpty()){
            hm.clear();                 
            hm.put(LPPlatform.LAB_FALSE, LPHashMap.hashMapToStringKeys(fieldsRequired, ", "));
        }        
        return hm;
    }
    
    public static String createSummaryTable(TestingAssertSummary tstAssert){
        String fileContentHeaderSummary = LPTestingOutFormat.tableStart()+rowStart();
        String fileContentSummary =rowStart();

        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total Tests");
        fileContentSummary = fileContentSummary +rowAddField(tstAssert.getTotalTests().toString()+LPTestingOutFormat.TST_BOOLEANMATCH); 
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total Match Boolean");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetBooleanMatch.toString());
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total Boolean Undefined");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetBooleanUndefined.toString()); 
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total Boolean Unmatch");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetBooleanUnMatch.toString()); 
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total Match ErrorCode");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetErrorCodeMatch.toString()); 
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total ErrorCode Undefined");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetErrorCodeUndefined.toString()); 
        fileContentHeaderSummary=fileContentHeaderSummary+headerAddField("Total ErrorCode Unmatch");                
        fileContentSummary = fileContentSummary +LPTestingOutFormat.rowAddField(tstAssert.totalLabPlanetErrorCodeUnMatch.toString()); 

        fileContentSummary = fileContentHeaderSummary+fileContentSummary +rowEnd();            
        fileContentSummary = fileContentSummary +tableEnd();        
        return fileContentSummary;        
    }
    
    public static String createTableWithHeader(String table1Header, Integer numEvaluationArguments){
        String fileContentTable = LPTestingOutFormat.tableStart();            
        fileContentTable=fileContentTable+headerAddFields(addUATColumns(table1Header.split(TESTING_FILES_FIELD_SEPARATOR), numEvaluationArguments));
        fileContentTable=fileContentTable+rowStart();        
        return fileContentTable;
    }
    
    public static BigDecimal csvExtractFieldValueBigDecimal(Object value){
        if (value==null) return null;
        try{
            return new BigDecimal(value.toString());        
        }catch(Exception e){return null;}        
    }
    public static Boolean csvExtractFieldValueBoolean(Object value){        
        if (value==null) return null;
        if (value.toString().length()==0){return null;}
        try{
            return Boolean.getBoolean(value.toString());        
        }catch(Exception e){return null;}                    
    }
    public static String csvExtractFieldValueString(Object value){
        if (value==null) return null;
        try{
            return value.toString();        
        }catch(Exception e){return null;}        
    }
    
    public static String[] csvExtractFieldValueStringArr(Object value){
        if (value==null) return new String[0];
        try{
            String[] fieldsToRetrieve = value.toString().split("\\|");
            return fieldsToRetrieve;        
        }catch(Exception e){return new String[0];}        
    }
    public static Float csvExtractFieldValueFloat(Object value){
        if (value==null) return null;
        try{
            return Float.valueOf(value.toString());
        }catch(NumberFormatException e){return null;}        
    }
    public static Integer csvExtractFieldValueInteger(Object value){
        if (value==null) return null;
        try{
            return Integer.valueOf(value.toString());
        }catch(NumberFormatException e){return null;}        
    }    
    public static Date csvExtractFieldValueDate(Object value){
        if (value==null) return null;
        try{
            return Date.valueOf(value.toString());
        }catch(NumberFormatException e){return null;}        
    }    
    
}
