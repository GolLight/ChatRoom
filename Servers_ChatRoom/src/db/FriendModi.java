package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import tools.ConTool;

public class FriendModi {
    
    /**
     * getCollectionsByJK
     * �����û���JK�Ż�ȡcollectionInfo ��list
     * @param jk JK��
     * @return List<CollectionInfo>
     * @throws SQLException SQL�쳣
     */
    public List<FriendInfo> getCollectionsByUID(int uid) throws SQLException {
    	ConTool contool=new ConTool();
        ResultSet rs = contool.query("SELECT * FROM uid_lid where uid=" + uid);
        ArrayList<FriendInfo> res = new ArrayList<>();
        while (rs.next()) {
            res.add(new FriendInfo(rs));
        }
        contool.release();
        return res;
    }
    
    /**
     * addUserToCollection
     * @param jk �û�JK��
     * @param coll_id �б�id
     * @return ��ӵ���Ŀ
     * @throws SQLException SQL�쳣
     */
    public int addUserToCollection(int ouid, int lid) throws SQLException {
    	ConTool contool=new ConTool();
        return contool.update(String.format("INSERT INTO relationship_1 (lid, uid) VALUES (%d, %d)", lid, ouid));
    }
    
    /**
     * createCollection
     * @param jk �û�JK��
     * @param collName �б���
     * @return �½����б�
     * @throws SQLException SQL�쳣
     */
    public FriendInfo createCollection(int uid, String collName) throws SQLException {
    	ConTool contool = new ConTool();
        String sql = String.format("INSERT INTO uid_lid (uid, lname) VALUES ('%d', %s)", uid, collName);
        int id = contool.insertAndGet(sql);
        return getCollection(id);
    }
    
    /**
     * getCollection
     * ��ȡָ���ĺ����б�
     * @param id �б�ID
     * @return �ҵ����б���Ϊnull
     * @throws SQLException SQL�쳣
     */
    public FriendInfo getCollection(int id) throws SQLException {
    	ConTool contool = new ConTool();
        String sql = String.format("SELECT * FROM uid_lid where lid=%d", id);
        ResultSet rs = contool.query(sql);
        if (!rs.next()) {
            return null;
        }
        FriendInfo result = new FriendInfo(rs);
        contool.release();
        return result;
    }

    public boolean isUserInCollection(int uid, int collectionId) throws SQLException {
    	boolean res=false;
    	ConTool contool = new ConTool();
        ResultSet rs = contool.query(String.format("SELECT * FROM relationship_1 where uid=%d AND lid=%d", uid, collectionId));
        if(rs.next())
        		res=true;
        contool.release();
        return res;
    }
    
    /**
     * 
     * @param name
     * @param jk
     * @return
     * @throws SQLException
     */
    public FriendInfo getCollectionByNameAndOwner(String name, int uid) throws SQLException {
        String sql = String.format("SELECT * FROM uid_lid where uid=%d AND lname='%s'", uid, name);
        ConTool contool = new ConTool();
        ResultSet rs = contool.query(sql);
        if (!rs.next()) {
        	contool.release();
            return null;
        }
        FriendInfo result = new FriendInfo(rs);
        contool.release();
        return result;
    }
    
    /**
     * removeCollection
     * @param id
     * @return
     * @throws SQLException
     */
    public int removeCollection(int id) throws SQLException {
        String sql = String.format("DELETE FROM uid_lid WHERE l_id=%d", id);
        ConTool contool = new ConTool();
        return contool.update(sql);
    }
    
    /**
     * getCollectionsByUser
     * @param user
     * @return
     * @throws Exception
     */
    public List<FriendInfo> getCollectionsByUser(UserInfo user) throws Exception {
        int uid = user.getUID();
        return getCollectionsByUID(uid);
    }
    
    /**
     * getCollectionsByUser
     * @param jk
     * @return
     * @throws SQLException
     */
    public List<FriendInfo> getCollectionsByUser(int uid) throws SQLException {
        return getCollectionsByUID(uid);
    }
    
//    public static void main(String args[]) throws SQLException {
//        DBConnection db = DBConnection.getInstance();
//        
//        //UserModel userModel = new UserModel(db);
//        CollectionModel collectionModel = new CollectionModel(db);
//        //UserInfo user = userModel.getUserByJK(0);
//        /*
//        List<CollectionInfo>  coll = collectionModel.getCollectionsByJK(0);
//		List<UserInfo> testlist;
//        for(int j = 0; j<coll.size();j++){
//        	try {
//        		System.out.println(coll.get(j).toString());
//				testlist = coll.get(j).getMembers();
//				for(int i = 0; i < testlist.size();i++){
//		        	System.out.println(testlist.get(i).getNickName());
//		        }
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        }*/
//     
//        CollectionInfo ci = collectionModel.getCollectionByNameAndOwner("�ҵĺ���", 0);
//        List<UserInfo> testlist;
//		try {
//			testlist = ci.getMembers();
//			System.out.println(ci.getId()+"  "+ci.getName());
//		    for(int i = 0; i< testlist.size();i++){
//		        System.out.println(testlist.get(i).getNickName());
//		    }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
