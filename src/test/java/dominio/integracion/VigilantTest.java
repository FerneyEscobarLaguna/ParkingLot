package dominio.integracion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		assertEquals(Vigilant.PARQUEADERO_LLENO,mensaje);
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
		assertEquals(Vigilant.PARQUEADERO_LLENO,mensaje);
	}
	
	@Test
	public void registrarIngresoVehiculoParqueadoTest(){
		// arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.conPlaca("SIMU-123").build();

		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro));
		
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(carro);
		//assert
		assertEquals(Vigilant.VEHICULO_PARQUEADO,mensaje);
	}
	
	@Test
	public void registrarIngresoVehiculoErrorTest(){
		// arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.conPlaca("SIMU-123").build();

		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.registrarIngreso(new ParkingRegister(carro))).thenReturn(false);
		when(repositorioParqueadero.contarVehiculosTipo("C")).thenReturn(0);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(null);
		
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		// act 
		String mensaje = vigilante.registrarIngresoVehiculo(carro);
		//assert
		assertEquals(Vigilant.ERROR,mensaje);
	}
	
	@Test
	public void registrarIngresoPlacaATest(){
		// arrange
		String placaConA = "AAA-123";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String stringFechaDomingo = "2017-12-17";
		String stringFechaLunes = "2017-12-18";
		String stringFechaMartes = "2017-12-19";
		Date fechaDomingo = null;
		Date fechaLunes = null;
		Date fechaMartes = null;
		try {
			fechaDomingo = sdf.parse(stringFechaDomingo);
			fechaLunes = sdf.parse(stringFechaLunes);
			fechaMartes = sdf.parse(stringFechaMartes);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		// act
		boolean domingo = vigilante.validarPlacaHabil(placaConA,fechaDomingo);
		boolean lunes = vigilante.validarPlacaHabil(placaConA,fechaLunes);
		boolean martes = vigilante.validarPlacaHabil(placaConA,fechaMartes);
		//assert
		assertTrue(domingo);
		assertTrue(lunes);
		assertFalse(martes);
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
		assertEquals(0,8000,costo);
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
		assertEquals(0,11000,costo);
	}
	
	@Test
	public void registrarSalidaCarroDiaTest(){
		//Arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -10);
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(carro.getPlaca());
		//Assert
		assertEquals(0,8000,costo);
	}
	
	@Test
	public void registrarSalidaCarroMinutosTest(){
		//Arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -30);
		Date fechaIngreso = calendar.getTime();//Se crea la fecha de ingreso esperada
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(new ParkingRegister(carro,fechaIngreso));
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(carro.getPlaca());
		//Assert
		assertEquals(0,500,costo);
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
		assertEquals(0,4000,costo);
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
		assertEquals(0,2100,costo);
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
		assertEquals(0,4100,costo);
	}
	
	@Test
	public void registrarSalidaCarroNoParqueadoTest(){
		//Arrange
		CarTestDataBuilder carroTestDataBuilder = new CarTestDataBuilder();
		Car carro = carroTestDataBuilder.build();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -27);
		
		IParkingRegisterService repositorioParqueadero = mock(IParkingRegisterService.class);
		when(repositorioParqueadero.obtenerVehiculoParqueadoPlaca(carro.getPlaca())).thenReturn(null);
		Vigilant vigilante = new Vigilant(repositorioParqueadero);
		//Act
		double costo = vigilante.registrarSalidaVehiculo(carro.getPlaca());
		//Assert
		assertEquals(0,0,costo);
	}
}
