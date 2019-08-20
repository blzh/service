package com.pactera.mockweb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 读取json处理类
 * 
 * @author xiegl
 * 
 */

public class JsonUtil {

	public static String map2Json(Map map) {
		// JSONObject jo = new JSONObject();
		// jo.putAll(map);
		// return jo.toString();

		// 修改为使用jackson方式
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将JSON数据解析成Map对象，键值对
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> json2Map(String jsonStr) {
		// JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		// Map<String, Object> jsonMap = new HashMap<String, Object>();
		// jsonObject2Map(jsonMap, jsonObj);
		// return jsonMap;

		// 修改为使用jackson方式
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 将JSONObject放到map
	 * 
	 * @param map
	 * @param jobj
	 */
	private static void jsonObject2Map(Map<String, Object> map, JSONObject jobj) {
		Iterator it = jobj.keys();
		while (it.hasNext()) {
			String leafKey = String.valueOf(it.next());
			Object leaf = jobj.get(leafKey);
			if (leaf instanceof JSONArray) {
				jsonArray2Map(map, leafKey, (JSONArray) leaf);
			} else {
				map.put(leafKey, String.valueOf(leaf));
			}
		}
	}

	/**
	 * 将JSONArray解析成Map集合，放到map中
	 * 
	 * @param map
	 * @param key
	 * @param jarray
	 */
	private static void jsonArray2Map(Map map, String key, JSONArray jarray) {
		List jsonList = new ArrayList();
		for (int i = 0; i < jarray.size(); i++) {
			Object obj = jarray.get(i);
			if (obj instanceof JSONObject) {
				JSONObject jobj = (JSONObject) obj;
				Map newMap = new HashMap<String, Object>();
				jsonObject2Map(newMap, jobj);
				jsonList.add(newMap);
			} else {
				jsonList.add(obj);
			}
		}
		map.put(key, jsonList);
	}

	public static Map<String, Object> beanArray2Map(String key, List list) {

		JSONArray jarray = new JSONArray();
		jarray.addAll(list);
		Map<String, Object> arrayMap = new HashMap<String, Object>();
		arrayMap.put(key, jarray.toString());
		return arrayMap;
	}

	public static void main(String[] args) {
		// JsonProcess process = new JsonProcess();
		// Map<String, Object> map1 = process.json2Map("{'data':['The Success Accounts number is :0','0001:accountID已存在!','0002:accountID已存在!'],'message':'处理成功','result':0}");
		// System.out.println(map1.get("data"));
	}
}
