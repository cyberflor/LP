/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

/**
 *
 * @author Administrator
 */
public class dbObjectsConfigTables {    
    
    public static final String TABLE_CONFIG_PERSON="person";
        public static final String FLD_CONFIG_PERSON_PERSON_ID="person_id";
        public static final String FLD_CONFIG_PERSON_BIRTH_DATE="birth_date";
        public static final String FLD_CONFIG_PERSON_FIRST_NAME="first_name";  
        public static final String FLD_CONFIG_PERSON_LAST_NAME="last_name";  
        public static final String FLD_CONFIG_PERSON_PHOTO="photo";
    private Object[][] tableConfigPersonScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_CONFIG_SPEC;
        return new Object[0][0];
    }  
    
    public static final String TABLE_CONFIG_SPEC="spec";
        public static final String FLD_CONFIG_SPEC_CODE="code";
        public static final String FLD_CONFIG_SPEC_CONFIG_VERSION="config_version";  
        public static final String FLD_CONFIG_SPEC_VARIATION_NAME="variation_name";
    private Object[][] tableConfigSpecScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_CONFIG_SPEC;
        String script = "CREATE TABLE "+tableName;
        return new Object[0][0];
    }  
    
    public static final String TABLE_CONFIG_SPEC_LIMITS="spec_limits";
        public static final String FLD_CONFIG_SPEC_LIMITS_ANALYSIS="analysis";
        public static final String FLD_CONFIG_SPEC_LIMITS_CODE="code";
        public static final String FLD_CONFIG_SPEC_LIMITS_CONFIG_VERSION="config_version";  
        public static final String FLD_CONFIG_SPEC_LIMITS_METHOD_NAME="method_name";
        public static final String FLD_CONFIG_SPEC_LIMITS_METHOD_VERSION="method_version";
        public static final String FLD_CONFIG_SPEC_LIMITS_NAME="name";          
        public static final String FLD_CONFIG_SPEC_LIMITS_PARAMETER="parameter";          
        public static final String FLD_CONFIG_SPEC_LIMITS_RULE_TYPE="rule_type";          
        public static final String FLD_CONFIG_SPEC_LIMITS_RULE_VARIABLES="rule_variables";          
        public static final String FLD_CONFIG_SPEC_LIMITS_VARIATION_NAME="variation_name";
        
    private Object[][] tableConfigSpecLimitsScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_CONFIG_SPEC_LIMITS;
        return new Object[0][0];
    }       

    public static final String TABLE_CONFIG_SPEC_RULES="spec_rules";
        public static final String FLD_CONFIG_SPEC_RULES_CODE="code";
        public static final String FLD_CONFIG_SPEC_RULES_CONFIG_VERSION="config_version";
        public static final String FLD_CONFIG_SPEC_RULES_ALLOW_OTHER_ANALYSIS="allow_other_analysis";
        public static final String FLD_CONFIG_SPEC_RULES_ALLOW_MULTI_SPEC="allow_multi_spec";    
    private Object[][] tableConfigSpecRulesScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_CONFIG_SPEC_RULES;
        return new Object[0][0];
    }     
    
}
