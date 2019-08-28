/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.environmentalMonitoring;

import LabPLANET.utilities.LPArray;
import functionalJava.sampleStructure.DataSample;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DataProgramSample extends DataSample{
    
    public DataProgramSample() {
        super();
    }

    /**
     *
     * @param schemaPrefix
     * @param programTemplate
     * @param programTemplateVersion
     * @param fieldName
     * @param fieldValue
     * @param userName
     * @param userRole
     * @param programName
     * @param programLocation
     * @param appSessionId
     * @return
     */
    public Object[] logProgramSample(String schemaPrefix, String programTemplate, Integer programTemplateVersion, String[] fieldName, Object[] fieldValue, String userName, String userRole, String programName, String programLocation, Integer appSessionId) {
        Object[] newProjSample = new Object[0];
        try {
            DataSample ds = new DataSample();
            fieldName = LPArray.addValueToArray1D(fieldName, "program_name");
            fieldValue = LPArray.addValueToArray1D(fieldValue, programName);
            newProjSample = ds.logSample(schemaPrefix, programTemplate, programTemplateVersion, fieldName, fieldValue, userName, userRole, appSessionId);
            /*if (!newProjSample[3].equalsIgnoreCase(LPPlatform.LAB_FALSE)){
            String schemaDataNameProj = LPPlatform.SCHEMA_DATA;
            String schemaConfigNameProj = LPPlatform.SCHEMA_CONFIG;
            LPPlatform labPlat = new LPPlatform();
            schemaDataNameProj = labPlat.buildSchemaName(schemaPrefix, schemaDataNameProj);
            schemaConfigNameProj = labPlat.buildSchemaName(schemaPrefix, schemaConfigNameProj);
            newProjSample = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataNameProj, "project_sample",
            new String[]{"project"}, new Object[]{projectName},
            new String[]{"sample_id"}, new Object[]{Integer.parseInt(newProjSample[newProjSample.length-1])});
            }*/
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newProjSample;
    }
    
}
