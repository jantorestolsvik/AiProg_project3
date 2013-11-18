package no.ntnu.pso;

public class Package {
	private double weight;
	private double value;
	private double volume;
	

	public Package(double weight, double value, double volume) {
		super();
		this.weight = weight;
		this.value = value;
		this.volume = volume;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	public double getVolume() {
		return volume;
	}


	public void setVolume(double volume) {
		this.volume = volume;
	}


	@Override
	public String toString() {
		return "Package [weight=" + weight + ", value=" + value + ", volume="
				+ volume + "]";
	}
}
