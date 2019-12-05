package tools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import msg.MsgAddFriend;
import msg.MsgAddFriendResp;
import msg.MsgChatText;
import msg.MsgHead;
import msg.MsgLogin;
import msg.MsgLoginResp;
import msg.MsgReg;
import msg.MsgRegResp;
import msg.MsgTeamList;

public class PackageTool {
	/*
	 * ??????????д??????????????
	 * 
	 * @param dous ??????
	 * 
	 * @param len ???????
	 * 
	 * @param s д????????
	 */
	private static void writeString(DataOutputStream dous, int len, String s) throws IOException {
		byte[] data = s.getBytes();
		if (data.length > len) {
			throw new IOException("д???????");
		}
		dous.write(data);
		while (data.length < len) {
			dous.writeByte('\0');
			len--;
		}
	}

	public static byte[] packMsg(MsgHead msg) throws IOException {
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		DataOutputStream dous = new DataOutputStream(bous);
		writeHead(dous, msg);
		int msgType = msg.getType();
		/*
		 * 0x01:???????
		 * 0x11:??????
		 * 0x02:???????
		 * 0x22:??????
		 * 0x05????????
		 * 0x55?????????
		 * 0x03:?????б?
		 * 0x04:???????
		 */
		if (msgType == 0x01) {
			MsgReg mr = (MsgReg) msg;
			writeString(dous, 10, mr.getNikeName());
			writeString(dous, 10, mr.getPwd());
		} else if (msgType == 0x11) {
			MsgRegResp mrr = (MsgRegResp) msg;
			dous.write(mrr.getState());
		} else if (msgType == 0x02) {
			MsgLogin mli = (MsgLogin) msg;
			writeString(dous, 10, mli.getPwd());
		} else if (msgType == 0x22) {
			MsgLoginResp mlr = (MsgLoginResp) msg;
			dous.write(mlr.getState());
		} else if (msgType == 0x03) {
			MsgTeamList mtl = (MsgTeamList) msg;

			// ??mtl?л?????
			String userName = mtl.getUserName();
			int pic = mtl.getPic();
			byte listCount = mtl.getListCount();
			String listName[] = mtl.getListName();
			byte bodyCount[] = mtl.getBodyCount();
			int bodyNum[][] = mtl.getBodyNum();
			int bodyPic[][] = mtl.getBodyPic();
			String nikeName[][] = mtl.getNikeName();
			byte bodyState[][] = mtl.getBodyState();

			// ???д??????
			int i, j;
			writeString(dous, 10, userName);
			dous.writeInt(pic);
			dous.write(listCount);// ???????
			for (i = 0; i < listCount; i++) {
				writeString(dous, 10, listName[i]);
				dous.write(bodyCount[i]);
				for (j = 0; j < bodyCount[i]; j++) {// ?????????
					dous.writeInt(bodyNum[i][j]);
					dous.writeInt(bodyPic[i][j]);
					writeString(dous, 10, nikeName[i][j]);
					dous.write(bodyState[i][j]);
				}
			}

		} else if (msgType == 0x04) {
			MsgChatText mct = (MsgChatText) msg;
			writeString(dous, mct.getTotalLen() - 13, mct.getMsgText());
		} else if (msgType == 0x05) {
			MsgAddFriend maf = (MsgAddFriend) msg;
			dous.writeInt(maf.getAdd_ID());
			writeString(dous,maf.getTotalLen() -  17,maf.getList_name());
		} else if (msgType == 0x55) {
			MsgAddFriendResp mafr = (MsgAddFriendResp) msg;
			dous.write(mafr.getState());
		}

		dous.flush();
		byte[] data = bous.toByteArray();
		return data;
	}
	
	private static void writeHead(DataOutputStream dous, MsgHead msg) throws IOException {
		dous.writeInt(msg.getTotalLen());
		dous.writeByte(msg.getType());
		dous.writeInt(msg.getDest());
		dous.writeInt(msg.getSrc());
	}
}
