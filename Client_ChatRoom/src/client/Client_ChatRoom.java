package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client_ChatRoom extends Thread {
	private String serverip;
	private int port;
	private Socket client;
	
	private InputStream receive;
	private OutputStream send;
	
	public Client_ChatRoom(String serverip, int port) {
		super();
		this.serverip = serverip;
		this.port = port;
	}
	/*
	 * ���ӷ�����
	*/	
	public boolean Connect() {
		try {
			client = new Socket(serverip, port);
			System.out.println("������������");
			receive= client.getInputStream();
			send = client.getOutputStream();// ��ȡ�����ӵ����������
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * ���ܷ�������Ϣ
	*/	
	public void run() {
		while (true) {
			try {
				processMsg();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��������Ͽ�����");
				JOptionPane.showMessageDialog(null, "��������Ͽ�����", "ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}

}
