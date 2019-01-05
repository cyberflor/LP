/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LabPLANETArray;

/**
 *
 * @author Administrator
 */
public class SqlStatement {

    public String buildSqlStatement(String operation, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] setFieldNames, Object[] setFieldValues, String[] fieldsToOrder, String[] fieldsToGroup) {
        LabPLANETArray labArr = new LabPLANETArray();
        String queryWhere = "";
        schemaName = schemaName.replace("\"", "");
        schemaName = "\"" + schemaName + "\"";
        tableName = tableName.replace("\"", "");
        tableName = "\"" + tableName + "\"";
        Integer i = 1;
        Boolean containsInClause = false;
        Object[] whereFieldValuesNew = new Object[0];
        if (whereFieldNames != null) {
            for (String fn : whereFieldNames) {
                if (i > 1) {
                    queryWhere = queryWhere + " and ";
                }
                if (fn.toUpperCase().contains("NULL")) {
                    queryWhere = queryWhere + fn;
                } else if (fn.toUpperCase().contains(" LIKE")) {
                    queryWhere = queryWhere + fn + " ? ";
                } else if (fn.toUpperCase().contains(" IN")) {
                    String separator = inSeparator(fn);
                    containsInClause = true;
                    String textSpecs = (String) whereFieldValues[i - 1];
                    String[] textSpecArray = textSpecs.split("\\" + separator);
                    //                         queryWhere = queryWhere + fn.replace(separator, "") + "(" ;
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    queryWhere = queryWhere + fn.substring(0, posicINClause + 2) + " (";
                    /*for (Integer iNew =0;iNew<i-1;iNew++){
                    whereFieldValuesNew[iNew] = whereFieldValues[i];
                    }*/
                    for (String f : textSpecArray) {
                        queryWhere = queryWhere + "?,";
                        whereFieldValuesNew = labArr.addValueToArray1D(whereFieldValuesNew, f);
                        i++;
                    }
                    for (Integer j = i; j <= whereFieldValues.length; j++) {
                        whereFieldValuesNew = labArr.addValueToArray1D(whereFieldValuesNew, whereFieldValues[j]);
                    }
                    queryWhere = queryWhere.substring(0, queryWhere.length() - 1);
                    queryWhere = queryWhere + ")";
                    whereFieldValues = whereFieldValuesNew;
                } else {
                    queryWhere = queryWhere + fn + "=? ";
                }
                i++;
            }
        }
        String fieldsToRetrieveStr = "";
        String fieldsToRetrieveArgStr = "";
        String setFieldNamesStr = "";
        String setFieldNamesArgStr = "";
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
        if (setFieldNames != null) {
            for (int iFields = 0; iFields < setFieldNames.length; iFields++) {
                setFieldNamesStr = setFieldNamesStr + setFieldNames[iFields] + ", ";
                setFieldNamesArgStr = setFieldNamesArgStr + "?, ";
            }
            setFieldNamesStr = setFieldNamesStr.substring(0, setFieldNamesStr.length() - 2);
            setFieldNamesArgStr = setFieldNamesArgStr.substring(0, setFieldNamesArgStr.length() - 2);
        }
        String query = "";
        switch (operation.toUpperCase()) {
            case "SELECT":
                query = "select " + fieldsToRetrieveStr + " from " + schemaName + "." + tableName + "   where " + queryWhere + " " + fieldsToGroupStr + " " + fieldsToOrderStr;
                break;
            case "INSERT":
                query = "insert into " + schemaName + "." + tableName + " (" + setFieldNamesStr + ") values ( " + setFieldNamesArgStr + ") ";
                break;
            case "UPDATE":
                String updateSetSectionStr = "";
                //String[] updateSetSection = labArr.joinTwo1DArraysInOneOf1DString(setFieldNames, setFieldValues, "=");
                for (int iFields = 0; iFields < setFieldNames.length; iFields++) {
                    updateSetSectionStr = updateSetSectionStr + setFieldNames[iFields] + "=?, ";
                }
                updateSetSectionStr = updateSetSectionStr.substring(0, updateSetSectionStr.length() - 2);
                query = "update " + schemaName + "." + tableName + " set " + updateSetSectionStr + " where " + queryWhere;
                break;
            default:
                break;
        }
        return query;
    }
    
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
