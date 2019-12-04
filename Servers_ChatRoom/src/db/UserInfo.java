package db;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserInfo {
	/*
	 * �û�������Ϣ
	 */

	private int UID;// ������û���UID��
	private String nickName;// ������û����ǳ�
	private int avatar;

	UserInfo(ResultSet userResult) throws SQLException {
	    UID = userResult.getInt("uid");
	    nickName = userResult.getString("nickname");
	    avatar = userResult.getInt("avatar");
    }

	/*
	 * �û�������Ϣ
	 */

	private byte collectionCount;// �����ж��������
	private String ListName[];// ����ÿ�����������
	private byte[] bodyCount;// ÿ���ж��ٸ���
	private int bodyNum[][];// ÿ�����ѵ�UID��
	private int bodypic[][];//����ͷ��
	private String bodyName[][];// ÿ�����ѵ��ǳ�

	public byte getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(byte listCount) {
		this.collectionCount = listCount;
	}

	public String[] getListName() {
		return ListName;
	}

	public void setListName(String[] listName) {
		ListName = listName;
	}

	public byte[] getBodyCount() {
		return bodyCount;
	}

	public void setBodyCount(byte[] bodyCount) {
		this.bodyCount = bodyCount;
	}

	public int[][] getBodyNum() {
		return bodyNum;
	}

	public void setBodyNum(int[][] bodyNum) {
		this.bodyNum = bodyNum;
	}

	public String[][] getBodyName() {
		return bodyName;
	}

	public void setBodyName(String[][] bodyName) {
		this.bodyName = bodyName;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uid) {
		UID = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nick) {
		nickName = nick;
	}

	public boolean equals(UserInfo compare) {
		if (compare.getUID() == UID/* && compare.getPassWord().equals(passWord)*/) {
			return true;
		}
		return false;
	}
  
	public int[][] getBodypic() {
		return bodypic;
	}

	public void setBodypic(int bodypic[][]) {
		this.bodypic = bodypic;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
}
