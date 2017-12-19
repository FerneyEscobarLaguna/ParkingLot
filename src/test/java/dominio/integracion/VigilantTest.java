package dominio.integracion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vigilant;
import co.ceiba.service.IParkingRegisterService;
import co.ceiba.testDataBuilder.CarTestDataBuilder;
import co.ceiba.testDataBuilder.MotorcycleTestDataBuilder;

public class VigilantTest {
	@Test
	public void registrarIngresoMotosSimultaneasTest(){
		// arrange
		MotorcycleTestDataBuilder motoTestDataBuilder = new MotorcycleTestDataBuilder();
		Motorcycle moto = motoTestDataBuilder.conPlaca("SIMU-123").build();

		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.contarVehiculosTipo("M")).thenReturn(10);
		
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(moto);
		//assert
		assertEquals(mensaje,Vigilant.PARQUEADERO_LLENO);
	}
	
	@Test
	public void registrarIngresoCarrosSimultaneasTest(){
		// arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.conPlaca("SIMU-123").build();

		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.contarVehiculosTipo("C")).thenReturn(20);
		
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(carro);
		//assert
		assertEquals(mensaje,Vigilant.PARQUEADERO_LLENO);
	}
	
	@Test
	public void registrarIngresoPlacaATest(){
		// arrange
		String placaConA = "AAA-123";
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.conPlaca(placaConA).build();
		Vigilant vigilante = new Vigilant();
		// act
		String mensaje = vigilante.registrarIngresoVehiculo(carro);
		//assert
		assertEquals(mensaje,Vigilant.PLACA_NO_HABIL);
	}
	
	@Test
	public void registrarSalidaCarroTest(){
		//Arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -8);//Se restan dos horas a la fecha actual
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(carro.getPlaca());
		//Assert
		assertEquals(costo,8000,0);
	}
	
	@Test
	public void registrarSalidaCarroDiasTest(){
		//Arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -27);
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(carro.getPlaca());
		//Assert
		assertEquals(costo,11000,0);
	}
	
	@Test
	public void registrarSalidaMotoTest(){
		//Arrange
		MotorcycleTestDataBuilder motoTestDataBuilder = new MotorcycleTestDataBuilder();
		Motorcycle moto = motoTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -8);//Se restan dos horas a la fecha actual
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(moto.getPlaca())).thenReturn(new ParkingRegister(moto,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(moto.getPlaca());
		//Assert
		assertEquals(costo,4000,0);
	}
	
	@Test
	public void registrarSalidaMotoDiasTest(){
		//Arrange
		MotorcycleTestDataBuilder motoTestDataBuilder = new MotorcycleTestDataBuilder();
		Motorcycle moto = motoTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -27);
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(moto.getPlaca())).thenReturn(new ParkingRegister(moto,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(moto.getPlaca());
		//Assert
		assertEquals(costo,2100,0);
	}
	
	@Test
	public void registrarSalidaMotoCilindrajeTest(){
		//Arrange
		MotorcycleTestDataBuilder motoTestDataBuilder = new MotorcycleTestDataBuilder();
		Motorcycle moto = motoTestDataBuilder.conCilindraje(600).build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -27);
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(moto.getPlaca())).thenReturn(new ParkingRegister(moto,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(moto.getPlaca());
		//Assert
		assertEquals(costo,4100,0);
	}
}
