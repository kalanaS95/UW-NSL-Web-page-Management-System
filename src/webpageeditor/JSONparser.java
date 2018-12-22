/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageeditor;

import com.sun.javafx.PlatformUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kalana
 */
public class JSONparser {
        
        
        
    //this will parse the user given JSON file according to link and the given keys and return a key and value pair
    public List<HashMap<String, String>> parser(List<String> keys, String Link, String ArrayName)
    {
        List<HashMap<String,String>> ListOmaps= new ArrayList<HashMap<String,String>>();
        HashMap<String, String> values = new HashMap<>();
        
        JSONParser parser = new JSONParser(); 
        JSONArray a = null;
        try {

            a = (JSONArray)parser.parse(new FileReader(System.getProperty("user.dir")+OSPathHandler()+Link));
            JSONObject obj = (JSONObject)a.get(0);
            // Loop through each item
                
            for(int currSize=0;currSize<((JSONArray)obj.get(ArrayName)).size();currSize++)
            {
                JSONObject obj_ = (JSONObject)((JSONArray)obj.get(ArrayName)).get(currSize);
                for(int currElement=0;currElement<keys.size();currElement++)
                {
                    String val = null;
                    
                    if(obj_.get(keys.get(currElement)) instanceof Long) //takes care of Long to String casting error
                        val = Long.toString((Long)obj_.get(keys.get(currElement)));
                    else
                        val = (String)obj_.get(keys.get(currElement));
                    
                    values.put(keys.get(currElement), val);
                    
                    
                }
                ListOmaps.add(new HashMap<String, String>(values));
       
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        System.err.println("return List size = "+ListOmaps.size());
        return ListOmaps;
    }
    
  
  
  private String OSPathHandler()
  {
      String path;
      if(PlatformUtil.isMac() || PlatformUtil.isLinux() || PlatformUtil.isUnix())
            return "/JSON/";
        else
            return "\\JSON\\";
  }
    
}
