/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.unitsOfMeasurement;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETNullValue;
import LabPLANET.utilities.LabPLANETPlatform;
import java.util.Arrays;

/**
 * functionality where units of measurements are involved
 * @author Fran Gomez
 * @version 0.1
 */
public class UnitsOfMeasurement {
    
    String classVersion = "0.1";
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
        String familyFieldNameDataBase = "measurement_family";
        Float valueConverted = valueToConvert;
        
        String schemaName = "config";
        LabPLANETArray labArr = new LabPLANETArray();
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);
        
        if (currentUnit==null){
            conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, "UnitsOfMeasurement_currentUnitsNotDefined",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
            return conversion;
        }
        if (newUnit==null){
            conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, "UnitsOfMeasurement_newUnitsNotDefined",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
            return conversion;
        }        
        if (newUnit.equals(currentUnit)){
            conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, "UnitsOfMeasurement_sameValueNotConverted",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
            conversion = labArr.addValueToArray1D(conversion, valueToConvert);
            return conversion;
        }         
        String[] fieldsToGet = new String[]{"name", familyFieldNameDataBase, "is_base", "factor_value", "offset_value"};
        Object[][] currentUnitInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"name"},  new Object[]{currentUnit}, fieldsToGet );
        Object[][] newUnitInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"name"},  new Object[]{newUnit}, fieldsToGet);
        if ("LABPLANET_FALSE".equalsIgnoreCase(currentUnitInfo[0][0].toString())){
            return conversion;            
        }        
        if ("LABPLANET_FALSE".equalsIgnoreCase(newUnitInfo[0][0].toString())){
            return conversion;            
        }
        Integer currentUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);         
        Integer newUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);                
        if ((currentUnitFamilyFieldPosic==-1) || (newUnitFamilyFieldPosic==-1) ){
            conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{familyFieldNameDataBase, Arrays.toString(fieldsToGet), schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
            return conversion;            
        }                        
        if (!currentUnitInfo[0][currentUnitFamilyFieldPosic].toString().equalsIgnoreCase(newUnitInfo[0][currentUnitFamilyFieldPosic].toString())){
            conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{currentUnit , currentUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            newUnit, newUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
            return conversion; 
        }
        
        valueConverted = valueConverted * ((float)newUnitInfo[0][3] / (float)currentUnitInfo[0][3]);
        valueConverted = valueConverted + ((float)newUnitInfo[0][4] - (float)currentUnitInfo[0][4]);
        valueConverted = Float.valueOf(String.format("%.10f",valueConverted));
        
        conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, "UnitsOfMeasurement_convertedSuccesfully",
                        new Object[]{currentUnit , newUnitInfo, valueToConvert, valueConverted, schemaPrefix,
                             "valueToConvert: "+valueToConvert+", : currentUnit"+LabPLANETNullValue.replaceNull(currentUnit)+", : newUnit"+LabPLANETNullValue.replaceNull(newUnit)});
        conversion = labArr.addValueToArray1D(conversion, valueConverted);
        conversion = labArr.addValueToArray1D(conversion, newUnit);
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
        LabPLANETArray labArr = new LabPLANETArray();        
        String schemaName = "config";
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);
        if (family==null){
            Object[] conversion = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, "UnitsOfMeasurement_FamilyArgumentMandatory",
                                    new Object[]{schemaPrefix});
            return labArr.array1dTo2d(conversion, conversion.length);
        }
      
        unitsList = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"measurement_family"},  new Object[]{family}, fieldsToRetrieve, new String[]{"factor_value", "offset_value"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(unitsList[0][0].toString())) return unitsList;            
        
        return unitsList;        
    }

    public String getFamilyBaseUnitName(Rdbms rdbm, String schemaPrefix, String family){
        String tableName = "units_of_measurement";                
        String schemaName = "config";
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);
       
        String baseUnitName="";
        
        Object[][] unitsList = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, 
                 new String[]{"measurement_family", "is_base"},  new Object[]{family, true}, new String[]{"name"});        
        
        return unitsList[0][0].toString();            

    }
    
}
