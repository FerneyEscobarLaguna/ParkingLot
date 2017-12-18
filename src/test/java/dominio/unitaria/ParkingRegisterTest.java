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
	@Test
	public void crearRegistroParqueadero(){
		// arrange
		ParkingRegisterTestDataBuilder registroParqueaderoTDB = new ParkingRegisterTestDataBuilder();	
		// act 
		ParkingRegister registroParqueadero = registroParqueaderoTDB.
													conFechaIngreso(FECHAINGRESO).
													conFechaSalida(FECHASALIDA).
													conCostoParqueadero(COSTOPARQUEADERO).
													build();
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(registroParqueadero.getCostoParqueadero(),COSTOPARQUEADERO,0);
	}
}
