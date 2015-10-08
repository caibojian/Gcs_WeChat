<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
</head>
<style type="text/css">
.breadcrumb a:hover {
	text-decoration: none;
}

.datagrid-view .datagrid-header .datagrid-header-inner .datagrid-cell {
	text-align: center;
	font-size: 14px;
	text-align: center;
	font-weight: bold;
}

</style>
<script type="text/javascript">
	var msg = "${MSG}";
	$(document).ready(function(){
			if(msg){
				alert(msg);
			}
		});
	function reloadGrid() {
		var param = $('#searchForm').form2json();
		$('#gcs_dg').datagrid('reload', param);
	}
	function changeSize(width, height) {
		$(document).ready(function() {
			//重新定义Panel宽度
			$('#panel3').panel('resize', {
				width : width
			});
		});
	}
	
</script>
<body class="easyui-layout" id="content">
<!-- 下拉树模块 -->

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="消息列表">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:100%;"
			options="url:'${contextPath}/management/msg/tztgMsgRecData?agentID=${agentid }',onResize:changeSize,pageSize:20,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<gcs:dataTh	options="field:'id',width:160,formatter:showDetail,align:'center'">操作</gcs:dataTh>
					<th data-options="field:'msgType',width:230,formatter:addTitle,align:'center'">消息类型</th>
					<th data-options="field:'content',width:400,align:'center',formatter:addTitle">消息内容</th>
					<th data-options="field:'createTimeStr',width:140,formatter:addTitle,align:'center'">接收时间</th>
					<th data-options="field:'fromUserName',width:140,align:'center',formatter:addTitle">发送人</th>
				</tr>
			</thead>
		</gcs:dataGrid>
	</gcs:layoutDiv>
<!-- 查询模块 -->
	<div id="tb" style="overflow-x: hidden; width: auto;">
		<div style="background-color: white; width: 100%;">
			<gcs:panel id="panel3" options="collapsible:true" title="查询条件">
				<gcs:form id="searchForm"  style="margin-bottom:2px;">
					<table style="width: 100%;">

						<tr height="30px">
							<td>&nbsp;&nbsp;&nbsp;&nbsp;发送人：</td>
							<td><gcs:validatebox id="title" name="title"
									style="width:194px"></gcs:validatebox>
							</td>
							
						</tr>
						<tr height="30px">
							<td> &nbsp;&nbsp;&nbsp;&nbsp;消息类型</td>
							<td>
								<select class="easyui-combobox" id="state" name="state" data-options="required:true" style="width:194px">
								<option value="" selected="selected">所有</option>
								<option value="text">文字</option>
								<option value="image">图片</option>
								<option value="voice">语音</option>
							</select>
							</td>
							<td colspan="8" align="right">
								<a id="subbtn" onclick="reloadGrid();return false;" 
								class="button glow button-rounded button-tiny" 
								style="width: 50px;cursor:pointer;margin: 15px 25px 5px 5px">
								<i class="icon-search"></i>查询</a>
							</td>
						</tr>
						<tr style="height: 10px">
							<td></td>
						</tr>
					</table>
				</gcs:form>
			</gcs:panel>
		</div>
	</div>

</body>
<script type="text/javascript">

// 页面长度标准
	   $("body").css("height",window.screen.height-210<=370?370:window.screen.height-210);
							
//操作跳转 
	function showDetail(value, row, index) {
		if (value) {
			var btns = [];
			var mx = "<a href='javascript:void(0)'  onclick='del(\"" + row.id + "\")'>删除</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='showUser(\"" + row.id + "\")'>查看内容</a>&nbsp;&nbsp;&nbsp;";
			btns.push(mx);
			return btns.join(" ");
		} else {
			return "";
		}
	}
	
	
//删除跳转
	function del(id) {
		art.dialog.through({
				title: '提示',
				icon: 'warning',
	    		content: '是否确认删除？',
	    		ok: function () {
				$.ajax({
					url : '${contextPath}/management/msg/delMsg',
					type : 'get',
					async : false,
					data : {
						'id' : id
					},
					success : function(data) {
						reloadGrid();
						art.dialog.through({title: '提示',icon: 'success',content: '删除成功！',lock:true});
					},
					error : function() {
						art.dialog.through({title: '提示',icon: 'error',content: '与服务器通信失败，请稍后再试！',lock:true});
					}
				});
	    		return true;
	    	},
	    	cancelVal: '取消',
	   		cancel: true, //为true等价于function(){}
	   		lock:true
		});
	}
	//查看消息内
	function showUser(id) {
			if(id){
				var url = '${contextPath}/management/msg/showMsg?id='+id;
				art.dialog.open(url, {title: '消息内容',fixed: true,lock: true,height:600,width:1000});
			}else{
				alert("修改异常");
			}
	}
	
</script>
</html>