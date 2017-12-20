package co.ceiba.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.ceiba.conection.Conection;

public class Tool {
	
	private static final Logger LOGGER = Logger.getLogger( Conection.class.getName() );
	
	public String convertToJSON(ResultSet resultSet){
	    JSONArray jsonArray = new JSONArray();

	    try {
			while (resultSet.next()) {
			    int totalColumns = resultSet.getMetaData().getColumnCount();
			    JSONObject obj = new JSONObject();
			    for (int i = 0; i < totalColumns; i++) {
			        obj.put(resultSet.getMetaData().getColumnLabel(i+1)
			                .toLowerCase(), resultSet.getObject(i + 1));
			    }
			    jsonArray.put(obj);
			}
		} catch (JSONException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
	    return jsonArray.toString();
	}
	
	public static int diferenciaHoras(Date fechaIngreso,Date fechaSalida){
		long diff = fechaSalida.getTime() - fechaIngreso.getTime();
		long segundos = diff / 1000;
		long minutos = segundos / 60;
		return Math.round(minutos / 60f);		
	}
}
