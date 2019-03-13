/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import databases.Rdbms;
import functionalJava.sop.Sop;
import functionalJava.sop.UserSop;
import functionalJava.user.Role;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import LabPLANET.utilities.LPPlatform;
/**
 *
 * @author Administrator
 */
public class RequirementDeployment {

    String classVersion = "0.1";

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "procedure";    

    String schemaRequirements = "requirements";
    
    String ermessage = "";
    
    // Originally 1196 lines
                                                                    //, String functionalArea, String values

    /**
     *
     * @param rdbm
     * @param procedure
     * @param procVersion
     * @return
     * @throws SQLException
     * @throws IOException
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public String _newRequirement ( String procedure, Integer procVersion) throws SQLException, IOException {    
       
        Integer queryInsertNum=0;
        FileWriter fw = null;  
        Writer wr = null;
return "";
    }
/*    
        String newEntry = d+" Starting requirements deployment..."+d;
        try {
            requirementsLogEntry(methodName, newEntry,null);
        } catch (IOException ex) {
            Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Integer pVersion = procVersion;

        Object[][] procedureInfo = Rdbms.getRecordFieldsByFilter("requirements", "procedure", new String[]{"name", "version"}, new Object[]{procedure, pVersion}, new String[]{"schema_prefix", "name", "version", "name", "version"});
        
        if (procedureInfo[0][3].toString().equalsIgnoreCase("FALSE")){
            return "ERROR: Error getting schema from procedure record. Error: "+procedureInfo[0][5].toString();
        }
        String schemaPrefix = (String) procedureInfo[0][0];   

            d = new Date();
        newEntry = d+" createDataBaseSchemas. Begin" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) { Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}            
        createDataBaseSchemas(schemaPrefix);        
            d = new Date();
        newEntry = d+" createDataBaseSchemas. End" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) { Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}            

            d = new Date();
        newEntry = d+" createDataBaseTable. Begin" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) { Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}            
        createDataBaseSchemaTable(schemaPrefix, procedure, procVersion);        
            d = new Date();
        newEntry = d+" createDataBaseTable. End" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) { Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}            
            
        if (1==1){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            return "Ended by 1==1 in line "+String.valueOf(elements[1].getLineNumber())+"of method "+elements[1].getClassName() + "." + elements[1].getMethodName();
        }
            d = new Date();
        newEntry = d+" addProcRolesAndPrivileges. Begin" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) { Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}            
        addProcRolesAndPrivileges(procedure, pVersion); 
            d = new Date();
        newEntry = d+" addProcRolesAndPrivileges. End" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}
        
            d = new Date();
        newEntry = d+" deploymentNavigatorNavId. Begin" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        
        deploymentNavigatorNavId(procedure, pVersion);
            d = new Date();
        newEntry = d+" deploymentNavigatorNavId. End" + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        

            d = new Date();
        newEntry = d+" add SOP. Begin..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}              
        addSop(procedure, pVersion, schemaPrefix+"-config");
            d = new Date();
        newEntry = d+" add SOP. End..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        

            d = new Date();
        newEntry = d+" RequirementConfigObjects. Begin..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        
        getConfigObject(procedure, pVersion);
            d = new Date();
        newEntry = d+" RequirementConfigObjects. End..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        

            d = new Date();
        newEntry = d+" adding UserSOP. Begin..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                
        addUserSop(procedure, pVersion,schemaPrefix);
            d = new Date();
        newEntry = d+" adding UserSOP. End..." + d;
            try {requirementsLogEntry(methodName, newEntry,null);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        
        
        ResourceBundle.clearCache();
        fw.close();        
        
        String ermessage = "Done!";
        return ermessage;
    }
   */ 

    /**
     *
     * @param procName
     * @param procVersion
     */
 
