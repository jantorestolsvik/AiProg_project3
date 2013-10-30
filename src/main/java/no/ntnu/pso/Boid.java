package no.ntnu.pso;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class Boid {
    private int dimensions;
    private double[] velocity;
    private double[] position;
    private double[] bestSeenPosition;
    private double c1,c2,c3,r1,r2,minValue = -10.0,maxValue = 10.0;
    
    public Boid(int dimensions) {
        this.dimensions = dimensions;
        this.velocity = new double[dimensions];
        this.position = new double[dimensions];
        this.bestSeenPosition = new double[dimensions];
        for (int i=0;i<dimensions;i++) {
            this.position[i] = Math.random() * 10;
            this.bestSeenPosition[i] = this.position[i];
        }
        c1 = 0.2;
        c2 = 2.0;
        c3 = 0.2;
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
    }

    public double[] getPosition() {
        return position;
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
