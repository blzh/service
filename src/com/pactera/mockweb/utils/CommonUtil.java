package com.pactera.mockweb.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtil {

	private static String webRoot;
	private static String uploadDir;

	/**
	 * 获取项目文件目录路径
	 * 
	 * @return
	 */
	public static String getWebRoot() {
		if (webRoot == null) {
			try {
				webRoot = new CommonUtil().getClass().getClassLoader().getResource("/").getPath();
				webRoot = webRoot.substring(0, webRoot.indexOf("WEB-INF"));
			} catch (Exception e) {
				webRoot = new File("").getAbsolutePath();
			}
		}
		return webRoot;
	}

	/**
	 * 获取项目文件目录路径（带参数）
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getWebRoot(Class clazz) {
		if (webRoot == null) {
			try {
				webRoot = clazz.getClass().getClassLoader().getResource("/").getPath();
				webRoot = webRoot.substring(0, webRoot.indexOf("WEB-INF"));
			} catch (Exception e) {
				webRoot = new File("").getAbsolutePath();
			}
		}
		return webRoot;
	}

	public static String getUploadDir() {
		if (uploadDir == null) {
			uploadDir = getWebRoot() + File.separator + "upload" + File.separator;
		}
		return uploadDir;
	}

	/**
	 * 获取文件的拓展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		if (fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return "";
	}

	public static boolean checkNullOrBlank(Object mapObj) {
		if (mapObj == null) {
			return true;
		}
		if ("".equals(mapObj)) {
			return true;
		}
		return false;
	}

	public static String parseString(Object mapObj) {
		if (mapObj != null) {
			return (String) mapObj;
		}
		return "";
	}

	public static List<String> parseArray(Object mapObj) {
		if (mapObj != null && mapObj instanceof List) {
			return (List<String>) mapObj;
		}
		return new ArrayList<String>();
	}

	public static int parseInteger(Object mapObj) {
		if (mapObj != null) {
			if (mapObj instanceof Integer) {
				return (Integer) mapObj;
			} else if (mapObj instanceof Double) {
				double dd = (Double) mapObj;
				return (int) dd;
			} else if (!"".equals(mapObj)) {
				return Integer.parseInt(mapObj.toString());
			}
		}
		return 0;
	}

	public static long parseLong(Object mapObj) {
		if (mapObj != null) {
			if (mapObj instanceof Long) {
				return (Long) mapObj;
			} else if (mapObj instanceof Double) {
				double dd = (Double) mapObj;
				return (long) dd;
			} else if (!"".equals(mapObj)) {
				return Long.parseLong(mapObj.toString());
			}
		}
		return 0L;
	}

	public static Double parseDouble(Object mapObj) {
		if (mapObj != null) {
			if (mapObj instanceof Double) {
				return (Double) mapObj;
			} else if (!"".equals(mapObj)) {
				return Double.parseDouble(mapObj.toString());
			}
		}
		return 0d;
	}

	public static String randomNumber(int size) {
		String ret = "";
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			int n = r.nextInt(10);
			ret = ret + n;
		}
		return ret;
	}

	public static String rightString(String str, int size) {
		if (str.length() > size) {
			return str.substring(str.length() - size, str.length());
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(CommonUtil.getWebRoot());
	}
}
