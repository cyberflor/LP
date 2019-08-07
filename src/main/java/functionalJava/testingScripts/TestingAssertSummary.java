/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.testingScripts;

/**
 *
 * @author Administrator
 */
public class TestingAssertSummary {
        Integer totalTests=0;
        Integer totalLabPlanetBooleanMatch=0;
        Integer totalLabPlanetBooleanUnMatch=0;
        Integer totalLabPlanetBooleanUndefined=0;
        Integer totalLabPlanetErrorCodeMatch=0;
        Integer totalLabPlanetErrorCodeUnMatch=0;
        Integer totalLabPlanetErrorCodeUndefined=0;    
        
        public void increaseTotalTests(){this.totalTests++;}
        public void increasetotalLabPlanetBooleanUndefined(){this.totalLabPlanetBooleanUndefined++;}
        public void increasetotalLabPlanetBooleanMatch(){this.totalLabPlanetBooleanMatch++;}
        public void increasetotalLabPlanetBooleanUnMatch(){this.totalLabPlanetBooleanUnMatch++;}        
        public void increasetotalLabPlanetErrorCodeUndefined(){this.totalLabPlanetErrorCodeUndefined++;}
        public void increasetotalLabPlanetErrorCodeMatch(){this.totalLabPlanetErrorCodeMatch++;}
        public void increasetotalLabPlanetErrorCodeUnMatch(){this.totalLabPlanetErrorCodeUnMatch++;}        
                
        public Integer getTotalTests(){return this.totalTests;}
        
        public void notifyResults(){
        }
}
