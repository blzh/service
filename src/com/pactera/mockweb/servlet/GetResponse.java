package com.pactera.mockweb.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.pactera.mockweb.resp.Resp;
import com.pactera.mockweb.resp.RespT020001;
import com.pactera.mockweb.resp.RespT020002;
import com.pactera.mockweb.resp.RespT020003;
import com.pactera.mockweb.resp.RespT020006;
import com.pactera.mockweb.resp.RespT020003.SelectedStockList;
import com.pactera.mockweb.resp.RespT020006.MethodList;
import com.pactera.mockweb.resp.RespT020006.MethodList.SelectedsubContent;
import com.pactera.mockweb.utils.CommonUtil;

public class GetResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String webroot = "";
	public static String responseDir = "responseFile";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetResponse() {
		super();
		webroot = CommonUtil.getWebRoot();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String path = request.getRequestURL().toString();
		if (path == null || "".equals(path)) {
			response.getWriter().append("{'retCode':'9999','retMsg':'非法路径'}");
			return;
		}
		
		if(path.endsWith("api")) {
			File file = new File(webroot + File.separator + responseDir + File.separator +  "多因子选股接口文档设计v1.1.doc");
			if (!file.exists()) {
				response.getWriter().append("{'retCode':'9999','retMsg':'" + "多因子选股接口文档设计v1.1.doc文件不存在'}");
				return;
			}

	   
	        response.setHeader("Content-Disposition", "attachment;filename=多因子选股接口文档设计v1.1.doc");
	        InputStream is= new FileInputStream(file);
	        OutputStream os = response.getOutputStream();
	        int len;
	        byte[] bys = new byte[1024];
	        while((len = is.read(bys)) != -1){
	            os.write(bys, 0, len);
	        }
	        
	        os.close();
	        is.close();
			return;
		}
		
		if(true) {
			doPost(request,response);
			return;
		}
		
		
		response.setContentType("text/html;charset=utf-8");
		String transId = (String) request.getParameter("transId");
		if (transId == null || "".equals(transId)) {
			response.getWriter().append("{'retCode':'9999','retMsg':'tradeNo不能为空'}");
			return;
		} else {
			File file = new File(webroot + File.separator + responseDir + File.separator + transId + ".properties");
			if (!file.exists()) {
				response.getWriter().append("{'retCode':'9999','retMsg':'" + transId + ".properties文件不存在'}");
				return;
			}
			InputStream in = new FileInputStream(file);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			Properties p = new Properties();
			p.load(bf);
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(p));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String path = request.getRequestURL().toString();
		if (path == null || "".equals(path)) {
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getFailureResp("非法路径")));
			return;
		}
		
		Random r = new Random();
		if(r.nextInt(10)%9==0) {
			String[] message = new String[] {"连接超时","登录状态失效","操作太频繁","系统异常","未知异常"}; 
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getFailureResp(message[r.nextInt(10)%message.length])));
			return;	
		}
		
		
		
		String transId = "";
		if(path.endsWith("queryStockFactors")) {
			transId = "T020001";
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(RespT020001.getRespData())));
			return;
		}
		
		if(path.endsWith("queryStockNum")) {
			transId = "T020002";
			RespT020002 respData = new RespT020002();
			Random random = new Random();
			int[] subitemCount = new int[5];
			int min = 10001;
			for(int i=0;i<5;i++) {
				subitemCount[i]= random.nextInt(10000);
				if(subitemCount[i]<min) {
					min = subitemCount[i];
				}
			}
			respData.setSubitemCount(subitemCount);
			respData.setResultCount(min <=0? 0 : random.nextInt(min));
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(respData)));
			return;
		}
		
		if(path.endsWith("queryStockResult") || path.endsWith("queryStockMethodDetail")) {
			transId = "T020003";
			RespT020003 respData = new RespT020003();
			List<RespT020003.SelectedStockList> list = new ArrayList<RespT020003.SelectedStockList>();
			/*
			 * list.add(new RespT020003.SelectedStockList(4609,"002256", "兆新股份"));
			 * list.add(new RespT020003.SelectedStockList(4609,"688011", "齐心集团"));
			 * list.add(new RespT020003.SelectedStockList(4609,"688003", "银河磁体"));
			 * list.add(new RespT020003.SelectedStockList(4609,"688005", "中欣氟材"));
			 * list.add(new RespT020003.SelectedStockList(4609,"688088", "科瑞技术"));
			 * list.add(new RespT020003.SelectedStockList(4609,"688388", "宇环数控"));
			 * list.add(new RespT020003.SelectedStockList(4609,"600311", "苏州银河"));
			 * list.add(new RespT020003.SelectedStockList(4609,"300508", "维宏股份"));
			 * list.add(new RespT020003.SelectedStockList(4609,"300781", "因赛集团"));
			 */
			
			list.add(new RespT020003.SelectedStockList(4614,"002960","N青鸟"));
			list.add(new RespT020003.SelectedStockList(4614,"002247","聚力文化"));
			list.add(new RespT020003.SelectedStockList(4614,"002075","沙钢股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300600","瑞特股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300340","科恒股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002815","崇达技术"));
			list.add(new RespT020003.SelectedStockList(4614,"002351","漫步者"));
			list.add(new RespT020003.SelectedStockList(4614,"002866","传艺科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002949","华阳国际"));
			list.add(new RespT020003.SelectedStockList(4621,"300492","山鼎设计"));
			list.add(new RespT020003.SelectedStockList(4621,"300663","科蓝软件"));
			list.add(new RespT020003.SelectedStockList(4621,"300709","精研科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300651","金陵体育"));
			list.add(new RespT020003.SelectedStockList(4614,"002528","英飞拓"));
			list.add(new RespT020003.SelectedStockList(4609,"000049","德赛电池"));
			list.add(new RespT020003.SelectedStockList(4609,"000608","阳光股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002256","兆新股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002364","中恒电气"));
			list.add(new RespT020003.SelectedStockList(4621,"300526","中潜股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002617","露笑科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300561","汇金科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002617","露笑科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300561","汇金科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002057","中钢天源"));
			list.add(new RespT020003.SelectedStockList(4614,"002562","兄弟科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002910","庄园牧场"));
			list.add(new RespT020003.SelectedStockList(4609,"000062","深圳华强"));
			list.add(new RespT020003.SelectedStockList(4614,"002018","*ST华信"));
			list.add(new RespT020003.SelectedStockList(4609,"000681","视觉中国"));
			list.add(new RespT020003.SelectedStockList(4609,"000760","*ST斯太"));
			list.add(new RespT020003.SelectedStockList(4614,"002711","*ST欧浦"));
			list.add(new RespT020003.SelectedStockList(4621,"300401","花园生物"));
			list.add(new RespT020003.SelectedStockList(4621,"300331","苏大维格"));
			list.add(new RespT020003.SelectedStockList(4614,"002757","南兴股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000523","广州浪奇"));
			list.add(new RespT020003.SelectedStockList(4621,"300509","新美星"));
			list.add(new RespT020003.SelectedStockList(4614,"002679","福建金森"));
			list.add(new RespT020003.SelectedStockList(4621,"300061","康旗股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002574","明牌珠宝"));
			list.add(new RespT020003.SelectedStockList(4614,"002859","洁美科技"));
			list.add(new RespT020003.SelectedStockList(4609,"000833","粤桂股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002229","鸿博股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300476","胜宏科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300099","精准信息"));
			list.add(new RespT020003.SelectedStockList(4614,"002511","中顺洁柔"));
			list.add(new RespT020003.SelectedStockList(4609,"000520","长航凤凰"));
			list.add(new RespT020003.SelectedStockList(4621,"300689","澄天伟业"));
			list.add(new RespT020003.SelectedStockList(4614,"002384","东山精密"));
			list.add(new RespT020003.SelectedStockList(4614,"002881","美格智能"));
			list.add(new RespT020003.SelectedStockList(4614,"002769","普路通"));
			list.add(new RespT020003.SelectedStockList(4621,"300779","惠城环保"));
			list.add(new RespT020003.SelectedStockList(4614,"002250","联化科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300326","凯利泰"));
			list.add(new RespT020003.SelectedStockList(4621,"300584","海辰药业"));
			list.add(new RespT020003.SelectedStockList(4614,"002373","千方科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002182","云海金属"));
			list.add(new RespT020003.SelectedStockList(4614,"002660","茂硕电源"));
			list.add(new RespT020003.SelectedStockList(4621,"300529","健帆生物"));
			list.add(new RespT020003.SelectedStockList(4614,"002503","搜于特"));
			list.add(new RespT020003.SelectedStockList(4614,"002609","捷顺科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002481","双塔食品"));
			list.add(new RespT020003.SelectedStockList(4621,"300747","锐科激光"));
			list.add(new RespT020003.SelectedStockList(4621,"300244","迪安诊断"));
			list.add(new RespT020003.SelectedStockList(4621,"300151","昌红科技"));
			list.add(new RespT020003.SelectedStockList(4609,"000911","*ST 南糖"));
			list.add(new RespT020003.SelectedStockList(4621,"300234","开尔新材"));
			list.add(new RespT020003.SelectedStockList(4614,"002076","雪 莱 特"));
			list.add(new RespT020003.SelectedStockList(4621,"300568","星源材质"));
			list.add(new RespT020003.SelectedStockList(4621,"300604","长川科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002496","辉丰股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002435","长江润发"));
			list.add(new RespT020003.SelectedStockList(4621,"300363","博腾股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300776","帝尔激光"));
			list.add(new RespT020003.SelectedStockList(4621,"300542","新晨科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300664","鹏鹞环保"));
			list.add(new RespT020003.SelectedStockList(4621,"300692","中环环保"));
			list.add(new RespT020003.SelectedStockList(4614,"002288","超华科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300131","英唐智控"));
			list.add(new RespT020003.SelectedStockList(4621,"300420","五洋停车"));
			list.add(new RespT020003.SelectedStockList(4621,"300251","光线传媒"));
			list.add(new RespT020003.SelectedStockList(4609,"000516","国际医学"));
			list.add(new RespT020003.SelectedStockList(4614,"002636","金安国纪"));
			list.add(new RespT020003.SelectedStockList(4621,"300770","新媒股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300675","建科院"));
			list.add(new RespT020003.SelectedStockList(4614,"002953","日丰股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002346","柘中股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300081","恒信东方"));
			list.add(new RespT020003.SelectedStockList(4621,"300748","金力永磁"));
			list.add(new RespT020003.SelectedStockList(4614,"002909","集泰股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002008","大族激光"));
			list.add(new RespT020003.SelectedStockList(4614,"002252","上海莱士"));
			list.add(new RespT020003.SelectedStockList(4614,"002913","奥士康"));
			list.add(new RespT020003.SelectedStockList(4621,"300168","万达信息"));
			list.add(new RespT020003.SelectedStockList(4621,"300198","纳川股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002852","道道全"));
			list.add(new RespT020003.SelectedStockList(4609,"000028","国药一致"));
			list.add(new RespT020003.SelectedStockList(4609,"000860","顺鑫农业"));
			list.add(new RespT020003.SelectedStockList(4614,"002352","顺丰控股"));
			list.add(new RespT020003.SelectedStockList(4621,"300778","新城市"));
			list.add(new RespT020003.SelectedStockList(4621,"300038","数知科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300436","广生堂"));
			list.add(new RespT020003.SelectedStockList(4621,"300746","汉嘉设计"));
			list.add(new RespT020003.SelectedStockList(4621,"300180","华峰超纤"));
			list.add(new RespT020003.SelectedStockList(4609,"000688","国城矿业"));
			list.add(new RespT020003.SelectedStockList(4614,"002799","环球印务"));
			list.add(new RespT020003.SelectedStockList(4621,"300285","国瓷材料"));
			list.add(new RespT020003.SelectedStockList(4609,"000915","山大华特"));
			list.add(new RespT020003.SelectedStockList(4614,"002732","燕塘乳业"));
			list.add(new RespT020003.SelectedStockList(4621,"300739","明阳电路"));
			list.add(new RespT020003.SelectedStockList(4621,"300633","开立医疗"));
			list.add(new RespT020003.SelectedStockList(4621,"300381","溢多利"));
			list.add(new RespT020003.SelectedStockList(4614,"002001","新 和 成"));
			list.add(new RespT020003.SelectedStockList(4621,"300765","新诺威"));
			list.add(new RespT020003.SelectedStockList(4621,"300352","北信源"));
			list.add(new RespT020003.SelectedStockList(4621,"300098","高新兴"));
			list.add(new RespT020003.SelectedStockList(4621,"300136","信维通信"));
			list.add(new RespT020003.SelectedStockList(4621,"300579","数字认证"));
			list.add(new RespT020003.SelectedStockList(4614,"002729","好利来"));
			list.add(new RespT020003.SelectedStockList(4621,"300781","因赛集团"));
			list.add(new RespT020003.SelectedStockList(4614,"002345","潮宏基"));
			list.add(new RespT020003.SelectedStockList(4614,"002354","天神娱乐"));
			list.add(new RespT020003.SelectedStockList(4621,"300495","美尚生态"));
			list.add(new RespT020003.SelectedStockList(4614,"002554","惠博普"));
			list.add(new RespT020003.SelectedStockList(4614,"002923","润都股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300553","集智股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002550","千红制药"));
			list.add(new RespT020003.SelectedStockList(4614,"002083","孚日股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000153","丰原药业"));
			list.add(new RespT020003.SelectedStockList(4621,"300090","盛运环保"));
			list.add(new RespT020003.SelectedStockList(4614,"002315","焦点科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002426","胜利精密"));
			list.add(new RespT020003.SelectedStockList(4614,"002556","辉隆股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002952","亚世光电"));
			list.add(new RespT020003.SelectedStockList(4614,"002522","浙江众成"));
			list.add(new RespT020003.SelectedStockList(4609,"000757","浩物股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300703","创源文化"));
			list.add(new RespT020003.SelectedStockList(4609,"000628","高新发展"));
			list.add(new RespT020003.SelectedStockList(4614,"002821","凯莱英"));
			list.add(new RespT020003.SelectedStockList(4614,"002335","科华恒盛"));
			list.add(new RespT020003.SelectedStockList(4614,"002041","登海种业"));
			list.add(new RespT020003.SelectedStockList(4614,"002177","御银股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300608","思特奇"));
			list.add(new RespT020003.SelectedStockList(4614,"002916","深南电路"));
			list.add(new RespT020003.SelectedStockList(4621,"300549","优德精密"));
			list.add(new RespT020003.SelectedStockList(4614,"002296","辉煌科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300072","三聚环保"));
			list.add(new RespT020003.SelectedStockList(4621,"300559","佳发教育"));
			list.add(new RespT020003.SelectedStockList(4614,"002568","百润股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300510","金冠股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002951","金时科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002514","宝馨科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300587","天铁股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002822","中装建设"));
			list.add(new RespT020003.SelectedStockList(4614,"002211","宏达新材"));
			list.add(new RespT020003.SelectedStockList(4614,"002532","新界泵业"));
			list.add(new RespT020003.SelectedStockList(4621,"300681","英搏尔"));
			list.add(new RespT020003.SelectedStockList(4609,"000703","恒逸石化"));
			list.add(new RespT020003.SelectedStockList(4621,"300585","奥联电子"));
			list.add(new RespT020003.SelectedStockList(4621,"300767","震安科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002823","凯中精密"));
			list.add(new RespT020003.SelectedStockList(4621,"300377","赢时胜"));
			list.add(new RespT020003.SelectedStockList(4621,"300735","光弘科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002842","翔鹭钨业"));
			list.add(new RespT020003.SelectedStockList(4621,"300176","派生科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300122","智飞生物"));
			list.add(new RespT020003.SelectedStockList(4621,"300172","中电环保"));
			list.add(new RespT020003.SelectedStockList(4621,"300404","博济医药"));
			list.add(new RespT020003.SelectedStockList(4614,"002638","勤上股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300450","先导智能"));
			list.add(new RespT020003.SelectedStockList(4614,"002179","中航光电"));
			list.add(new RespT020003.SelectedStockList(4621,"300759","康龙化成"));
			list.add(new RespT020003.SelectedStockList(4614,"002504","弘高创意"));
			list.add(new RespT020003.SelectedStockList(4614,"002325","洪涛股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002237","恒邦股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300530","达志科技"));
			list.add(new RespT020003.SelectedStockList(4621,"300080","易成新能"));
			list.add(new RespT020003.SelectedStockList(4621,"300009","安科生物"));
			list.add(new RespT020003.SelectedStockList(4609,"000023","深天地A"));
			list.add(new RespT020003.SelectedStockList(4621,"300063","天龙集团"));
			list.add(new RespT020003.SelectedStockList(4614,"002740","爱迪尔"));
			list.add(new RespT020003.SelectedStockList(4609,"000952","广济药业"));
			list.add(new RespT020003.SelectedStockList(4621,"300197","铁汉生态"));
			list.add(new RespT020003.SelectedStockList(4614,"002147","ST新光"));
			list.add(new RespT020003.SelectedStockList(4614,"002218","拓日新能"));
			list.add(new RespT020003.SelectedStockList(4614,"002220","ST天宝"));
			list.add(new RespT020003.SelectedStockList(4614,"002221","东华能源"));
			list.add(new RespT020003.SelectedStockList(4614,"002266","浙富控股"));
			list.add(new RespT020003.SelectedStockList(4614,"002305","南国置业"));
			list.add(new RespT020003.SelectedStockList(4614,"002309","中利集团"));
			list.add(new RespT020003.SelectedStockList(4614,"002334","英威腾"));
			list.add(new RespT020003.SelectedStockList(4614,"002409","雅克科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002445","ST中南"));
			list.add(new RespT020003.SelectedStockList(4614,"002452","长高集团"));
			list.add(new RespT020003.SelectedStockList(4614,"002453","华软科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002457","青龙管业"));
			list.add(new RespT020003.SelectedStockList(4614,"002502","鼎龙文化"));
			list.add(new RespT020003.SelectedStockList(4614,"002516","旷达科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002526","山东矿机"));
			list.add(new RespT020003.SelectedStockList(4614,"002563","森马服饰"));
			list.add(new RespT020003.SelectedStockList(4614,"002593","日上集团"));
			list.add(new RespT020003.SelectedStockList(4614,"002644","佛慈制药"));
			list.add(new RespT020003.SelectedStockList(4614,"002682","龙洲股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002717","岭南股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002774","快意电梯"));
			list.add(new RespT020003.SelectedStockList(4614,"002793","东音股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000029","深深房A"));
			list.add(new RespT020003.SelectedStockList(4614,"002323","*ST百特"));
			list.add(new RespT020003.SelectedStockList(4614,"002450","*ST康得"));
			list.add(new RespT020003.SelectedStockList(4614,"002477","*ST雏鹰"));
			list.add(new RespT020003.SelectedStockList(4621,"300666","江丰电子"));
			list.add(new RespT020003.SelectedStockList(4621,"300676","华大基因"));
			list.add(new RespT020003.SelectedStockList(4621,"300347","泰格医药"));
			list.add(new RespT020003.SelectedStockList(4621,"300603","立昂技术"));
			list.add(new RespT020003.SelectedStockList(4621,"300015","爱尔眼科"));
			list.add(new RespT020003.SelectedStockList(4621,"300628","亿联网络"));
			list.add(new RespT020003.SelectedStockList(4614,"002422","科伦药业"));
			list.add(new RespT020003.SelectedStockList(4614,"002603","以岭药业"));
			list.add(new RespT020003.SelectedStockList(4614,"002824","和胜股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300446","乐凯新材"));
			list.add(new RespT020003.SelectedStockList(4614,"002595","豪迈科技"));
			list.add(new RespT020003.SelectedStockList(4609,"000785","武汉中商"));
			list.add(new RespT020003.SelectedStockList(4621,"300114","中航电测"));
			list.add(new RespT020003.SelectedStockList(4621,"300533","冰川网络"));
			list.add(new RespT020003.SelectedStockList(4621,"300195","长荣股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000625","长安汽车"));
			list.add(new RespT020003.SelectedStockList(4614,"002892","科力尔"));
			list.add(new RespT020003.SelectedStockList(4621,"300360","炬华科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002134","天津普林"));
			list.add(new RespT020003.SelectedStockList(4621,"300323","华灿光电"));
			list.add(new RespT020003.SelectedStockList(4614,"002580","圣阳股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002906","华阳集团"));
			list.add(new RespT020003.SelectedStockList(4609,"000006","深振业A"));
			list.add(new RespT020003.SelectedStockList(4609,"000976","华铁股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002735","王子新材"));
			list.add(new RespT020003.SelectedStockList(4621,"300521","爱司凯"));
			list.add(new RespT020003.SelectedStockList(4621,"300118","东方日升"));
			list.add(new RespT020003.SelectedStockList(4614,"002728","特一药业"));
			list.add(new RespT020003.SelectedStockList(4621,"300272","开能健康"));
			list.add(new RespT020003.SelectedStockList(4621,"300645","正元智慧"));
			list.add(new RespT020003.SelectedStockList(4621,"300041","回天新材"));
			list.add(new RespT020003.SelectedStockList(4614,"002022","科华生物"));
			list.add(new RespT020003.SelectedStockList(4621,"300612","宣亚国际"));
			list.add(new RespT020003.SelectedStockList(4621,"300475","聚隆科技"));
			list.add(new RespT020003.SelectedStockList(4609,"000030","富奥股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002807","江阴银行"));
			list.add(new RespT020003.SelectedStockList(4614,"002655","共达电声"));
			list.add(new RespT020003.SelectedStockList(4614,"002836","新宏泽"));
			list.add(new RespT020003.SelectedStockList(4621,"300283","温州宏丰"));
			list.add(new RespT020003.SelectedStockList(4609,"000620","新华联"));
			list.add(new RespT020003.SelectedStockList(4614,"002713","东易日盛"));
			list.add(new RespT020003.SelectedStockList(4614,"002456","欧菲光"));
			list.add(new RespT020003.SelectedStockList(4609,"000620","新华联"));
			list.add(new RespT020003.SelectedStockList(4614,"002713","东易日盛"));
			list.add(new RespT020003.SelectedStockList(4614,"002456","欧菲光"));
			list.add(new RespT020003.SelectedStockList(4614,"002561","徐家汇"));
			list.add(new RespT020003.SelectedStockList(4614,"002212","南洋股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000514","渝 开 发"));
			list.add(new RespT020003.SelectedStockList(4621,"300074","华平股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000752","*ST西发"));
			list.add(new RespT020003.SelectedStockList(4609,"000717","韶钢松山"));
			list.add(new RespT020003.SelectedStockList(4614,"002206","海 利 得"));
			list.add(new RespT020003.SelectedStockList(4614,"002387","维信诺"));
			list.add(new RespT020003.SelectedStockList(4614,"002036","联创电子"));
			list.add(new RespT020003.SelectedStockList(4614,"002558","巨人网络"));
			list.add(new RespT020003.SelectedStockList(4609,"000949","新乡化纤"));
			list.add(new RespT020003.SelectedStockList(4609,"000713","丰乐种业"));
			list.add(new RespT020003.SelectedStockList(4621,"300380","安硕信息"));
			list.add(new RespT020003.SelectedStockList(4609,"000407","胜利股份"));
			list.add(new RespT020003.SelectedStockList(4609,"001872","招商港口"));
			list.add(new RespT020003.SelectedStockList(4621,"300616","尚品宅配"));
			list.add(new RespT020003.SelectedStockList(4614,"002289","*ST宇顺"));
			list.add(new RespT020003.SelectedStockList(4621,"300277","海联讯"));
			list.add(new RespT020003.SelectedStockList(4621,"300259","新天科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002071","长城影视"));
			list.add(new RespT020003.SelectedStockList(4621,"300489","中飞股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300097","智云股份"));
			list.add(new RespT020003.SelectedStockList(4621,"300132","青松股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000605","渤海股份"));
			list.add(new RespT020003.SelectedStockList(4609,"000893","*ST东凌"));
			list.add(new RespT020003.SelectedStockList(4609,"000026","飞亚达A"));
			list.add(new RespT020003.SelectedStockList(4614,"002628","成都路桥"));
			list.add(new RespT020003.SelectedStockList(4621,"300217","东方电热"));
			list.add(new RespT020003.SelectedStockList(4621,"300610","晨化股份"));
			list.add(new RespT020003.SelectedStockList(4614,"002539","云图控股"));
			list.add(new RespT020003.SelectedStockList(4621,"300282","三盛教育"));
			list.add(new RespT020003.SelectedStockList(4621,"300425","环能科技"));
			list.add(new RespT020003.SelectedStockList(4614,"002039","黔源电力"));
			list.add(new RespT020003.SelectedStockList(4614,"002485","希努尔"));
			list.add(new RespT020003.SelectedStockList(4609,"000800","一汽轿车"));
			list.add(new RespT020003.SelectedStockList(4609,"000502","绿景控股"));
			list.add(new RespT020003.SelectedStockList(4621,"300226","上海钢联"));
			list.add(new RespT020003.SelectedStockList(4614,"002275","桂林三金"));
			list.add(new RespT020003.SelectedStockList(4614,"002189","中光学"));
			list.add(new RespT020003.SelectedStockList(4621,"300208","青岛中程"));
			list.add(new RespT020003.SelectedStockList(4614,"002190","*ST集成"));
			list.add(new RespT020003.SelectedStockList(4614,"002898","赛隆药业"));
			list.add(new RespT020003.SelectedStockList(4621,"300066","三川智慧"));

			
			/*
			 * list.add(new RespT020003.SelectedStockList(4353,"603602", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603773", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603300", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"600584", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"600671", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603706", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603738", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"600855", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603329", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"601718", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603609", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603160", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603256", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"600336", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"603006", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"601166", "")); list.add(new
			 * RespT020003.SelectedStockList(4368,"688008", "")); list.add(new
			 * RespT020003.SelectedStockList(4609,"000876", "")); list.add(new
			 * RespT020003.SelectedStockList(4353,"600300", "")); list.add(new
			 * RespT020003.SelectedStockList(4614,"002300", ""));
			 */
			                                        
			Random random = new Random();
			int n = random.nextInt(list.size());
			n = n <=0 ?  list.size() : n;
		
			List<RespT020003.SelectedStockList> selectedStockList = new ArrayList<RespT020003.SelectedStockList>();
			for(int i=0;i<n;i++) {
				selectedStockList.add(list.get(i));
			}
			respData.setResultCount(selectedStockList.size());
			respData.setCurrentDayIncrease(random.nextInt(200)+"%");
			respData.setSelectedStockList(selectedStockList);
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(respData)));
			return;
		}
		
		if(path.endsWith("saveStockMethod")) {
			transId = "T020005";
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(null)));
			return;
		}
		
		if(path.endsWith("queryStockMethods")) {
			transId = "T020006";
			RespT020006 respData = new RespT020006();
			respData.setMethodList(new ArrayList<RespT020006.MethodList>());
			
			Random random = new Random();
			int n = random.nextInt(20);
			n = n <=0 ?  11 : n;
			String[] names = new String[] {"股价[9.9~180]","仙人指路","资产负债率[小于10%]","红三兵","MACD[红二波]","跑赢大盘[近一个月跑赢大盘]",
					"市场[沪深300]","换手率[40~67]"};
			for(int i=0;i<n;i++) {
				MethodList method = new MethodList();
				method.setId(i+1+"");
				
				int length = random.nextInt(names.length*2)%names.length;
				length = length<=0 ? 4 : length;
				
				List<SelectedsubContent> selectedsubContent = new ArrayList<SelectedsubContent>();
				boolean isAppendName = true;
				for(int j=0;j<length;j++) {
					if(method.getName() != null && method.getName().length()>=20) {
						isAppendName = false;
					}
					
					String nameStr = names[random.nextInt(1000)%names.length];
					if(isAppendName) {
						if((method.getName() == null || "".equals(method.getName()))) {
							method.setName(nameStr);
						}else {
							method.setName(method.getName() + "&"+nameStr);
						}
					}
					selectedsubContent.add(new SelectedsubContent("1"+j,nameStr));
				}
				
				method.setSelectedsubContent(selectedsubContent);
				method.setStockCount(random.nextInt(3500));
				respData.getMethodList().add(method);
			}
			
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(respData)));
			return;
		}
		
		if(path.endsWith("deleteStockMethod")) {
			transId = "T020007";
			response.getWriter().print(new ObjectMapper().writeValueAsString(Resp.getSuccessResp(null)));
			return;
		}
		
		
		if (transId == null || "".equals(transId)) {
			response.getWriter().append("{'retCode':'9999','retMsg':'未配置对应的交易'}");
			return;
		}
		
		File file = new File(webroot + File.separator + responseDir + File.separator + transId + ".txt");
		if (!file.exists()) {
			response.getWriter().append("{'retCode':'9999','retMsg':'" + transId + ".txt文件不存在'}");
			return;
		}
		
		
		InputStream in = new FileInputStream(file);
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String retStr = bf.readLine();
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().print(mapper.convertValue(retStr, String.class));
		in.close();
	}

}
