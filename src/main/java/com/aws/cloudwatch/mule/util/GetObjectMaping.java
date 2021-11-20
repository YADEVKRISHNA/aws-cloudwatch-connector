package com.aws.cloudwatch.mule.util;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class GetObjectMaping {
	
	public static Object isJSONValid(Object test) {
		Object returnObject = null;
		try {
			
			Gson g = new Gson();
			String str = g.toJson(test);
			//JSONObject json = new JSONObject(test);
			//returnObject = json;
			returnObject = str;

		} catch (JSONException ex) {
			try {
				new JSONArray(test);
				JSONArray jsonArray = new JSONArray(test);
				returnObject = jsonArray.toList();
			} catch (JSONException ex1) {
				returnObject = test;
			}
		}

		return returnObject;
	}

}
