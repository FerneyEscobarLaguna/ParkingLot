package dominio.integracion;

import org.junit.Test;
import org.mockito.Mockito;

import co.ceiba.conection.Conection;
import co.ceiba.domain.Car;
import co.ceiba.domain.Motorcycle;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vigilant;
import co.ceiba.testDataBuilder.CarTestDataBuilder;
import persistencia.repositorio.ParkingRegisterService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ParkingRegisterServiceTest {
	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterServiceTest.class.getName() );
	@Test
	public void obtenerVehiculoParqueadoPlacaTest(){
		//arrange
		String placa = "PLAC-123";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getString(1)).thenReturn("C");
			when(rs.getString(3)).thenReturn("2017-12-15 14:05:00");
			when(rs.getString(4)).thenReturn("0");			
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		Date fechaIngreso=null;
		try {
			fechaIngreso = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-15 14:05:00");
		} catch (ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		ParkingRegister pr = prs.obtenerVehiculoParqueadoPlaca(placa);
		//assert
		assertEquals(placa,pr.getVehiculo().getPlaca());
		assertEquals(fechaIngreso,pr.getFechaIngreso());
		assertEquals(0,pr.getRegistroVehiculoId());
	}
	
	@Test
	public void obtenerVehiculoParqueadoPlacaMotoTest(){
		//arrange
		String placa = "PLAC-123";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getString(1)).thenReturn("M");
			when(rs.getString(2)).thenReturn("110");
			when(rs.getString(3)).thenReturn("2017-12-15 14:05:00");
			when(rs.getString(4)).thenReturn("0");			
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		Date fechaIngreso=null;
		try {
			fechaIngreso = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-15 14:05:00");
		} catch (ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		ParkingRegister pr = prs.obtenerVehiculoParqueadoPlaca(placa);
		//assert
		assertEquals(placa,pr.getVehiculo().getPlaca());
		assertEquals(110,((Motorcycle)pr.getVehiculo()).getCilindraje());
		assertEquals(fechaIngreso,pr.getFechaIngreso());
		assertEquals(0,pr.getRegistroVehiculoId());
	}
	
	@Test
	public void obtenerVehiculoParqueadoPlacaNullTest(){
		//arrange
		String placa = "PLAC-123";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(false);		
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		ParkingRegister pr = prs.obtenerVehiculoParqueadoPlaca(placa);
		//assert
		assertNull(pr);
	}
	
	@Test
	public void registrarIngresoTest(){
		//arrange
		Car car = new CarTestDataBuilder().build();	
		ParkingRegister pr = new ParkingRegister(car);
		Conection con = mock(Conection.class);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		boolean respuesta=prs.registrarIngreso(pr);
		//assert
		assertTrue(respuesta);
	}
	
	@Test
	public void registrarIngresoErrorTest(){
		//arrange
		Car car = new CarTestDataBuilder().build();	
		ParkingRegister pr = new ParkingRegister(car);
		Conection con = mock(Conection.class);
		try {
			doThrow(SQLException.class).when(con).executeUpdate(Mockito.anyString());
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		boolean respuesta=prs.registrarIngreso(pr);
		//assert
		assertFalse(respuesta);
	}
	
	@Test
	public void registraSalidaTest(){
		//arrange
		Car car = new CarTestDataBuilder().build();	
		ParkingRegister pr = new ParkingRegister(car);
		pr.setFechaSalida(new Date());
		Conection con = mock(Conection.class);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		prs.registraSalida(pr);
		//assert
	}
	
	@Test
	public void contarVehiculosTipoTest(){
		//arrange
		int cantidadVehiculos=5;
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true);
			when(rs.getInt("TOTAL")).thenReturn(cantidadVehiculos);	
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		int pr = prs.contarVehiculosTipo("C");
		//assert
		assertEquals(cantidadVehiculos,pr);
	}
	
	@Test
	public void contarVehiculosTipoCeroTest(){
		//arrange
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(false);
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		int pr = prs.contarVehiculosTipo("C");
		//assert
		assertEquals(0,pr);
	}
	
	@Test
	public void obtenerVehiculosParqueadosTest(){
		//arrange
		String placa = "PLAC-123";
		String tipoVehiculo = "C";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getString(1)).thenReturn(placa);
			when(rs.getString(2)).thenReturn("C");
			when(rs.getString(3)).thenReturn("2017-12-15 14:05:00");			
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		Date fechaIngreso=null;
		try {
			fechaIngreso = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-15 14:05:00");
		} catch (ParseException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRegisterService prs = new ParkingRegisterService(con);
		//act
		List<ParkingRegister> vehiculos = prs.obtenerVehiculosParqueados();
		//assert
		ParkingRegister pr = vehiculos.get(0);
		assertEquals(placa,pr.getVehiculo().getPlaca());
		assertEquals(fechaIngreso,pr.getFechaIngreso());
		assertEquals(tipoVehiculo,pr.getTipoVehiculo());
	}
}
