package dominio.unitaria;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import co.ceiba.domain.Car;
import co.ceiba.domain.ParkingRegister;
import co.ceiba.domain.Vehicle;
import co.ceiba.testDataBuilder.CarTestDataBuilder;
import co.ceiba.testDataBuilder.ParkingRegisterTestDataBuilder;

public class ParkingRegisterTest {
	private final Date FECHAINGRESO = new Date();
	private final Date FECHASALIDA = new Date();
	private final double COSTOPARQUEADERO = 500;
	private final int REGISTROVEHICULOID = 1;
	private final String TIPOVEHICULO = "M";
	@Test
	public void crearRegistroParqueadero(){
		// arrange
		ParkingRegisterTestDataBuilder registroParqueaderoTDB = new ParkingRegisterTestDataBuilder();	
		// act 
		ParkingRegister registroParqueadero = registroParqueaderoTDB.
													conFechaIngreso(FECHAINGRESO).
													conFechaSalida(FECHASALIDA).
													conCostoParqueadero(COSTOPARQUEADERO).
													conRegistroVehiculoId(REGISTROVEHICULOID).
													conTipoVehiculo(TIPOVEHICULO).
													build();
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
		assertEquals(COSTOPARQUEADERO,registroParqueadero.getCostoParqueadero(),0);
		assertEquals(REGISTROVEHICULOID,registroParqueadero.getRegistroVehiculoId(),0);
	}
	
	@Test
	public void crearRegistroParqueaderoTipoVehiculo(){
		// arrange
		Car car = new CarTestDataBuilder().build();
		// act 
		ParkingRegister registroParqueadero = new ParkingRegister(car,FECHAINGRESO,TIPOVEHICULO);
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
		assertEquals(registroParqueadero.getVehiculo().getPlaca(),car.getPlaca());
	}
	
	@Test
	public void crearRegistroParqueaderoRegistroVehiculoId(){
		// arrange
		Car car = new CarTestDataBuilder().build();
		// act 
		ParkingRegister registroParqueadero = new ParkingRegister(car,FECHAINGRESO,TIPOVEHICULO,REGISTROVEHICULOID);
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
		assertEquals(registroParqueadero.getVehiculo().getPlaca(),car.getPlaca());
		assertEquals(registroParqueadero.getRegistroVehiculoId(),REGISTROVEHICULOID);
	}
	
	@Test
	public void crearRegistroParqueaderoCosto(){
		// arrange
		Car car = new CarTestDataBuilder().build();
		// act 
		ParkingRegister registroParqueadero = new ParkingRegister(car,FECHAINGRESO,FECHASALIDA,COSTOPARQUEADERO);
		//assert
		assertEquals(registroParqueadero.getVehiculo().getPlaca(),car.getPlaca());
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(COSTOPARQUEADERO,registroParqueadero.getCostoParqueadero(),0);
	}
	
	@Test
	public void crearRegistroParqueaderoRegistroIdCosto(){
		// arrange
		Car car = new CarTestDataBuilder().build();
		// act 
		ParkingRegister registroParqueadero = new ParkingRegister(REGISTROVEHICULOID,car,FECHAINGRESO,FECHASALIDA,COSTOPARQUEADERO);
		//assert
		assertEquals(registroParqueadero.getRegistroVehiculoId(),REGISTROVEHICULOID);
		assertEquals(registroParqueadero.getVehiculo().getPlaca(),car.getPlaca());
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(COSTOPARQUEADERO,registroParqueadero.getCostoParqueadero(),0);
	}
	
	@Test
	public void modificarRegistroParqueadero(){
		// arrange
		ParkingRegisterTestDataBuilder registroParqueaderoTDB = new ParkingRegisterTestDataBuilder();
		Vehicle vehiculo = new CarTestDataBuilder().conPlaca("REM-123").build();
		// act 
		ParkingRegister registroParqueadero = registroParqueaderoTDB.build();
		registroParqueadero.setFechaIngreso(FECHAINGRESO);
		registroParqueadero.setFechaSalida(FECHASALIDA);
		registroParqueadero.setCostoParqueadero(COSTOPARQUEADERO);
		registroParqueadero.setRegistroVehiculoId(REGISTROVEHICULOID);
		registroParqueadero.setTipoVehiculo(TIPOVEHICULO);
		registroParqueadero.setVehiculo(vehiculo);
		//assert
		assertEquals(registroParqueadero.getFechaIngreso(),FECHAINGRESO);
		assertEquals(registroParqueadero.getFechaSalida(),FECHASALIDA);
		assertEquals(COSTOPARQUEADERO,registroParqueadero.getCostoParqueadero(),0);
		assertEquals(REGISTROVEHICULOID,registroParqueadero.getRegistroVehiculoId(),0);
		assertEquals(registroParqueadero.getTipoVehiculo(),TIPOVEHICULO);
		assertEquals(registroParqueadero.getVehiculo().getPlaca(),vehiculo.getPlaca());
	}
}
