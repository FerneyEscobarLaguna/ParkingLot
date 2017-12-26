package co.ceiba.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingAvailability;
import co.ceiba.service.IParkingRegisterService;
import co.ceiba.service.Tool;
import persistencia.repositorio.ParkingAvailability;
import persistencia.repositorio.ParkingRegisterService;

public class Vigilant {
	public static final String VEHICULO_INGRESADO = "El vehiculo ingreso al parqueadero satisfactoriamente";
	public static final String PLACA_NO_HABIL = "El vehiculo no puede ingresar al parqeadero, las placas que inician con la letra 'A' solo pueden ser registradas los dias Domingo y Lunes";
	public static final String PARQUEADERO_LLENO = "No hay lugares disponibles para el vehiculo";
	public static final String VEHICULO_PARQUEADO = "El vehiculo ya se encuentra en el parqueadero";
	public static final String ERROR = "Ha ocurrido un error durante el registro del vehiculo, intente de nuevo";
	
	private IParkingRegisterService repositorioParqueadero;
	private IParkingAvailability repositorioDisponivilidad;
	
	public Vigilant(IParkingRegisterService repositorioParqueadero, IParkingAvailability disponivilidadParqueadero) {
		this.repositorioParqueadero = repositorioParqueadero;
		this.repositorioDisponivilidad = disponivilidadParqueadero;
	}
	
	public Vigilant(){
		this.repositorioParqueadero = new ParkingRegisterService(new Conection());
		this.repositorioDisponivilidad = new ParkingAvailability(new Conection());
	}
	
	public String registrarIngresoVehiculo(Vehicle vehiculo){
		String placa = vehiculo.getPlaca();
		Date fechaIngreso = new Date();
		
		if(!validarPlacaHabil(placa,fechaIngreso))
			return PLACA_NO_HABIL;
		
		if(vehiculoParqueado(placa)){
			return VEHICULO_PARQUEADO;
		}
		
		String tipoVehiculo=vehiculo.getTipoVehiculo();
		
		if(!hayCupoParqueadero(tipoVehiculo))
			return PARQUEADERO_LLENO;
		
		if(registrarIngreso(vehiculo))		
			return VEHICULO_INGRESADO;
		else
			return ERROR;
	}
	
	private boolean registrarIngreso(Vehicle vehiculo){
		ParkingRegister registroParqueadero = new ParkingRegister(vehiculo);
		return repositorioParqueadero.registrarIngreso(registroParqueadero);
	}
	
	private boolean hayCupoParqueadero(String tipoVehiculo){
		return!(repositorioParqueadero.contarVehiculosTipo(tipoVehiculo)>=
				repositorioDisponivilidad.obtenerDisponibilidadTipoVehiculo(tipoVehiculo));
	}
	
	public boolean validarPlacaHabil(String placa, Date fechaIngreso){
		Calendar ca = Calendar.getInstance();
		ca.setTime(fechaIngreso);
		int day = ca.get(Calendar.DAY_OF_WEEK);
		return placa.substring(0, 1).equals("A") && day!=1 && day!=2?false:true;
	}
	
	public double registrarSalidaVehiculo(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		
		if(registroParqueadero==null)
			return 0;
		
		Vehicle vehiculo=registroParqueadero.getVehiculo();
		
		Date fechaIngreso = registroParqueadero.getFechaIngreso();
		Date fechaSalida = new Date();
		
		int horasEnParqueadero = Tool.diferenciaHoras(fechaIngreso,fechaSalida);
		
		double valorcobrar= calcularCosto(horasEnParqueadero,vehiculo);
		
		registroParqueadero.setCostoParqueadero(valorcobrar);
		registroParqueadero.setFechaSalida(fechaSalida);
		
		repositorioParqueadero.registraSalida(registroParqueadero);
		
		return valorcobrar;
	}
	
	private double calcularCosto(int horasEnParqueadero, Vehicle vehiculo) {		
		return vehiculo.getParkingCost(horasEnParqueadero);
	}

	public boolean vehiculoParqueado(String placa){
		ParkingRegister registroParqueadero = repositorioParqueadero.obtenerVehiculoParqueadoPlaca(placa);
		return registroParqueadero !=null?true:false;
	}
	
	public List<ParkingRegister> consultarVehiculos(){
		return repositorioParqueadero.obtenerVehiculosParqueados();
	}
}
