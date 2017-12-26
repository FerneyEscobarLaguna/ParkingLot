package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import co.ceiba.domain.Car;
import co.ceiba.service.IParkingRate;
import co.ceiba.testDataBuilder.CarTestDataBuilder;
import persistencia.repositorio.ParkingRate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		assertEquals("C", car.getTipoVehiculo());
	}
	
	@Test
	public void crearCarroVacioTest() {
		// act
		Car car = new Car();
		car.setPlaca(PLACA);

		// assert
		assertEquals(PLACA, car.getPlaca());
		assertEquals("C", car.getTipoVehiculo());
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
		assertEquals("C", car.getTipoVehiculo());
	}
	
	@Test
	public void getParkingCostTest() {
		
		// arrange
		IParkingRate parkingRate = mock(IParkingRate.class); 
		when(parkingRate.obtenerTarifa("C", "H")).thenReturn((double) 1000);
		when(parkingRate.obtenerTarifa("C", "D")).thenReturn((double) 8000);
		Car car = new Car("PRU-123", parkingRate);

		// act
		double costo = car.getParkingCost(27);

		// assert
		assertEquals(0, 11000,costo);
	}
}
