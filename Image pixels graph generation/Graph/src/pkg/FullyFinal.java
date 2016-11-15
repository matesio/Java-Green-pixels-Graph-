package pkg;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class FullyFinal {
	static TimeSeries ts = new TimeSeries("data", Millisecond.class);

	static int[] array;

	private static Socket socket;

	public static void main(String[] args) throws InterruptedException {

		try {
			String host = "127.0.0.1";
			int port = 3265;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			InputStream is = socket.getInputStream();

			BufferedInputStream ois = new BufferedInputStream(is);

			int element = ois.read();
			int i = 0;
			System.out.println("Server Responded");
			while (true) {
				array[i] = element;
				i++;
				gen myGen = new gen();
				new Thread(myGen).start();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection(ts);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("GraphTest",
				"Time", "Value", dataset, true, true, false);
		final XYPlot plot = chart.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0);

		JFrame frame = new JFrame("GraphTest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ChartPanel label = new ChartPanel(chart);
		frame.getContentPane().add(label);
		// Suppose I add combo boxes and buttons here later

		frame.pack();
		frame.setVisible(true);
	}

	static class gen implements Runnable {
		private Random randGen = new Random();

		public void run() {

			int i = 0;
			while (true) {
				// int num = randGen.nextInt(1000);

				System.out.println(array[i]);
				ts.addOrUpdate(new Millisecond(), array[i]);
				i++;
				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
					System.out.println(ex);
				}
			}
		}
	}

}