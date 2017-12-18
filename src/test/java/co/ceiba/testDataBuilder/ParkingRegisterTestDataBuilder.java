package co.ceiba.testDataBuilder;

import java.util.Date;

import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;

public class ParkingRegisterTestDataBuilder {
	private final Vehicle VEHICULO = new CarTestDataBuilder().build();
	private final Date FECHAINGRESO = new Date();
	private final Date FECHASALIDA = new Date();
	private final double COSTOPARQUEADERO = 0;

	private Vehicle vehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private double costoParqueadero;
	
	public ParkingRegisterTestDataBuilder(){
		this.vehiculo=VEHICULO;
		this.fechaIngreso=FECHAINGRESO;
		this.fechaSalida = FECHASALIDA;
		this.costoParqueadero = COSTOPARQUEADERO;
	}
	
	public ParkingRegisterTestDataBuilder conVehiculo(Vehicle vehiculo){
		this.vehiculo=vehiculo;
		return this;
	} 
	
	public ParkingRegisterTestDataBuilder conFechaIngreso(Date fechaIngreso){
		this.fechaIngreso=fechaIngreso;
		return this;
	}
	
	public ParkingRegisterTestDataBuilder conFechaSalida(Date fechaSalida){
		this.fechaSalida=fechaSalida;
		return this;
	}
	
	public ParkingRegisterTestDataBuilder conCostoParqueadero(double costoParqueadero){
		this.costoParqueadero=costoParqueadero;
		return this;
	}
	
	public ParkingRegister build(){
		return new ParkingRegister(this.vehiculo,this.fechaIngreso,this.fechaSalida,this.costoParqueadero);
	}
}
