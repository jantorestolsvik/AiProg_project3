package no.ntnu.pso;

import java.util.ArrayList;

public class Container {
	ArrayList<Package> pK;
	//int Dimentions [];
	//double weight;
	//double volume;
	double maxWeight = 1000;
	double maxVolume = 1000;
	
	public Container(ArrayList<Package> pK){
            this.pK	= pK;
            //Dimentions = new int[pK.size()];	
	}
        
        public double fitness(Boid boid) {
            double sum = 0.0;
            double weight = 0.0;
            for (int i = 0; i < pK.size(); i++) {
                if (boid.getPosition()[i] >= 0) {
                    sum += pK.get(i).getValue();
                    weight += pK.get(i).getWeight();
                }
            }
            if (weight > maxWeight) {
                return -100;
            }
            return sum;
        }
}
