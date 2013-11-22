package no.ntnu.pso;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static no.ntnu.pso.App.boids;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class KnapsackProblem {
	int weightLimit = 1000;
	int spaceLimit = 1000;

	public static void main(String[] args) {
            
            ArrayList<Package> pK = inputReader.read(null);
            
            Container container = new Container(pK);
            
            XYSeries series = new XYSeries("Best global fitness");
            
            int nrOfBoids = 100;
            int dimensions = pK.size();
            
            
            for (int i = 0; i < nrOfBoids; i++) {
                boids.add(new Boid(dimensions, boids, container));
            }

            double[] bestGlobalPosition = new double[dimensions];
            double bestGlobalFitness = Double.MIN_VALUE;
            int i = 0;
            for (i = 0; i < 100; i++) {
                for (Boid boid : boids) {
                    double boidFitness = container.fitness(boid);
                    if (boidFitness > bestGlobalFitness ) {
                        bestGlobalFitness = boidFitness;
                        bestGlobalPosition = boid.getPosition().clone();
                    }
                }
                series.add(i,bestGlobalFitness);

                for (Boid boid : boids) {
                    boid.nextIteration(bestGlobalPosition);
                }
            }
        
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
}
