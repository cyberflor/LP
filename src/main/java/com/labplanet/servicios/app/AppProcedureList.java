/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETJson;
import databases.Rdbms;
import databases.Token;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
//import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
/**
 *
 * @author Administrator
 */
public class AppProcedureList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
            Rdbms rdbm = new Rdbms();       
            LabPLANETArray labArr = new LabPLANETArray();
            LabPLANETJson labJson = new LabPLANETJson();
            response.setContentType("application/json");        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet AppProcedureList at " + request.getServletPath()};      
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);       
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }                    

            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
            String dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];         
            String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
            String userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];         
                        
            boolean isConnected = false;
            isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                out.println(Arrays.toString(errObject));
                return ;               
            }       
            
JSONArray logs = new JSONArray();
JSONObject log = new JSONObject();
log.put("connected", true);
log.put("person_name", internalUserID);

            UserProfile usProf = new UserProfile();
            Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(rdbm, dbUserName);
logs.add(Arrays.toString(allUserProcedurePrefix));
log.put("user profiles", logs);

            
            
Response.ok().build();
response.getWriter().write(log.toString());   

rdbm.closeRdbms();

if (1==1) return;
            
  String[] procFldNameArray = new String[0];
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "name");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "version");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "label_en");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "label_es");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "schemaPrefix");
//JSONArray procArrayJson = labJson.convertToJSON(procFldNameArray);
Object[] procArray = new Object[0];
procArray = labArr.addValueToArray1D(procArray, "ProcessUSA");
procArray = labArr.addValueToArray1D(procArray, 1);
procArray = labArr.addValueToArray1D(procArray, "Process US");
procArray = labArr.addValueToArray1D(procArray, "Proceso EEUU");
procArray = labArr.addValueToArray1D(procArray, "process-us");

procArray = labArr.addValueToArray1D(procArray, "ProcessEU");
procArray = labArr.addValueToArray1D(procArray, 1);
procArray = labArr.addValueToArray1D(procArray, "Process EU");
procArray = labArr.addValueToArray1D(procArray, "Proceso UE");
procArray = labArr.addValueToArray1D(procArray, "process-eu");
Object[][] procArray2d = labArr.array1dTo2d(procArray, procFldNameArray.length);

String[] procEventFldNameArray = new String[0];
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "name");
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "label_en");
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "label_es");
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "branch_level");
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "type");
procEventFldNameArray = labArr.addValueToArray1D(procEventFldNameArray, "mode");

Object[] procEventArray = new Object[0];
procEventArray = labArr.addValueToArray1D(procEventArray, "Home");
procEventArray = labArr.addValueToArray1D(procEventArray, "Home");
procEventArray = labArr.addValueToArray1D(procEventArray, "Inicio");
procEventArray = labArr.addValueToArray1D(procEventArray, "level1");
procEventArray = labArr.addValueToArray1D(procEventArray, "tree-list");
procEventArray = labArr.addValueToArray1D(procEventArray, "edit");

procEventArray = labArr.addValueToArray1D(procEventArray, "logSample");
procEventArray = labArr.addValueToArray1D(procEventArray, "Log Sample");
procEventArray = labArr.addValueToArray1D(procEventArray, "Registrar Muestra");
procEventArray = labArr.addValueToArray1D(procEventArray, "level2");
procEventArray = labArr.addValueToArray1D(procEventArray, "tree-list");
procEventArray = labArr.addValueToArray1D(procEventArray, "edit");

procEventArray = labArr.addValueToArray1D(procEventArray, "receiveSample");
procEventArray = labArr.addValueToArray1D(procEventArray, "Receive Sample");
procEventArray = labArr.addValueToArray1D(procEventArray, "Recibir Muestra");
procEventArray = labArr.addValueToArray1D(procEventArray, "level2");
procEventArray = labArr.addValueToArray1D(procEventArray, "tree-list");
procEventArray = labArr.addValueToArray1D(procEventArray, "edit");

Object[][] procEventArray2d = labArr.array1dTo2d(procEventArray, procEventFldNameArray.length);

JSONArray procedures = new JSONArray();


