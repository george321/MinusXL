package minusXL_charts_management;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class ChartsManager extends ApplicationFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String xLabels;
	private String yLabel;
	private String cate;
	private int[][] data;

	public ChartsManager(String chart, String chartTitle, String xLabels, String yLabel, String cate, int[][] data) {
		super(chartTitle);
		this.title = chartTitle;
		this.xLabels = xLabels;
		this.yLabel = yLabel;
		this.data = data;
		this.cate = cate;
		if (chart.equals("BARCHART")) {createBarChart();
		} else if (chart.equals("LINECHART")) {
			createLineChart();
		}
	}

	public void createBarChart() {
		CategoryDataset tmp = createDataset();
		JFreeChart barChart = ChartFactory.createBarChart(title, "Category", yLabel, tmp, PlotOrientation.VERTICAL,
				true, true, false);
		ChartPanel chartPanel = new ChartPanel(barChart);
		JFrame f = new JFrame(title);
		f.setTitle(title);
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.setLayout(new BorderLayout(0, 5));
		f.add(chartPanel, BorderLayout.CENTER);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setHorizontalAxisTrace(true);
		chartPanel.setVerticalAxisTrace(true);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void createLineChart() {
		CategoryDataset tmp = createDataset();

		JFreeChart lineChart = ChartFactory.createLineChart(
				title,
		         "Years","Number of Schools",
		         tmp,
		         PlotOrientation.VERTICAL,
		         true,true,false);
		         
		      ChartPanel chartPanel = new ChartPanel( lineChart );
		      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		      setContentPane( chartPanel );
		      JFrame f = new JFrame(title);
				f.setTitle(title);
				f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				f.setLayout(new BorderLayout(0, 5));
				f.add(chartPanel, BorderLayout.CENTER);
				chartPanel.setMouseWheelEnabled(true);
				chartPanel.setHorizontalAxisTrace(true);
				chartPanel.setVerticalAxisTrace(true);
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
	}

	private CategoryDataset createDataset() {

		List<String> categ = Arrays.asList(cate.split(","));
		List<String> x = Arrays.asList(xLabels.split(","));
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int j = 0; j < data[0].length; j++) {
			// every category
			for (int i = 0; i < data.length; i++) {
				// x titles
				dataset.addValue(data[i][j], categ.get(i), x.get(j));
			}
		}
		return dataset;
	}
}
