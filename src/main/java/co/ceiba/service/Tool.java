package co.ceiba.service;

import java.sql.ResultSet;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tool {
	public String convertToJSON(ResultSet resultSet)
	        throws Exception {
	    JSONArray jsonArray = new JSONArray();

	    while (resultSet.next()) {
	        int total_columns = resultSet.getMetaData().getColumnCount();
	        JSONObject obj = new JSONObject();
	        for (int i = 0; i < total_columns; i++) {
	            obj.put(resultSet.getMetaData().getColumnLabel(i+1)
	                    .toLowerCase(), resultSet.getObject(i + 1));
	        }
	        jsonArray.put(obj);
	    }
	    return jsonArray.toString();
	}
	
	public static int diferenciaHoras(Date fechaIngreso,Date fechaSalida){
		long diff = fechaSalida.getTime() - fechaIngreso.getTime();
		long segundos = diff / 1000;
		long minutos = segundos / 60;
		int diferenciaHoras =(int) Math.round(minutos / 60);
		return diferenciaHoras;
	}
}
