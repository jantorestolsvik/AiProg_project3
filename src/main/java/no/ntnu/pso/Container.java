package no.ntnu.pso;

import java.util.ArrayList;

public class Container {
	ArrayList<Package> pK;
	int Dimentions [];
	double weight;
	double volume;
	double maxWeight = 1000;
	double maxVolume = 1000;
	
	public Container(ArrayList<Package> pK){
	this.pK	= pK;
	Dimentions = new int[pK.size()];
		
	}
	
	

}
