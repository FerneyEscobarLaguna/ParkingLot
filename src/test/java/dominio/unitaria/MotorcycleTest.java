package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.service.IParkingRate;
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
		assertEquals("M", motorcycle.getTipoVehiculo());
	}
	
	@Test
	public void crearMotoTestPlaca() {		
		// arrange

		// act
		Motorcycle motorcycle = new Motorcycle(PLACA);

		// assert
		assertEquals(PLACA, motorcycle.getPlaca());
		assertEquals("M", motorcycle.getTipoVehiculo());
	}
	
	@Test
	public void crearMotoTestVacio() {		
		// arrange

		// act
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setPlaca(PLACA);

		// assert
		assertEquals(PLACA, motorcycle.getPlaca());
		assertEquals("M", motorcycle.getTipoVehiculo());
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
		assertEquals("M", motorcycle.getTipoVehiculo());
	}
	
	@Test
	public void getParkingCostTest() {
		
		// arrange
		IParkingRate parkingRate = mock(IParkingRate.class); 
		when(parkingRate.obtenerTarifa("M", "H")).thenReturn((double) 500);
		when(parkingRate.obtenerTarifa("M", "D")).thenReturn((double) 4000);
		Motorcycle motorcycle = new Motorcycle("PRU-123", 110,parkingRate);

		// act
		double costo = motorcycle.getParkingCost(33);

		// assert
		assertEquals(8000,costo,0);
	}
}
