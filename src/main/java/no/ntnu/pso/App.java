package no.ntnu.pso;

import com.sun.j3d.internal.Distance;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        
        Scanner in = new Scanner(System.in);
        System.out.println("How many dimensions do you want?");
        int dimensions = 0;
        try {
            dimensions = in.nextInt();
            if (dimensions < 1) {
                throw new Exception("Dimensions must be bigger than 1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        for (int i = 0; i < 5; i++) {
            boids.add(new Boid(dimensions));
        }
        double[] goal = new double[dimensions];
        double[] bestGlobalPosition = new double[dimensions];
        
        double bestGlobalDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < 1000; i++) {
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
        }
        
        System.out.println("Best global position:");
        System.out.println(positionToString(bestGlobalPosition));
        System.out.println("");
        System.out.println("Boid positions:");
        for (Boid boid : boids) {
            System.out.println(boid);
        }
    }
    
    public static String positionToString(double[] position) {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        for (int i=0;i<position.length;i++) {
            sb.append(position[i]);
            if (i!=position.length-1) {
                sb.append(" , ");
            }
        }
        sb.append(" )");
        return sb.toString();
    }
}
