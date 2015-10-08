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
	
</script>
<body class="content" id="content">
<!-- 下拉树模块 -->

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="阅读人列表">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:150px;"
			options="url:'${contextPath}/management/article/commentListData?titleId=${titleId }',onResize:changeSize,pageSize:20,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<gcs:dataTh	options="field:'id',width:80,formatter:showDetail,align:'center'">操作</gcs:dataTh>
					<th data-options="field:'userName',width:100,formatter:addTitle,align:'center'">姓名</th>
					<th data-options="field:'orgStr',width:140,align:'center',formatter:addTitle">单位</th>
					<th data-options="field:'content',width:530,align:'center',formatter:addTitle">评论内容</th>
					<th data-options="field:'createTimeStr',width:140,formatter:addTitle,align:'center'">评论时间</th>
					
				</tr>
			</thead>
		</gcs:dataGrid>
	</gcs:layoutDiv>
<!-- 查询模块 -->
	<div id="tb" style="overflow-x: hidden; width: auto;">
		<div style="background-color: white; width: 100%;">
					<table style="width: 100%;">

						<tr >
							<td>
							<a href="javascript:history.go(-1);"
						class="button glow button-rounded button-tiny"><i
						class="icon-back"></i>返回</a>
							</td>
						</tr>
					</table>
		</div>
	</div>

</body>
<script type="text/javascript">
$("body").css("height",window.screen.height-250<=370?370:window.screen.height-335);
							
	function resetSelect() {
		$('#scjl').combobox('setValue', '');
		$('#scjl').combobox('setText', '');
	}
//操作跳转 
	function showDetail(value, row, index) {
		if (value) {
			var btns = [];
			<shiro:hasPermission name="zxdtwzlb:delete">
			var del = "<a href='javascript:void(0)'  onclick='del(\"" + row.id + "\")'>删除</a>&nbsp;&nbsp;&nbsp;";
			btns.push(del);
			</shiro:hasPermission>
			
			return btns.join(" ");
		} else {
			return "";
		}
	}
	
	//当前状态改变值
	function changeStr(value, row, index) {
		if (value) {
			var btns = [];
			if(value=='1'){
				var mx = "已发布";
				btns.push(mx);
			}else if(value=='2'){
				var mx = "审核不通过";
				btns.push(mx);
			}else if(value=='0'){
				var mx = "待审核";
				btns.push(mx);
			}
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
	
</script>
</html>
