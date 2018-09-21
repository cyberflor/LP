/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.naming.NamingException;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import java.util.List;
import databases.Rdbms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 *
 * @author Administrator
 */
public class LabPLANETQualityAssurance {
	
//	 private static final boolean debug = true;

    public LabPLANETQualityAssurance() {}	

    public void listFilesAndFolders(String directoryName) {
            File directory = new File(directoryName);
            // get all the files from a directory
            File[] fList = directory.listFiles();
            for (File file : fList) {
                    System.out.println(file.getName());
            }
    }
	
    public Method[] getMethodsList() {
    	//Guru99MethodMetaData  guru99ClassVar  = new Guru99MethodMetaData  ();
    	Class  guru99ClassObjVar  = this.getClass();
    	Method[] guru99Method1 = guru99ClassObjVar.getDeclaredMethods();
    	return guru99Method1;
    }
    
    public static Method[] getMethodsList(Class className) {
    	//Guru99MethodMetaData  guru99ClassVar  = new Guru99MethodMetaData  ();
    	//Class  guru99ClassObjVar  = className.getClass();
    	Method[] guru99Method1 =  className.getMethods();
    	return guru99Method1;
    }
    
    public static List getClasseNamesInPackage(String jarName, String packageName){
        ArrayList classes = new ArrayList ();

        packageName = packageName.replaceAll("\\." , "/");
        boolean debug = false;
        if (debug) System.out.println
            ("Jar " + jarName + " looking for " + packageName);
            try{
                JarInputStream jarFile = new JarInputStream
                                (new FileInputStream (jarName));
                JarEntry jarEntry;

                while(true) {
                    jarEntry=jarFile.getNextJarEntry ();
                    if(jarEntry == null){
                        break;
                    }
                    if((jarEntry.getName ().startsWith (packageName)) &&
                         (jarEntry.getName ().endsWith (".class")) ) {
                             if (debug) System.out.println
                             ("Found " + jarEntry.getName().replaceAll("/", "\\."));
                             classes.add (jarEntry.getName().replaceAll("/", "\\."));
                         }
                }
            }catch( IOException e){}
        return (List) classes;
    }

    public static Object[] isJAvaDocException(Rdbms rdbm, String project, String pack, String clase, String metodo){        
        
        String schemaName = "requirements";
        String tableName = "java_class_doc_exception";
        Object[] diagn = new Object[2];
        diagn[0] = false;  
        diagn[1] = "";
        
                
        String[] existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName, new String[]{"exception_level","object_name"}, new Object[]{"project", project});
        if ("TRUE".equalsIgnoreCase(existsRecord[3])){
            diagn[0] = true;  
            diagn[1] = "Exception at project level for "+project;            
            return diagn;
        }
        
        existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName, new String[]{"exception_level","object_name"}, new Object[]{"package", pack});
        if ("TRUE".equalsIgnoreCase(existsRecord[3])){
            diagn[0] = true;  
            diagn[1] = "Exception at package level for "+pack;            
            return diagn;
        }

        existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName, new String[]{"exception_level","object_name"}, new Object[]{"class", clase});
        if ("TRUE".equalsIgnoreCase(existsRecord[3])){
            diagn[0] = true;  
            diagn[1] = "Exception at class level for "+clase;            
            return diagn;
        }

        existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName, new String[]{"exception_level","object_name"}, new Object[]{"method", metodo});
        if ("TRUE".equalsIgnoreCase(existsRecord[3])){
            diagn[0] = true;  
            diagn[1] = "Exception at method level for "+metodo;            
            return diagn;
        }
        
        return diagn;
    }
    
    
    public static Object[][] javaDocChecker() throws ClassNotFoundException {

        LabPLANETArray labArr = new LabPLANETArray();

        Integer linesInBetween = 40;
        Boolean containsFiles = false;
        String schemaName = "requirements";
        String tableName = "java_class_doc"; //module_backend_content";
        String projectName = "LabPLANET";

        String[] totalDiagnostic = new String[0];
//        String[] totalDiagnosticHeader = new String[0];        
        String[] keyFieldValues = new String[0];
        String[] keyFieldNames = new String[0];

        Object[] dirNames = new Object[0];
        String[] fileNames = new String[0];
        
        fileNames = labArr.addValueToArray1D(fileNames, "database.rdbms.Rdbms_NotUse");
/*
        fileNames = labArr.addValueToArray1D(fileNames, "databases.DataDataIntegrity");
        fileNames = labArr.addValueToArray1D(fileNames, "databases.Rdbms");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.analysis.UserMethod");			
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.audit.LogTransac");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.audit.SampleAudit");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.batch.Batch");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.batch.BatchArray");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.batch.DataBatch");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSamplingPlanForSpec");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSpecRule");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSpecStructure");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.materialSpec.DataSpec");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.parameter.Parameter");			
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.project.DataProject");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.project.DataProjectSample");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.requirement.ConfigRequirement");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.requirement.Requirement");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.requirement.RequirementDeployment");
*/        
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.sampleStructure.DataSample");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.sop.Sop");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.sop.SopList");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.sop.UserSop");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.unitsOfMeasurement.UnitsOfMeasurement");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.user.Role");
        fileNames = labArr.addValueToArray1D(fileNames, "functionalJava.user.UserProfile");
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETArray");
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETJson");
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETMath");
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETNullValue");	
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETPlatform");
        fileNames = labArr.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETQualityAssurance");

        Rdbms rdbm = new Rdbms();            
        boolean isConnected = false;
        try {
             isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            return null;
        }

