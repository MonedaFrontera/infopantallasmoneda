package com.monedafrontera.factory;

import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.monedafrontera.dto.Dolartoday;
import com.monedafrontera.jdbc.DolarTodayDeserializer;
import com.monedafrontera.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Esta clase deserializa el Web Services de tipo Api Rest, de la empresa  DolarToday. 
 * Es utilizada la libreria Gson de Google. https://code.google.com/p/google-gson/
 * para mayor informacion de uso de esta libreria, ver el tutorial: 
 * http://www.javacreed.com/gson-deserialiser-example/
 * El objecto <code>DolarTodayDeserializer</code> parsea los miembros
 * del Json a objetos java.
 * 
 * @author 
 */
public class DolarTodayJson {
 
    public static Dolartoday getDolartoday2Object() {
        
    	Dolartoday dolar = null;// DTO objeto dolar a devolver

        try {
        	Log.imprimirLog(new Date(), "INFO", "Consumiendo REST Api DolarToday...");            
            
            String url = "https://s3.amazonaws.com/dolartoday/data.json";
            
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Dolartoday.class, new DolarTodayDeserializer());
            Gson gson = gsonBuilder.create();
            dolar = gson.fromJson(IOUtils.toString(new URL(url)), Dolartoday.class);
            return dolar;

        } catch (Exception e) {
            Log.imprimirLog(new Date(), "Exceptions",
                    e.getMessage());
            e.printStackTrace();
        }
        return dolar;
    }
    
   
}
