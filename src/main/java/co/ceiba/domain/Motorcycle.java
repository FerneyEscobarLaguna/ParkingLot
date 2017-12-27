package co.ceiba.domain;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingRate;
import co.ceiba.service.Tool;
import persistencia.repositorio.ParkingRate;

public class Motorcycle extends Vehicle{
	private int cilindraje;
	private IParkingRate repositorioTarifa;

	public Motorcycle(String placa, int cilindraje) {
		super(placa,"M");
		this.cilindraje = cilindraje;
		this.repositorioTarifa = new ParkingRate(new Conection());
	}
	
	public Motorcycle(String placa) {
		super(placa,"M");
		this.repositorioTarifa = new ParkingRate(new Conection());
	}
	
	public Motorcycle() {
		super(null,"M");
		this.repositorioTarifa = new ParkingRate(new Conection());
	}
	
	public Motorcycle(String placa, int cilindraje, IParkingRate repositorioTarifa) {
		super(placa,"M");
		this.cilindraje = cilindraje;
		this.repositorioTarifa = repositorioTarifa;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}	
	
	@Override
	public double getParkingCost(int hoursParking) {
		int diasCobrar=Tool.calcularHorasDiasCobro(hoursParking, 9, true);
		int horasCobrar=Tool.calcularHorasDiasCobro(hoursParking, 9, false);
		double valorcobrar=0;
		
		double tarifaHora = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "H");
		double tarifaDia = repositorioTarifa.obtenerTarifa(this.getTipoVehiculo(), "D");
		
		valorcobrar=(diasCobrar*tarifaDia) + (horasCobrar*tarifaHora);
		
		if(this.cilindraje>500)
			valorcobrar+=2000;
		return valorcobrar;
	}	
}
