package no.ntnu.pso;

import java.util.List;

public class KSBoid {
	private int dimensions;
	private double[] velocity;
	public double[] position;
	private double[] bestSeenPosition;
	private double c1,c2,c3,r1,r2,minValue = -1,maxValue = 1, bestSeenFitness;
	private List<KSBoid> boids;
	private Container container;


	public KSBoid(int dimensions, List<KSBoid> boids, Container container) {
		this.container = container;
		this.boids = boids;
		this.dimensions = dimensions;
		this.velocity = new double[dimensions];
		this.position = new double[dimensions];
		this.bestSeenPosition = new double[dimensions];
		for (int i=0;i<dimensions;i++) {
			this.position[i] = Math.random() * 2.0 - 1.8;
			this.velocity[i] = Math.random() * 2.0 - 1.0;
			this.bestSeenPosition[i] = this.position[i];
		}
		if (container != null) {
			bestSeenFitness = fitness();
		}
		c1 = 0.2;
		c2 = 0.1;
		c3 = 0.425;
	}
	private double clamp(double value) {
		return Math.max(minValue, Math.min(value, maxValue));
	}

	public double fitness(){
		int [] dim = new int [dimensions];
		for (int i = 0; i < dimensions; i++) {
//			position[i] = clamp(position[i]);
			if(Math.random()<position[i]){
				position[i]=1;
			}else{
				position[i]=0;
			}
			dim[i] = (int) position[i];
		}
		container.addDimentions(dim);
		container.calculateWeight();
//		    	System.out.println(container);
		if(container.weight>container.maxWeight){
			return -1;
		}
		return container.value;
	}

	public void nextIteration(double[] bestGlobalPosition) {
		double [] tempVelocity = new double [velocity.length];
		double [] tempPosition = new double [position.length];
		for (int i = 0; i < dimensions; i++) {
			//Not sure if r1 and r2 should be random every iteration
			//New velocity
			r1 = Math.random();
			r2 = Math.random();
			

			if (c3 > 0.4) {
				c3 = c3 - 0.1;
			}

			tempVelocity[i] = (c3 * velocity[i]) + (c1 * r1 * (bestSeenPosition[i] - position[i]) + (c2 * r2 * (bestGlobalPosition[i] - position[i])));
			tempVelocity[i] = clamp(tempVelocity[i]);

			//New position
			tempPosition[i] = position[i] + tempVelocity[i];
			tempPosition[i] = clamp(tempPosition[i]);
			if(Math.random()<tempPosition[i]){
				tempPosition[i]=1;
			}else{
				tempPosition[i]=0;
			}
		}

		//Best local position
		if (container != null) {

			double fitness = fitness();
//			if(fitness!=-1){
				position = tempPosition;
				velocity = tempVelocity;
			if (fitness > bestSeenFitness) {
				this.bestSeenFitness = fitness;
				this.bestSeenPosition = position;
//			}else{
//				int [] dim = new int [dimensions];
//				for (int i = 0; i < dimensions; i++) {
//					dim[i] = (int) position[i];
//				}
//				container.addDimentions(dim);
//			}
			}
		}

	}
	public String toString(){
		return container.toString();
	}
}
