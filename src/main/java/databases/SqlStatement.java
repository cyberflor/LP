/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LPArray;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class SqlStatement {

    /**
     *
     * @param operation
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @param setFieldNames
     * @param setFieldValues
     * @param fieldsToOrder
     * @param fieldsToGroup
     * @return
     */
    public HashMap<String, Object[]> buildSqlStatement(String operation, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] setFieldNames, Object[] setFieldValues, String[] fieldsToOrder, String[] fieldsToGroup) {        
        HashMap<String, Object[]> hm = new HashMap();        
        
        String queryWhere = "";
        schemaName = setSchemaName(schemaName);
        tableName = setSchemaName(tableName);
        
        Object[] whereFieldValuesNew = new Object[0];
        if (whereFieldNames != null) {
            //for (String fn : whereFieldNames) {
            for (int iwhereFieldNames=0; iwhereFieldNames<whereFieldNames.length; iwhereFieldNames++){
                String fn = whereFieldNames[iwhereFieldNames];
                if (iwhereFieldNames > 0) {
                    queryWhere = queryWhere + " and ";
                }
                if (fn.toUpperCase().contains("NULL")) {
                    queryWhere = queryWhere + fn;
                } else if (fn.toUpperCase().contains(" LIKE")) {
                    queryWhere = queryWhere + fn + " ? ";
                    whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, whereFieldValues[iwhereFieldNames]);
                } else if (fn.toUpperCase().contains(" IN")) {
                    String separator = inSeparator(fn);
                    String textSpecs = (String) whereFieldValues[iwhereFieldNames];
                    String[] textSpecArray = textSpecs.split("\\" + separator);
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    queryWhere = queryWhere + fn.substring(0, posicINClause + 2) + " (";
                    for (String f : textSpecArray) {
                        queryWhere = queryWhere + "?,";
                        whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, f);
                    }
                    queryWhere = queryWhere.substring(0, queryWhere.length() - 1);
                    queryWhere = queryWhere + ")";
                    whereFieldValues = whereFieldValuesNew;
                } else {
                    queryWhere = queryWhere + fn + "=? ";
                    whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, whereFieldValues[iwhereFieldNames]);
                }
            }
        }
        String fieldsToRetrieveStr = buildFieldsToRetrieve(fieldsToRetrieve);
        String fieldsToOrderStr = buildOrderBy(fieldsToOrder);
        String fieldsToGroupStr = buildGroupBy(fieldsToGroup);
        
        String setFieldNamesStr = buildSetFieldNames(setFieldNames);
        String setFieldNamesArgStr = buildSetFieldNamesValues(setFieldNames);
        
        String query = "";
        switch (operation.toUpperCase()) {
            case "SELECT":
                query = "select " + fieldsToRetrieveStr + " from " + schemaName + "." + tableName + "   where " + queryWhere + " " + fieldsToGroupStr + " " + fieldsToOrderStr;
                break;
            case "INSERT":
                query = "insert into " + schemaName + "." + tableName + " (" + setFieldNamesStr + ") values ( " + setFieldNamesArgStr + ") ";
                break;
            case "UPDATE":
                String updateSetSectionStr=buildUpdateSetFields(setFieldNames);
                query = "update " + schemaName + "." + tableName + " set " + updateSetSectionStr + " where " + queryWhere;
                whereFieldValuesNew= LPArray.addValueToArray1D(setFieldValues, whereFieldValuesNew);
                break;
            default:
                break;
        }
        hm.put(query, whereFieldValuesNew);
        return hm;
    }

    private String  buildUpdateSetFields(String[] setFieldNames) {
        String updateSetSectionStr = "";
        for (String setFieldName : setFieldNames) {
            updateSetSectionStr = updateSetSectionStr + setFieldName + "=?, ";
        }
        updateSetSectionStr = updateSetSectionStr.substring(0, updateSetSectionStr.length() - 2);
        return updateSetSectionStr;
    }

    private String buildSetFieldNamesValues(String[] setFieldNames) {
        String setFieldNamesArgStr = "";
        if (setFieldNames != null) {
            for (String setFieldName : setFieldNames) {
                setFieldNamesArgStr = setFieldNamesArgStr + "?, ";
            }
            setFieldNamesArgStr = setFieldNamesArgStr.substring(0, setFieldNamesArgStr.length() - 2);
        }
        return setFieldNamesArgStr;
    }

    private String buildSetFieldNames(String[] setFieldNames) {
        String setFieldNamesStr = "";
        if (setFieldNames != null) {
            for (String setFieldName : setFieldNames) {
                setFieldNamesStr = setFieldNamesStr + setFieldName + ", ";
            }
            setFieldNamesStr = setFieldNamesStr.substring(0, setFieldNamesStr.length() - 2);
        }
        return setFieldNamesStr;
    }

    private String setSchemaName(String schemaName) {
        schemaName = schemaName.replace("\"", "");
        schemaName = "\"" + schemaName + "\"";
        return schemaName;
    }

    private String buildFieldsToRetrieve(String[] fieldsToRetrieve) {
        String fieldsToRetrieveStr = "";
        if (fieldsToRetrieve != null) {
            for (String fn : fieldsToRetrieve) {
                if (fn.toUpperCase().contains(" IN")) {
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    fn = fn.substring(0, posicINClause - 1);
                    fieldsToRetrieveStr = fieldsToRetrieveStr + fn.toLowerCase() + ", ";
                }
                fieldsToRetrieveStr = fieldsToRetrieveStr + fn.toLowerCase() + ", ";
            }
            fieldsToRetrieveStr = fieldsToRetrieveStr.substring(0, fieldsToRetrieveStr.length() - 2);
        }
        return fieldsToRetrieveStr;
    }

    private String buildGroupBy(String[] fieldsToGroup) {
        String fieldsToGroupStr = "";
        if (fieldsToGroup != null) {
            for (String fn : fieldsToGroup) {
                fieldsToGroupStr = fieldsToGroupStr + fn + ", ";
            }
            if (fieldsToGroupStr.length() > 0) {
                fieldsToGroupStr = fieldsToGroupStr.substring(0, fieldsToGroupStr.length() - 2);
                fieldsToGroupStr = "Group By " + fieldsToGroupStr;
            }
        }
        return fieldsToGroupStr;
    }

    private String buildOrderBy(String[] fieldsToOrder) {
        String fieldsToOrderStr = "";
        if (fieldsToOrder != null) {
            for (String fn : fieldsToOrder) {
                fieldsToOrderStr = fieldsToOrderStr + fn + ", ";
            }
            if (fieldsToOrderStr.length() > 0) {
                fieldsToOrderStr = fieldsToOrderStr.substring(0, fieldsToOrderStr.length() - 2);
                fieldsToOrderStr = "Order By " + fieldsToOrderStr;
            }
        }
        return fieldsToOrderStr;
    }
    
    /**
     *
     * @param fn
     * @return
     */
    public String inSeparator(String fn){
        Integer posicINClause = fn.toUpperCase().indexOf("IN");
        String separator = fn;
        separator = separator.substring(posicINClause + 2, posicINClause + 3);
        separator = separator.trim();
        separator = separator.replace(" IN", "");
        if (separator.length() == 0) {
            separator = "|";
        }        
        return separator;
    }
    
}
