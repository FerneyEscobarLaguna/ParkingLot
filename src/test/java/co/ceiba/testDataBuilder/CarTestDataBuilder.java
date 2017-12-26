package co.ceiba.testDataBuilder;

import co.ceiba.domain.Car;
import co.ceiba.service.IParkingRate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarTestDataBuilder {
	private final String PLACA = "FALS-123";
	
	private String placa;
	
	public CarTestDataBuilder(){
		this.placa=PLACA;
	}
	
	public CarTestDataBuilder conPlaca(String placa){
		this.placa=placa;
		return this;
	}
	
	public Car build(){
		IParkingRate parkingRate = mock(IParkingRate.class);
		when(parkingRate.obtenerTarifa("C", "H")).thenReturn((double) 1000);
		when(parkingRate.obtenerTarifa("C", "D")).thenReturn((double) 8000);
		return new Car(this.placa,parkingRate);
	}
}
