/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.unitsOfMeasurement;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * functionality where units of measurements are involved
 * @author Fran Gomez
 * @version 0.1
 */
public class UnitsOfMeasurement {
    String classVersion = "0.1";
    /**
     *
     */
    public UnitsOfMeasurement(){
        // Not implemented yet
    }
/**
 * Convert one value expressed in currentUnit to be expressed in newUnit. Notes: 
 *  Units not assigned to any measurement family are not compatible so not convertible.
 *  Both units should belong to the same measurement family.
 * @param schemaPrefix String - The schema/procedure where both uom should be present. 
 * @param valueToConvert Float - The value to be converted
 * @param currentUnit String - The units as expressed the value before be converted
 * @param newUnit String - the units for the value once converted
 * @return Object[] - position 0 - boolean to know if converted (true) or not (false)
 *                  - position 1 - the new value once converted
 *                  - position 2 - the new units when converted or the current units when not converted
 *                  - position 3 - conclusion in a wording mode to provide further detail about what the method applied for this particular case
 */    
    public Object[] twoUnitsInSameFamily( String schemaPrefix, BigDecimal valueToConvert, String currentUnit, String newUnit){
        Object[] conversion = new Object[6];        
        if (currentUnit==null){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_currentUnitsNotDefined",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion;
        }
        if (newUnit==null){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_newUnitsNotDefined",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion;
        }        
        if (newUnit.equals(currentUnit)){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_sameValueNotConverted",
                        new Object[]{schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            conversion = LabPLANETArray.addValueToArray1D(conversion, valueToConvert);
            return conversion;
        }    
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        String tableName = "units_of_measurement";
        String familyFieldNameDataBase = "measurement_family";                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);
             
        String[] fieldsToGet = new String[]{"name", familyFieldNameDataBase, "is_base", "factor_value", "offset_value"};
        Object[][] currentUnitInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"name"},  new Object[]{currentUnit}, fieldsToGet );
        Object[][] newUnitInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"name"},  new Object[]{newUnit}, fieldsToGet);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(currentUnitInfo[0][0].toString())){
            return conversion;            
        }        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newUnitInfo[0][0].toString())){
            return conversion;            
        }
        Integer currentUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);         
        Integer newUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);                
        if ((currentUnitFamilyFieldPosic==-1) || (newUnitFamilyFieldPosic==-1) ){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{familyFieldNameDataBase, Arrays.toString(fieldsToGet), schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion;            
        }                        
        if (!currentUnitInfo[0][currentUnitFamilyFieldPosic].toString().equalsIgnoreCase(newUnitInfo[0][currentUnitFamilyFieldPosic].toString())){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{currentUnit , currentUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            newUnit, newUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion; 
        }
        conversion[0]="LABPLANET_TRUE";
        return conversion;        
    }
    
    public Object[] convertValue( String schemaPrefix, BigDecimal valueToConvert, String currentUnit, String newUnit){
        
        Object[] unitsCompatible = twoUnitsInSameFamily(schemaPrefix, valueToConvert, currentUnit, newUnit);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(unitsCompatible[0].toString())){
            return unitsCompatible;}
        
        Object[] conversion = new Object[6];
        String tableName = "units_of_measurement";
        String familyFieldNameDataBase = "measurement_family";
        BigDecimal valueConverted = valueToConvert;
        
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);
             
        String[] fieldsToGet = new String[]{"name", familyFieldNameDataBase, "is_base", "factor_value", "offset_value"};
        Object[][] currentUnitInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"name"},  new Object[]{currentUnit}, fieldsToGet );
        Object[][] newUnitInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"name"},  new Object[]{newUnit}, fieldsToGet);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(currentUnitInfo[0][0].toString())){
            return conversion;            
        }        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newUnitInfo[0][0].toString())){
            return conversion;            
        }
        
        //if (currentUnit.equalsIgnoreCase(newUnit)){return valueToConvert;}
        
        Integer currentUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);         
        Integer newUnitFamilyFieldPosic = Arrays.asList(fieldsToGet).indexOf(familyFieldNameDataBase);                
        if ((currentUnitFamilyFieldPosic==-1) || (newUnitFamilyFieldPosic==-1) ){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{familyFieldNameDataBase, Arrays.toString(fieldsToGet), schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion;            
        }                        
        if (!currentUnitInfo[0][currentUnitFamilyFieldPosic].toString().equalsIgnoreCase(newUnitInfo[0][currentUnitFamilyFieldPosic].toString())){
            conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_methodError_familyFieldNotAddedToTheQuery",
                        new Object[]{currentUnit , currentUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            newUnit, newUnitInfo[0][currentUnitFamilyFieldPosic].toString(), 
                            schemaPrefix,  "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
            return conversion; 
        }
        BigDecimal currentUnitFactor = new BigDecimal(currentUnitInfo[0][3].toString());
        BigDecimal newUnitFactor = new BigDecimal(newUnitInfo[0][3].toString());
        BigDecimal currentUnitOffset = new BigDecimal(currentUnitInfo[0][4].toString());
        BigDecimal newUnitOffset = new BigDecimal(newUnitInfo[0][4].toString());

        newUnitFactor=newUnitFactor.divide(currentUnitFactor);
        valueConverted=valueConverted.multiply(newUnitFactor);
        newUnitOffset=newUnitOffset.add(currentUnitOffset.negate());
        valueConverted=valueConverted.add(newUnitOffset);
        
        //valueConverted.add(new BigDecimal(  - new BigDecimal(currentUnitInfo[0][4].toString()) ));
        //valueConverted = valueConverted * ((float)newUnitInfo[0][3] / (float)currentUnitInfo[0][3]);
        //valueConverted = valueConverted + ((float)newUnitInfo[0][4] - (float)currentUnitInfo[0][4]);
        //valueConverted = Float.valueOf(String.format("%.10f",valueConverted));
        
        conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, "UnitsOfMeasurement_convertedSuccesfully",
                        new Object[]{currentUnit , newUnitInfo, valueToConvert, valueConverted, schemaPrefix,
                             "valueToConvert: "+valueToConvert+", : currentUnit"+LPNulls.replaceNull(currentUnit)+", : newUnit"+LPNulls.replaceNull(newUnit)});
        conversion = LabPLANETArray.addValueToArray1D(conversion, valueConverted);
        conversion = LabPLANETArray.addValueToArray1D(conversion, newUnit);
        return conversion;
    }
