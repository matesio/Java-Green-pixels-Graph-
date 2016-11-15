package pkg;

import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import javax.swing.JPanel;

public class Client_c extends JPanel {

	static int[] array;

	private static Socket socket;

	public static final int width = 500; // 960
	public static final int height = 700; // 960
	int a = 0, b = 0, x = 0;

	public static void main(String args[]) {

		try {
			String host = "127.0.0.1";
			int port = 3265;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			InputStream is = socket.getInputStream();

			BufferedInputStream ois = new BufferedInputStream(is);

			int element = ois.read();
			int i = 0;
			while (true) {
				array[i] = element;
				i++;
				System.out.println("Server Responded");

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

	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public void paint(Graphics g) {
		g.drawLine(0, 350, width, 350);
		g.drawLine(250, 0, 250, 700);
		int j = 0, k = 1;
		int temp = 0, i;

		while (true) {

			for (i = 0; i <= array.length; i += 10) {
				a = randInt(0, 255);
		//		b = randInt(0, 255);
				b = array[i]; 
				g.drawLine(i, a, i + 10, b);
				temp += 10;
				System.out.print("X1 = " + i + "  Y1 = " + a);
				System.out.print("    X2 = " + temp + "  Y2 = " + b);
				System.out.println();
				j++;
				k++;

			}
		}
		// getContentPane().repaint();
	}

}
