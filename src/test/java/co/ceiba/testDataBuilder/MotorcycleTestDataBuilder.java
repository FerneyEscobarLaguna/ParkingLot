package co.ceiba.testDataBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.ceiba.domain.Motorcycle;
import co.ceiba.service.IParkingRate;

public class MotorcycleTestDataBuilder {
	private final String PLACA = "FALS-123";
	private final int CILINDRAJE = 110;
	
	private String placa;
	private int cilindraje;
	
	public MotorcycleTestDataBuilder(){
		this.placa=PLACA;
		this.cilindraje=CILINDRAJE;
	}
	
	public MotorcycleTestDataBuilder conPlaca(String placa){
		this.placa=placa;
		return this;
	}
	
	public MotorcycleTestDataBuilder conCilindraje(int cilindraje){
		this.cilindraje=cilindraje;
		return this;
	}
	
	public Motorcycle build(){
		IParkingRate parkingRate = mock(IParkingRate.class);
		when(parkingRate.obtenerTarifa("M", "H")).thenReturn((double) 500);
		when(parkingRate.obtenerTarifa("M", "D")).thenReturn((double) 4000);
		return new Motorcycle(this.placa,this.cilindraje,parkingRate);
	}
}