/*        totalDiagnosticHeader = labArr.addValueToArray1D(totalDiagnosticHeader, "project_name");
        totalDiagnosticHeader = labArr.addValueToArray1D(totalDiagnosticHeader, "package_name");
        totalDiagnosticHeader = labArr.addValueToArray1D(totalDiagnosticHeader, "class_name");
        totalDiagnosticHeader = labArr.addValueToArray1D(totalDiagnosticHeader, "method_name");  
        totalDiagnosticHeader = labArr.addValueToArray1D(totalDiagnosticHeader, "Evaluation"); 
*/        
        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "project_name");
        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "package_name");
        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "class_name");
        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "method_name"); 
        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "Evaluation");    
        
        Integer totalDiagnosticNumColumns = totalDiagnostic.length;
        
        for (String fileName : fileNames) {
             //Rdbms_NotUse m = new Rdbms_NotUse();
             //Class<?> act = Class.forName("functionalJava.batch.DataBatch");
             Class<?> act = Class.forName(fileName);
             Method[] guru99Method1 = getMethodsList(act);
             System.out.println(fileName + " has " + guru99Method1.length);

            for (Method mm : guru99Method1) {
                String methodName = mm.getName();
                switch (methodName) {
                    case "wait":
                    case "notify":
                    case "toString":
                    case "getClass":
                    case "hashCode":
                        break;
                    case "equals":
                    case "notifyAll":
                    case "replaceNull":
                        break;
                    default:
                        String[] fn = fileName.split("\\.");
                        Integer fnLen = fn.length;
                        String className = fn[fnLen-1];
                        String packsName = fileName;
                        packsName = packsName.replaceAll("."+className, "");
                        
                        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, projectName);
                        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, packsName);
                        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, className);
                        totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, methodName);
                        
                        Object[] isException = isJAvaDocException(rdbm, projectName, packsName, className, methodName);
                        if ((Boolean) isException[0]){
                            totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "Declared as exception in java_doc_exception exceptions table by "+isException[1] );
                            break;
                        }
                        
                        
                        keyFieldNames = new String[0];
                        //keyFieldNames = labArr.addValueToArray1D(keyFieldNames, "project_name");
                        keyFieldNames = labArr.addValueToArray1D(keyFieldNames, "package");
                        keyFieldNames = labArr.addValueToArray1D(keyFieldNames, "class");
                        keyFieldNames = labArr.addValueToArray1D(keyFieldNames, "method");                        
                        keyFieldValues = new String[0];
                        //keyFieldValues = labArr.addValueToArray1D(keyFieldValues, projectName);
                        keyFieldValues = labArr.addValueToArray1D(keyFieldValues, packsName);
                        keyFieldValues = labArr.addValueToArray1D(keyFieldValues, className);
                        keyFieldValues = labArr.addValueToArray1D(keyFieldValues, methodName);
                        String[] existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName, keyFieldNames, keyFieldValues);
                        
                        if ( "FALSE".equalsIgnoreCase(existsRecord[3])){
                            totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "ERROR: Not found any entry in "+tableName+" for this method.Database returned: "+existsRecord[5]);
                            break;
                        }
                        String[] fieldsToGet = new String[0];
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "line_name");      
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "line");
                        Object[][] javaDocEntries = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, keyFieldNames, keyFieldValues, fieldsToGet, new String[] {"line"});
                        Object[] javaDocEntries1D = labArr.array2dTo1d(javaDocEntries, 0);
                        Object[] javaDocEntriesLineNumber = labArr.array2dTo1d(javaDocEntries, 1);
                        
                        fieldsToGet = new String[0];
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "id");
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "note_brief");
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "line_name");
                        fieldsToGet = labArr.addValueToArray1D(fieldsToGet, "line_name");
                        String[] keyFieldNames2 = labArr.addValueToArray1D(keyFieldNames, "covered");
                        Object[] keyFieldValues2 = labArr.addValueToArray1D(keyFieldValues, false);
                        Object[][] javaDocEntriesNotCovered = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, keyFieldNames2, keyFieldValues2, fieldsToGet, new String[] {"line"});

                        Boolean existMark = labArr.valueInArray(javaDocEntries1D, "BEGIN");       
                        if (!existMark){totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "ERROR. lineName BEGIN is mandatory in the code and was not found.");break;}

                        existMark = labArr.valueInArray(javaDocEntries1D, "END");
                        if (!existMark){totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "ERROR. lineName END is mandatory in the code and was not found.");break;}

                        if (javaDocEntries1D.length<=2){totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, "ERROR. Even having BEGIN-END there are no lineName for requirements.");break;}
                        
                        Integer lineFirst = (Integer) javaDocEntriesLineNumber[0];
                        Integer lineEnd = (Integer) javaDocEntriesLineNumber[javaDocEntriesLineNumber.length-1];
                        
                        String warningsNotDocumented = "";    
                        Integer numWarnings = 0;
                        if (lineEnd-lineFirst > linesInBetween){
                            for (Integer iLines=0;iLines<javaDocEntriesLineNumber.length-1;iLines++){
                                Integer currentLine = (Integer) javaDocEntriesLineNumber[iLines];
                                Integer nextLine = (Integer) javaDocEntriesLineNumber[iLines+1];
                                if (nextLine-currentLine > linesInBetween){
                                    numWarnings++; 
                                    warningsNotDocumented = warningsNotDocumented+"<br>"+numWarnings+". "+(nextLine-currentLine)+" lines are too much lines to require some requirements "
                                            + "in between the line named "+javaDocEntries[iLines][0]+" (Line "+currentLine+") "
                                            + "and the line named "+javaDocEntries[iLines+1][0]+" (Line "+nextLine+") ";                                                                               
                                }
                            }
                            if (warningsNotDocumented.length()>0){
                                warningsNotDocumented = "WARNINGS ON DOCUMENTATION: Found "+numWarnings+" warning(s):"+"<br>"+warningsNotDocumented;
                                //totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, warningsNotDocumented);//break;
                            }    
                        }

                        String warningsNotCovered = "";    
                        if (javaDocEntriesNotCovered.length>0){
                            for (Integer iLines=0;iLines<javaDocEntriesNotCovered.length;iLines++){
                                
                                warningsNotCovered = warningsNotCovered+"<br>"+iLines+". "
                                        + "id: "+javaDocEntriesNotCovered[iLines][0]
                                        + "note_brief: "+javaDocEntriesNotCovered[iLines][1]
                                        + "line_name: "+javaDocEntriesNotCovered[iLines][2];
                                        
                            }
                                warningsNotCovered = "WARNING ON REQUIREMENTS NOT MARKED AS COVERED: Found "+javaDocEntriesNotCovered.length+" warning(s):"+"<br>"+warningsNotCovered;                                
                        }                        
                        
                        if (warningsNotCovered.length()==0 && warningsNotDocumented.length()==0){
                            totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, existsRecord[3]+". Records found.");
                        }else{
                            totalDiagnostic = labArr.addValueToArray1D(totalDiagnostic, warningsNotCovered);//break;
                        }    

                        System.out.println(fileName + " >> " + " Package:" + packsName + " >> " + " Class:" + className + " >> " + " Method:" + mm.getName());
                        break;
                }
            }
        }
        String[][] diagn = labArr.array1dTo2d(totalDiagnostic, totalDiagnosticNumColumns);
        return diagn;                

        /*			Stream<Path> f = Files.list(Paths.get("."));
         List list =  myTestingJava.getClasseNamesInPackage
         ("functionalJava.batch", "com.sun.mail.handlers");
         System.out.println(list);
         dirNames = f.toArray();
          */
         /*
          * List fileNamesList = (List) new ArrayList();
          * Files.newDirectoryStream(Paths.get(), path ->
          * path.toString().endsWith("")).forEach(filePath ->
          * fileNamesList.add(filePath.toString()));
          */
    }
}    
    

