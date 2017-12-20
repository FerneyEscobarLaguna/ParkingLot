package dominio.unitaria;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import co.ceiba.domain.ParkingRegister;
import co.ceiba.testDataBuilder.ParkingRegisterTestDataBuilder;

public class ParkingRegisterTest {
	private final Date FECHAINGRESO = new Date();
	private final Date FECHASALIDA = new Date();
	private final double COSTOPARQUEADERO = 500;
	private final int REGISTROVEHICULOID = 1;
	private final String TIPOVEHICULO = "M";
	@Test
	public void crearRegistroParqueadero(){
		// arrange
		ParkingRegisterTestDataBuilder registroParqueaderoTDB = new ParkingRegisterTestDataBuilder();	
		// act 
		ParkingRegister registroParqueadero = registroParqueaderoTDB.
													conFechaIngreso(FECHAINGRESO).
													conFechaSalida(FECHASALIDA).
													conCostoParqueadero(COSTOPARQUEADERO).
													conRegistroVehiculoId(REGISTROVEHICULOID).
													conTipoVehiculo(TIPOVEHICULO).
													build();
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
		assertEquals(0,registroParqueadero.getCostoParqueadero(),COSTOPARQUEADERO);
		assertEquals(0,registroParqueadero.getRegistroVehiculoId(),REGISTROVEHICULOID);
	}
	
	@Test
	public void modificarRegistroParqueadero(){
		// arrange
		ParkingRegisterTestDataBuilder registroParqueaderoTDB = new ParkingRegisterTestDataBuilder();	
		// act 
		ParkingRegister registroParqueadero = registroParqueaderoTDB.build();
		registroParqueadero.setFechaIngreso(FECHAINGRESO);
		registroParqueadero.setFechaSalida(FECHASALIDA);
		registroParqueadero.setCostoParqueadero(COSTOPARQUEADERO);
		registroParqueadero.setRegistroVehiculoId(REGISTROVEHICULOID);
		registroParqueadero.setTipoVehiculo(TIPOVEHICULO);
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(0,registroParqueadero.getCostoParqueadero(),COSTOPARQUEADERO);
		assertEquals(0,registroParqueadero.getRegistroVehiculoId(),REGISTROVEHICULOID);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
	}
}
