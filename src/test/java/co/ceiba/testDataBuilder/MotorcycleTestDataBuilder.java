package co.ceiba.testDataBuilder;

import co.ceiba.domain.Motorcycle;

public class MotorcycleTestDataBuilder {
	private final String PLACA = "FALS-123";
	private final int CILINDRAJE = 600;
	
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
		return new Motorcycle(this.placa,this.cilindraje);
	}
}
