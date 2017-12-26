package persistencia.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import co.ceiba.conection.Conection;
import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;
import co.ceiba.service.IParkingRegisterService;

public class ParkingRegisterService implements IParkingRegisterService{
	
	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterService.class.getName() );
	private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	private Conection con;
	
	public ParkingRegisterService(Conection con) {
		this.con = con;
	}

	public ParkingRegister obtenerVehiculoParqueadoPlaca(String placa){
		con.conect();
		String query = "SELECT TIPO_VEHICULO,CILINDRAJE,FECHA_INGRESO,REGISTRO_PARQUEADERO_ID FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl AND PLACA='"+placa+"'";		
		ResultSet rs = con.executeQuery(query);
		try {
			if(rs.next()){
				String tipoVehiculo = rs.getString(1).substring(0, 1);
				Vehicle vehiculo;
				if(tipoVehiculo.equals("C")){
					vehiculo= new Car(placa);
				}else{
					int cilindraje= Integer.parseInt(rs.getString(2));
					vehiculo = new Motorcycle(placa,cilindraje);
				}
				Date fechaIngreso=null;
				fechaIngreso = new SimpleDateFormat(DATEFORMAT).parse(rs.getString(3));
				int registroVehiculoId = Integer.parseInt(rs.getString(4));
				con.close();
				return new ParkingRegister(registroVehiculoId,vehiculo,fechaIngreso,null,0);
			}
		} catch (SQLException|ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		} 
		return null;
	}
	
	public boolean registrarIngreso(ParkingRegister registroParqueadero){
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
		con.conect();
		try {
			con.executeUpdate(insert);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void registraSalida(ParkingRegister registroParqueadero) {
		int registroVehiculoId = registroParqueadero.getRegistroVehiculoId();
		double costo = registroParqueadero.getCostoParqueadero();
		DateFormat df = new SimpleDateFormat(DATEFORMAT);
		String fechaSalida = df.format(registroParqueadero.getFechaSalida());
		con.conect();
		String update = "UPDATE REGISTRO_PARQUEADERO SET COSTO_PARQUEADERO="+costo+", FECHA_SALIDA='"+fechaSalida+"' WHERE REGISTRO_PARQUEADERO_ID="+registroVehiculoId;
		try {
			con.executeUpdate(update);
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
	}

	public List<ParkingRegister> obtenerVehiculosParqueados() {
		con.conect();
		String query = "SELECT PLACA,TIPO_VEHICULO,FECHA_INGRESO FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl";
		ResultSet rs = con.executeQuery(query);	
		
		List<ParkingRegister> registros = new ArrayList();
		Vehicle vehiculoActual;
		String placaActual;
		String tipoVehiculo;
		Date fechaIngreso;
		try {
			while (rs.next()) {
				placaActual=rs.getString(1);
				tipoVehiculo=rs.getString(2);
				fechaIngreso=new SimpleDateFormat(DATEFORMAT).parse(rs.getString(3).substring(0,19));
				
				vehiculoActual=new Vehicle(placaActual);
				
				registros.add(new ParkingRegister(vehiculoActual,fechaIngreso,tipoVehiculo));				
			}
			con.close();
		} catch (SQLException|ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return registros;
	}
	
	public int contarVehiculosTipo(String tipo){
		con.conect();
		String query = "SELECT COUNT(PLACA) AS TOTAL FROM REGISTRO_PARQUEADERO WHERE FECHA_SALIDA IS NULl AND TIPO_VEHICULO='"+tipo+"'";
		ResultSet rs = con.executeQuery(query);
		int total=0;
		try {
			if(rs.next()){
				total = rs.getInt("TOTAL");
			}
			else{
				total = 0;
			}
			con.close();
			return total;
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return 0;
	}
}
