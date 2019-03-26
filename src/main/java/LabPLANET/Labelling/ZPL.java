/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.Labelling;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
/**
 *
 * @author Administrator
 */
public class ZPL {
    
// aca obtenemos la printer default  
PrintService printService = PrintServiceLookup.lookupDefaultPrintService();  
  
String zplCommand = "^XA\n" +  
"^FO10,0^ARN,11,7^FD SOME TEXT ^FS\n" +  
"^FO300,0^ARN,11,7^FD SOME VALUE ^FS\n" +  
"^FO10,35^ARN,11,7^FD SOME TEXT ^FS\n" +  
"^FO300,35^ARN,11,7^FD SOME VALUE ^FS\n" +  
"^FO10,70^ARN,11,7^FD SOME CODE ^FS\n" +  
"^FO10,115^ARN,11,7^BCN,60,Y,Y,N^FD 23749237439827 ^FS\n" +  
"^XZ";  
  
// convertimos el comando a bytes  
byte[] by = zplCommand.getBytes();  
DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;  
Doc doc = new SimpleDoc(by, flavor, null);  
  
// creamos el printjob  
DocPrintJob job = printService.createPrintJob();  
  
// imprimimos  

//job.print(doc, null); 

//http://labelary.com/viewer.html

}
