package com.rest;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
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
		String respuesta=new Vigilant().registrarIngresoVehiculo(vehiculo);
		System.out.println(respuesta);
		return respuesta;
	}
}
