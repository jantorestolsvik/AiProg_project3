package no.ntnu.pso;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class App 
{
    static final int GLOBAL = 0;
    static int dimensions = 0;
    static List<Boid> boids = new ArrayList();
    static int nrOfNeighbours = 0;
    
    public static void main( String[] args )
    {   
        
        Scanner in = new Scanner(System.in);
        System.out.println("How many dimensions do you want?");
        try {
            dimensions = in.nextInt();
            if (dimensions < 1) {
                throw new Exception("Dimensions must be bigger than 1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
                
        System.out.println("How many boids do you want?");
        int nrOfBoids = 0;
        try {
            nrOfBoids = in.nextInt();
            if (nrOfBoids < 1) {
                throw new Exception("Number of boids must be bigger or equal to 1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("How many neighbours should each boid consult with? 0 = global knowladge");
        try {
            nrOfNeighbours = in.nextInt();
            if (nrOfNeighbours < 0) {
                throw new Exception("Number of neighbours must be bigger or equal to 0");
            } else if (nrOfNeighbours >= nrOfBoids) {
                throw new Exception("Number of neighbours must be less than number of boids");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        for (int i = 0; i < nrOfBoids; i++) {
            boids.add(new Boid(dimensions, boids));
        }
        
        double[] bestGlobalPosition = new double[dimensions];
        double bestGlobalDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < 1000; i++) {
            for (Boid boid : boids) {
                double boidDistance = Roost.fitness(boid.getPosition());
                if (boidDistance < bestGlobalDistance ) {
                    bestGlobalDistance = boidDistance;
                    bestGlobalPosition = boid.getPosition().clone();
                }
            }

            for (Boid boid : boids) {
                if (nrOfNeighbours == GLOBAL) {
                    boid.nextIteration(bestGlobalPosition);
                } else {
                    double bestNeighbourFitness = Double.MAX_VALUE;
                    Boid bestNeighbour = null;
                    for (Boid b : closestNeighbours(boid)) {
                        if (b.getBestSeenFitness() < bestNeighbourFitness) {
                            bestNeighbourFitness = b.getBestSeenFitness();
                            bestNeighbour = b;
                        }
                    }
                    boid.nextIteration(bestNeighbour.getBestSeenPosition());
                }
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
     
    public static double distance(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += (x[i] - y[i]) * (x[i] - y[i]);
        }
        return sum;
    }

    private static Boid[] closestNeighbours(Boid boid) {
        Boid[] neighbours = new Boid[nrOfNeighbours];
        Map<Double, Boid> map = new TreeMap<Double, Boid>();
        for (Boid b : boids) {
            if (b != boid) {
                map.put(distance(b.getPosition(), boid.getPosition()), b);
            }
        }
        int i = 0;
        for (double key : map.keySet()) {
            if (i < nrOfNeighbours) {
                neighbours[i] = map.get(key);
                i++;   
            } else {
                break;
            }
        }
        return neighbours;
    }
}
