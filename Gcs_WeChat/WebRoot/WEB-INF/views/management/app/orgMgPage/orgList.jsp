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

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="组织列表">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:100%;"
			options="url:'${contextPath}/management/organizationMG/orgListDate',onResize:changeSize,pageSize:20,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<th data-options="field:'name',width:230,formatter:addTitle,align:'center'">部门名称</th>
					<!-- 
					<th data-options="field:'phone',width:200,align:'center',formatter:addTitle">部门电话</th>
					<th data-options="field:'leader',width:240,formatter:addTitle,align:'center'">部门领导</th>
					 -->
					<th data-options="field:'parendName',width:230,align:'center',formatter:addTitle">上级部门名称</th>
					<th data-options="field:'ifsyn',width:150,align:'center',formatter:changCn">状态</th>
					<gcs:dataTh	options="field:'id',width:160,formatter:showDetail,align:'center'">操作</gcs:dataTh>
				</tr>
			</thead>
		</gcs:dataGrid>
	</gcs:layoutDiv>
<!-- 查询模块 -->

</body>
<script type="text/javascript">

// 页面长度标准
	   $("body").css("height",window.screen.height-210<=370?370:window.screen.height-210);
							
	
//操作跳转 
	function showDetail(value, row, index) {
		if (value) {
			var btns = [];
			var mx = '';
			if(row.ifsyn==0){
				mx = "<a href='javascript:void(0)'  onclick='syn(\"" + row.id + "\")'>微信同步</a>";
			}else{
				mx = "部门已经同步到微信！";
			}
			btns.push(mx);
			return btns.join(" ");
		} else {
			return "";
		}
	}
	
	function changCn(value, row, index) {
		if (value) {
			var btns = [];
			var mx = '';
			if(row.ifsyn==0){
				mx = "未同步";
			}else{
				mx = "已同步";
			}
			btns.push(mx);
			return btns.join(" ");
		} else {
			return "";
		}
	}
	
	
//删除跳转
	function syn(id) {
		art.dialog.through({
				title: '提示',
				icon: 'warning',
	    		content: '是否确认同步？',
	    		ok: function () {
	    		$.messager.progress();
				$.ajax({
					url : '${contextPath}/management/organizationMG/orgSyn',
					type : 'get',
					async : false,
					data : {
						'id' : id
					},
					success : function(data) {
						$.messager.progress('close');
						var data = eval('(' + data + ')');
						reloadGrid();
						 if(data.success==true){
							 	art.dialog.through({
								    content: data.msg,
								    icon : '',
								    fixed: true,
								    lock: true,
								    ok: function () {
								    	
								        return true;
								    }
								});
							 }else{
							 	art.dialog.through({
							 		content: data.msg,
								    icon : 'error', 
								    fixed: true,
								    lock: true,
								    ok:true
								});
							 }
					},
					error : function() {
						$.messager.progress('close');
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
