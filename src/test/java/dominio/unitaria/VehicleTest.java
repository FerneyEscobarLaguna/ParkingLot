package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import co.ceiba.domain.Vehicle;
import co.ceiba.testDataBuilder.CarTestDataBuilder;

public class VehicleTest {
	private static final String PLACA = "PLACA-123";
	@Test
	public void crearVehiculoTest() {		
		// act
		Vehicle vehicle = new Vehicle(PLACA);

		// assert
		assertEquals(PLACA, vehicle.getPlaca());
	}
	
	@Test
	public void modificarVehiculoTest() {		
		// arrange
		String placaTemporal = "TEMPO-123";

		// act
		Vehicle vehicle = new Vehicle(placaTemporal);
		vehicle.setPlaca(PLACA);

		// assert
		assertEquals(PLACA, vehicle.getPlaca());
	}
}
