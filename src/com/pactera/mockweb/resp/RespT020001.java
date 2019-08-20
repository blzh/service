package com.pactera.mockweb.resp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;

import com.pactera.mockweb.servlet.GetResponse;
import com.pactera.mockweb.utils.CommonUtil;

public class RespT020001 {
	private String id;
	private String index;
	private String name;
	private String desc;
	private int dialogType;
	private boolean isMultiCheck;
	private List<RespT020001> children;

	public RespT020001 id(String id) {
		this.id = id;
		return this;
	}

	public RespT020001 index(String index) {
		this.index = index;
		return this;
	}

	public RespT020001 name(String name) {
		this.name = name;
		return this;
	}

	public RespT020001 desc(String desc) {
		this.desc = desc;
		return this;
	}

	public RespT020001 dialogType(int dialogType) {
		this.dialogType = dialogType;
		return this;
	}

	public RespT020001 isMultiCheck(boolean isMultiCheck) {
		this.isMultiCheck = isMultiCheck;
		return this;
	}

	public RespT020001 children(List<RespT020001> children) {
		this.children = children;
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getDialogType() {
		return dialogType;
	}

	public void setDialogType(int dialogType) {
		this.dialogType = dialogType;
	}

	public boolean getMultiCheck() {
		return isMultiCheck;
	}

	public void setMultuCheck(boolean isMultiCheck) {
		this.isMultiCheck = isMultiCheck;
	}

	public List<RespT020001> getChildren() {
		return children;
	}

	public void setChildren(List<RespT020001> children) {
		this.children = children;
	}

	public static List<RespT020001> getRespData() {
		List<RespT020001> list = new ArrayList<RespT020001>();

		RespT020001 factor1 = new RespT020001().id("1").name("选股范围").children(new ArrayList<RespT020001>());
		RespT020001 shichang = new RespT020001().id("1_1").name("市场").children(new ArrayList<RespT020001>());
		shichang.isMultiCheck(true).dialogType(2);
		shichang.getChildren().add(new RespT020001().id("1_1_1").name("沪深300").desc("沪深300"));
		shichang.getChildren().add(new RespT020001().id("1_1_2").name("上证a股").desc("上证a股"));
		shichang.getChildren().add(new RespT020001().id("1_1_3").name("深证a股").desc("深证a股"));
		shichang.getChildren().add(new RespT020001().id("1_1_4").name("上证50").desc("上证50"));
		shichang.getChildren().add(new RespT020001().id("1_1_5").name("中证500").desc("中证500"));
		shichang.getChildren().add(new RespT020001().id("1_1_6").name("中小板").desc("中小板"));
		shichang.getChildren().add(new RespT020001().id("1_1_7").name("创业板").desc("创业板"));
		factor1.getChildren().add(shichang);
		factor1.getChildren().add(
				new RespT020001().id("1_2").name("行业板块").isMultiCheck(true).dialogType(1).children(new ArrayList<RespT020001>()));
		factor1.getChildren().add(
				new RespT020001().id("1_3").name("概念板块").isMultiCheck(true).dialogType(1).children(new ArrayList<RespT020001>()));
		factor1.getChildren().add(
				new RespT020001().id("1_4").name("地域板块").isMultiCheck(true).dialogType(1).children(new ArrayList<RespT020001>()));
		for (int i = 1; i <= 3; i++) {
			char index = 'A';
			for (int j = 1; j <= 27; j++) {
				String indexStr = ((char) (index + j - 1)) + "";
				if(j==27) {
					indexStr = "#";
				}
				String idStr = "1_" + (i + 1) + "_" + j;
				factor1.getChildren().get(i).getChildren().add(new RespT020001().id(idStr).index(indexStr).children(new ArrayList<RespT020001>()));
				Random random = new Random();
				int randomNum = random.nextInt(50);
				int l=1;
				do {
					String name = "行业板块";
					if (i == 2) {
						name = "概念板块";
					} else if (i == 3) {
						name = "地域板块";
					}
					name = indexStr + name;
					factor1.getChildren().get(i).getChildren().get(j - 1).getChildren().add(new RespT020001().id(idStr + "_" + l).name(name + l).desc(name + l));
					l=l+1;
				}while(l<=randomNum);
			}
		}

		list.add(factor1);

		String[] factorArr = new String[] { "行情价量", "技术指标", "K线形态", "财务数据", "小方特色" };
		for (int i = 0; i < factorArr.length; i++) {
			RespT020001 factor = getFactorByFileName((i + 2) + "", factorArr[i]);
			if (factor != null) {
				list.add(factor);
			}
		}

		return list;
	}

	private static RespT020001 getFactorByFileName(String id, String factorName) {
		File file = new File(CommonUtil.getWebRoot() + File.separator + GetResponse.responseDir + File.separator
				+ factorName + ".txt");
		if (!file.exists()) {
			return null;
		}

		InputStream in = null;
		BufferedReader bf = null;
		String line = null;
		RespT020001 factor = null;

		try {
			in = new FileInputStream(file);
			bf = new BufferedReader(new InputStreamReader(in));
			factor = new RespT020001().id(id).name(factorName).children(new ArrayList<RespT020001>());

			int lines = 0;
			while ((line = bf.readLine()) != null && !"".equals(line)) {
				String[] strs = line.split(":");
				if (strs == null || strs.length < 1) {
					continue;
				}
				if (strs[0] != null && !"".equals(strs[0])) {
					Random radom = new Random();
					int radomN = radom.nextInt(10);
					String desc =  strs[0];
					for(int n=0;n<radomN;n++) {
						desc = desc + "_"+strs[0];
					}
					
					factor.getChildren().add(new RespT020001().id(id + "_" + (++lines)).name(strs[0]).desc(desc)
							.children(new ArrayList<RespT020001>()));
				}

				if (strs.length >= 2 && strs[1] != null && !"".equals(strs[1])) {
					String[] children = strs[1].split(";");
					int i = 0;
					for (; children != null && i < children.length; i++) {
						if (children[i] != null && !"".equals(children[i])) {
							RespT020001 one = new RespT020001().index(id + "_" + lines + "_" + (i+1)).name(children[i])
									.desc(strs[0] + "[" + children[i] + "]");
							factor.getChildren().get(factor.getChildren().size() - 1).getChildren().add(one);

						}
					}

					int dialog = 0;
					if (i != 0 && children[i - 1].contains("自定义")) {
						dialog = 3;
					} else {
						dialog = 2;
					}
					factor.getChildren().get(factor.getChildren().size() - 1).dialogType(dialog);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bf = null;
			in = null;
		}
		return factor;
	}
}
