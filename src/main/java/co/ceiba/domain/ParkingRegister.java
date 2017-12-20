package co.ceiba.domain;

import java.util.Date;

public class ParkingRegister {
	private Vehicle vehiculo;
	private String tipoVehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private double costoParqueadero;
	private int registroVehiculoId;	
	
	public ParkingRegister(Vehicle vehiculo, String tipoVehiculo, Date fechaIngreso, Date fechaSalida,
			double costoParqueadero, int registroVehiculoId) {
		super();
		this.vehiculo = vehiculo;
		this.tipoVehiculo = tipoVehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.costoParqueadero = costoParqueadero;
		this.registroVehiculoId = registroVehiculoId;
	}

	public ParkingRegister(Vehicle vehiculo){
		this.vehiculo = vehiculo;
		this.fechaIngreso = new Date();
		this.tipoVehiculo = vehiculo.getClass().equals(Car.class)?"C":"M";
	}
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipoVehiculo = vehiculo.getClass().equals(Car.class)?"C":"M";
	}	
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso,String tipoVehiculo){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipoVehiculo = tipoVehiculo;
	}	
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso,String tipoVehiculo,int registroVehiculoId){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipoVehiculo = tipoVehiculo;
		this.registroVehiculoId = registroVehiculoId;
	}	

	public ParkingRegister(Vehicle vehiculo, Date fechaIngreso, Date fechaSalida, double costoParqueadero) {
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.costoParqueadero = costoParqueadero;
	}
	
	public ParkingRegister(int registroVehiculoId,Vehicle vehiculo, Date fechaIngreso, Date fechaSalida, double costoParqueadero) {
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.costoParqueadero = costoParqueadero;
		this.registroVehiculoId = registroVehiculoId;
	}
	
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public Vehicle getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehicle vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public double getCostoParqueadero() {
		return costoParqueadero;
	}
	public void setCostoParqueadero(double costoParqueadero) {
		this.costoParqueadero = costoParqueadero;
	}

	public int getRegistroVehiculoId() {
		return registroVehiculoId;
	}

	public void setRegistroVehiculoId(int registroVehiculoId) {
		this.registroVehiculoId = registroVehiculoId;
	}	
	
}
