package main;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * Класс, выполняемый в отдельном потоке, 
 * предназначенный для установления соединения 
 * между клиентом и сервером
 * 
 * 

 */
public class Connector implements Runnable{
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static Socket connection;
	private static boolean isRunning = true;
	
	/**
	 * Метод стартующий при запуске потока.
	 * 
	 * Устанавливает соединения и начинает читать input.
	 */
	@Override
	public void run() {
		try {
			while(isRunning){
				connection = new Socket(InetAddress.getByName("127.0.0.1"), 1234);
				output = new ObjectOutputStream(connection.getOutputStream());
				output.flush();
				input = new ObjectInputStream(connection.getInputStream());
				Client.setL1("Соединение установленно");
				JOptionPane.showMessageDialog(null, (String)input.readObject());
			}
			close();
		}catch(HeadlessException | IOException | ClassNotFoundException e) {}
	}
	
	/**
	 * Передает данные на сервер.	 * 
	 * @param s Строка, которую нужно передать.
	 */
	public void sendData(String s){
		try {
			output.flush();
			output.writeObject(s);
			output.flush();
		} catch (IOException e) {}
	}
	
	/**
	 * Закрывает соединения
	 * 
	 * Метод закрывает соединения, чтобы не возникало 
	 * дальнейших проблем при последующем запуске приложения
	 */
	public void close() {
		try {
			isRunning = false;
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {}
	}

}
