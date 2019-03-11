/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.batch;

import java.util.Date;

/**
 * 
 * @author Administrator
 */
public class Batch {

    String classVersion = "0.1";
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
    
    /**
     *
     * @param template
     * @param templateVersion
     * @param name
     * @param creator
     */
    public Batch(String template, Integer templateVersion, String name, String creator){                

        this.template = template;
        this.templateVersion = templateVersion;                
        this.name = name;
        this.dateCreation = new Date();
        this.creator = creator;
    }

    /**
     *
     * @return
     */
    public String getBatchName() {return name;}        

    /**
     *
     * @return
     */
    public String getBatchTemplate() {return template;}        

    /**
     *
     * @return
     */
    public Integer getBatchTemplateVersion() {return templateVersion;}          

    /**
     *
     * @return
     */
    public Date getBatchDateCreation(){ return dateCreation;}
    
    /**
     *
     * @return
     */
    public String getBatchCreator(){ return creator;}
    
    /**
     *
     * @return
     */
    public String getBatchComment(){ return batchComment;}
    
    /**
     *
     * @return
     */
    public String getBatchCommentAuthor(){ return batchCommentAuthor;}
    
    /**
     *
     * @return
     */
    public String getBatchLocation(){ return batchLocation;}
    
    /**
     *
     * @return
     */
    public String getBatchOperator(){ return batchOperator;}
    
    /**
     *
     * @param newComment
     * @param newCommAuthor
     */
    public void setBatchComment(String newComment, String newCommAuthor){this.batchComment=newComment; this.batchCommentAuthor=newCommAuthor;}
        
    /**
     *
     * @param newLoc
     */
    public void setBatchLocation(String newLoc){ this.batchLocation=newLoc;}
    
    /**
     *
     * @param newOper
     */
    public void setBatchOperator(String newOper){ this.batchOperator=newOper;}
   
    /**
     *
     * @param template
     * @param templateVersion
     * @param name
     * @param creator
     */
    public void newBatch(String template, Integer templateVersion, String name, String creator){
        new Batch(template, templateVersion, name, creator);
    }

    /**
     *
     * @param comment
     * @param author
     */
    public void batchCommentAdd(String comment, String author){    this.batchComment = comment;  this.batchCommentAuthor=author;}

    /**
     *
     */
    public void batchSampleCommentAdd(){
                // Not implemented yet
    } //Improve!!

    /**
     *
     * @param oper
     */
    public void batchAssignOperator(String oper){   this.batchOperator=oper; }

    /**
     *
     * @param oper
     */
    public void batchChangeOperator(String oper){   batchAssignOperator(oper);}
        
    /**
     *
     * @param oper
     * @param mode
     * @return
     */
    public String batchCommentAddOthers(String oper, String mode){ // Improve!!
    String comment = "";
    return comment;
}

    /**
     *
     * @param oper
     * @param mode
     * @return
     */
    public String batchCommentOpen(String oper, String mode){
    if (this.batchCommentAuthor.equalsIgnoreCase(oper)) 
            {return this.batchComment;}
    else
    { return batchCommentAddOthers(oper, mode);
    
    }
              
}

    /**
     *
     * @param loc
     */
    public void batchSetLocation(String loc){   this.batchLocation=loc; }
     
}
