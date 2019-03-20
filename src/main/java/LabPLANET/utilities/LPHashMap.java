/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class LPHashMap {
        
    /**
     *
     * @param map
     * @param separator
     * @return
     */
    public static String HashMapToStringKeys(HashMap<String, Object> map, String separator){ 
        String keys="";
        if (map.isEmpty()){return "";}
         
        String[] strs = map.keySet().toArray(new String[map.size()]);
        for(String str : strs) {
          keys=keys+str+separator;
        }         
         return keys;
    }
}
