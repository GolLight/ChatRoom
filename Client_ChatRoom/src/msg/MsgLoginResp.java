package msg;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/*
 * ����Ϣ��Ϊ��½״̬����
 */
public class MsgLoginResp extends MsgHead {
	/*
	 * |state(1)|
	 */
	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	private byte state;

}
