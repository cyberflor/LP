/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
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

    private static final String LB_TRUE = "LABPLANET_TRUE";
    private static final String LB_FALSE = "LABPLANET_FALSE";
    
    /**
     *
     */    
    public LabPLANETQualityAssurance() { //not developed yet        
    }	

    /**
     *
     * @param directoryName
     */
    public static void listFilesAndFolders(String directoryName) {
            File directory = new File(directoryName);
            // get all the files from a directory
            File[] fList = directory.listFiles();
            for (File file : fList) {
                    System.out.println(file.getName());
            }
    }
	
    /**
     *
     * @return
     */
    public Method[] getMethodsList() {
    	//Guru99MethodMetaData  guru99ClassVar  = new Guru99MethodMetaData  ();
                 
    	Class  guru99ClassObjVar  = this.getClass();
    	Method[] guru99Method1 = guru99ClassObjVar.getDeclaredMethods();
    	return guru99Method1;
    }
    
    /**
     *
     * @param className
     * @return
     */
    public static Method[] getMethodsList(Class className) {
    	//Guru99MethodMetaData  guru99ClassVar  = new Guru99MethodMetaData  ();
    	//Class  guru99ClassObjVar  = className.getClass();
    	Method[] guru99Method1 =  className.getMethods();
    	return guru99Method1;
    }
    
    /**
     *
     * @param jarName
     * @param packageName
     * @return
     */
    public static List getClasseNamesInPackage(String jarName, String packageName) throws IOException{
        ArrayList classes = new ArrayList ();

        packageName = packageName.replaceAll("\\." , "/");
        boolean debug = false;
        System.out.println("Jar " + jarName + " looking for " + packageName);
            JarInputStream jarFile =null;
            try{                

                jarFile = new JarInputStream(new FileInputStream (jarName));
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
            }catch( IOException e){jarFile.close();}        
        return (List) classes;
    }

    /**
     *
     * @param project
     * @param pack
     * @param clase
     * @param metodo
     * @return
     */
    public static Object[] isJAvaDocException( String project, String pack, String clase, String metodo){        
        
        String schemaName = "requirements";
        String tableName = "java_class_doc_exception";
        String fieldName_exceptionLevel = "exception_level";
        String fieldName_objectName = "object_name";
        Object[] diagn = new Object[2];
        diagn[0] = false;  
        diagn[1] = "";
        
        
                
        Object[] existsRecord = Rdbms.existsRecord(schemaName, tableName, new String[]{fieldName_exceptionLevel,fieldName_objectName}, new Object[]{"project", project});
        if (LB_TRUE.equalsIgnoreCase(existsRecord[0].toString())){
            diagn[0] = true;  
            diagn[1] = "Exception at project level for "+project;            
            return diagn;
        }
        
        existsRecord = Rdbms.existsRecord(schemaName, tableName, new String[]{fieldName_exceptionLevel,fieldName_objectName}, new Object[]{"package", pack});
        if (LB_TRUE.equalsIgnoreCase(existsRecord[0].toString())){
            diagn[0] = true;  
            diagn[1] = "Exception at package level for "+pack;            
            return diagn;
        }

        existsRecord = Rdbms.existsRecord(schemaName, tableName, new String[]{fieldName_exceptionLevel,fieldName_objectName}, new Object[]{"class", clase});
        if (LB_TRUE.equalsIgnoreCase(existsRecord[0].toString())){
            diagn[0] = true;  
            diagn[1] = "Exception at class level for "+clase;            
            return diagn;
        }

        existsRecord = Rdbms.existsRecord(schemaName, tableName, new String[]{fieldName_exceptionLevel,fieldName_objectName}, new Object[]{"method", metodo});
        if (LB_TRUE.equalsIgnoreCase(existsRecord[0].toString())){
            diagn[0] = true;  
            diagn[1] = "Exception at method level for "+metodo;            
            return diagn;
        }
        
        return diagn;
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     */
    public static Object[][] javaDocChecker() throws ClassNotFoundException {

        Integer linesInBetween = 40;
        Boolean containsFiles = false;
        String schemaName = "requirements";
        String tableName = "java_class_doc"; //module_backend_content";
        String projectName = "LabPLANET";
        String fieldName_lineName="line_name";

        String[] totalDiagnostic = new String[0];
//        String[] totalDiagnosticHeader = new String[0];        
        String[] keyFieldValues = new String[0];
        String[] keyFieldNames = new String[0];

        String[] fileNames = new String[0];
        
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "database.rdbms.Rdbms_NotUse");
/*
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "databases.DataDataIntegrity");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "databases.Rdbms");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.analysis.UserMethod");			
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.audit.LogTransac");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.audit.SampleAudit");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.batch.Batch");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.batch.BatchArray");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.batch.DataBatch");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSamplingPlanForSpec");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSpecRule");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.materialSpec.ConfigSpecStructure");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.materialSpec.DataSpec");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.parameter.Parameter");			
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.project.DataProject");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.project.DataProjectSample");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.requirement.ConfigRequirement");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.requirement.Requirement");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.requirement.RequirementDeployment");
*/        
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.sampleStructure.DataSample");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.sop.Sop");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.sop.SopList");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.sop.UserSop");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.unitsOfMeasurement.UnitsOfMeasurement");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.user.Role");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "functionalJava.user.UserProfile");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETArray");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETJson");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETMath");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETNullValue");	
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETPlatform");
        fileNames = LabPLANETArray.addValueToArray1D(fileNames, "LabPLANET.utilities.LabPLANETQualityAssurance");

        Boolean isConnected = Rdbms.getRdbms().startRdbms("labplanet", "LabPlanet");       

