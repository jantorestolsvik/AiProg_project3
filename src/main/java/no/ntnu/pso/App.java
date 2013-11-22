package no.ntnu.pso;


import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Jan Tore Stølsvik & Tom Glover
 */
public class App 
{
    static final int GLOBAL = 0;
    static int dimensions = 0;
    static List<Boid> boids = new ArrayList();
    static int nrOfNeighbours;
    
    public static void main( String[] args ) throws MalformedURLException, IOException
    {   
        
        XYSeries series = new XYSeries("Best global fitness");
        
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
            boids.add(new Boid(dimensions, boids, null));
        }
        
        double[] bestGlobalPosition = new double[dimensions];
        double bestGlobalFitness = Double.MAX_VALUE;
        int i = 0;
        for (i = 0; i < 1000; i++) {
            for (Boid boid : boids) {
                double boidDistance = Roost.fitness(boid.getPosition());
                if (boidDistance < bestGlobalFitness ) {
                    bestGlobalFitness = boidDistance;
                    bestGlobalPosition = boid.getPosition().clone();
                }
            }
            series.add(i,bestGlobalFitness);
           
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
            if (bestGlobalFitness < 0.001) {
                break;
            }
        }
        
        
        /*System.out.println("Iteration nr: " + i);
        System.out.println("Best global fitness " + bestGlobalFitness);
        System.out.println("Best global position:");
        System.out.println(positionToString(bestGlobalPosition));
        System.out.println("");
        System.out.println("Boid positions:");*/
        /*for (Boid boid : boids) {
            System.out.println(boid);
        }*/
        
        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Plot", // Title
            "timestep", // x-axis Label
            "value", // y-axis Label
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Plot Orientation
            true, // Show Legend
            true, // Use tooltips
            false // Configure chart to generate URLs?
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("C:\\chart.jpg"), chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }

        ImageIcon image = new ImageIcon("C:\\chart.jpg");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);
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
        List<Boid> list = new ArrayList<Boid>(boids);
        final double[] position = boid.getPosition();
        Collections.sort(list, new Comparator<Boid>() {
            public int compare(Boid o1, Boid o2) {
                return new Double(distance(o1.getPosition(), position)).compareTo(distance(o2.getPosition(), position));
            }
        });
        for (int i = 0; i < nrOfNeighbours; i++) {
            neighbours[i] = list.get(i);
        }
        return neighbours;
    }
}
