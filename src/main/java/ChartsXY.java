import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartsXY {

	private static ChartsXY charts;
	private static ArrayList<XYSeriesCollection> datasetsAnalog = new ArrayList<XYSeriesCollection>();
	private static CombinedDomainXYPlot plot;
	private static JFreeChart chart;
	private static JFrame frame;
	private static XYSeries tempSeries;
	public static double timeStep = 0.00025;

	@SuppressWarnings("deprecation")
	public ChartsXY(){
		plot = new CombinedDomainXYPlot(new NumberAxis("R, Om"));

		chart = new JFreeChart(plot);
		chart.setBorderPaint(Color.black);
		chart.setBorderVisible(true);
		chart.setBackgroundPaint(Color.white);

		frame = new JFrame("Hodograf");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ChartPanel(chart));
		frame.setSize(1024,768);
		frame.show();
	}

	public static void createAnalogChart(int number){
		if(charts==null) charts = new ChartsXY();
		String name = "X, Om";
		XYSeriesCollection dataset = new XYSeriesCollection();
		NumberAxis rangeAxis = new NumberAxis(name); rangeAxis.setAutoRangeIncludesZero(false);
		XYPlot subplot = new XYPlot(dataset, null, rangeAxis, new StandardXYItemRenderer() );
		subplot.setBackgroundPaint(Color.WHITE);
		plot.add(subplot);
		subplot.setWeight(3);
		datasetsAnalog.add(dataset);


		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesLinesVisible(1, false);
		renderer.setSeriesLinesVisible(2, false);
		renderer.setSeriesLinesVisible(3, false);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShapesVisible(3, true);
		renderer.setSeriesLinesVisible(4, false);
		renderer.setSeriesShapesVisible(4, true);
		renderer.setSeriesLinesVisible(5, false);
		renderer.setSeriesShapesVisible(5, true);
		subplot.setRenderer(renderer);


	}

	public static void addSeries(String name, int chartNumber, int number){
		XYSeries series = new XYSeries(name); series.add(0,0);
		datasetsAnalog.get(chartNumber).addSeries(series);
	}

	public static void addAnalogData(int chart, int series, double r, double x){
		tempSeries = (XYSeries) datasetsAnalog.get(chart).getSeries().get(series);
		tempSeries.add(r, x);
	}
}