/*        totalDiagnosticHeader = LabPLANETArray.addValueToArray1D(totalDiagnosticHeader, "project_name");
        totalDiagnosticHeader = LabPLANETArray.addValueToArray1D(totalDiagnosticHeader, "package_name");
        totalDiagnosticHeader = LabPLANETArray.addValueToArray1D(totalDiagnosticHeader, "class_name");
        totalDiagnosticHeader = LabPLANETArray.addValueToArray1D(totalDiagnosticHeader, "method_name");  
        totalDiagnosticHeader = LabPLANETArray.addValueToArray1D(totalDiagnosticHeader, "Evaluation"); 
*/        
        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "project_name");
        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "package_name");
        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "class_name");
        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "method_name"); 
        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "Evaluation");    
        
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
                        
                        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, projectName);
                        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, packsName);
                        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, className);
                        totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, methodName);
                        
                        Object[] isException = isJAvaDocException(projectName, packsName, className, methodName);
                        if ((Boolean) isException[0]){
                            totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "Declared as exception in java_doc_exception exceptions table by "+isException[1] );
                            break;
                        }
                        
                        
                        keyFieldNames = new String[0];
                        //keyFieldNames = LabPLANETArray.addValueToArray1D(keyFieldNames, "project_name");
                        keyFieldNames = LabPLANETArray.addValueToArray1D(keyFieldNames, "package");
                        keyFieldNames = LabPLANETArray.addValueToArray1D(keyFieldNames, "class");
                        keyFieldNames = LabPLANETArray.addValueToArray1D(keyFieldNames, "method");                        
                        keyFieldValues = new String[0];
                        //keyFieldValues = LabPLANETArray.addValueToArray1D(keyFieldValues, projectName);
                        keyFieldValues = LabPLANETArray.addValueToArray1D(keyFieldValues, packsName);
                        keyFieldValues = LabPLANETArray.addValueToArray1D(keyFieldValues, className);
                        keyFieldValues = LabPLANETArray.addValueToArray1D(keyFieldValues, methodName);
                        Object[] existsRecord = Rdbms.existsRecord(schemaName, tableName, keyFieldNames, keyFieldValues);
                        
                        if (LB_FALSE.equalsIgnoreCase(existsRecord[0].toString())){
                            totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "ERROR: Not found any entry in "+tableName+" for this method.Database returned: "+existsRecord[5]);
                            break;
                        }
                        String[] fieldsToGet = new String[0];
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, fieldName_lineName);      
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, "line");
                        Object[][] javaDocEntries = Rdbms.getRecordFieldsByFilter(schemaName, tableName, keyFieldNames, keyFieldValues, fieldsToGet, new String[] {"line"});
                        Object[] javaDocEntries1D = LabPLANETArray.array2dTo1d(javaDocEntries, 0);
                        Object[] javaDocEntriesLineNumber = LabPLANETArray.array2dTo1d(javaDocEntries, 1);
                        
                        fieldsToGet = new String[0];
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, "id");
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, "note_brief");
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, fieldName_lineName);
                        fieldsToGet = LabPLANETArray.addValueToArray1D(fieldsToGet, fieldName_lineName);
                        String[] keyFieldNames2 = LabPLANETArray.addValueToArray1D(keyFieldNames, "covered");
                        Object[] keyFieldValues2 = LabPLANETArray.addValueToArray1D(keyFieldValues, false);
                        Object[][] javaDocEntriesNotCovered = Rdbms.getRecordFieldsByFilter(schemaName, tableName, keyFieldNames2, keyFieldValues2, fieldsToGet, new String[] {"line"});

                        Boolean existMark = LabPLANETArray.valueInArray(javaDocEntries1D, "BEGIN");       
                        if (!existMark){totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "ERROR. lineName BEGIN is mandatory in the code and was not found.");break;}

                        existMark = LabPLANETArray.valueInArray(javaDocEntries1D, "END");
                        if (!existMark){totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "ERROR. lineName END is mandatory in the code and was not found.");break;}

                        if (javaDocEntries1D.length<=2){totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, "ERROR. Even having BEGIN-END there are no lineName for requirements.");break;}
                        
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
                                //totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, warningsNotDocumented);//break;
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
                            totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, existsRecord[3]+". Records found.");
                        }else{
                            totalDiagnostic = LabPLANETArray.addValueToArray1D(totalDiagnostic, warningsNotCovered);//break;
                        }    

                        System.out.println(fileName + " >> " + " Package:" + packsName + " >> " + " Class:" + className + " >> " + " Method:" + mm.getName());
                        break;
                }
            }
        }
        String[][] diagn = LabPLANETArray.array1dTo2d(totalDiagnostic, totalDiagnosticNumColumns);
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
    

