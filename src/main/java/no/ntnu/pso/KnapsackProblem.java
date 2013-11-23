package no.ntnu.pso;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	static List<KSBoid> boids = new ArrayList();

	public static void main(String[] args) {
		ArrayList<Package> pK = inputReader.read(null);
            
            
//            Container container = new Container(pK);
            
            
        
            XYSeries series = new XYSeries("Best global fitness");
            
            int nrOfBoids = 100;
            int dimensions = pK.size();
            
            for (int nrOfTries = 0; nrOfTries < 1000; nrOfTries++) {
                for (int i = 0; i < nrOfBoids; i++) {
                    boids.add(new KSBoid(dimensions, boids, new Container(pK)));
                }

                double[] bestGlobalPosition = new double[dimensions];
                double bestGlobalFitness = Double.MIN_VALUE;
                int i = 0;
                for (i = 0; i < 40; i++) {
                    for (int boidIndex = 0; boidIndex < boids.size(); boidIndex++) {
                        KSBoid boid = boids.get(boidIndex);
                        double boidFitness = boid.fitness();
                        while (boidFitness == -1) {                        
                            boid.nextIteration(bestGlobalPosition);
                            boid = boids.get(boidIndex);
                            boidFitness = boid.fitness();
                        }
                        if (boidFitness > bestGlobalFitness ) {
                            bestGlobalFitness = boidFitness;
                            bestGlobalPosition = boid.position.clone();
                        }
                    }
                    series.add(i,bestGlobalFitness);

                    for (KSBoid boid : boids) {
                        boid.nextIteration(bestGlobalPosition);
                    }
                }
                System.out.println(bestGlobalFitness + ":"+  nrOfTries);
//                System.out.println(boids.get(4));
            }
            
        /*
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
	*/
        }
}
