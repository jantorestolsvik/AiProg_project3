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
        
	public void run(int nrOfBoids, int nrOfRuns, boolean volume){
		ArrayList<Package> pK = inputReader.read(null);
		if(!volume){
			for (int i = 0; i < pK.size(); i++) {
				pK.get(i).setVolume(0.0);
			}
		}

		XYSeries series = new XYSeries("Best global fitness");

		int dimensions = pK.size();
		int[] bestGlobalPosition = new int[dimensions];
		double bestGlobalFitness = Double.MIN_VALUE;
		int stagnantBoids=0;
		Container c =  new Container(pK);

		for (int i = 0; i < nrOfBoids; i++) {
			boids.add(new KSBoid(dimensions, boids, new Container(pK)));
		}
		for (int nrOfTries = 0; nrOfTries < nrOfRuns; nrOfTries++) {

			double boidFitness;
			for (int boidIndex = 0; boidIndex < boids.size(); boidIndex++) {
				KSBoid boid = boids.get(boidIndex);
				boidFitness = boid.fitness();
                                int counter = 1;
				while (boidFitness == -1) {                        
                                    if (counter % 1000 == 0) {
                                        boid.explodeRandom();
                                    } else {
                                        boid.nextIteration(bestGlobalPosition);
                                    }
                                    boidFitness = boid.fitness();
                                    counter++;
				}
				if (boidFitness > bestGlobalFitness ) {
					bestGlobalFitness = boidFitness;
					bestGlobalPosition = boid.getContainer().Dimentions.clone();
					stagnantBoids=0;
				}else {
					stagnantBoids++;
				}
			}
                        for (KSBoid boid : boids) {
				boid.nextIteration(bestGlobalPosition);
			}
			
                        c.addDimentions(bestGlobalPosition);
			System.out.println(bestGlobalFitness + ":"+  nrOfTries);
			System.out.println(c);
                        series.add(nrOfTries, bestGlobalFitness);
                        
			if(stagnantBoids>2*nrOfBoids){
				stagnantBoids=0;
				for (KSBoid boid : boids) {
					boid.explodeRandom();
				}
			}

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
