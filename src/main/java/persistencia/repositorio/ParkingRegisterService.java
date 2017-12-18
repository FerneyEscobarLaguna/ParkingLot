package persistencia.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.ceiba.conection.Conection;
import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;
import co.ceiba.service.IParkingRegisterService;

public class ParkingRegisterService implements IParkingRegisterService{

	public ParkingRegister obtenerVehiculoParqueadoPlaca(String placa){
		Conection con= new Conection();
		String query = "SELECT TIPO_VEHICULO,CILINDRAJE,FECHA_INGRESO,REGISTRO_PARQUEADERO_ID FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl AND PLACA='"+placa+"'";		
		ResultSet rs = con.executeQuery(query);
		try {
			if(rs.next()){
				String tipoVehiculo = rs.getObject(1).toString().substring(0, 1);
				Vehicle vehiculo;
				if(tipoVehiculo.equals("C")){
					vehiculo= new Car(placa);
				}else{
					int cilindraje= Integer.parseInt(rs.getObject(2).toString());
					vehiculo = new Motorcycle(placa,cilindraje);
				}
				Date fechaIngreso=null;
				try {
					fechaIngreso = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getObject(3).toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				int registro_vehiculo_id = Integer.parseInt(rs.getObject(4).toString());
				return new ParkingRegister(registro_vehiculo_id,vehiculo,fechaIngreso,null,0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void registrarIngreso(ParkingRegister registroParqueadero) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaIngreso = df.format(registroParqueadero.getFechaIngreso());
		String placa = registroParqueadero.getVehiculo().getPlaca();
		String tipo_vehiculo = registroParqueadero.getVehiculo().getClass().equals(Car.class)?"C":"M";
		String insert="";
		if(tipo_vehiculo.equals("M")){
			int cilindraje = ((Motorcycle)registroParqueadero.getVehiculo()).getCilindraje();
			insert= "INSERT INTO REGISTRO_PARQUEADERO(PLACA,TIPO_VEHICULO,FECHA_INGRESO,CILINDRAJE) "+
					"VALUES('" + placa + "','"+tipo_vehiculo+"','"+fechaIngreso+"',"+cilindraje + ")";
		}else
			insert= "INSERT INTO REGISTRO_PARQUEADERO(PLACA,TIPO_VEHICULO,FECHA_INGRESO) "+
					"VALUES('" + placa + "','"+tipo_vehiculo+"','"+fechaIngreso+"')";
		Conection con= new Conection();
		con.executeUpdate(insert);
		con.close();
	}
	
	public void registraSalida(ParkingRegister registroParqueadero) {
		int registro_vehiculo_id = registroParqueadero.getRegistro_vehiculo_id();
		double costo = registroParqueadero.getCostoParqueadero();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaSalida = df.format(registroParqueadero.getFechaSalida());
		Conection con= new Conection();
		String update = "UPDATE REGISTRO_PARQUEADERO SET COSTO_PARQUEADERO="+costo+", FECHA_SALIDA='"+fechaSalida+"' WHERE REGISTRO_PARQUEADERO_ID="+registro_vehiculo_id;
		con.executeUpdate(update);
		con.close();
	}

	public ResultSet obtenerVehiculosParqueados() {
		Conection con= new Conection();
		String query = "SELECT PLACA,TIPO_VEHICULO,FECHA_INGRESO FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl";
		ResultSet rs = con.executeQuery(query);
		return rs;
	}
	
	public int contarVehiculosTipo(String tipo){
		Conection con= new Conection();
		String query = "SELECT COUNT(PLACA) AS TOTAL FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl AND TIPO_VEHICULO='"+tipo+"'";
		ResultSet rs = con.executeQuery(query);
		try {
			if(rs.next())
				return rs.getInt("TOTAL");
			else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
