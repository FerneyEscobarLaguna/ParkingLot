package co.ceiba.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

import co.ceiba.service.IVehicle;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Motorcycle.class, name = "motorcycle"),
    @JsonSubTypes.Type(value = Car.class, name = "car"),
    @JsonSubTypes.Type(value = Vehicle.class, name = "Vehicle")
})//Se agrega propiedad type para identificar la clase de objeto enviada por json

public class Vehicle implements IVehicle{
	private String placa;
	private String tipoVehiculo;

	public Vehicle() {		
	}
	
	public Vehicle(String placa) {
		this.placa = placa;
	}
	
	public Vehicle(String placa, String tipoVehiculo) {
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getParkingCost(int hoursParking) {
		return 0;
	}	
}
