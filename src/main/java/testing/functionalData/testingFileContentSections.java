/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalData;

import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class testingFileContentSections {
    
    public static String getHtmlStyleHeader(String servletName){
            String fileContent = "";
            fileContent = fileContent + "<!DOCTYPE html>" + "";
            fileContent = fileContent + "<html>" + "";
            fileContent = fileContent + "<head>" + "";
            fileContent = fileContent + "<style>";
                ResourceBundle prop = ResourceBundle.getBundle("parameter.config.labtimus");        
                fileContent = fileContent+prop.getString("testingTableStyle1");                
                fileContent = fileContent+prop.getString("testingTableStyle2");                
                fileContent = fileContent+prop.getString("testingTableStyle3");                
                fileContent = fileContent+prop.getString("testingTableStyle4");                
                fileContent = fileContent+prop.getString("testingTableStyle5");                
            fileContent = fileContent + "</style>";

            fileContent = fileContent + "<title>Servlet "+servletName+"</title>" + "";
            fileContent = fileContent + "</head>" + "";
            fileContent = fileContent + "<body>" + "\n";
            fileContent = fileContent + "<h1>Servlet TestingUnitConversion at " + servletName + "</h1>" + "";
            fileContent = fileContent + "</body>" + "";
            fileContent = fileContent + "</html>" + "";
            fileContent = fileContent + "<table id=\"scriptTable\">";        

        
        return fileContent;
    }
    
}
