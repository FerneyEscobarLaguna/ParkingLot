package co.ceiba.domain;

public class Motorcycle extends Vehicle{
	private int cilindraje;

	public Motorcycle(String placa, int cilindraje) {
		super(placa,"M");
		this.cilindraje = cilindraje;
	}
	
	public Motorcycle(String placa) {
		super(placa,"M");
	}
	
	public Motorcycle() {
		super(null,"M");
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}	
	
	public double getCostParking(int hoursParking) {
		int diasCobrar=0;
		int horasCobrar=0;
		double valorcobrar=0;
		if(hoursParking>=9){
			if(hoursParking>24){
				diasCobrar=hoursParking/24;
				horasCobrar=hoursParking%24;
			}else{
				diasCobrar=1;
				horasCobrar=0;				
			}
		}else{
			diasCobrar=0;
			horasCobrar=hoursParking;
		}
		int cilindraje = this.cilindraje;
		valorcobrar=(diasCobrar*600d) + (horasCobrar*500d);
		if(cilindraje>500)
			valorcobrar+=2000;
		return valorcobrar;
	}	
}
