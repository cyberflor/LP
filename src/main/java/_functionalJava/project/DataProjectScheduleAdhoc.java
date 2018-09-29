/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.project;

import LabPLANET.utilities.LabPLANETArray;
import java.text.SimpleDateFormat;
import LabPLANET.utilities.LabPLANETDate;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DataProjectScheduleAdhoc {

    String project;
    int scheduleId;
    int schedule_size;
    String itemsMeasurement; 
    Date FirstDay;
    Date endDay;

    private static class dataProjectSchedule {

        public dataProjectSchedule() {
        }
    }
    
    public enum itemsMeasurementType {
        DAYS, MONTHS, YEARS;
    }
    
    public enum recursiveRules{
        MONDAYS, TUESDAYS, WEDNESDAYS, THURSDAYS, FRIDAYS, SATURDAYS, SUNDAYS;
    }
    
    
    public void dataProjectSchedule (int schedule_size, String itemsMeasurement, Date startDay){
        //EnumUtils.isValidEnum(itemsMeasurementType.class, itemsMeasurement);
        this.itemsMeasurement =itemsMeasurement;
        this.schedule_size=schedule_size;
        this.FirstDay=startDay;
        
        Date endDay=null;
        
        switch (itemsMeasurement.toUpperCase()){
            case "DAYS":
                endDay = LabPLANETDate.addDays(startDay, schedule_size);
                break;
            case "MONTHS":
                endDay = LabPLANETDate.addMonths(startDay, schedule_size);
                break;
            case "YEARS":
                endDay = LabPLANETDate.addYears(startDay, schedule_size);
                break;                
            default:
                
                break;
        }
        this.endDay=endDay;                        
        
    }

    public List<dataProjectSchedule> getDataProjectSchedulers(Rdbms rdbm, String schemaName, String pName, int projSchedId) {
    	List<dataProjectSchedule> project= new ArrayList<>();
        String condition = "1 ";

        String tableName = "project_schedule";
        
        Object[][] projectSchedInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, new String[]{"project", "id"}, new Object[]{pName, projSchedId}, 
                new String[]{"project", "id", "schedule_size_unit", "schedule_size", "start_date", "end_date"});
        
        //Cursor cursor = this.db.query(TABLE_PROJECT, new String[] { "*"},
        //  condition + " ORDER BY pro_dateConfirm ASC" , null, null, null, null); 		
          //condition + "and (pro_finalTecnico = 'null' OR pro_finalTecnico = 'F') AND pro_dateConfirm <> 'null' ORDER BY pro_dateConfirm ASC" , null, null, null, null);
        project = setProjectSchedulerFromDb(projectSchedInfo);
          
        return project;    	    	
    }   
    
    public List<dataProjectSchedule> setProjectSchedulerFromDb(Object[][] projectSchedInfo) {
        List<dataProjectSchedule> project= new ArrayList<>();
        
        for (Object[] projectSchedInfo1 : projectSchedInfo) {
            dataProjectSchedule aux = new dataProjectSchedule();
  
            int i=0;
            this.project = (String) projectSchedInfo[0][i];
            this.scheduleId =(int) projectSchedInfo[0][i++];
            this.itemsMeasurement = (String) projectSchedInfo[0][i++];
            this.schedule_size=(int) projectSchedInfo[0][i++];
            this.FirstDay=(Date) projectSchedInfo[0][i++];
            this.endDay=(Date) projectSchedInfo[0][i++];
            project.add(aux);
        }    
        return project;
    }  

    @SuppressWarnings("empty-statement")
    public Object[] importHolidaysCalendarSchedule(Rdbms rdbm, String schemaName, String calendarCode, String pName, Integer projSchedId) throws SQLException{
        
        String tableName = "project_schedule";
        String conflictDetail = "This day was converted on holidays";
        Object[] diagn = new String[6];
        diagn[0] = ""; diagn[1] = ""; diagn[2] = ""; diagn[4] = ""; diagn[5] = "";
        diagn[3] = "FALSE";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETDate labDate = new LabPLANETDate();        
        
        String schemaNameConfig = "config";labPlat.buildSchemaName(schemaName, "config");   
        String schemaNameData = labPlat.buildSchemaName(schemaName, "data");   
        
        Object[] existsRecord = rdbm.existsRecord(rdbm, schemaNameConfig, "holidays_calendar",  new String[]{"code", "active"}, new Object[]{calendarCode, true});
        if ("LABPLANET_FALSE".equalsIgnoreCase(existsRecord[0].toString())){ return existsRecord;}     

        Object[][] holidaysCalendarDates = rdbm.getRecordFieldsByFilter(rdbm, schemaNameConfig, "holidays_calendar_date", 
                new String[]{"code"}, new Object[]{calendarCode}, new String[]{"id", "date", "date", "date"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(holidaysCalendarDates[0][0].toString())){diagn[5]=holidaysCalendarDates[0][5];}
        
        existsRecord = rdbm.existsRecord(rdbm, schemaNameData, "project_schedule",  new String[]{"project", "id"}, new Object[]{pName, projSchedId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(existsRecord[0].toString())){ return existsRecord;}

        Object[] newProjSchedRecursive=null;
        if (holidaysCalendarDates.length>0){
            newProjSchedRecursive = rdbm.insertRecordInTable(rdbm, schemaNameData, "project_schedule_recursive", 
                    new String[]{"project", "project_schedule_id", "rule", "is_holidays"},
                    new Object[]{pName, projSchedId, calendarCode, true});
        }else{
            diagn[5]="No dates in the holidays calendar "+calendarCode; //+" between "+startDate.toString()+" and "+endDate.toString(); return diagn;
        }
        int projRecursiveId = Integer.parseInt(newProjSchedRecursive[6].toString());
        String datesStr ="";
        for (Object[] holidaysCalendarDate : holidaysCalendarDates) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy MMM dd HH:mm:ss"); //yyyy-MM-dd
            String s;
            Date calDate = (Date) holidaysCalendarDate[1]; //String s = cal.getTime().toString();
            s = format1.format(calDate.getTime());            
            datesStr=datesStr+s+"|";
            newProjSchedRecursive = rdbm.insertRecordInTable(rdbm, schemaNameData, "project_schedule_item", 
                    new String[]{"project", "project_schedule_id", "project_sched_recursive_id", "date", "is_holidays"},
                    new Object[]{pName, projSchedId, projRecursiveId, calDate, true});
            Object[][] itemsSameDay = rdbm.getRecordFieldsByFilter(rdbm, schemaNameData, "project_schedule_item", 
                    new String[]{"project", "project_schedule_id", "date", "is_holidays"},
                    new Object[]{pName, projSchedId, calDate, false}, 
                    new String[]{"id", "project", "project_schedule_id", "project_sched_recursive_id", "date", "is_holidays"});
            if (!"FALSE".equalsIgnoreCase(itemsSameDay[0][3].toString())){
                for (Object[] itemsSameDay1 : itemsSameDay) {
                    Long itemId = (Long) itemsSameDay1[0];
                    Object[] updateResult = rdbm.updateRecordFieldsByFilter(rdbm, schemaNameData, "project_schedule_item",
                            new String[]{"conflict", "conflict_detail"}, new Object[]{true, conflictDetail},
                            new String[]{"id"}, new Object[]{itemId.intValue()});
                    if ("LABPLANET_FALSE".equalsIgnoreCase(updateResult[0].toString())){return updateResult;}                    
                }
            }                        
        }
        diagn[3]="TRUE"; diagn[5]=datesStr;        
        
        return diagn;
    }
    
    public Object[] addRecursiveSchedulePoint(Rdbms rdbm, String schemaName, String pName, Integer projSchedId, String[] fieldName, Object[] fieldValue){
        
        String tableName = "project_schedule";
        String conflictDetail = "This day is marked as holidays";
        Object[] diagn = new String[6];
        diagn[0] = ""; diagn[1] = ""; diagn[2] = ""; diagn[4] = ""; diagn[5] = "";
        diagn[3] = "FALSE";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETDate labDate = new LabPLANETDate();
        
        schemaName = labPlat.buildSchemaName(schemaName, "data");       
        Object[] existsRecord = rdbm.existsRecord(rdbm, schemaName, tableName,  new String[]{"project", "id"}, new Object[]{pName, projSchedId});
        if ("LABPLANET_FALSE".equals(existsRecord[0].toString())){ return existsRecord;}
        
        Calendar startDate = null; Calendar endDate = null;
        
        if (labArr.valueInArray(fieldName, "start_date")){
            startDate = (Calendar) fieldValue[labArr.valuePosicInArray(fieldName, "start_date")];
        }
        if (labArr.valueInArray(fieldName, "end_date")){
            endDate = (Calendar) fieldValue[labArr.valuePosicInArray(fieldName, "end_date")];
        }      
        Object[][] projectInfo = null;
        if (startDate==null || endDate==null){
            projectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, new String[]{"project", "id"}, new Object[]{pName, projSchedId}, new String[]{"project", "start_date", "end_date", "end_date"});
            if (startDate==null){
                Date currDate = (Date) projectInfo[0][1]; 
                if (currDate!=null){
                    startDate = Calendar.getInstance();
                    startDate.setTime(currDate);
                }else{
                    diagn[5]="The schedule should be limited but it is not the case, start_date is null";
                    return diagn;
                }
            }
            if (endDate==null){
                Date currDate = (Date) projectInfo[0][2]; 
                if (currDate!=null){
                    endDate = Calendar.getInstance();
                    endDate.setTime(currDate);                
                }else{
                    diagn[5]="The schedule should be limited but it is not the case, end_date is null";
                    return diagn;
                }                
            }            
        }
        String[] daysOfWeekArr = null;
        String daysOfWeek ="";
        if (labArr.valueInArray(fieldName, "DAYOFWEEK")){
            daysOfWeek = (String) fieldValue[labArr.valuePosicInArray(fieldName, "DAYOFWEEK")];
            //if ( daysOfWeek!=null){daysOfWeekArr = (String[]) daysOfWeek.split("\\*");}
        }
        
        
        String datesStr = "";

        Object[] daysInRange = labDate.getDaysInRange(startDate, endDate, daysOfWeek);  
        Object[] newProjSchedRecursive = null;
        if (daysInRange.length>0){
            newProjSchedRecursive = rdbm.insertRecordInTable(rdbm, schemaName, "project_schedule_recursive", 
                    new String[]{"project", "project_schedule_id", "rule", "start_date", "end_date"},
                    new Object[]{pName, projSchedId, daysOfWeek, (Date) projectInfo[0][1], (Date) projectInfo[0][2]});
        }else{
            diagn[5]="No dates in this range for. Day: "+daysOfWeek+" between "+startDate.toString()+" and "+endDate.toString(); return diagn;
        }
        int projRecursiveId = Integer.parseInt(newProjSchedRecursive[6].toString());
        for (Object daysInRange1 : daysInRange) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy MMM dd HH:mm:ss"); //yyyy-MM-dd
            String s;
            Date cale = (Date) daysInRange1; //String s = cal.getTime().toString();
            s = format1.format(cale.getTime());            
            datesStr=datesStr+s+"|";
            
            Object[] isHolidays = rdbm.existsRecord(rdbm, schemaName, "project_schedule_item", 
                    new String[]{"project", "project_schedule_id", "date", "is_holidays"}, 
                    new Object[]{pName, projSchedId, daysInRange1, true});             
            String[] fieldNames = new String[]{"project", "project_schedule_id", "project_sched_recursive_id", "date"};
            Object[] fieldValues = new Object[]{pName, projSchedId, projRecursiveId, daysInRange1};
            if ("LABPLANET_TRUE".equalsIgnoreCase(isHolidays[0].toString())){
                fieldNames=labArr.addValueToArray1D(fieldNames, "conflict");
                fieldNames=labArr.addValueToArray1D(fieldNames, "conflict_detail");
                fieldValues=labArr.addValueToArray1D(fieldValues, true);
                fieldValues=labArr.addValueToArray1D(fieldValues, conflictDetail);
            }         
            newProjSchedRecursive = rdbm.insertRecordInTable(rdbm, schemaName, "project_schedule_item", 
                    fieldNames,    
                    fieldValues);            

        }
        diagn[3]="TRUE"; diagn[5]=datesStr;
        
        
        return diagn;
    }
        
}
