package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import co.ceiba.domain.Motorcycle;
import co.ceiba.testDataBuilder.MotorcycleTestDataBuilder;

public class MotorcycleTest {
	private static final String PLACA = "PLACA-123";
	private static final int CILINDRAJE = 250;
	
	@Test
	public void crearMotoTest() {
		
		// arrange
		MotorcycleTestDataBuilder motorcycleTestDataBuilder = new MotorcycleTestDataBuilder().
				conPlaca(PLACA).
				conCilindraje(CILINDRAJE);

		// act
		Motorcycle motorcycle = motorcycleTestDataBuilder.build();

		// assert
		assertEquals(PLACA, motorcycle.getPlaca());
		assertEquals(CILINDRAJE, motorcycle.getCilindraje());
	}
	
	@Test
	public void modificarMotoTest() {
		
		// arrange
		MotorcycleTestDataBuilder motorcycleTestDataBuilder = new MotorcycleTestDataBuilder();

		// act
		Motorcycle motorcycle = motorcycleTestDataBuilder.build();
		motorcycle.setPlaca(PLACA);
		motorcycle.setCilindraje(CILINDRAJE);

		// assert
		assertEquals(PLACA, motorcycle.getPlaca());
		assertEquals(CILINDRAJE, motorcycle.getCilindraje());
	}
}
