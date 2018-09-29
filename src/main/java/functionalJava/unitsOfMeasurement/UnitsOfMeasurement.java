/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.unitsOfMeasurement;

import databases.Rdbms;
import labPLANET.utilities.LabPLANETArray;
import labPLANET.utilities.LabPLANETPlatform;

/**
 * functionality where units of measurements are involved
 * @author Fran Gomez
 * @version 0.1
 */
public class UnitsOfMeasurement {
    
    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";    
    
    public UnitsOfMeasurement(){
    }
/**
 * Convert one value expressed in currentUnit to be expressed in newUnit. Notes: 
 *  Units not assigned to any measurement family are not compatible so not convertible.
 *  Both units should belong to the same measurement family.
 * @param rdbm Rdbms - Database communication channel
 * @param schemaPrefix String - The schema/procedure where both uom should be present. 
 * @param valueToConvert Float - The value to be converted
 * @param currentUnit String - The units as expressed the value before be converted
 * @param newUnit String - the units for the value once converted
 * @return Object[] - position 0 - boolean to know if converted (true) or not (false)
 *                  - position 1 - the new value once converted
 *                  - position 2 - the new units when converted or the current units when not converted
 *                  - position 3 - conclusion in a wording mode to provide further detail about what the method applied for this particular case
 */    
    public Object[] convertValue(Rdbms rdbm, String schemaPrefix, Float valueToConvert, String currentUnit, String newUnit){
        
        Object[] conversion = new Object[6];
        String tableName = "units_of_measurement";
        Float valueConverted = valueToConvert;
        
        String schemaName = "config";
        schemaName = labPlat.buildSchemaName(schemaPrefix, schemaName);

        conversion[0] = false;
        conversion[1] = valueToConvert;
        conversion[2] = currentUnit;
        conversion[3] = "Not converted to "+newUnit+" and not defined the reason" ;
        
        if (currentUnit==null){
            conversion[3] = "Not converted, current units is set to null" ;            
            return conversion;
        }
        if (newUnit==null){
            conversion[3] = "Not converted, new units is set to null" ;            
            return conversion;
        }        
        if (newUnit.equals(currentUnit)){
            conversion[0] = true;
            conversion[3] = "Nothing to convert, both units are the same" ;            
            return conversion;
        }         
        Object[][] currentUnitInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"name"},  new Object[]{currentUnit}, new String[]{"name", "measurement_family", "is_base", "factor_value", "offset_value"});
        Object[][] newUnitInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"name"},  new Object[]{newUnit}, new String[]{"name", "measurement_family", "is_base", "factor_value", "offset_value"});
        if (currentUnitInfo[0][3].toString().equalsIgnoreCase("FALSE")){
            conversion[3] = "current unit not found in the database. Error: "+currentUnitInfo[0][5].toString() ;            
            return conversion;            
        }        
        if (newUnitInfo[0][3].toString().equalsIgnoreCase("FALSE")){
            conversion[3] = "current unit not found in the database. Error: "+newUnitInfo[0][5].toString() ;            
            return conversion;            
        }
        if (!currentUnitInfo[0][1].toString().equalsIgnoreCase(newUnitInfo[0][1].toString())){
            conversion[3] = "Units not belonging to the same family. ";
            return conversion; 
        }
        if (currentUnitInfo[0][1].toString()==null || currentUnitInfo[0][1].toString().length()==0){
            conversion[3] = "Units not assigned to any family. ";
            return conversion;            
        }
        
        valueConverted = valueConverted * ((float)newUnitInfo[0][3] / (float)currentUnitInfo[0][3]);
        valueConverted = valueConverted + ((float)newUnitInfo[0][4] - (float)currentUnitInfo[0][4]);
        valueConverted = Float.valueOf(String.format("%.10f",valueConverted));
        
        conversion[0] = true;
        conversion[1] = valueConverted;
        conversion[2] = newUnit;
        conversion[3] = "SUCCESSFULLY CONVERTED"+String.format("%.10f",valueConverted) ;
        
        return conversion;
    }
/**
 * Get all units of measurement from the same given family from one specific schema/procedure and getting as many fields
 * as the ones specified in fieldsToRetrieve.
 * @param rdbm - Rdbms - Database communication channel
 * @param schemaPrefix - String - ProcedureName  
 * @param family - String - The given family for the uom to be got.
 * @param fieldsToRetrieve String[] - Fields from the table that should be added to the array returned.
 * @return Object[][] - a dynamic table containing the fields specified in fieldsToRetrieve, position[0][3] set to FALSE when cannot buuilt.
 */
    public Object[][] getAllUnitsPerFamily(Rdbms rdbm, String schemaPrefix, String family, String[] fieldsToRetrieve ){
        
        Object[][] unitsList = new Object[0][6];
        String tableName = "units_of_measurement";        
        
        String schemaName = "config";
        schemaName = labPlat.buildSchemaName(schemaPrefix, schemaName);

        if (family==null){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            unitsList[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            unitsList[0][1]= classVersion;            
            unitsList[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());           
            unitsList[0][3] = "FALSE" ;            
            unitsList[0][4] = "family argument cannot be null" ;            
            return unitsList;
        }
      
        unitsList = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"measurement_family"},  new Object[]{family}, fieldsToRetrieve, new String[]{"factor_value", "offset_value"});
        if (unitsList[0][3].toString().equalsIgnoreCase("FALSE")){
//            unitsList[3] = "current unit not found in the database. Error: "+currentUnitInfo[0][5].toString() ;            
            return unitsList;            
        }        

        
        return unitsList;        
    }

    public String getFamilyBaseUnitName(Rdbms rdbm, String schemaPrefix, String family){
        String tableName = "units_of_measurement";                
        String schemaName = "config";
        schemaName = labPlat.buildSchemaName(schemaPrefix, schemaName);
        
        String baseUnitName="";
        
        Object[][] unitsList = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"measurement_family", "is_base"},  new Object[]{family, true}, new String[]{"name", "name", "name", "name"});
        if (unitsList[0][3].toString().equalsIgnoreCase("FALSE")){
            return "";            
        }           
        return unitsList[0][0].toString();            

    }
    
}
