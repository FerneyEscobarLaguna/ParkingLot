package co.ceiba.domain;

import java.util.Date;

public class ParkingRegister {
	private Vehicle vehiculo;
	private String tipo_vehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private double costoParqueadero;
	private int registro_vehiculo_id;
	
	public ParkingRegister(Vehicle vehiculo){
		this.vehiculo = vehiculo;
		this.fechaIngreso = new Date();
		this.tipo_vehiculo = vehiculo.getClass().equals(Car.class)?"C":"M";
	}
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipo_vehiculo = vehiculo.getClass().equals(Car.class)?"C":"M";
	}	
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso,String tipo_vehiculo){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipo_vehiculo = tipo_vehiculo;
	}	
	
	public ParkingRegister(Vehicle vehiculo,Date fechaIngreso,String tipo_vehiculo,int registro_vehiculo_id){
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.tipo_vehiculo = tipo_vehiculo;
		this.tipo_vehiculo = vehiculo.getClass().equals(Car.class)?"C":"M";
		this.registro_vehiculo_id = registro_vehiculo_id;
	}	
	
	public String getTipo_vehiculo() {
		return tipo_vehiculo;
	}

	public void setTipo_vehiculo(String tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}

	public ParkingRegister(Vehicle vehiculo, Date fechaIngreso, Date fechaSalida, double costoParqueadero) {
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.costoParqueadero = costoParqueadero;
	}
	
	public ParkingRegister(int registro_vehiculo_id,Vehicle vehiculo, Date fechaIngreso, Date fechaSalida, double costoParqueadero) {
		this.vehiculo = vehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.costoParqueadero = costoParqueadero;
		this.registro_vehiculo_id = registro_vehiculo_id;
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

	public int getRegistro_vehiculo_id() {
		return registro_vehiculo_id;
	}

	public void setRegistro_vehiculo_id(int registro_vehiculo_id) {
		this.registro_vehiculo_id = registro_vehiculo_id;
	}	
	
}
