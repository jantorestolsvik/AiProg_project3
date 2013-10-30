package no.ntnu.pso;

import com.sun.j3d.internal.Distance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class App 
{
    public static double distance(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += (x[i] - y[i]) * (x[i] - y[i]);
        }
        return sum;
    }
    
    public static void main( String[] args )
    {
        System.out.println( "Start" );
        List<Boid> boids = new ArrayList();
        
        //DIMENSION SPESIFFIC
        for (int i = 0; i < 5; i++) {
            boids.add(new Boid(1));
        }
        double[] goal = new double[1];
        double[] bestGlobalPosition = new double[1];
        //DIMENSION SPESIFFIC
        
        double bestGlobalDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < 200; i++) {
            for (Boid boid : boids) {
                double boidDistance = distance(boid.getPosition(), goal);
                if (boidDistance < bestGlobalDistance ) {
                    bestGlobalDistance = boidDistance;
                    bestGlobalPosition = boid.getPosition().clone();
                }
            }
            for (Boid boid : boids) {
                boid.nextIteration(bestGlobalPosition);
            }
            System.out.println(bestGlobalPosition[0]);
        }
        
        for (Boid boid : boids) {
            System.out.println(boid);
        }
    }
}
