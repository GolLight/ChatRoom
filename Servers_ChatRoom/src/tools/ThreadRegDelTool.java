package tools;

import db.ThreadPool;
import server.ServerThread;

public class ThreadRegDelTool {
	public static void RegThread(ServerThread thread) {
		ThreadPool.threadpool.put(String.valueOf(thread.getUserid()), thread);
	}

	// ���߳����ݿ��м�ɾ��
	public static void DelThread(int Userid) {
//		System.out.println("Del JK");
		ThreadPool.threadpool.remove(String.valueOf(Userid));
	}
}
