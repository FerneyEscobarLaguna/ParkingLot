package dominio.unitaria;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vigilant;
import co.ceiba.service.IParkingAvailability;
import co.ceiba.service.IParkingRegisterService;
import co.ceiba.testDataBuilder.CarTestDataBuilder;
import co.ceiba.testDataBuilder.MotorcycleTestDataBuilder;

import static org.junit.Assert.*;

public class VigilantTest {
	@Test
	public void registrarIngresoVehiculoCarroTest(){
		// arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(null);
		when(repositorioParqueadero.contarVehiculosTipo("C")).thenReturn(0);
		when(repositorioParqueadero.registrarIngreso(Mockito.any(ParkingRegister.class))).thenReturn(true);
		
		IParkingAvailability repositorioDisponibilidad = mock(IParkingAvailability.class);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("C")).thenReturn(20);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("M")).thenReturn(10);
				
		Vigilant vigilante = new Vigilant(repositorioParqueadero,repositorioDisponibilidad);
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(carro);
		//assert
		assertEquals(Vigilant.VEHICULO_INGRESADO,mensaje);
	}
	
	@Test
	public void registrarIngresoVehiculoMotoTest(){
		// arrange
		MotorcycleTestDataBuilder motoTestDataBuilder = new MotorcycleTestDataBuilder();
		Motorcycle moto = motoTestDataBuilder.build();
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(moto.getPlaca())).thenReturn(null);
		when(repositorioParqueadero.contarVehiculosTipo("M")).thenReturn(0);
		when(repositorioParqueadero.registrarIngreso(Mockito.any(ParkingRegister.class))).thenReturn(true);
		
		IParkingAvailability repositorioDisponibilidad = mock(IParkingAvailability.class);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("C")).thenReturn(20);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("M")).thenReturn(10);
				
		Vigilant vigilante = new Vigilant(repositorioParqueadero,repositorioDisponibilidad);
		
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(moto);
		//assert
		assertEquals(Vigilant.VEHICULO_INGRESADO,mensaje);
	}
	
	@Test
	public void vehiculoParqueadoTest(){
		// arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro));
		
		IParkingAvailability repositorioDisponibilidad = mock(IParkingAvailability.class);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("C")).thenReturn(20);
		when(repositorioDisponibilidad.obtenerDisponibilidadTipoVehiculo("M")).thenReturn(10);
				
		Vigilant vigilante = new Vigilant(repositorioParqueadero,repositorioDisponibilidad);
		// act 
		boolean estaParqueado = vigilante.vehiculoParqueado(carro.getPlaca());
		//assert
		assertTrue(estaParqueado);
	}
}
