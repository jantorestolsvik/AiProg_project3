package no.ntnu.pso;

import java.util.List;

public class KSBoid {
	private int dimensions;
	private double[] velocity;
	public double[] position;
	private double[] bestSeenPosition;
	private double c1,c2,c3,r1,r2,minValue = -4.25,maxValue = 4.25, bestSeenFitness=Double.MIN_VALUE;
	private List<KSBoid> boids;
	private Container container;
	private double max=0, min=0;


	public KSBoid(int dimensions, List<KSBoid> boids, Container container) {
		this.container = (container);
		this.boids = boids;
		this.dimensions = dimensions;
		this.velocity = new double[dimensions];
		this.position = new double[dimensions];
		this.bestSeenPosition = new double[dimensions];
		for (int i=0;i<dimensions;i++) {
			this.position[i] = container.Dimentions[i];
			this.velocity[i] = Math.random() * 2.5 - 1.0;
			this.bestSeenPosition[i] = this.position[i];
		}
		if (container != null) {
			bestSeenFitness = fitness();
		}
		c1 = 1.2;
		c2 = 0.2;
		c3 = 1.0;
	}
	private double clamp(double value) {
		return Math.max(minValue, Math.min(value, maxValue));
	}
	public void explodeRandom(){
		for (int i = 0; i < dimensions; i++) {
		this.position[i] = Math.random() * 1.0 - 0.8;
		this.velocity[i] = Math.random() * 2.5 - 1.0;
		this.bestSeenPosition[i] = this.position[i];
		}
	}

	public double fitness(){
		int [] dim = new int [dimensions];
		for (int i = 0; i < dimensions; i++) {
			dim[i]=(int) position[i];
		}
		getContainer().addDimentions(dim);

		int pos;
		while(getContainer().weight>getContainer().maxWeight||getContainer().volume>getContainer().maxVolume){
                    return -1;
		}
		return getContainer().value;
	}

	public void nextIteration(int[] bestGlobalPosition) {
		for (int i = 0; i < dimensions; i++) {
			//New velocity
			r1 = Math.random();
			r2 = Math.random();


			if (c3 > 0.4) {
				c3 = c3 - 0.0006;
			}

			velocity[i] = (c3 * velocity[i]) + (c1 * r1 * (bestSeenPosition[i] - position[i]) + (c2 * r2 * (bestGlobalPosition[i] - position[i])));
			velocity[i] = clamp(velocity[i]);
			
			//New position
			position[i] = position[i] + velocity[i];
//			position[i] = clamp(position[i]);

			if(Math.random()<position[i]){
				position[i]=1;
			}else{
				position[i]=0;
			}
		}
		



		//Best local position
		if (getContainer() != null) {

			double fitness = fitness();
			if (fitness > bestSeenFitness) {
				this.bestSeenFitness = fitness;
				this.bestSeenPosition = position;
			}
		}

	}
	public String toString(){
		return getContainer().toString();
	}
	public Container getContainer() {
		return container;
	}

}
