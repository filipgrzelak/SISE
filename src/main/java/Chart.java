import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Chart extends JFrame {


    public Chart(String title, ArrayList<Integer> x, ArrayList<Double> y) {
        super(title);
        // Create dataset
        DefaultXYDataset dataset = createDataset(x, y);
        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Training data", // Chart title
                "ages", // X-Axis Label
                "error", // Y-Axis Label
                dataset
        );
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultXYDataset createDataset(ArrayList<Integer> x, ArrayList<Double> y) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] tab1 = new double[2][x.size()];
        for (int i = 0; i < x.size(); i++) {
            tab1[0][i] = x.get(i);
            tab1[1][i] = y.get(i);
        }
        dataset.addSeries("Neural network error",tab1);
        return dataset;
    }
}
