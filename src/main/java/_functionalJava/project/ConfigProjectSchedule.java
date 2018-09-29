/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.project;

/**
 *
 * @author Administrator
 */
public class ConfigProjectSchedule {
    
    Double numItems;
    String itemsMeasurement; 
    enum itemsM {
        HOURS, DAYS, MONTHS, YEARS;
    }
    
    public void configProjectSchedule (Double numItems, String itemsMeasurement){
        this.itemsMeasurement =itemsMeasurement;
        this.numItems=numItems;
    }
    
    
    
}
