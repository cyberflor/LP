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
       return buildSqlStatement(operation, schemaName, tableName, whereFieldNames, whereFieldValues, fieldsToRetrieve, setFieldNames, setFieldValues, fieldsToOrder, fieldsToGroup, false);      
    }
    public HashMap<String, Object[]> buildSqlStatement(String operation, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] setFieldNames, Object[] setFieldValues, String[] fieldsToOrder, String[] fieldsToGroup, Boolean forceDistinct) {        
        HashMap<String, Object[]> hm = new HashMap();        
        
        String queryWhere = "";
        schemaName = setSchemaName(schemaName);
        tableName = setSchemaName(tableName);
        
        Object[] whereFieldValuesNew = new Object[0];
        if (whereFieldNames != null) {
            Object[] whereClauseContent = buildWhereClause(whereFieldNames, whereFieldValues);            
            queryWhere=(String) whereClauseContent[0];
            whereFieldValuesNew=(Object[]) whereClauseContent[1];
        }
        String fieldsToRetrieveStr = buildFieldsToRetrieve(fieldsToRetrieve);
        String fieldsToOrderStr = buildOrderBy(fieldsToOrder);
        String fieldsToGroupStr = buildGroupBy(fieldsToGroup);
        
        String insertFieldNamesStr = buildInsertFieldNames(setFieldNames);
        String insertFieldValuesStr = buildInsertFieldNamesValues(setFieldNames);
        
        String query = "";
        switch (operation.toUpperCase()) {
            case "SELECT":
                query = "select ";
                if (forceDistinct){query=query+ " distinct ";}
                query=query+ " " + fieldsToRetrieveStr + " from " + schemaName + "." + tableName + "   where " + queryWhere + " " + fieldsToGroupStr + " " + fieldsToOrderStr;
                break;
            case "INSERT":
                query = "insert into " + schemaName + "." + tableName + " (" + insertFieldNamesStr + ") values ( " + insertFieldValuesStr + ") ";
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
    private Object[] buildWhereClause(String[] whereFieldNames, Object[] whereFieldValues){
        StringBuilder queryWhere = new StringBuilder();
        Object[] whereFieldValuesNew = new Object[0];
        for (int iwhereFieldNames=0; iwhereFieldNames<whereFieldNames.length; iwhereFieldNames++){
            String fn = whereFieldNames[iwhereFieldNames];
            if (iwhereFieldNames > 0) {
                queryWhere.append(" and ");
            }
            if (fn.toUpperCase().contains("NULL")) {
                queryWhere.append(fn);
            } else if (fn.toUpperCase().contains(" LIKE")) {
                queryWhere.append(" ? ");
                whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, whereFieldValues[iwhereFieldNames]);
            } else if (fn.toUpperCase().contains(" IN")) {
                String separator = inSeparator(fn);
                String textSpecs = (String) whereFieldValues[iwhereFieldNames];
                String[] textSpecArray = textSpecs.split("\\" + separator);
                Integer posicINClause = fn.toUpperCase().indexOf("IN");
                queryWhere.append(fn.substring(0, posicINClause + 2)).append(" (");
                for (String f : textSpecArray) {
                    queryWhere.append("?,");
                    whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, f);
                }
                queryWhere.deleteCharAt(queryWhere.length() - 1);
                //queryWhere.append(queryWhere.toString().substring(0, queryWhere.toString().length() - 1));
                queryWhere.append(")");
            } else {
                queryWhere.append(fn).append("=? ");
                whereFieldValuesNew = LPArray.addValueToArray1D(whereFieldValuesNew, whereFieldValues[iwhereFieldNames]);
            }
        }
        return new Object[]{queryWhere.toString(), whereFieldValuesNew};
    }
    private String  buildUpdateSetFields(String[] setFieldNames) {
        StringBuilder updateSetSectionStr = new StringBuilder();
        for (String setFieldName : setFieldNames) {
            updateSetSectionStr.append(setFieldName).append("=?, ");
        }
        updateSetSectionStr.deleteCharAt(updateSetSectionStr.length() - 1);
        updateSetSectionStr.deleteCharAt(updateSetSectionStr.length() - 1);
//        updateSetSectionStr = updateSetSectionStr.substring(0, updateSetSectionStr.length() - 2);
        return updateSetSectionStr.toString();
    }

    private String buildInsertFieldNames(String[] setFieldNames) {
        StringBuilder setFieldNamesStr = new StringBuilder();
        if (setFieldNames != null) {
            for (String setFieldName: setFieldNames) {
                setFieldNamesStr.append(setFieldName).append(", ");
            }
            setFieldNamesStr.deleteCharAt(setFieldNamesStr.length() - 1);
            setFieldNamesStr.deleteCharAt(setFieldNamesStr.length() - 1);
        }
        return setFieldNamesStr.toString();
    }

    private String buildInsertFieldNamesValues(String[] setFieldNames) {
        StringBuilder setFieldNamesArgStr = new StringBuilder();
        if (setFieldNames != null) {
            for (String setFieldName: setFieldNames) {
                setFieldNamesArgStr.append("?, ");
            }
            setFieldNamesArgStr.deleteCharAt(setFieldNamesArgStr.length() - 1);
            setFieldNamesArgStr.deleteCharAt(setFieldNamesArgStr.length() - 1);
        }
        return setFieldNamesArgStr.toString();
    }
    
    private String setSchemaName(String schemaName) {
        schemaName = schemaName.replace("\"", "");
        schemaName = "\"" + schemaName + "\"";
        return schemaName;
    }

    private String buildFieldsToRetrieve(String[] fieldsToRetrieve) {
        StringBuilder fieldsToRetrieveStr = new StringBuilder();
        if (fieldsToRetrieve != null) {
            for (String fn : fieldsToRetrieve) {
                if (fn.toUpperCase().contains(" IN")) {
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    fn = fn.substring(0, posicINClause - 1);
                    fieldsToRetrieveStr.append(fn.toLowerCase()).append(", ");
                }
                fieldsToRetrieveStr.append(fn.toLowerCase()).append(", ");
            }
            fieldsToRetrieveStr.deleteCharAt(fieldsToRetrieveStr.length() - 1);
            fieldsToRetrieveStr.deleteCharAt(fieldsToRetrieveStr.length() - 1);
        }
        return fieldsToRetrieveStr.toString();
    }

    private String buildGroupBy(String[] fieldsToGroup) {
        StringBuilder fieldsToGroupStr = new StringBuilder();
        if (fieldsToGroup != null) {
            for (String fn : fieldsToGroup) {
                fieldsToGroupStr.append(fn).append(", ");
            }
            if (fieldsToGroupStr.length() > 0) {
                fieldsToGroupStr.deleteCharAt(fieldsToGroupStr.length() - 1);
                fieldsToGroupStr.deleteCharAt(fieldsToGroupStr.length() - 1);
                fieldsToGroupStr.insert(0, "Group By ");
            }
        }
        return fieldsToGroupStr.toString();
    }

    private String buildOrderBy(String[] fieldsToOrder) {
        StringBuilder fieldsToOrderBuilder = new StringBuilder();
        if (fieldsToOrder != null) {
            for (String fn : fieldsToOrder) {
                fieldsToOrderBuilder.append(fn).append(", ");
            }
            if (fieldsToOrderBuilder.length() > 0) {
                fieldsToOrderBuilder.deleteCharAt(fieldsToOrderBuilder.length() - 1);
                fieldsToOrderBuilder.deleteCharAt(fieldsToOrderBuilder.length() - 1);
                fieldsToOrderBuilder.insert(0, "Order By ");
            }
        }
        return fieldsToOrderBuilder.toString();
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
