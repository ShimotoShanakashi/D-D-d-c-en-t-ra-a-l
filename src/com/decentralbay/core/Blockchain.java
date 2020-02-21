package com.decentralbay.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.iq80.leveldb.DBException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.decentralbay.cipherutils.SignProduct;
import com.decentralbay.leveldb.DBController;
import com.decentralbay.miner.CalculateNonce;
import com.decentralbay.net.networking;
import com.decentralbay.network.ServerManager;
import com.decentralbay.utils.JSONObjectToString;
import com.decentralbay.utils.StringToJSONObject;
import com.decentralbay.utils.StringUtil;
import com.decentralbay.wallet.Wallet;



public class Blockchain extends DBController implements networking {
	private static Blockchain Blockchain;
	private static ServerManager network;
	private final static Logger logger = Logger.getLogger(Blockchain.class.getName());
	private static ArrayList<JSONObject> pendingProducts = new ArrayList<JSONObject>();
	
	
	
	private Blockchain(String[] args) throws IOException, JSONException {
		network = getNetwork();
		initialChecks();
	}
	
	
	public static Blockchain getSingletonBlockchain(String[] args) throws IOException, JSONException {
		if (Blockchain == null) {
			Blockchain = new Blockchain(args);
		}
		return Blockchain;
	}	
	
	
	private static void initialChecks() throws IOException, JSONException {
		DBController.recordGenesisBlock();
	}
	
	
	private static boolean minedBlock(JSONObject block) throws UnknownHostException, IOException, DBException, JSONException {
		network.broadcastMessage("addBlock#" + JSONObjectToString.JSONObjectToString(block));		
		DBController.addJSONObjBlock(block);
		pendingProducts.clear();
		return true;
	}
	
	
	public JSONObject createNewProduct(String name, String productValue, String productStatus, String imgLink, String tags, String description, PublicKey sellerAddress, PrivateKey sellerKey, 
			PublicKey serverAddress, PrivateKey serverKey, String serverIp) throws JSONException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeySpecException {
		JSONObject prod = new Product(name, productValue, productStatus, imgLink, tags, description, sellerAddress, sellerKey, serverAddress, serverKey, serverIp).signProduct();
		return prod;
	}	
	
	
	public JSONObject createNewProduct(String name, String productValue, String productStatus, String imgLink, String tags, String description, String sellerAddress, String sellerKey, 
			String serverAddress, String serverKey, String serverIp) throws JSONException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeySpecException {
		JSONObject prod = new Product(name, productValue, productStatus, imgLink, tags, description, sellerAddress, sellerKey, serverAddress, serverKey, serverIp).signProduct();
		return prod;
	}	
	
	
	public static void mineBlock(Thread t) throws DBException, JSONException, IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		Wallet wallet1 = new Wallet();

		PublicKey pbk1 = wallet1.publicKey;
		PrivateKey pvk1 = wallet1.privateKey;


		ArrayList<JSONObject> pending = new ArrayList<JSONObject>();
		
		
		// implements - block confirmations -> increments by 1
		Block block = new Block(DBController.getLastIndex() + 1, String.valueOf(InetAddress.getLocalHost().getHostAddress()), DBController.getLastHash(), pendingProducts);
		block.setMerkleRoot();
		JSONObject JSONMinedBlock = CalculateNonce.calculateNonce(new JSONObject(block));
		minedBlock(JSONMinedBlock);
		t.interrupt();
	}
	
	
	public static void addProduct(String productx) throws JSONException, UnknownHostException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidAlgorithmParameterException {
		JSONObject product = SignProduct.signServerProduct(StringToJSONObject.stringToJSONObject(productx));
		pendingProducts.add(product);
		network.broadcastMessage("addProduct#" + JSONObjectToString.JSONObjectToString(product));
		
		
		if(pendingProducts.size() >= Config.maxblocks) {
			(new Thread() {
				public void run() {
					try {
						mineBlock(this);
					} catch (DBException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | JSONException
							| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}			
			}).start();
		}		
	}
	
	
	public static void addProduct(JSONObject productx) throws UnknownHostException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidAlgorithmParameterException, JSONException {
		JSONObject product = SignProduct.signServerProduct(productx);
		pendingProducts.add(product);
		network.broadcastMessage("addProduct#" + JSONObjectToString.JSONObjectToString(product));		
		
		if(Config.automining == true) {
			if(pendingProducts.size() >= Config.maxblocks) {
				(new Thread() {
					public void run() {
						try {
							mineBlock(this);
						} catch (DBException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | JSONException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
					}			
				}).start();
			}	
		}	
	}
	
	
	// Getters
	public static JSONObject getLastJSONObjBlock() throws DBException, JSONException, IOException {
		return DBController.getLastBlock();
	}
	
	
	public static String getLastStringBlock() throws DBException, JSONException, IOException {
		return StringUtil.JSONObjectToString(DBController.getLastBlock());
	}
	
	
	public static JSONArray getPendingProducts() {
		JSONArray JSONArrayProducts = new JSONArray(pendingProducts);
		return JSONArrayProducts;
	}
	
	
	public static JSONArray getJSONArrayChain() throws IOException, JSONException {
		ArrayList<JSONObject> blocks = new ArrayList<JSONObject>();
		
		for (int i = 0; i < DBController.getLastIndex() + 1; i++) {
			blocks.add(DBController.getJSONObjBlockAtIndex(String.valueOf(i)));
		}
		JSONArray chainJSON = new JSONArray(blocks);
		return chainJSON;
	}

	
	public static String getLastHash() throws DBException, JSONException, IOException {
		return DBController.getLastHash();
	}
	

	public static String getJSONStringLightChain() throws DBException, JSONException, IOException {
		String chainJSON = "";

		for (int i = 0; i < DBController.getLastIndex(); i++) {
			JSONObject block = DBController.getJSONObjBlockAtIndex(String.valueOf(i));

			block.put("products", new JSONArray());
			chainJSON += JSONObjectToString.JSONObjectToString(block);
		}
		
		return chainJSON;
	}
	
	
	public static JSONArray getJSONArrayLightChain() throws DBException, JSONException, IOException {
		ArrayList<JSONObject> blocks = new ArrayList<JSONObject>();

		for (int i = 0; i < DBController.getLastIndex() + 1; i++) {
			JSONObject block = DBController.getJSONObjBlockAtIndex(String.valueOf(i));
			
			block.put("products", new JSONArray());
			blocks.add(block);
		}
		JSONArray chainJSON = new JSONArray(blocks);
		return chainJSON;
	}
}
