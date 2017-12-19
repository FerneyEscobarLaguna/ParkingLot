package co.ceiba.domain;

public class Motorcycle extends Vehicle{
	private int cilindraje;

	public Motorcycle(String placa, int cilindraje) {
		super(placa);
		this.cilindraje = cilindraje;
	}
	
	public Motorcycle(String placa) {
		super(placa);
	}
	
	public Motorcycle() {		
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}	
}
