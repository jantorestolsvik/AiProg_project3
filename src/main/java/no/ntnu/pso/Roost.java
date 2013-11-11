package no.ntnu.pso;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class Roost {
    public static double fitness(double[] position) {
        double sum = 0.0;
        for (double d : position) {
            sum += d*d;
        }
        return sum;
    }
}
