import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.leveldb.DBController;
import com.decentralbay.utils.JSONObjectToString;

public class DBMain extends DBController {
	public static void main(String[] args) throws IOException, JSONException {
		System.out.println(DBController.getDatabaseStatus());
		String chainJSON = "";
		System.out.println(DBController.getLastIndex());
		for (int i = 0; i <= DBController.getLastIndex(); i++) {
			JSONObject block = DBController.getJSONObjBlockAtIndex(String.valueOf(i));
			System.out.println(block);
			block.put("products", new JSONArray());
			chainJSON += JSONObjectToString.JSONObjectToString(block) + "\n" + "products of block: " + block.getJSONArray("products").toString();
		}
		System.out.println(chainJSON);
		System.out.println("Last hash: " + DBController.getLastHash());
	}
}
