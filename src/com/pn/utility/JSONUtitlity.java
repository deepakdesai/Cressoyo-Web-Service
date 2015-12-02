package com.pn.utility;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.pn.model.CityPojo;
import com.pn.model.CountryPojo;
import com.pn.model.StatePojo;

public class JSONUtitlity {
	/**
	 * Null check Method
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean isNotNull(String txt) {
		return txt != null && txt.trim().length() >= 0 ? true : false;
	}

	/**
	 * Method to construct JSON
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructJSON(String tag, boolean status) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("function", tag);
			obj.put("status", new Boolean(status));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return obj.toString();
	}

	/**
	 * Method to construct JSON with Error Msg
	 * 
	 * @param tag
	 * @param status
	 * @param err_msg
	 * @return
	 */
	public static String constructJSON(String tag, boolean status,String err_msg) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("function", tag);
			obj.put("status", new Boolean(status));
			obj.put("error_msg", err_msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj.toString(); 
	}
	
	public static String constructJSONForCountryList(List<CountryPojo> countryList) {
		JSONObject jsonAreaData = new JSONObject();
		JSONArray jsonCountryArray = new JSONArray();
		
		try {
			jsonAreaData.put("json_area", jsonCountryArray);
			
			for(CountryPojo countryPojo : countryList) {
				JSONObject jsonCountry = new JSONObject();
				jsonCountry.put("id", countryPojo.getId());
				jsonCountry.put("country", countryPojo.getName());
				
				JSONArray jsonStateArray = new JSONArray();
				
				for(StatePojo statePojo : countryPojo.getStateList()) {
					JSONObject jsonState = new JSONObject();
					jsonState.put("id", statePojo.getId());
					jsonState.put("country_id", statePojo.getCountryId());
					jsonState.put("state", statePojo.getName());
					
					JSONArray jsonCityArray = new JSONArray();
					
					for(CityPojo cityPojo : statePojo.getCityList()) {
						JSONObject jsonCity = new JSONObject();
						jsonCity.put("id", cityPojo.getId());
						jsonCity.put("country_id", cityPojo.getCountryId());
						jsonCity.put("state_id", cityPojo.getStateId());
						jsonCity.put("city", cityPojo.getName());
						
						jsonCityArray.put(jsonCity);
					}
					
					jsonState.put("city_list", jsonCityArray);
					
					jsonStateArray.put(jsonState);
				}
				
				jsonCountry.put("state_list", jsonStateArray);
				
				jsonCountryArray.put(jsonCountry);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonAreaData.toString();
	}
	
}
