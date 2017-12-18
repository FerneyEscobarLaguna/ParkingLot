package co.ceiba.domain;

import java.util.Date;

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
		
		if(placa.substring(0, 1).equals("A") && fechaIngreso.getDay()!=0 && fechaIngreso.getDay()!=1)
			return PLACA_NO_HABIL;
		
		if(vehiculoParqueado(placa)){
			return VEHICULO_PARQUEADO;
		}
		
		String tipo_vehiculo="";
		if(vehiculo.getClass().equals(Car.class)){
			tipo_vehiculo="C";
			if(repositorioParqueadero.contarVehiculosTipo(tipo_vehiculo)>=20)
				return PARQUEADERO_LLENO;
		}else{
			tipo_vehiculo="M";
			if(repositorioParqueadero.contarVehiculosTipo(tipo_vehiculo)>=10)
				return PARQUEADERO_LLENO;
		}	
		
		ParkingRegister registroParqueadero = new ParkingRegister(vehiculo);
		try{
			repositorioParqueadero.registrarIngreso(registroParqueadero);
		}catch(Exception e){
			return ERROR;
		}
		return VEHICULO_INGRESADO;
	}
	
	public double registrarSalidaVehiculo(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		
		if(registroParqueadero==null)
			return 0;
		
		String tipoVehiculo;
		if(registroParqueadero.getVehiculo().getClass().equals(Car.class))
			tipoVehiculo = "C";
		else
			tipoVehiculo = "M";
		
		Date fechaIngreso = registroParqueadero.getFechaIngreso();
		Date fechaSalida = new Date();
		
		long diff = fechaSalida.getTime() - fechaIngreso.getTime();
		long segundos = diff / 1000;
		long minutos = segundos / 60;
		int horasEnParqueadero =(int) Math.round(minutos / 60);//Diferencia en milisegundos se convierte a horas dividiendo entre mil, luego entre 60 segundos y luego entre 60 minutos
				
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
			int cilindraje = ((Motorcycle)registroParqueadero.getVehiculo()).getCilindraje();
			valorcobrar=(diasCobrar*600) + (horasCobrar*500);
			if(cilindraje>500)
				valorcobrar+=2000;
		}
		registroParqueadero.setCostoParqueadero(valorcobrar);
		registroParqueadero.setFechaSalida(fechaSalida);
		repositorioParqueadero.registraSalida(registroParqueadero);
		return valorcobrar;
	}
	
	public boolean vehiculoParqueado(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		if(registroParqueadero != null)
			return true;
		return false;
	}
	
	public String consultarVehiculos() throws Exception{
		Tool tool = new Tool();
		return tool.convertToJSON(repositorioParqueadero.obtenerVehiculosParqueados());
	}
}
