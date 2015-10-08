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
	var nodeid='';
	//多条件查询 
	function reloadGrid() {
		
		var param = $('#searchForm').form2json();
		$('#gcs_dg').datagrid('reload', param);
	}
	function treeCollapseAll() {
		$("#tt").tree("collapseAll");
	}
	$(document).ready(
			function() {
				//行政区划树添加单击事件
				$('#tt').tree({
					onClick : function(node) {
						// 在用户点击的时候提示
						nodeid = node.id;
						$('#orgid').val(node.id);
						reloadGrid();
					}
				});
				$('#subbtn').click(function() {
					reloadGrid();
				});
				

				$('#panel3').panel({
					onCollapse : function() {
						//收缩
						$('#gcs_dg').datagrid('resize');
					},
					onExpand : function() {
						//展开
						$('#gcs_dg').datagrid('resize');
					}
				});
			});
	function changeSize(width, height) {
		$(document).ready(function() {
			//重新定义Panel宽度
			$('#panel3').panel('resize', {
				width : width
			});
		});
	}
	function adduser() {
			if(nodeid==''){
				alert("请先选择左边单位");
			}else{
				openWindow('${contextPath}/management/userMg/addUserIndex?orgid='+nodeid,'添加');
			}
	}
	
			function openWindow(url,title){
				if(nodeid!=''){
					art.dialog.open(url, {title: title,fixed: true,lock: true,height:600});
				}else{
					art.dialog({
					    icon: 'warning',
					    content: '请先选择左边单位'
					});
				}
			}
	
</script>
<body class="easyui-layout" id="content">
<!-- 下拉树模块 -->

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="文章列表">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:100%;"
			options="url:'${contextPath}/management/article/articleListDate?articleType=1',onResize:changeSize,pageSize:20,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<gcs:dataTh	options="field:'id',width:160,formatter:showDetail,align:'center'">操作</gcs:dataTh>
					<th data-options="field:'title',width:330,formatter:addTitle,align:'center'">文章标题</th>
					<th data-options="field:'createTime',width:140,formatter:addTitle,align:'center'">发布时间</th>
					<th data-options="field:'createOrg',width:140,align:'center',formatter:addTitle">发布单位</th>
					<th data-options="field:'createUser',width:140,align:'center',formatter:addTitle">发布人</th>
					<th data-options="field:'readAmount',width:140,align:'center',formatter:addTitle">阅读量</th>
					<th data-options="field:'commentAmount',width:140,align:'center',formatter:addTitle">评论量</th>
					<th data-options="field:'praiseAmount',width:140,align:'center',formatter:addTitle">点赞量</th>
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

						<tr height="30px" >
							<td>&nbsp;&nbsp;文章标题：</td>
							<td><gcs:validatebox id="title" name="title"
									style="width:194px"></gcs:validatebox></td>
							<td>开始时间</td>
							<td><input id="kssj" name="kssj" type="text" class="easyui-datetimebox" style="width:194px"></input></td>
							<td>结束时间</td>
							<td><input id="jssj" name="jssj" type="text" class="easyui-datetimebox" style="width:194px"></input></td>
							
							<td colspan="8" align="right">
								<a id="add" href="javascript:void(0)" onclick="add()" class="button glow button-rounded button-tiny" style="width: 60px;cursor:pointer;margin:10px">发布文章</a>
								<a id="subbtn" onclick="reloadGrid();return false;" class="button glow button-rounded button-tiny" style="width: 50px;cursor:pointer;margin:10px"><i class="icon-search"></i>查询</a>
							</td>
						</tr>
						<tr style="height: 20px">
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
							
	function resetSelect() {
		$('#scjl').combobox('setValue', '');
		$('#scjl').combobox('setText', '');
	}
//操作跳转 
	function showDetail(value, row, index) {
		if (value) {
			var btns = [];
			var mx = "<a href='javascript:void(0)'  onclick='del(\"" + row.id + "\")'>删除</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='showArticle(\"" + row.id + "\")'>查看内容</a>&nbsp;&nbsp;&nbsp;​";
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
					url : '${contextPath}/management/article/delArticle',
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
	
	function showArticle(id) {
			if(id){
				var url = '${contextPath}/management/article/showArticle?id='+id;
				art.dialog.open(url, {title: '文章内容',fixed: true,lock: true,height:600,width:1000});
			}else{
				alert("服务器异常");
			}
	}
	function add() {
			var url = '${contextPath}/management/article/zxdtIndex';
			art.dialog.open(url, {title: '发布文章',fixed: true,lock: true,height:600,width:1000});
	}
	
</script>
</html>
