package com.rest;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;
import co.ceiba.domain.Vigilant;

@CrossOrigin
@RestController
public class VigilantServices {
	@RequestMapping(value = "/consultarVehiculos", method=RequestMethod.GET)
	public List<ParkingRegister> consultarVehiculos(){
		List<ParkingRegister> registros = new Vigilant().consultarVehiculos();
		return registros;
	}
	
	@RequestMapping(value = "/registrarIngreso", method=RequestMethod.POST)
	public String registrarVehiculo(@RequestBody Vehicle vehiculo) {
		String respuesta="[{\"res\":\""+new Vigilant().registrarIngresoVehiculo(vehiculo)+"\"}]";
		return respuesta;
	}
	
	@RequestMapping(value = "/registrarSalida", method=RequestMethod.POST)
	public Double registrarSalida(@RequestBody Vehicle vehiculo){
		return new Vigilant().registrarSalidaVehiculo(vehiculo.getPlaca());
	}
}