/**
 * Get all units of measurement from the same given family from one specific schema/procedure and getting as many fields
 * as the ones specified in fieldsToRetrieve.
 * @param schemaPrefix - String - ProcedureName  
 * @param family - String - The given family for the uom to be got.
 * @param fieldsToRetrieve String[] - Fields from the table that should be added to the array returned.
 * @return Object[][] - a dynamic table containing the fields specified in fieldsToRetrieve, position[0][3] set to FALSE when cannot buuilt.
 */
    public Object[][] getAllUnitsPerFamily( String schemaPrefix, String family, String[] fieldsToRetrieve ){
       
        String tableName = "units_of_measurement";        
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);
        if (family==null){
            Object[] conversion = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UnitsOfMeasurement_FamilyArgumentMandatory",
                                    new Object[]{schemaPrefix});
            return LabPLANETArray.array1dTo2d(conversion, conversion.length);
        }
      
        Object[][] unitsList = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"measurement_family"},  new Object[]{family}, fieldsToRetrieve, new String[]{"factor_value", "offset_value"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(unitsList[0][0].toString())) return unitsList;            
        
        return unitsList;        
    }

    /**
     *
     * @param schemaPrefix
     * @param family
     * @return
     */
    public String getFamilyBaseUnitName( String schemaPrefix, String family){
        String tableName = "units_of_measurement";                
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);
       
        String baseUnitName="";
        
        Object[][] unitsList = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                 new String[]{"measurement_family", "is_base"},  new Object[]{family, true}, new String[]{"name"});        
        
        return unitsList[0][0].toString();            

    }
    
}
