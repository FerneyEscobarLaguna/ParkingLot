package co.ceiba.service;

public interface IParkingRate {
	/**
	 * Permite obtener la tarifa del parqueadero para un tipo de vehiculo
	 * @param tipoVehiculo
	 * @param tiempo ('H'-Horas, 'D'-dias)
	 */
	double obtenerTarifa(String tipoVehiculo,String tiempo);
}
