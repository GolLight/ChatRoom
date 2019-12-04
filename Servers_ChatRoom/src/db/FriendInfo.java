package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class FriendInfo {
	private int ownerUID;
    private List<UserInfo> members;
    private String name;
    private int id;

    /**
     * CollectionInfo
     * ��collection���ж�ȡ�����ݹ���info����
     * @param rs
     * @throws SQLException
     */
    FriendInfo(ResultSet rs) throws SQLException {
        ownerUID = rs.getInt("uid");
        name = rs.getString("lname");
        id = rs.getInt("lid");
    }
    
    /**
     * getMembers
     * ��ȡһ��collection�еĶ���
     * @return List<UserInfo>
     * @throws Exception
     */
    public List<UserInfo> getMembers() throws Exception {
        if (members == null) {
            UserModi usermodi= new UserModi();
            members = usermodi.getUsersInCollection(id);
        }
        return members;
    }
    
    /**
     * �������
     */
    public String toString() {
        return String.format("Collection: %s, id: %d, ownerUID: %d", name, id, ownerUID);
    }
    

    public int getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(int ownerUID) {
        this.ownerUID = ownerUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
