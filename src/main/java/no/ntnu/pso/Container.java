package no.ntnu.pso;

import java.util.ArrayList;

public class Container {
	ArrayList<Package> pK;
	int Dimentions [];
	double weight= 0;
	double volume= 0;
	double value = 0;
	double maxWeight = 1000;
	double maxVolume = 1000;

	public Container(ArrayList<Package> pK){
            this.pK = pK;
            Dimentions = new int[pK.size()];
            int k = 0;
            for (int i = 0; i < 500; i++) {
                    k = (int) (Math.random()*2001);
                    movePackage(k);
            }	
	}
        
	/**
	 * adds new dimentions and updates weight volume and value
	 * usefull for testing if the other functions are correct too
	 * @param Dimentions
	 */

	public void addDimentions(int [] Dimentions){
		if(Dimentions.length!=this.Dimentions.length){
			System.out.println("keep it togeter man! your slipping dude");
		}else{
			for (int i = 0; i < Dimentions.length; i++) {
				if(Dimentions[i]!=this.Dimentions[i]){
					if(Dimentions[i]==1){
						volume+=pK.get(i).getVolume();
						value+=pK.get(i).getValue();
						weight+=pK.get(i).getWeight();
					}else{
						volume-=pK.get(i).getVolume();
						value-=pK.get(i).getValue();
						weight-=pK.get(i).getWeight();
					}
				}
			}
			this.Dimentions = Dimentions;


		}
	}
	/**
	 * moves a package out or in of the container depending on the initial state and updates values.
	 * @param i
	 */
	public void movePackage(int i){
		if(Dimentions[i]==1){
			volume-=pK.get(i).getVolume();
			value-=pK.get(i).getValue();
			weight-=pK.get(i).getWeight();
			Dimentions[i]=0;
		}else{
			volume+=pK.get(i).getVolume();
			value+=pK.get(i).getValue();
			weight+=pK.get(i).getWeight();
			Dimentions[i]=1;
		}
		if(weight>maxWeight||volume>maxVolume){
			movePackage(i);
		}

		
		
	}
	public void movePackageForced(int i){
		if(Dimentions[i]==1){
			volume-=pK.get(i).getVolume();
			value-=pK.get(i).getValue();
			weight-=pK.get(i).getWeight();
			Dimentions[i]=0;
		}else{
			volume+=pK.get(i).getVolume();
			value+=pK.get(i).getValue();
			weight+=pK.get(i).getWeight();
			Dimentions[i]=1;
		}
	

		
		
	}
	public void calculateWeight(){
		weight = 0;
		for (int i = 0; i < Dimentions.length; i++) {
			if(Dimentions[i]==1){
				weight+=pK.get(i).getWeight();
			}
		}
//		System.out.println("weight: " + weight);
	}

	public void calculateVolume(){
		volume = 0;
		for (int i = 0; i < Dimentions.length; i++) {
			if(Dimentions[i]==1){
				volume+=pK.get(i).getVolume();
			}
		}
//		System.out.println("volume: " +volume);
	}

	public void calculateValue(){
		value = 0;
		for (int i = 0; i < Dimentions.length; i++) {
			if(Dimentions[i]==1){
				value+=pK.get(i).getValue();
			}
		}
//		System.out.println("value: " +value);
	}
	@Override
	public String toString() {
		/*String reture = "Dimentions[";
		for (int i = 0; i < Dimentions.length; i++) {
			reture += Dimentions[i] + ",";
		}
		reture+= "]";/**/
		return "Container [weight=" + weight + ", volume=" + volume
				+ ", value=" + value + "]" /*+ reture/**/;
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
                return -1;
            }
            return sum;
        }
 
}
