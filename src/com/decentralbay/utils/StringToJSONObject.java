package com.decentralbay.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class StringToJSONObject {
	public static JSONObject stringToJSONObject(String x) throws JSONException {
		JSONObject jsonObject = new JSONObject(x);
		return jsonObject;
	}
}