package co.ceiba.domain;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingRate;
import co.ceiba.service.Tool;
import persistencia.repositorio.ParkingRate;

public class Car extends Vehicle{

	private IParkingRate repositorioTarifa;
	
	public Car(String placa) {
		super(placa,"C"); 
		this.repositorioTarifa = new ParkingRate(new Conection());
	}	
	
	public Car() { 
		super(null,"C");
		this.repositorioTarifa = new ParkingRate(new Conection());
	}
	
	public Car(String placa, IParkingRate repositorioTarifa) {
		super(placa,"C"); 
		this.repositorioTarifa = repositorioTarifa;
	}	
	
	@Override
	public double getParkingCost(int hoursParking) {
		int diasCobrar=Tool.calcularHorasDiasCobro(hoursParking, 9, true);
		int horasCobrar=Tool.calcularHorasDiasCobro(hoursParking, 9, false);
		double valorcobrar=0;
		
		double tarifaHora = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "H");
		double tarifaDia = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "D");
		
		valorcobrar=(diasCobrar*tarifaDia) + (horasCobrar*tarifaHora);
		return valorcobrar;
	}	
}