for (int xProc=0; xProc<procArray2d.length;xProc++){  
  JSONObject procedure = new JSONObject();
  for (int yProc=0; yProc<procFldNameArray.length; yProc++){ 
    procedure.put(procFldNameArray[yProc], procArray2d[xProc][yProc]);
  }
  //JSONObject procEventsJson = new JSONObject();
  JSONArray procEventsJson = new JSONArray();
  for ( int xProcEv=0; xProcEv<procEventArray2d.length;xProcEv++){
    JSONObject procEventJson = new JSONObject();
    for (int yProcEv=0; yProcEv<procEventFldNameArray.length;yProcEv++){ 
        procEventJson.put(procEventFldNameArray[yProcEv], procEventArray2d[xProcEv][yProcEv]);
    }    
    procEventsJson.add(procEventJson);
  }
    procedure.put("definition", procEventsJson);
    procedures.add(procedure);
}
  



  JSONObject proceduresList = new JSONObject();
  proceduresList.put("procedures", procedures);
                      Response.ok().build();
                   response.getWriter().write(proceduresList.toString());   
  
            
if (1==1) return;
            
JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
JsonObjectBuilder procedureListBuilder = Json.createObjectBuilder();

procedureListBuilder.add("procedure", "proc");


JsonObject procedureObj = procedureListBuilder.build();
JsonArrayBuilder kvArrBld = Json.createArrayBuilder();

JsonArrayBuilder procEvent1 = Json.createArrayBuilder();
JsonArrayBuilder procEvent2 = Json.createArrayBuilder();
procEvent1.add(Json.createObjectBuilder().add("name", "Home").build());
procEvent1.add(Json.createObjectBuilder().add("label_es", "Inicio").build());
procEvent2.add(Json.createObjectBuilder().add("name", "receiveSample").build());
JsonArrayBuilder procedureList = Json.createArrayBuilder();
/*
String[] procFldNameArray = new String[0];
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "name");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "version");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "label_en");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "label_es");
procFldNameArray = labArr.addValueToArray1D(procFldNameArray, "schemaPrefix");
//JSONArray procArrayJson = labJson.convertToJSON(procFldNameArray);

Object[] procArray = new Object[0];
procArray = labArr.addValueToArray1D(procArray, "Module1");
procArray = labArr.addValueToArray1D(procArray, 1);
procArray = labArr.addValueToArray1D(procArray, "Module 1");
procArray = labArr.addValueToArray1D(procArray, "Modulo 1");
procArray = labArr.addValueToArray1D(procArray, "module1");
//JSONArray procArrayJson = labJson.convertToJSON(procArray);

Object[] procArray2 = new Object[0];
procArray2 = labArr.addValueToArray1D(procArray2, "Module1A");
procArray2 = labArr.addValueToArray1D(procArray2, 1);
procArray2 = labArr.addValueToArray1D(procArray2, "Module 1 A");
procArray2 = labArr.addValueToArray1D(procArray2, "Modulo 1 A");
procArray2 = labArr.addValueToArray1D(procArray2, "module1-A");
//JSONArray procArrayJson2 = labJson.convertToJSON(procArray);
*/


/*
JsonArrayBuilder procedures = Json.createArrayBuilder();
for (int i=0; i<procFldNameArray.length;i++){
    procedures.add(Json.createObjectBuilder().add(procFldNameArray[i], procArray[i]).build());
}
JsonArrayBuilder procedures2 = Json.createArrayBuilder();
for (int i=0; i<procFldNameArray.length;i++){
    procedures2.add(Json.createObjectBuilder().add(procFldNameArray[i], procArray2[i]).build());
}
*/
//                JsonArrayBuilder added = procedures.add(Json.createObjectBuilder().add(procArrayJson.toString()).build());


//procedureListBuilder.add("", procedures2.build()).add("", procedures.build());
//procedureListBuilder.add("", procedures2.build());
//procedureList.add(Json.createObjectBuilder().add("procedure", procArrayJson).build());
//procedureList.add(Json.createObjectBuilder().add("name", "Module1").build());


//procedureList.add(Json.createObjectBuilder().add("definition", procEvent1).add("def", procEvent2).build());
procedureListBuilder.add("procedure", procedureListBuilder);

JsonObject procListObj = procedureListBuilder.build();

StringWriter strWtr = new StringWriter();
JsonWriter jsonWtr = Json.createWriter(strWtr);
jsonWtr.writeObject(procListObj);
jsonWtr.close(); 


//JsonObject procedureListObjectAll = procedureListObject.build();
/*StringWriter strWtr = new StringWriter();
JsonWriter jsonWtr = Json.createWriter(strWtr);
jsonWtr.writeObject(empObj);
jsonWtr.close();
*/
                    Response.ok().build();
                   response.getWriter().write(strWtr.toString());   
       
if (1==1) return;
            
            

            
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
