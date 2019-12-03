package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import dataBase.Figures;
import dataBase.ListInfo;
import msg.MsgAddFriendResp;
import msg.MsgChatText;
import msg.MsgHead;
import tools.DialogTool;
import tools.PackageTool;
import tools.ParseTool;

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
	/**
	 * sendMsg ������Ϣ
	 * 
	 * @param to
	 * @param Msg
	 * @throws IOException
	 */
	public void sendMsg(int to, String Msg) throws IOException {
		MsgChatText mct = new MsgChatText();
		byte data[] = Msg.getBytes();
		int TotalLen = 13;
		TotalLen += data.length;
		byte type = 0x04;
		mct.setTotalLen(TotalLen);
		mct.setType(type);
		mct.setDest(to);
		mct.setSrc(OwnJKNum);
		mct.setMsgText(Msg);

		byte[] sendMsg = PackageTool.packMsg(mct);
		ous.write(sendMsg);
		ous.flush();
	}
	
	/**
	 * processMsg ���ܷ�������������Ϣ
	 * 
	 * @throws IOException
	 */
	public void processMsg() throws IOException {
		byte[] data = receiveMsg();
		// ������ת��Ϊ��
		MsgHead recMsg = ParseTool.parseMsg(data);
		byte MsgType = recMsg.getType();

		// ���ݲ�ͬ����Ϣ���д���
		if (MsgType == 0x04) {
			MsgChatText mct = (MsgChatText) recMsg;
			int from = mct.getSrc();
			String Msg = mct.getMsgText();
			DialogTool.ShowMessage(from, Msg);
		}
		else if(MsgType == 0x03){//���º����б�
			System.out.println("Refresh list");
			ListInfo list = packlist(recMsg);
			Figures.list.Refresh_List(list);
		}
		if (MsgType == 0x55){
//			System.out.println("Here");
			MsgAddFriendResp mafr = (MsgAddFriendResp) recMsg;
			byte result = mafr.getState();
			System.out.println("Add Friend Result "+result);
			if(Figures.afu != null){
//				System.out.println("To show Result");
				Figures.afu.showResult(result);
			}
		}
	}

}