    public void _deploymentNavigatorNavId ( String procName, Integer procVersion){                       
        Integer queryInsertNum=0;
        Integer rootNode = 0;
        Integer newBranch;
        String navCode = "";
        String procCode = "";
        String privId = "";
    }
/*        String codeName = "OIL-PL1";

        Object[][] procUserReqBranchesInfo = Rdbms.getRecordFieldsByFilter(schemaRequirements, "procedure_user_requirements", 
                                                new String[]{"procedure", "version", "branch_need", "code is not null", "active", "in_scope", "in_system"}, 
                                                new Object [] {procName, procVersion, true, true, true, true}, 
                                                new String[]{"code", "name", "roles", "widget_action_priv_name", "sop_name", "sop_section"});
        try {
            Integer contProcUserReqBranches = procUserReqBranchesInfo.length;
            String newEntry = " query returns " + (contProcUserReqBranches+1) + " records.";
            try {requirementsLogEntry(methodName, newEntry,1);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

            // Create the root node for the procedure being deployed.    
            if(contProcUserReqBranches>0){
                Object[][] procNavInfo = Rdbms.getRecordFieldsByFilter(schemaRequirements, "procedure", 
                                                    new String[]{"name", "version"}, 
                                                    new Object [] {procName, procVersion}, 
                                                    new String[]{"navigation_node_name", "navigation_icon_name", "name", "version"});                    
                navCode = (String) procNavInfo[0][0];
                String navRootTagValue = (String) procNavInfo[0][0];
                privId = procName + "_" + procUserReqBranchesInfo[0][3].toString();                      

                rootNode = createNav(navCode, rdbm, privId, 0, true, procName, procVersion, navCode, "", "");      
                Parameter param = new Parameter();
                String logEntry = param.addTagInPropertiesFile("usernav", "nav_id_"+String.valueOf(rootNode), navRootTagValue);
                requirementsLogEntry(methodName, logEntry,5);
            }
            ResultSet res = null;   
            for(int icontProcUserReqBranches=0;icontProcUserReqBranches<contProcUserReqBranches;icontProcUserReqBranches++){ //Create all branches belonging to the root note created above.
                res.absolute(icontProcUserReqBranches+1);

                navCode = (String) procUserReqBranchesInfo[icontProcUserReqBranches+1][0];
                String navTagValue = (String) procUserReqBranchesInfo[icontProcUserReqBranches+1][1];
                
                procCode = navCode;

                navCode = navCode.replace("-","");
                navCode = procName + "_" + navCode;
                navCode = navCode.toLowerCase();
                privId = procName + "_" + procUserReqBranchesInfo[icontProcUserReqBranches+1][3].toString();

                String sopName = (String) procUserReqBranchesInfo[icontProcUserReqBranches+1][4];
                String sopSection = (String) procUserReqBranchesInfo[icontProcUserReqBranches+1][5]; 

                Parameter param = new Parameter();
                newBranch = createNav(navCode, rdbm, privId, rootNode, false, procName, procVersion, procCode, sopName, sopSection);
                String logEntry = param.addTagInPropertiesFile("usernav", "nav_id_"+String.valueOf(newBranch), navTagValue);
                requirementsLogEntry("nav_id_"+String.valueOf(newBranch), logEntry,5);

                logEntry = param.addTagInPropertiesFile("userview", navCode, navTagValue);
                requirementsLogEntry(navCode, logEntry,5);                    

                deploymentNavigatorNavTabId (procName, procVersion, newBranch, procCode);    
            }                     
        }catch(SQLException er){ermessage = er.getErrorCode()+er.getLocalizedMessage()+er.getCause();
        }catch(Exception ex)   {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}
    }
*/

    /**
     *
     * @param procName
     * @param procVersion
     * @param navId
     * @param procCode
     */

    public void _deploymentNavigatorNavTabId ( String procName, Integer procVersion, Integer navId, String procCode){            
        Integer queryInsertNum=0;
        String navCode = "";
        Integer navTabId = 0;
        String catw_widget_name = "";
        Integer catw_module_id = 0;
        Integer catw_module_version = 0;
        Integer catw_module_revision = 0;
        String widgetsAdded = "";
        String subTabsAdded = "";
        String prurs_widget_fields = "";
        String prurs_name = "";
        String prurs_widget_desc = "";
        String prurs_schema_name = "";
        String prurs_table_name = "";
    }    
 /*       
        try{
            String query = "SELECT catw.module_id catw_module_id, catw.module_version catw_module_version, catw.module_revision catw_module_revision, "
                        + "        catw.widget_name catw_widget_name, prurs.parent_code prurs_parent_code, prurs.widget_fields prurs_widget_fields, "
                        + "        prurs.name prurs_name, prurs.widget_desc prurs_widget_desc, prurs.schema_name prurs_schema_name, prurs.table_name prurs_table_name, " 
                        + "        prurs.sop_name sop_name, prurs.sop_section sop_section"
                        + "   FROM requirements.procedure proc, requirements.procedure_user_requirements prurs, requirements.module_catalog cat, requirements.module_catalog_widget catw "
                        + "  where proc.name=? and proc.version=? "
                        + "    and prurs.parent_code is not null and prurs.code is not null "
                        + "    and prurs.active=? and prurs.in_scope=? and prurs.in_system=? "                    
                        + "    and prurs.procedure=proc.name and prurs.version = proc.version "
                        + "    and proc.module_catalog = cat.name and proc.module_version = cat.module_version "
                        + "    and catw.module_id = cat.module_id and catw.module_version = cat.module_version and catw.module_revision = cat.module_revision "
                        //+ "    and (prurs.code = ?) "
                        + "    and (prurs.parent_code = ? or prurs.code = ?) "
                        + "    and catw.widget_name = prurs.widget || '-BASE' "
                        + "  order by prurs.widget ";            

            ResultSet res = Rdbms.prepRdQuery(query, new Object [] {procName, procVersion, true, true, true, procCode, procCode}); //Ahora no toma los hijos.
            res.last();
            Integer i;
            i = res.getRow();
                         
            for(int j=0;j<i;j++){
                res.absolute(j+1);

                prurs_name = res.getString("prurs_name");                 
                catw_widget_name = res.getString("catw_widget_name");
                
                prurs_widget_desc = res.getString("prurs_widget_desc"); 
                
                if (prurs_widget_desc==null){prurs_widget_desc=prurs_name;}
                
                if (prurs_widget_desc==null){prurs_widget_desc=catw_widget_name;}
                
                String catw_widget_nameBase = catw_widget_name;
                Integer hasBase = catw_widget_nameBase.indexOf("-BASE");                 
                if(hasBase==-1){
                    catw_widget_name = catw_widget_name + "-BASE";}
                catw_module_id = res.getInt("catw_module_id");    
                catw_module_version = res.getInt("catw_module_version");    
                catw_module_revision = res.getInt("catw_module_revision");    
                prurs_widget_fields = res.getString("prurs_widget_fields");    
                prurs_schema_name = res.getString("prurs_schema_name");
                prurs_table_name = res.getString("prurs_table_name");
                
                String sopName = res.getString("sop_name");
                String sopSection = res.getString("sop_section");
                
                String navSubTabCode = procCode; //res.getString("prurs_code");

                navCode = procName + "_" + navSubTabCode + "_" + catw_widget_name;
                navCode = navCode.replace("-","");
                navCode = navCode.toLowerCase();

                if(!subTabsAdded.contains(navCode)){        
                    subTabsAdded = subTabsAdded + navCode + "|";
                    navTabId = createNavTab(navCode, rdbm, navId, "true", procName, procVersion, procCode, sopName, sopSection);
                    Parameter param = new Parameter();
                    String logEntry = param.addTagInPropertiesFile("userview", navCode, prurs_widget_desc); 
                    requirementsLogEntry(navCode, logEntry,5);                    
                }else {navTabId = 0;}                
                
                if(navTabId>0){    
                    // Build the Widget-BASE part. Begin
                    Object[][] resComp = Rdbms.getRecordFieldsByFilter(schemaRequirements, "module_catalog_widget", 
                                    new String[]{"module_id", "module_version", "module_revision", "widget_name"}, 
                                    new Object [] {catw_module_id, catw_module_version, catw_module_revision, catw_widget_nameBase}, 
                                    new String[]{"region", "xtype", "header", "footer", "preload", "tbar", "bbar", "item", "sql", "item_json", "norder", "description", "close_header"});
                    Boolean formCreated = createNavTabComp(navId, navTabId, procName, procVersion, procCode, catw_widget_name, catw_widget_nameBase, catw_module_id, catw_module_version, catw_module_revision, prurs_widget_fields, prurs_schema_name, prurs_table_name, sopName, sopSection );
                    if (prurs_widget_fields!=null){
                        String[] prurs_widget_fieldsArr = prurs_widget_fields.split(",");
                        for (String fd: prurs_widget_fieldsArr)
                        {
                            String fieldCode = procName + "_" + navSubTabCode + "_" + catw_widget_name + "_" + fd;
                            fieldCode = fieldCode.replace("-","");
                            fieldCode = fieldCode.toLowerCase();

                            Parameter param = new Parameter();
                            String logEntry = param.addTagInPropertiesFile("userview", fieldCode, fd);
                            requirementsLogEntry(fieldCode, logEntry,5);
                        }
                    }    
                }
            }                            
        }catch(SQLException er){String ermessage = er.getErrorCode()+er.getLocalizedMessage()+er.getCause();
        } catch (Exception ex) {            
                Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
            }  
        
    } */

