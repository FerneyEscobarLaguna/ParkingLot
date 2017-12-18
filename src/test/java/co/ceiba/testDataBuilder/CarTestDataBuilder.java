package co.ceiba.testDataBuilder;

import co.ceiba.domain.Car;

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
		return new Car(this.placa);
	}
}
