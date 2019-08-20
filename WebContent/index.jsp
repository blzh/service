<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function getResp1() {
		$.post("indexStock/queryStockFactors", {
			transId : "T020001"
		}, function(data) {
			$("#retData1").text(data);
		});
	}
	
	function getResp2() {
		$.post("indexStock/queryStockNum", {
			transId : "T020001"
		}, function(data) {
			$("#retData2").text(data);
		});
	}
	
	function getResp3() {
		$.post("indexStock/queryStockResult", {
			transId : "T020001"
		}, function(data) {
			$("#retData3").text(data);
		});
	}
	
	function getResp4() {
		$.post("indexStock/queryStockMethodDetail", {
			transId : "T020001"
		}, function(data) {
			$("#retData4").text(data);
		});
	}
	
	function getResp5() {
		$.post("indexStock/saveStockMethod", {
			transId : "T020001"
		}, function(data) {
			$("#retData5").text(data);
		});
	}
	
	function getResp6() {
		$.post("indexStock/queryStockMethods", {
			transId : "T020001"
		}, function(data) {
			$("#retData6").text(data);
		});
	}
	
	function getResp7() {
		$.post("indexStock/deleteStockMethod", {
			transId : "T020001"
		}, function(data) {
			$("#retData7").text(data);
		});
	}
</script>
</head>
<body>
	<form id="testForm" action="getResp" method="post" style="margin-left: 100px">
		<h3>移动端调用均使用POST请求，URL示例 : http://172.22.70.97:8080/api/indexStock/请求路径?para1=value1&amp;para2=value2</h3>
		<h3><a href="getResp/api" target="_blank">接口文档</a></h3>
		
		1.1.查询选股因子&nbsp;&nbsp;&nbsp;&nbsp;请求路径:queryStockFactors<br/>
		<button type="button" onclick="getResp1()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData1"></div>
		<br/><br/>
		
		1.2.查询选股数量&nbsp;&nbsp;&nbsp;&nbsp;请求路径:queryStockNum<br/>
		<button type="button" onclick="getResp2()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData2"></div>
		<br/><br/>
		
		1.3.查询选股结果&nbsp;&nbsp;&nbsp;&nbsp;请求路径:queryStockResult<br/>
		<button type="button" onclick="getResp3()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData3"></div>
		<br/><br/>
		
		1.4.查询选股方法详情&nbsp;&nbsp;&nbsp;&nbsp;请求路径:queryStockMethodDetail<br/>
		<button type="button" onclick="getResp4()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData4"></div>
		<br/><br/>
		
		1.5.保存选股方法&nbsp;&nbsp;&nbsp;&nbsp;请求路径:saveStockMethod<br/>
		<button type="button" onclick="getResp5()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData5"></div>
		<br/><br/>
		
		1.6.查询选股方法&nbsp;&nbsp;&nbsp;&nbsp;请求路径:queryStockMethods<br/>
		<button type="button" onclick="getResp6()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData6"></div>
		<br/><br/>
		
		1.7.删除选股方法&nbsp;&nbsp;&nbsp;&nbsp;请求路径:deleteStockMethod<br/>
		<button type="button" onclick="getResp7()" style="width: 200px;">发送请求</button>
		<br/>返回数据：
		<div id="retData7"></div>
		<br/><br/>
	</form>
</body>
</html>