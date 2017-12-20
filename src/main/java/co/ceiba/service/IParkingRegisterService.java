package co.ceiba.service;

import java.sql.ResultSet;

import co.ceiba.domain.ParkingRegister;

public interface IParkingRegisterService {
	/**
	 * Permite obtener vehiculo que se encuentre en el parqueadero
	 * @param placa
	 */
	ParkingRegister obtenerVehiculoParqueadoPlaca(String placa);
	
	/**
	 * Permite registrar el ingreso de un vehiculo al parqueadero
	 * @param registroParqueadero
	 */
	void registrarIngreso(ParkingRegister registroParqueadero);
	
	/**
	 * Permite registrar la salida de un vehiculo del parqueadero
	 * @param registroParqueadero
	 */
	void registraSalida(ParkingRegister registroParqueadero);
	
	/**
	 * Permite consultar los vehiculos en el parqueadero
	 * @param registroParqueadero
	 */
	ResultSet obtenerVehiculosParqueados();
	
	/**
	 * Permite consultar la cantidad de vehiculos en el parqueadero de un tipo espesifico
	 * @param tipo_vehiculo
	 */
	int contarVehiculosTipo(String tipo);
}
