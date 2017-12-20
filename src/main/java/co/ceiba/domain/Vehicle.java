package co.ceiba.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Motorcycle.class, name = "motorcycle"),
    @JsonSubTypes.Type(value = Car.class, name = "car"),
    @JsonSubTypes.Type(value = Vehicle.class, name = "Vehicle")
})//Se agrega propiedad type para identificar la clase de objeto enviada por json

public class Vehicle {
	private String placa;

	public Vehicle() {		
	}
	
	public Vehicle(String placa) {
		this.placa = placa;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}	
}
