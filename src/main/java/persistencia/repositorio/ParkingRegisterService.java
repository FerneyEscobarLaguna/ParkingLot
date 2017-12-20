package persistencia.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import co.ceiba.conection.Conection;
import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;
import co.ceiba.service.IParkingRegisterService;

public class ParkingRegisterService implements IParkingRegisterService{
	
	private static final Logger LOGGER = Logger.getLogger( Conection.class.getName() );
	public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	
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
				fechaIngreso = new SimpleDateFormat(DATEFORMAT).parse(rs.getObject(3).toString());
				int registroVehiculoId = Integer.parseInt(rs.getObject(4).toString());
				return new ParkingRegister(registroVehiculoId,vehiculo,fechaIngreso,null,0);
			}
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		} catch (ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		} 
		return null;
	}
	
	public void registrarIngreso(ParkingRegister registroParqueadero) {
		DateFormat df = new SimpleDateFormat(DATEFORMAT);
		String fechaIngreso = df.format(registroParqueadero.getFechaIngreso());
		String placa = registroParqueadero.getVehiculo().getPlaca();
		String tipoVehiculo = registroParqueadero.getVehiculo().getClass().equals(Car.class)?"C":"M";
		String insert="";
		if(tipoVehiculo.equals("M")){
			int cilindraje = ((Motorcycle)registroParqueadero.getVehiculo()).getCilindraje();
			insert= "INSERT INTO REGISTRO_PARQUEADERO(PLACA,TIPO_VEHICULO,FECHA_INGRESO,CILINDRAJE) "+
					"VALUES('" + placa + "','"+tipoVehiculo+"','"+fechaIngreso+"',"+cilindraje + ")";
		}else
			insert= "INSERT INTO REGISTRO_PARQUEADERO(PLACA,TIPO_VEHICULO,FECHA_INGRESO) "+
					"VALUES('" + placa + "','"+tipoVehiculo+"','"+fechaIngreso+"')";
		Conection con= new Conection();
		con.executeUpdate(insert);
		con.close();
	}
	
	public void registraSalida(ParkingRegister registroParqueadero) {
		int registroVehiculoId = registroParqueadero.getRegistroVehiculoId();
		double costo = registroParqueadero.getCostoParqueadero();
		DateFormat df = new SimpleDateFormat(DATEFORMAT);
		String fechaSalida = df.format(registroParqueadero.getFechaSalida());
		Conection con= new Conection();
		String update = "UPDATE REGISTRO_PARQUEADERO SET COSTO_PARQUEADERO="+costo+", FECHA_SALIDA='"+fechaSalida+"' WHERE REGISTRO_PARQUEADERO_ID="+registroVehiculoId;
		con.executeUpdate(update);
		con.close();
	}

	public ResultSet obtenerVehiculosParqueados() {
		Conection con= new Conection();
		String query = "SELECT PLACA,TIPO_VEHICULO,FECHA_INGRESO FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl";
		return con.executeQuery(query);		
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
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return 0;
	}
}