    /**
     *
     * @param nodename
     * @param privilege_id
     * @param fathernode
     * @param haschildren
     * @param procedure
     * @param version
     * @param code
     * @param sopName
     * @param sopSection
     * @return
     * @throws SQLException
     */
    public Integer createNav(String nodename,  String privilege_id, Integer fathernode, Boolean haschildren, String procedure, Integer version, String code, String sopName, String sopSection) throws SQLException   {            
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String methodName = elements[1].getMethodName();

        Integer created = 0;
        Integer numr = 0;    
        String newEntry = "";        
        Role rol = new Role();

        if (privilege_id.equalsIgnoreCase(procedure+"_null")){
            Object[] diagnoses = Rdbms.existsRecord(schemaConfigName, "privilege", 
                    new String[]{"privilege_id"}, new Object[]{procedure});
            if ( !LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                diagnoses = rol.createPrivilege(procedure);
                try {
                    requirementsLogEntry(methodName, (String) diagnoses[6], 3);
                } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                    
            }
            diagnoses = rol.addPrivilegeToRole(procedure, "ALL", procedure);
            try {
                requirementsLogEntry(methodName, (String) diagnoses[6], 3);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                    
            
            privilege_id = procedure;
        }
        privilege_id = privilege_id.trim();
        privilege_id = privilege_id.replace(" ", "");

        Object[] diagnoses = Rdbms.insertRecordInTable("config", "nav", 
                                    new String[]{"privilege_id", "has_children", "show_in_menu", "father_nav_id", "procedure", "proc_version", "proc_code", "sop_name", "sop_section"}, 
                                    new Object[]{privilege_id, haschildren, true, fathernode, procedure, version, code, sopName, sopSection});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){
            newEntry = " ***Error creating navId for the node name " + nodename + ". Error: "+ diagnoses[4] + " / " + diagnoses[5];
            numr = 0;
        }
        numr = Integer.valueOf(diagnoses[diagnoses.length-1].toString());
                
        //load record from template and then insert replacing required code
        if (numr>0){
            Integer pk=0;

            if (!haschildren){
                Rdbms.updateRecordFieldsByFilter("config", "nav", 
                        new String[]{"header", "close_header", "footer"}, 
                        new Object []{"{id:'nav-"+numr+"', xtype:'tabpanel', title:gTr('paramView','"+nodename+"'), closable:true, activeTab:0, items:[","]", "}"}, 
                        new String[]{"nav_id"}, new Object []{numr});
            }
            created = numr;                
        }

        if (numr>0){newEntry = " created navId " + numr + " for the node name " + nodename;}

        try {requirementsLogEntry(methodName, newEntry,2);
        } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}        
        return created;
    }

    private Integer createNavTab(String subtabname,  Integer navId, String glypname, String procName, Integer procVersion, String procCode, String sopName, String sopSection) throws SQLException     {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String methodName = elements[1].getMethodName();
        
        Boolean created =false;
        Integer numr = 0;
        String newEntry = "";
        
        Object[] diagnoses = Rdbms.insertRecordInTable("config", "nav_tab", 
                                    new String[]{"nav_id", "has_child", "procedure", "proc_version", "proc_code", "sop_name", "sop_section"}, 
                                    new Object[]{navId, false, procName, procVersion, procCode, sopName, sopSection});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){
            newEntry = " ***Error creating nav Tab for the proc Code " + procVersion + ". Error: "+ diagnoses[4] + " / " + diagnoses[5];
            try {requirementsLogEntry(methodName, newEntry,3);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}
            return 0;
        }    
        numr = Integer.parseInt(diagnoses[6].toString());
        if (numr>0){
            newEntry = " created Nav Tab Id " + numr + " for the proc Code " + procCode;
            try {requirementsLogEntry(methodName, newEntry,3);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

            diagnoses = Rdbms.updateRecordFieldsByFilter("config", "nav_tab", 
                    new String[]{"header", "close_header", "footer"}, 
                    new Object []{"{title:gTr('paramView', '"+subtabname+"'), xtype:'container', id:'nav-"+navId+"_"+numr+"', glyph:'"+glypname+"', closable:true, activeTab:0, bodyPadding:5,fieldDefaults:{labelAlign:'left',labelWidth:120,anchor:'100%'},items:[", "]", "}"}, 
                    new String[]{"nav_tab_id"}, new Object []{numr});            
        }
        return numr;
    }
    
    private Boolean _createNavTabComp( Integer navId, Integer navTabId, String procName, Integer procVersion, String procCode, String catw_widget_name, String catw_widget_nameBase, Integer catw_module_id, Integer catw_module_version, Integer catw_module_revision, String prurs_widget_fields, String prurs_schema_name, String prurs_table_name, String sopName, String sopSection ) throws SQLException    {   
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String methodName = elements[1].getMethodName();

        Boolean created =false;    
        String dynNavTabCompId = "";
        Integer pk=0;
        String ButtonsAdhoc = "";
        String currCatwTbar = "";
        return false;
    }        
