/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.batch;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.util.Date;

/**
 * 
 * @author Administrator
 */
public class Batch {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    
    String[] diagnoses = new String[7];    

    String name = "";
    String template = "";
    Integer templateVersion = 0;
    
    Date dateCreation;
    String creator = "";        
    String batchComment = "";
    String batchCommentAuthor = "";
    String batchLocation = "";
    String batchOperator = "";
    
    public Batch(String template, Integer templateVersion, String name, String creator){                

        this.template = template;
        this.templateVersion = templateVersion;                
        this.name = name;
        this.dateCreation = new Date();
        this.creator = creator;
    }

    public String getBatchName() {return name;}        

    public String getBatchTemplate() {return template;}        

    public Integer getBatchTemplateVersion() {return templateVersion;}          

    public Date getBatchDateCreation(){ return dateCreation;}
    
    public String getBatchCreator(){ return creator;}
    
    public String getBatchComment(){ return batchComment;}
    
    public String getBatchCommentAuthor(){ return batchCommentAuthor;}
    
    public String getBatchLocation(){ return batchLocation;}
    
    public String getBatchOperator(){ return batchOperator;}
    
    public void setBatchComment(String newComment, String newCommAuthor){this.batchComment=newComment; this.batchCommentAuthor=newCommAuthor;}
        
    public void setBatchLocation(String newLoc){ this.batchLocation=newLoc;}
    
    public void setBatchOperator(String newOper){ this.batchOperator=newOper;}
   
    public void newBatch(String template, Integer templateVersion, String name, String creator){
        Batch myBatch = new Batch(template, templateVersion, name, creator);
    }

    public void batchCommentAdd(String comment, String author){    this.batchComment = comment;  this.batchCommentAuthor=author;}

    public void batchSampleCommentAdd(){} //Improve!!

    public void batchAssignOperator(String oper){   this.batchOperator=oper; }

    public void batchChangeOperator(String oper){   batchAssignOperator(oper);}

    public String batchCommentAddOthers(String oper, String mode){ // Improve!!
    String comment = "";
    return comment;
}

    public String batchCommentOpen(String oper, String mode){
    if (this.batchCommentAuthor.equalsIgnoreCase(oper)) 
            {return this.batchComment;}
    else
    { return batchCommentAddOthers(oper, mode);
    
    }
              
}

    public void batchSetLocation(String loc){   this.batchLocation=loc; }
     
}
