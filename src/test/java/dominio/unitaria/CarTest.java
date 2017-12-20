package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import co.ceiba.domain.Car;
import co.ceiba.testDataBuilder.CarTestDataBuilder;

public class CarTest {
	private static final String PLACA = "PLACA-123";
	@Test
	public void crearCarroTest() {
		
		// arrange
		CarTestDataBuilder carTestDataBuilder = new CarTestDataBuilder().
				conPlaca(PLACA);

		// act
		Car car = carTestDataBuilder.build();

		// assert
		assertEquals(PLACA, car.getPlaca());
	}
	
	@Test
	public void modificarCarroTest() {
		
		// arrange
		CarTestDataBuilder carTestDataBuilder = new CarTestDataBuilder();

		// act
		Car car = carTestDataBuilder.build();
		car.setPlaca(PLACA);

		// assert
		assertEquals(PLACA, car.getPlaca());
	}
}