/*    
        Object[][] resComp = Rdbms.getRecordFieldsByFilter(schemaRequirements, "module_catalog_widget", 
                new String[]{"module_id", "module_version", "module_revision", "widget_name"}, 
                new Object [] {catw_module_id, catw_module_version, catw_module_revision, catw_widget_nameBase}, 
                new String[]{"region", "xtype", "header", "footer", "preload", "tbar", "bbar", "item", "sql", "item_json", "norder", "description", "close_header"});
        if (resComp[0][3].toString().equalsIgnoreCase("FALSE")){return false;}        
            String region = (String) resComp[0][0];        String xtype = (String) resComp[0][1];
            String header = (String) resComp[0][2];        String footer = (String) resComp[0][3];
            String preload = (String) resComp[0][4];       String tbar = (String) resComp[0][5];
            String bbar = (String) resComp[0][6];          String item = (String) resComp[0][7];
            String sql = (String) resComp[0][8];           String itemjson = (String) resComp[0][9];
            Integer norder = 0;
            String description = (String) resComp[0][11];  String closeHeader = (String) resComp[0][12];
            String[] items = null;
            String tableName = "";
            String[] fieldsArr = null;
            Integer navTabCompId = 0;

            Object[] diagnoses = Rdbms.insertRecordInTable("config", "nav_tab_comp", 
                        new String[]{"nav_id", "nav_tab_id", "region", "xtype", "sql", "header", "footer", "close_header", "procedure", "proc_version", "proc_code", "widget_name", "sop_name", "sop_section"}, 
                        new Object[]{navId, navTabId, region, xtype, sql, header, footer, closeHeader, procName, procVersion, procCode, catw_widget_nameBase, sopName, sopSection });
            if (diagnoses[3].toString().equalsIgnoreCase("FALSE")){navTabCompId=0;}
            else{navTabCompId = (Integer) diagnoses[6];}

            if (navTabCompId>0){
                String newEntry = " created Nav Tab Comp Id " + navTabCompId + " for the proc Code " + procCode;
                try {requirementsLogEntry(methodName, newEntry,4);
                } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                

                tableName = sql; //prurs_schema_name + "." + prurs_table_name;

                if (tableName==null){fieldsArr = null;}
                else{               
                    if (prurs_widget_fields==null){fieldsArr = null;}   
                    else{fieldsArr = prurs_widget_fields.split(",");}
                }
                String fieldPrefix = procName.toLowerCase()+ "_" + procCode.toLowerCase().replace("-", "")+ "_" + catw_widget_nameBase.toLowerCase().replace("-", "");
                LabPLANETJson labJson = new LabPLANETJson();
                
                if (fieldsArr==null){items = labJson.getJsonArrayFields(tableName, null, fieldPrefix);}
                else{items = labJson.getJsonArrayFields(tableName, fieldsArr, fieldPrefix);}                   

                Rdbms.updateRecordFieldsByFilter("config", "nav_tab_comp", 
                        new String[]{"item", "sql"}, new Object[]{items, tableName}, 
                        new String[]{"nav_tab_comp_id"}, new Object[]{navTabCompId});

                dynNavTabCompId = Integer.toString(navId) + "_" + Integer.toString(navTabId) + "_" + Integer.toString(navTabCompId);

                // Get the custom parts assigned to the widget. Begin
                String query = "";
                query = "select distinct prurs.widget_action, catw.tbar catw_tbar, prurs.widget, catw.widget_name, catw.widget_action "
                    + "    from requirements.procedure_user_requirements prurs, requirements.module_catalog_widget catw "
                    + "   where prurs.procedure = ? and prurs.version = ? "
                    + "     and prurs.active= ? and prurs.In_system= ? and prurs.in_scope= ? "
                    + "     and prurs.widget = ? "
                    + "     and prurs.parent_code = ? "
                    + "     and catw.module_id = ? and catw.module_version= ? and catw.module_revision= ? "
                //    + "     and catw.widget_name = ? "
                    + "     and prurs.widget = catw.widget_name "
                    + "     and prurs.widget_action = catw.widget_action "
                    + "     and catw.tbar is not null ";
                catw_widget_name = catw_widget_name.replace("-BASE", "");

                ResultSet resButAdhoc = Rdbms.prepRdQuery(query, new Object [] {procName, procVersion, true, true, true, catw_widget_name, procCode, catw_module_id, catw_module_version, catw_module_revision, catw_widget_nameBase});  

                resButAdhoc.last();            

                Integer resButAdhocCont = resButAdhoc.getRow();

                for(int j=0;j<resButAdhocCont;j++){
                    resButAdhoc.absolute(j+1);  
                    currCatwTbar = resButAdhoc.getString("catw_tbar");
                    ButtonsAdhoc = ButtonsAdhoc + currCatwTbar;    
        //           query = "select * "
        //               + = "  from requirements.module_catalog_widget" 
        //               + = " where module_id =? and module_version=? and module_revision=?" 
       //                + = "   and widget_name = ?" 
        //               + = "   and widget_action is not null" 
        //               + = "   and widget_action = 'new'" 
                }        

                ButtonsAdhoc = "";

                tbar = tbar.replace("#BUTTONS,", ButtonsAdhoc);
                tbar = tbar.replace("#NAVTABCOMPID", dynNavTabCompId);
                bbar = bbar.replace("#NAVTABCOMPID", dynNavTabCompId);

                String upn = "update config.nav_tab_comp set bbar = ?" //, bbar = ?"
                           + " where nav_tab_comp_id = ?";

               // pk = Rdbms.prepUpQuery(upn, new Object []{bbar, navTabCompId});

                if (pk > 0){
                created = true;    
                }    
            }     
        return created;
    }
*/
    void requirementsLogEntry(String FunctionName, String entryValue, Integer numTabs) throws IOException{

        String newLogFileName = "Requirements.txt";        
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");        
        String logDir = prop.getString("logDirPath");

        Integer queryInsertNum=0;
        FileWriter fw = null;  
        Writer wr = null;
        
        String logFile = logDir + "/" + newLogFileName;
        logFile = logFile.replace("/", "\\");

        fw = new FileWriter(logFile, true);                  
        String newEntry = "";
        if (numTabs!=null){
            for (Integer i=0;i<numTabs;i++){
                newEntry = newEntry + "     ";
            }
        }
        newEntry = newEntry + FunctionName + ": " + entryValue + "\n";            
        fw.append(newEntry);

        fw.close();        
    }    

    /**
     *
     * @param procName
     * @param procVersion
     * @throws SQLException
     */
    public void addProcRolesAndPrivileges( String procName, Integer procVersion) throws SQLException{
    
        String methodName = "addProcRolesAndPrivileges";
        Role rol = new Role();

        Object[][] procUserReqInfo = Rdbms.getRecordFieldsByFilter("requirements", "procedure_user_requirements", 
                new String[]{"procedure", "version", "active", "in_scope", "in_system"}, new Object [] {procName, procVersion, true, true, true}, 
                new String[]{"code, name", "roles", "widget_action_priv_name", "order_number", "id"},
                new String[]{"order_number", "id"});

        Integer contProcUserReqInfo = procUserReqInfo.length;

        String newEntry = " query returns " + contProcUserReqInfo+1 + " records.";
        try {
            requirementsLogEntry(methodName, newEntry,1);
        } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

        // Create the root node for the procedure being deployed.    
        for (Integer icontProcUserReqInfo=0; icontProcUserReqInfo<contProcUserReqInfo;icontProcUserReqInfo++){

            String roles = (String) procUserReqInfo[icontProcUserReqInfo][2];
            String privName = (String) procUserReqInfo[icontProcUserReqInfo][3];

            newEntry = " Parsing record " + (icontProcUserReqInfo+1) + "/" + contProcUserReqInfo + ": Roles=" + roles + " // Privileges: " + privName;
            try {
                requirementsLogEntry(methodName, newEntry,2);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

            if (roles!=null){
                out.println(roles);
                String[] role = roles.split(",");
                for (String r: role){
                    r = procName + "_" + r;
                    r = r.replace(" ", "").replace("\n", "");

                    Object[] diagnoses = Rdbms.existsRecord(schemaConfigName, "role", 
                            new String[]{"role_id"}, new Object[]{r});
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                  
                        diagnoses = rol.createRole(r);
                        try {
                            requirementsLogEntry(methodName, diagnoses[6].toString(), 3);
                        } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                    
                    }
                }
            }
            if (privName!=null){
                out.println(roles);
                String[] priv = privName.split(",");
                for (String pr: priv){
                    pr = procName + "_" + pr;
                    pr = pr.replace(" ", "").replace("\n", "");
                    Object[] diagnoses = Rdbms.existsRecord("config", "privilege", 
                            new String[]{"privilege_id"}, new Object[]{pr});
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        diagnoses = rol.createPrivilege(pr);
                        try {
                            requirementsLogEntry(methodName, diagnoses[6].toString(), 3);
                        } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                    
                        
                    }
                    roles = (String) procUserReqInfo[icontProcUserReqInfo][2];
                    if (roles!=null){
                        out.println(roles);
                        String[] role = roles.split(",");
                        for (String r: role){
                            r = procName + "_" + r;
                            r = r.replace(" ", "").replace("\n", "");
                            if (r.toUpperCase().contains("ALL")){
                                //r = r;
                            }
                            diagnoses = Rdbms.existsRecord("config", "role_privilege", 
                                    new String[]{"privilege_id"}, new Object[]{pr + "," + r} );
                            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                      

                                diagnoses = rol.addPrivilegeToRole(pr, r, procName);
                                try {
                                    requirementsLogEntry(methodName, diagnoses[6].toString(), 3);
                                } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}                    

                            }
                        }
                    }
                }
            }        
        }
    }     

    private void addSop( String procName, Integer procVersion, String schemaName) throws SQLException{

        String methodName = "addSop";
        Sop sop = new Sop();
        
        String sopList = "";

        Object[][] procUseReqInfo = Rdbms.getRecordFieldsByFilter("requirements", "procedure_user_requirements", 
                                            new String[]{"procedure", "version", "code is not null", "active", "in_scope", "in_system"}, 
                                            new Object[]{procName, procVersion, "", true, true, true}, 
                                            new String[]{"code", "name", "sop_name", "sop_section", "widget_action_priv_name", "schema_name"}, 
                                            new String[]{"order_number", "id"});

        Integer contProcUseReqInfo = procUseReqInfo.length;       

        String newEntry = " query returns " + contProcUseReqInfo+1 + " records.";
        try {requirementsLogEntry(methodName, newEntry,1);
        } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

        out.println(contProcUseReqInfo.toString());
        // Create the root node for the procedure being deployed.    

        for (Integer icontProcUseReqInfo=0; icontProcUseReqInfo<contProcUseReqInfo;icontProcUseReqInfo++){

            String sopName = (String) procUseReqInfo[icontProcUseReqInfo][2];
            String sopSectionName = (String) procUseReqInfo[icontProcUseReqInfo][3];
            
            newEntry = " Parsing record " + (icontProcUseReqInfo+1) + "/" + contProcUseReqInfo + ": SOP=" + sopName;
            try {requirementsLogEntry(methodName, newEntry,2);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

            if (sopName!=null){
                out.println(sopName);
                String[] sopNames = sopName.split(",");
                for (String sp: sopNames){
                    if (!sopList.contains(sp)){
                        Object[] diagnoses = Rdbms.existsRecord(schemaName, "sop_meta_data", 
                                new String[]{"sop_name"}, new Object[]{sp});
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                            diagnoses = sop.createSop(schemaName, sp);
                            sopList = sopList + sp + "|";                             
                            }                        
                    }
                    if ( (sopSectionName!=null) && (!sopList.contains(sopSectionName)) ){
                        Object[] diagnoses = sop.createSop(schemaName, sp+"-"+sopSectionName);
                         sopList = sopList + sp+"-"+sopSectionName + "|";                            
                         diagnoses = sop.updateSop(schemaName, sp, "has_child", "true", "boolean");
                         diagnoses = sop.updateSop(schemaName, sopSectionName, "parent_sop", sp, "text");
                         sopList = sopList + sp+"-"+sopSectionName + "|";                         
                    }    
                }
            }
        }
    }         

    private void addUserSop( String procName, Integer procVersion, String schemaName) throws SQLException{

        String methodName = "addUserSop";
        String newEntry = "";
        String sopList = "";
        
        Object[][] procUserReqInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                        new String[]{"procedure", "version", "code is not null", "active", "in_scope", "in_system"}, 
                        new Object[]{procName, procVersion, "", true, true, true}, 
                        new String[]{"code", "name", "sop_name", "sop_section", "roles", "schema_name schema_name"}, 
                        new String[]{"order_number", "id"});

        Integer contProcUserReqInfo = procUserReqInfo.length;       

        newEntry = " query returns " + contProcUserReqInfo++ + " records.";
        contProcUserReqInfo--;
        try {
            requirementsLogEntry(methodName, newEntry,1);
        } catch (IOException ex) {
            Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Integer icontProcUserReqInfo=0; icontProcUserReqInfo<contProcUserReqInfo;icontProcUserReqInfo++){            
            String sopName = (String) procUserReqInfo[icontProcUserReqInfo][2]; 
            String sopSectionName = (String) procUserReqInfo[icontProcUserReqInfo][3];
            String role = (String) procUserReqInfo[icontProcUserReqInfo][4];
            
            newEntry = " Parsing record " + (icontProcUserReqInfo+1) + "/" + contProcUserReqInfo + ": Sop=" + sopName + " Section=" + sopSectionName + " Role=" + role;
            try {requirementsLogEntry(methodName, newEntry,2);
            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

            if (sopName!=null){                
                String[] sopNames = sopName.split(",");
                for (String sp: sopNames){
                    if (sopSectionName!=null){sp = sp+"-"+sopSectionName;}  
                    Object[] diagnoses = Rdbms.existsRecord(schemaName+"-config", "sop_meta_data", 
                            new String[]{"sop_name"}, new Object[]{sp});
                    if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) && (role!=null) ){                  
                        String[] roles = role.split(",");
                        for (String r: roles){         
                            Object[][] userProfileInfo = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                                            new String[]{"role_id"}, 
                                            new Object[]{procName+"_"+r}, 
                                            new String[]{"user_info_id", "user_info_id", "user_info_id", "user_info_id"});

                            Integer contUser = userProfileInfo.length;     

                            newEntry = "Found " + contUser + " users having assigned the role "+procName+"_"+r;
                            try {requirementsLogEntry(methodName, newEntry,3);
                            } catch (IOException ex) {Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

                            for (Integer icontUser=0;icontUser<contUser;icontUser++){
                                UserSop usSop=new UserSop();
                                String userInfoId = (String) userProfileInfo[icontUser][0];

                                Object[] newSopUser = usSop.addSopToUserByName(schemaName+"-data", userInfoId, sopName);

                                newEntry = icontUser+"/"+contUser+"  "+newSopUser[newSopUser.length-1].toString();
                                try {requirementsLogEntry(methodName, newEntry,4);
                                } catch (IOException ex) {
                                    Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);}

                                sopList = sopList + sp + "|";            
                            }    
                        }    
                    }                                                                    
                }
            }
        }
    }         

    private void _createDataBaseSchemas( String schemaNamePrefix){
        // Not implemented yet
    }
