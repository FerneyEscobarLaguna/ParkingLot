package co.ceiba.domain;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingRate;
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
		int diasCobrar=0;
		int horasCobrar=0;
		double valorcobrar=0;
		if(hoursParking>=9){
			diasCobrar=hoursParking/24;
			horasCobrar=hoursParking%24;
			if(horasCobrar>=9){
				diasCobrar++;
				horasCobrar=0;
			}
		}else{
			diasCobrar=0;
			horasCobrar=hoursParking;
		}
		
		double tarifaHora = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "H");
		double tarifaDia = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "D");
		
		valorcobrar=(diasCobrar*tarifaDia) + (horasCobrar*tarifaHora);
		return valorcobrar;
	}	
}
