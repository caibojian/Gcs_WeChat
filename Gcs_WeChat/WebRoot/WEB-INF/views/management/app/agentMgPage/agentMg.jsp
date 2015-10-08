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
			if(nodeid==''||nodeid=='0'){
				alert("请先选择左边应用");
			}else{
				openWindow('${contextPath}/management/agent/addTagUserIndex?agentID='+nodeid,'添加');
			}
	}
	
			function openWindow(url,title){
				if(nodeid!=''){
					art.dialog.open(url, {title: title,fixed: true,lock: true,height:300});
				}else{
					art.dialog({
					    icon: 'warning',
					    content: '请先选择左边应用'
					});
				}
			}
	
</script>
<body class="easyui-layout" id="content">
<!-- 下拉树模块 -->
	<gcs:layoutDiv options="region:'west',split:true" title="应用列表"
						style="width:200px;">
						<ul id="tt" class="easyui-tree"
							data-options="url:'${contextPath}/management/agent/getTagData',method:'post',onLoadSuccess:treeCollapseAll"></ul>
	</gcs:layoutDiv>

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="人员管理">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:100%;"
			options="url:'${contextPath}/management/agent/listDate',onResize:changeSize,pageSize:30,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<gcs:dataTh	options="field:'userid',width:100,formatter:showDetail,align:'center'">操作</gcs:dataTh>
					<th data-options="field:'policeID',width:140,formatter:addTitle,align:'center'">警员警号</th>
					<th data-options="field:'name',width:120,formatter:addTitle,align:'center'">警员姓名</th>
					<th data-options="field:'departmentstr',width:140,align:'center',formatter:addTitle">所属组织</th>
					<th data-options="field:'mobile',width:140,align:'center',formatter:addTitle">手机号码</th>
					<th data-options="field:'email',width:150,align:'center',formatter:addTitle">电子邮件</th>
					<th data-options="field:'weixinid',width:140,align:'center',formatter:addTitle">微信账号</th>
					<th data-options="field:'position',width:140,formatter:addTitle,align:'center'">职位</th>
				</tr>
			</thead>
		</gcs:dataGrid>
	</gcs:layoutDiv>
<!-- 查询模块 -->
	<div id="tb" style="overflow-x: hidden; width: auto;">
		<div style="background-color: white; width: 100%;">
				<gcs:form id="searchForm"  style="margin-bottom:2px;">
					<table style="width: 100%;">
						
						<input type="hidden" name="orgid" id="orgid">

					</table>
				</gcs:form>
		</div>
		<div style="background-color: white; width: 100%; border: 1" align="left">
 			 <a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="adduser();return false;"><i class="icon-add"></i>添加人员</a>
			<!-- -->
			&nbsp;&nbsp;&nbsp;&nbsp;
			
			<a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="syn();return false;"><i class="icon-add"></i>同步微信</a>
			<!-- -->
			&nbsp;&nbsp;&nbsp;&nbsp;
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
			var mx = "<a href='javascript:void(0)' onclick='del(\"" + row.userid + "\")' >删除应用可见</a>";
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
					url : '${contextPath}/management/agent/delTagUser',
					type : 'get',
					async : false,
					data : {
						'userid' : id,
						'agentID' : nodeid
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
	
	function syn() {
		art.dialog.through({
				title: '提示',
				icon: 'warning',
	    		content: '是否确认同步？',
	    		ok: function () {
				$.ajax({
					url : '${contextPath}/management/agent/synTagUser',
					type : 'get',
					async : false,
					data : {
						
					},
					success : function(data) {
						reloadGrid();
						art.dialog.through({title: '提示',icon: 'success',content: '同步成功！',lock:true});
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
	
	
</script>
</html>
