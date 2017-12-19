package co.ceiba.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import co.ceiba.service.IParkingRegisterService;
import co.ceiba.service.Tool;
import persistencia.repositorio.ParkingRegisterService;

public class Vigilant {
	public static final String VEHICULO_INGRESADO = "El vehiculo ingreso al parqueadero satisfactoriamente";
	public static final String PLACA_NO_HABIL = "El vehiculo no puede ingresar al parqeadero, las placas que inician con la letra 'A' solo pueden ser registradas los dias Domingo y Lunes";
	public static final String PARQUEADERO_LLENO = "No hay lugares disponibles para el vehiculo";
	public static final String VEHICULO_PARQUEADO = "El vehiculo ya se encuentra en el parqueadero";
	public static final String ERROR = "Ha ocurrido un error durante el registro del vehiculo, intente de nuevo";
	
	private IParkingRegisterService repositorioParqueadero;
	
	public Vigilant(IParkingRegisterService repositorioParqueadero) {
		this.repositorioParqueadero = repositorioParqueadero;
	}
	
	public Vigilant(){
		this.repositorioParqueadero = new ParkingRegisterService();
	}
	
	public String registrarIngresoVehiculo(Vehicle vehiculo){
		String placa = vehiculo.getPlaca();
		Date fechaIngreso = new Date();
		
		if(!validarPlacaHabil(placa,fechaIngreso))
			return PLACA_NO_HABIL;
		
		if(vehiculoParqueado(placa)){
			return VEHICULO_PARQUEADO;
		}
		
		String tipo_vehiculo=definirTipoVehiculo(vehiculo);
		
		if(!hayCupoParqueadero(tipo_vehiculo))
			return PARQUEADERO_LLENO;
		
		if(registrarIngreso(vehiculo))		
			return VEHICULO_INGRESADO;
		else
			return ERROR;
	}
	
	private String definirTipoVehiculo(Vehicle vehiculo){
		String tipo_vehiculo="";
		if(vehiculo.getClass().equals(Car.class)){
			tipo_vehiculo="C";
		}else{
			tipo_vehiculo="M";
		}
		return tipo_vehiculo;
	}
	
	private boolean registrarIngreso(Vehicle vehiculo){
		ParkingRegister registroParqueadero = new ParkingRegister(vehiculo);
		try{
			repositorioParqueadero.registrarIngreso(registroParqueadero);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	private boolean hayCupoParqueadero(String tipo_vehiculo){
		if(tipo_vehiculo.equals("C")){
			if(repositorioParqueadero.contarVehiculosTipo(tipo_vehiculo)>=20)
				return false;
		}else{
			if(repositorioParqueadero.contarVehiculosTipo(tipo_vehiculo)>=10)
				return false;
		}
		return true;
	}
	
	private boolean validarPlacaHabil(String placa, Date fechaIngreso){
		if(placa.substring(0, 1).equals("A") && fechaIngreso.getDay()!=0 && fechaIngreso.getDay()!=1)
			return false;
		return true;
	}
	
	public double registrarSalidaVehiculo(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		
		if(registroParqueadero==null)
			return 0;
		
		Vehicle vehiculo=registroParqueadero.getVehiculo();
		
		String tipoVehiculo = definirTipoVehiculo(vehiculo);
		
		Date fechaIngreso = registroParqueadero.getFechaIngreso();
		Date fechaSalida = new Date();
		
		int horasEnParqueadero = Tool.diferenciaHoras(fechaIngreso,fechaSalida);
		
		double valorcobrar= calcularCosto(horasEnParqueadero,tipoVehiculo,vehiculo);
		
		registroParqueadero.setCostoParqueadero(valorcobrar);
		registroParqueadero.setFechaSalida(fechaSalida);
		
		repositorioParqueadero.registraSalida(registroParqueadero);
		
		return valorcobrar;
	}
	
	private double calcularCosto(int horasEnParqueadero, String tipoVehiculo, Vehicle vehiculo) {
		int diasCobrar=0;
		int horasCobrar=0;
		double valorcobrar=0;
		if(horasEnParqueadero>9){
			if(horasEnParqueadero>24){
				diasCobrar=horasEnParqueadero/24;
				horasCobrar=horasEnParqueadero%24;
			}else{
				diasCobrar=1;
				horasCobrar=0;				
			}
		}else{
			diasCobrar=0;
			if(horasEnParqueadero<1)
				horasCobrar=1;
			else
				horasCobrar=horasEnParqueadero;
		}
		
		if(tipoVehiculo.equals("C")){
			valorcobrar=(diasCobrar*8000) + (horasCobrar*1000);
		}else{
			int cilindraje = ((Motorcycle)vehiculo).getCilindraje();
			valorcobrar=(diasCobrar*600) + (horasCobrar*500);
			if(cilindraje>500)
				valorcobrar+=2000;
		}
		
		return valorcobrar;
	}

	public boolean vehiculoParqueado(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		if(registroParqueadero != null)
			return true;
		return false;
	}
	
	public List<ParkingRegister> consultarVehiculos(){
		ResultSet rs = repositorioParqueadero.obtenerVehiculosParqueados();
		
		List<ParkingRegister> registros = new ArrayList();
		Vehicle vehiculoActual;
		String placaActual;
		String tipoVehiculo;
		Date fechaIngreso;
		try {
			while (rs.next()) {
				//tipoVehicoloActual=rs.getString(0);
				placaActual=rs.getString(1);
				tipoVehiculo=rs.getString(2);
				fechaIngreso=(Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(3).substring(0,19));
				
				vehiculoActual=new Vehicle(placaActual);
				
				registros.add(new ParkingRegister(vehiculoActual,fechaIngreso,tipoVehiculo));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return registros;
	}
}
