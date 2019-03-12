/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class LPTestingOutFormat {
    
    public static String TESTING_FILES_PATH ="\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\";
    public static String TESTING_FILES_FIELD_SEPARATOR=";";
    public static String TESTING_USER="labplanet";
    public static String TESTING_PW="avecesllegaelmomento";
    public static String MSG_DB_CON_ERROR="<th>Error connecting to the database</th>";
    
    public static HttpServletResponse responsePreparation(HttpServletResponse response){
        response.setCharacterEncoding(LPPlatform.LAB_ENCODER_UTF8);

        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
        String frontendUrl = prop.getString("frontend_url");

        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setHeader("Access-Control-Allow-Methods", "GET");          
        response.setContentType("text/html;charset=UTF-8");
        return response;
    }      
    
    public static String tableStart(){        return "<table>";    }
    public static String tableEnd(){        return "</table>";    }
    public static String headerStart(){        return "<th>";    }
    public static String headerEnd(){        return "</th>";    }
    public static String rowStart(){        return "<tr>";    }
    public static String rowEnd(){        return "</tr>";    }

    public static String rowAddField(Object field){
        String content="";
        //for (String fld: fields){
       /* if (field.getClass()=="String[]"){
            content = content+"<td>"+Arrays.toString((Object[]) field)+"</td>";           
        }else{*/
            content = content+"<td>"+LPNulls.replaceNull((String) field.toString())+"</td>";           
        //}
        return content;
    }
    /**
     *
     * @param csvPathName
     * @param fileContent
     * @param servletName
     * @return
     * @throws java.io.IOException
     */
    
    public static void createLogFile(String csvPathName, String fileContent) throws IOException{
        csvPathName = csvPathName.replace(".txt", ".html");            
        File file = new File(csvPathName);
            try (FileWriter fileWriter = new FileWriter(file)) {
                Files.deleteIfExists(file.toPath());
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
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.labtimus");
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
    

    
}