/*
        String methodName = "createDataBaseSchemas";
        String newEntry = "";
        String[] schemaNames = new String[0];
        
        schemaNames = LabPLANETArray.addValueToArray1D(schemaNames, "config");
        schemaNames = LabPLANETArray.addValueToArray1D(schemaNames, "data");        
        schemaNames = LabPLANETArray.addValueToArray1D(schemaNames, "data-audit"); 

        for (String fn:schemaNames){
            String configSchemaName = schemaNamePrefix+"-"+fn;
            try {
                requirementsLogEntry(methodName, configSchemaName,2);
            } catch (IOException ex) {
                Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            configSchemaName = labPlat.buildSchemaName(configSchemaName, fn);
            String configSchemaScript = "CREATE SCHEMA "+configSchemaName+"  AUTHORIZATION postgres;";     
            try {
                Rdbms.prepUpQuery(configSchemaScript, null);
            } catch (SQLException ex) {
                Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
*/
    private void _createDataBaseSchemaTable( String schemaNamePrefix, String procedure, Integer procVersion){
        // Not implemented yet
    }
/*    
        String methodName = "createDataBaseSchemaTable";
        String newEntry = "";
        tableName = "procedure_db_structure_table";   
        String fieldTableName = "procedure_db_structure_table_fields";   
        
        Object[][] procTableStructure = Rdbms.getRecordFieldsByFilter("requirements", tableName, 
                new String[]{"procedure", "version", "active"}, new Object[]{procedure, procVersion, true}, 
                new String[]{"schema_name", "table_name", "script", "mandatory"});
        for (Integer iTableCont=0; iTableCont<procTableStructure.length;iTableCont++){
            String currSchemaName = (String) procTableStructure[iTableCont][0];
            String currTableName = (String) procTableStructure[iTableCont][1];
            String currTableScript = (String) procTableStructure[iTableCont][2];
            
            String schemaName = labPlat.buildSchemaName(schemaNamePrefix, currSchemaName);
            
            try {
                requirementsLogEntry(methodName, currTableName,2);
            } catch (IOException ex) {
                Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex);
            }
            currTableScript = currTableScript.replace("<schemaName>", schemaName);
            currTableScript = currTableScript.replace("<tableName>", currTableName);
            
            if (currTableScript.toUpperCase().contains("<CUSTOMFIELDS>")){
                Object[][] procTableFieldStructure = Rdbms.getRecordFieldsByFilter("requirements", fieldTableName, 
                        new String[]{"procedure", "version", "active", "schema_name", "table_name"}, new Object[]{procedure, procVersion, true, currSchemaName, currTableName}, 
                        new String[]{"field_name", "mandatory", "field_type", "field_type", "field_size", "nullable"});
                if (procTableFieldStructure[0][3].toString().equalsIgnoreCase("FALSE")){
                    currTableScript = currTableScript.replace("<customFields>", "");                    
                } else {
                    String customFieldsScript = "";
                    for (Integer iFieldCont=0; iFieldCont<procTableFieldStructure.length;iFieldCont++){
                        String currFieldName = (String) procTableFieldStructure[iFieldCont][0];
                        String currFieldType = (String) procTableFieldStructure[iFieldCont][2];
                        String currFieldSize = (String) procTableFieldStructure[iFieldCont][4];
                        Boolean currFieldNullable = (Boolean) procTableFieldStructure[iFieldCont][5];
                        customFieldsScript = customFieldsScript + ", " + currFieldName + " " + currFieldType;
                        // for char with size if (!currFieldNullable){customFieldsScript = customFieldsScript + " NOT NULL";}
                        if (!currFieldNullable){customFieldsScript = customFieldsScript + " NOT NULL";}
                    }
                    currTableScript = currTableScript.replace("<customFields>", customFieldsScript);                                        
                }        
            }            
            try {
                Rdbms.prepUpQuery(currTableScript, null);
                try {
                    requirementsLogEntry(methodName, "Created table "+currTableName+" in schema "+currSchemaName+".  Script: "+currTableScript,2);
                } catch (IOException ex2) {Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex2);}
                
            } catch (SQLException ex) {
                Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    requirementsLogEntry(methodName, "Error " + ex.getMessage() + " trying to create the table "+currTableName+".  Script: "+currTableScript,2);
                } catch (IOException ex2) {Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex2);}                
            }            
            try {
                requirementsLogEntry(methodName, currTableScript,3);
            } catch (IOException ex) {
                Logger.getLogger(RequirementDeployment.class.getName()).log(Level.SEVERE, null, ex);
            }
        schemaName = "";    
        }
    }
*/

    /**
     *
     * @param procedure
     * @param pVersion
     */

    public void _getConfigObject( String procedure, Integer pVersion){
        // Not implemented yet
    }
