
package main;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class Client {
	static JLabel l1;
	public static void setL1(String s) {
		l1.setText(s);
	}

	static Server s = new Server();
	static Thread th = new Thread ((Runnable) s);
	static JButton b1 = new JButton("Send");
	static JTextField t1 = new JTextField(10);
	static JFrame frame = new JFrame("Client");
	static Connector cn = new Connector();
	
	
	public static void main(String[] args) {	
		th.start();
		l1 = new JLabel("");
		Thread th1 = new Thread((Runnable) cn);
		th1.start();
		l1.setText("Соединения устанавливаются...");
		createGUI();	
	}

	
	private static void createGUI() {
		frame.setLayout(new FlowLayout());
		frame.add(l1);
		frame.add(t1);
		frame.add(b1);
		frame.pack();
		frame.setSize(340, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		b1.addActionListener((ActionEvent e) -> {
                    if(e.getSource()==b1){
                        cn.sendData(t1.getText());
                    }
                });
	}
}
