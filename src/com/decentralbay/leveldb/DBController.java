package com.decentralbay.leveldb;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.iq80.leveldb.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.decentralbay.blockchecks.BlockValid;
import com.decentralbay.core.Block;
import com.decentralbay.utils.StringToJSONObject;
import com.decentralbay.utils.StringUtil;
import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;
import static org.fusesource.leveldbjni.JniDBFactory.factory;
import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;




public abstract class DBController {
	private static final String databaseName = "blockchain";
	private final static Logger logger = Logger.getLogger(DBController.class.getName());
	private static int lastIndex = 0;
	
	
	public static String getStringBlockAtIndex(int index) throws IOException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String value = asString(db.get(bytes(String.valueOf(index))));
		db.close();
		return value;
	}
	
	
	public static String getStringBlockWithHash(String hash) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("hash").equals(hash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}
	
	
	public static String getStringBlockWithTimestamp(String timestamp) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("timeStamp").equals(timestamp)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}
	
	
	public static String getStringBlockWithPrevioushash(String previoushash) throws IOException, DBException, JSONException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("previousHash").equals(previoushash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}	
	
	
	public static String getStringBlockWithHeight(String height) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("height").equals(height)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}
	
	
	public static String getStringBlockWithServerWinner(String serverwinner) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("serverWinner").equals(serverwinner)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}
	
	
	public static String getStringBlockWithNonce(String nonce) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("nonce").equals(nonce)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return x;
			}
		}		
		db.close();
		return x;
	}
	
	
	public static JSONObject getJSONObjBlockWithNonce(String nonce) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("nonce").equals(nonce)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	

	public static JSONObject getJSONObjBlockWithServerWinner(String serverwinner) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("serverWinner").equals(serverwinner)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	
	
	public static JSONObject getJSONObjBlockWithHeight(String height) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("height").equals(height)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	

	public static JSONObject getJSONObjBlockWithPrevioushash(String previousHash) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("previousHash").equals(previousHash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	
	
	public static JSONObject getJSONObjBlockWithTimestamp(String timestamp) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("timeStamp").equals(timestamp)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	
	
	public static JSONObject getJSONObjBlockWithHash(String hash) throws JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			if (block.getString("hash").equals(hash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.close();
				return StringToJSONObject.stringToJSONObject(x);
			}
		}		
		db.close();
		return StringToJSONObject.stringToJSONObject(x);
	}
	
	
	public static JSONObject getJSONObjBlockAtIndex(String index) throws JSONException, IOException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String value = asString(db.get(bytes(String.valueOf(index))));
		db.close();
		return StringToJSONObject.stringToJSONObject(value);
	}
	
	
	public static void recordGenesisBlock() throws IOException, JSONException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		if (db.get(bytes(String.valueOf("0"))) == null) {
			//(int height, String data, String previousHash, ArrayList<JSONObject> Transactions
			ArrayList<JSONObject> x  = new ArrayList<JSONObject>();

			// hard coded genesis block 
			Block blck = new Block(0, "Genesis Block", "null", x);
			blck.setHash("7b244aad9bfabe48002ed52fee0a977ea536ca89cf0a79b30a5205f77d583341");
			blck.setTimeStamp(1573919007);
			blck.setNonce(0);
			blck.setMerkleRoot("merklerootasdasdadsadadsadsadasdasdadsasdasdadsasdadsad");
			blck.setHeight(0);
			String JSONBlock = StringUtil.JSONObjectToString(new JSONObject(blck));
			db.put(bytes("0"), bytes(JSONBlock));
		} else {
			
		}
		db.close();
	}
	
	
	public static void deleteBlockAtIndex(String index) throws IOException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		db.delete(bytes(index));
		db.close();
	}
	
	
	public static void deleteBlockWithHash(String hash) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("hash").equals(hash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void deleteBlockWithTimestamp(String timestamp) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("timeStamp").equals(timestamp)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void deleteBlockWithPreviousHash(String previoushash) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("previousHash").equals(previoushash)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void deleteBlockWithHeight(String height) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("height").equals(height)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void deleteBlockWithServerWinner(String serverwinner) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("serverWinner").equals(serverwinner)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void deleteBlockWithNonce(String nonce) throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex() + 1;
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found"; 
		for (int i = 0; i < lastIndex; i++) {
			JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
			
			
			if (block.getString("nonce").equals(nonce)) {
				x = asString(db.get(bytes(String.valueOf(i))));
				db.delete(bytes(String.valueOf(i)));				
				db.close();
			}
		}		
		db.close();
	}
	
	
	public static void recordStringBlock(String height, String blockx) throws IOException, DBException, JSONException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());

		if(BlockValid.isBlockValid(blockx)) {
			db.put(bytes(height), bytes(blockx));
			db.close();
		} else {
			db.close();
		}
	}
	
	
	public static void recordJSONObjBlock(String height, JSONObject blockx) throws IOException, DBException, JSONException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String JSONBlock = StringUtil.JSONObjectToString(blockx);

		if(BlockValid.isBlockValid(blockx)) {			
			db.put(bytes(height), bytes(JSONBlock));
			db.close();
		} else {
			db.close();
		}
	}
	
	
	public static String getDatabaseStatus() throws IOException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String stats = db.getProperty("leveldb.stats");
		System.out.println("\n\nSTATS: \n--------------------------------------------------\n" + stats);
		db.close();
		return stats;
	}
	
	
	public static synchronized int getLastIndex() throws IOException {
		DBController.setLastIndex();
		return lastIndex;
	}
	
	
	public static synchronized void setLastIndex() throws IOException {
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		DBIterator iterator = db.iterator();
		String key = "";
		
		try {
		  for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
		    key = asString(iterator.peekNext().getKey());
		    if (Integer.valueOf(key) > lastIndex) {
			    lastIndex = Integer.valueOf(key);
		    }
		  }
		} finally {
			iterator.close();
		}
		db.close();
	}
	
	
	public static JSONObject getLastBlock() throws DBException, JSONException, IOException {
		int i = getLastIndex();
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());

		String databaseIndex = String.valueOf(i);
		//	&& new CheckBlock(){}.BlockMerkleRoot(block) == true && new CheckBlock(){}.CorruptBlock(block) == false
		
		JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(i)))));
		return block;
	}
	
	
	public static void addStringBlock(String blockx) throws IOException, DBException, JSONException {
		int i = getLastIndex() + 1;
		JSONObject block = new JSONObject(blockx);
		String lasthash = getLastHash();
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());

		String databaseIndex = String.valueOf(i);
		//	&& new CheckBlock(){}.BlockMerkleRoot(block) == true && new CheckBlock(){}.CorruptBlock(block) == false
		System.out.println("DBCOntroller: " + BlockValid.isBlockValid(blockx));
		if(BlockValid.isBlockValid(block) == true && block.getString("previousHash").equals(lasthash)) {
			db.put(bytes(databaseIndex), bytes(blockx));
			db.close();
		} else {
			db.close();
		}	
	}
	
	
	public synchronized static void addJSONObjBlock(JSONObject block) throws IOException, DBException, JSONException {
		int i = getLastIndex() + 1;
		String lasthash = getLastHash();
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());

		String JSONBlock = StringUtil.JSONObjectToString(block);
		String databaseIndex = String.valueOf(i);
		//	&& new CheckBlock(){}.BlockMerkleRoot(block) == true && new CheckBlock(){}.CorruptBlock(block) == false
		System.out.println("DBCOntroller: " + BlockValid.isBlockValid(block));
		if(BlockValid.isBlockValid(block) == true && block.getString("previousHash").equals(lasthash)) {
			db.put(bytes(databaseIndex), bytes(JSONBlock));
			db.close();
		} else {
			db.close();
		}	
	}
	
	
	public static synchronized String getLastHash() throws DBException, JSONException, IOException {
		int lastIndex = getLastIndex();
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		String x = "not found";
		
		JSONObject block = StringToJSONObject.stringToJSONObject(asString(db.get(bytes(String.valueOf(lastIndex)))));
		db.close();			
		return block.getString("hash");		
	}
	
	
	public static ArrayList<String> getEmptyIndexes() throws IOException {
		ArrayList<String> emptyIndexes = new ArrayList<String>();
		int lastIndex = DBController.getLastIndex();

		
		
		DB db = factory.open(new File(databaseName), DatabaseConfig.getConfig());
		
		for (int i = 0; i < lastIndex + 1; i++) {
			//System.out.println(db.get(bytes(String.valueOf(i))) + " on pos " + i);
			if (db.get(bytes(String.valueOf(i))) == null) {
				emptyIndexes.add(String.valueOf(i));
			}
		}
	
		db.close();
		logger.setLevel(Level.ALL);
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.FINE);
		logger.addHandler(ch);
		
		
		
		try {
			FileHandler fileHandler = new FileHandler("BlockchainLogger.log");
			fileHandler.setLevel(Level.FINE);			
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "File logger not working!", e);
		}
		
		for(String empty: emptyIndexes) {
			logger.log(Level.INFO, "Missing block at height: " + empty);
		}
		
		return emptyIndexes;
	}
} 
 