/*
        String methodName = "RequirementConfigObjects-ProcessRequest";

        Integer id =0;           
        Requirement req = new Requirement();

        Object[][] procConfigObjInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                        new String[]{"procedure", "version", "active", "ready"}, 
                        new Object[]{procedure, pVersion, true, true}, 
                        new String[]{"schema_name", "table_name", "object_name", "field_name_1", "field_value_1", "field_name_2", "field_value_2"}, 
                        new String[]{"object_id"});

        Integer contProcConfigObjInfo = procConfigObjInfo.length;

        String newEntry = " query returns " + contProcConfigObjInfo+1 + " records.";
        try {requirementsLogEntry(methodName, newEntry,1);
        } catch (IOException ex) {Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);}
        // Create the root node for the procedure being deployed.
        for (Integer icontProcConfigObjInfo=0; icontProcConfigObjInfo<contProcConfigObjInfo;icontProcConfigObjInfo++){
            String schemaName = (String) procConfigObjInfo[icontProcConfigObjInfo][0];
            String tableName = (String) procConfigObjInfo[icontProcConfigObjInfo][1];
            String objectName = (String) procConfigObjInfo[icontProcConfigObjInfo][2];
            String fieldName1 = (String) procConfigObjInfo[icontProcConfigObjInfo][3];
            String fieldValue1 = (String) procConfigObjInfo[icontProcConfigObjInfo][4];
            String fieldName2 = (String) procConfigObjInfo[icontProcConfigObjInfo][5];
            String fieldValue2 = (String) procConfigObjInfo[icontProcConfigObjInfo][6];                            

            newEntry = icontProcConfigObjInfo.toString()+"/"+contProcConfigObjInfo.toString()+ " get object "+objectName;
            try {requirementsLogEntry(methodName, newEntry,2);
            } catch (IOException ex) {Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);}

            String foreignTableName = "user_info";

            Object[] diagnoses = Rdbms.existsRecord("config", foreignTableName, 
                    new String[]{fieldName1}, new Object[]{fieldValue1});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                schemaName = labPlat.buildSchemaName(schemaName, schemaName);
                diagnoses = Rdbms.insertRecordInTable(schemaName, foreignTableName, new String[]{"user_info_id"}, new Object[]{fieldValue1});
                id = Integer.parseInt(diagnoses[6].toString());
            }
            else{newEntry = " The "+foreignTableName+" " + fieldValue1 + " already exist";}   
            try {requirementsLogEntry(methodName, newEntry,3);
            } catch (IOException ex1) {Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex1);}

            foreignTableName = "role";
            diagnoses = Rdbms.existsRecord("config", foreignTableName, 
                    new String[]{fieldName2}, new Object[]{fieldValue2});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){         
                schemaName = labPlat.buildSchemaName(schemaName, schemaName);
                diagnoses = Rdbms.insertRecordInTable(schemaName, foreignTableName, new String[]{"user_info_id"}, new Object[]{fieldValue1});
                id = Integer.parseInt(diagnoses[6].toString());
            }
            else{newEntry = " The "+foreignTableName+" " + fieldValue2 + " already exist";}   
            try {requirementsLogEntry(methodName, newEntry,3);
            } catch (IOException ex1) {Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex1);}

            //user role    
            Integer userRoleCount = 0;
            diagnoses = Rdbms.existsRecord("config", foreignTableName, new String[]{fieldName1, fieldName2}, new Object[]{fieldValue1, fieldValue2});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                                
                schemaName = "\"" + schemaName + "\"";
                if ( userRoleCount==0){
                    try{                
                        String query="select count(role_id) as cont from config.user_profile";                                                
                        ResultSet res = Rdbms.prepRdQuery(query, new Object [] {});
                        res.first();
                        userRoleCount = res.getInt("cont");
                    } catch (SQLException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
                userRoleCount++;

                diagnoses = Rdbms.insertRecordInTable(schemaName, tableName, 
                        new String[]{fieldName1, fieldName2, "user_profile_id"}, new Object[]{fieldValue1, fieldValue2, userRoleCount});
                id = Integer.parseInt(diagnoses[6].toString());
            }    
        }        
    } 
    */
}
