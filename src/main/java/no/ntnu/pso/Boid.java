package no.ntnu.pso;

import java.util.List;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class Boid {
    private int dimensions;
    private double[] velocity;
    private double[] position;
    private double[] bestSeenPosition;
    private double c1,c2,c3,r1,r2,minValue = -10.0,maxValue = 10.0, bestSeenFitness;
    private List<Boid> boids;
    
    public Boid(int dimensions, List<Boid> boids) {
        this.boids = boids;
        this.dimensions = dimensions;
        this.velocity = new double[dimensions];
        this.position = new double[dimensions];
        this.bestSeenPosition = new double[dimensions];
        for (int i=0;i<dimensions;i++) {
            this.position[i] = Math.random() * 10;
            this.bestSeenPosition[i] = this.position[i];
        }
        bestSeenFitness = Roost.fitness(position);
        c1 = 0.5;
        c2 = 1.5;
        c3 = 0.8;
    }

    private double clamp(double value) {
        return Math.max(minValue, Math.min(value, maxValue));
    }
    
    public void nextIteration(double[] bestGlobalPosition) {
        for (int i = 0; i < dimensions; i++) {
            //Not sure if r1 and r2 should be random every iteration
            //New velocity
            r1 = Math.random();
            r2 = Math.random();
            velocity[i] = (c3 * velocity[i]) + (c1 * r1 * (bestSeenPosition[i] - position[i]) + (c2 * r2 * (bestGlobalPosition[i] - position[i])));
            velocity[i] = clamp(velocity[i]);
            
            //New position
            position[i] = position[i] + velocity[i];
        }
        
        //Best local position
        double fitness = Roost.fitness(position);
        if (fitness < bestSeenFitness) {
            this.bestSeenFitness = fitness;
            this.bestSeenPosition = position;
        }
    }

    public double[] getPosition() {
        return position;
    }

    public double getBestSeenFitness() {
        return bestSeenFitness;
    }

    public double[] getBestSeenPosition() {
        return bestSeenPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        for (int i=0;i<dimensions;i++) {
            sb.append(position[i]);
            if (i!=dimensions-1) {
                sb.append(" , ");
            }
        }
        sb.append(" )");
        return sb.toString();
    }
}
