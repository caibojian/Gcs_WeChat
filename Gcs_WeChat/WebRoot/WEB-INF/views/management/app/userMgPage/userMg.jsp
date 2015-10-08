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
	<gcs:layoutDiv options="region:'west',split:true" title="组织机构"
						style="width:200px;">
						<ul id="tt" class="easyui-tree"
							data-options="url:'${contextPath}/management/organizationMG/treeData',method:'post',onLoadSuccess:treeCollapseAll"></ul>
	</gcs:layoutDiv>

	<gcs:layoutDiv options="region:'center',split:true"	style="width:100%;height:100%;" title="人员管理">
		<gcs:dataGrid id="gcs_dg" style="width:100%;height:100%;"
			options="url:'${contextPath}/management/userMg/listDate',onResize:changeSize,pageSize:30,border:false,singleSelect:true,autoRowHeight:false,fit:true,pagination:true,toolbar: '#tb'">
			<thead>
				<tr>
					<gcs:dataTh	options="field:'userid',width:100,formatter:showDetail,align:'center'">操作</gcs:dataTh>
					<th data-options="field:'policeID',width:110,formatter:addTitle,align:'center'">警员警号</th>
					<th data-options="field:'name',width:110,formatter:addTitle,align:'center'">警员姓名</th>
					<th data-options="field:'departmentstr',width:140,align:'center',formatter:addTitle">所属组织</th>
					<th data-options="field:'mobile',width:140,align:'center',formatter:addTitle">手机号码</th>
					<th data-options="field:'email',width:150,align:'center',formatter:addTitle">电子邮件</th>
					<th data-options="field:'weixinid',width:140,align:'center',formatter:addTitle">微信账号</th>
					<th data-options="field:'position',width:140,formatter:addTitle,align:'center'">职位</th>
					<th data-options="field:'ifsyn',width:120,formatter:change,align:'center'">是否同步</th>
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
							<td>&nbsp;&nbsp;民警姓名：</td>
							<td><gcs:validatebox id="name" name="name"
									style="width:194px"></gcs:validatebox></td>
							<td>微信号码：</td>
							<td><gcs:validatebox id="weixinid" name="weixinid"
									style="width:194px"></gcs:validatebox>
								<input type="hidden" name="hy" id="hyHidden"></td>
							<td>电子邮箱：</td>
							<td><gcs:validatebox id="email" name="email"
									style="width:194px"></gcs:validatebox>
							</td>
						</tr>
						<input type="hidden" name="orgid" id="orgid">

						<tr height="30px">
							<td>&nbsp;&nbsp;手机号码：</td>
							<td><gcs:validatebox id="phone" name="phone"
									style="width:194px"></gcs:validatebox></td>
							<td>警员警号</td>
							<td><gcs:validatebox id="policeID" name="policeID"
									style="width:194px"></gcs:validatebox></td>
							<td>是否同步</td>
							<td>
								<select class="easyui-combobox" id="ifsyn" name="ifsyn" data-options="required:true" style="width:194px">
								<option value="" selected="selected">请选择</option>
								<option value="0">未同步</option>
								<option value="1">已同步</option>
							</select>
							</td>
							<td colspan="8" align="right">
								<a id="subbtn" onclick="reloadGrid();return false;" class="button glow button-rounded button-tiny" style="width: 50px;cursor:pointer;margin:10px"><i class="icon-search"></i>查询</a>
							</td>
						</tr>
					</table>
				</gcs:form>
			</gcs:panel>
		</div>
		<div style="background-color: white; width: 100%; border: 1" align="left">
 			 <a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="adduser();return false;"><i class="icon-add"></i>添加人员</a>
				 <a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="userListReprot();return false;"><i class="icon-add"></i>导出人员</a>
				 <a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="importUser();return false;"><i class="icon-add"></i>导入人员</a>
				 <a id = "adduser"
				class="button glow button-rounded button-tiny"
				style="width: 80px; cursor: pointer;margin:10px" onclick="synuser();return false;"><i class="icon-add"></i>同步微信</a>
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
	//重置按钮获取值
	function r() {
		$("#xmmc").val("");
		$("#jsdw").val("");
		$("#xmlx").combobox("setValue", "");
		$("#hy").combotree("setValue", "");
		$("#szysjq").combotree("setValue", "");
		$("#sqwh").val("");
		$("#scyjwh").val("");
		$("#scjl").combobox("setValue", "");
		$("#scjl").combobox("setText", "");
		$("#pfyjyfsjStart").datebox("setValue", "");
		$("#bljssjStart").datebox("setValue", "");
		$("#pfyjyfsjEnd").datebox("setValue", "");
		$("#bljssjEnd").datebox("setValue", "");

	}
//操作跳转 
	function showDetail(value, row, index) {
		if (value) {
			var btns = [];
			var mx = "<a href='javascript:void(0)' onclick='del(\"" + row.userid + "\")' >删除</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='updateUser(\"" + row.userid + "\")'>修改</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='showUser(\"" + row.userid + "\")'>查看</a>";
			btns.push(mx);

		//	var xg = "<a href='${contextPath}/management/rcgl/szybgsc/toEdit?qsxmid="
		//			+ row.xmid + "'>修改</a>";
		//	btns.push(xg);

		//	var sc = "<a href='#' onclick='del(\"" + row.szyid
		//			+ "\");return false;'>删除</a>";
		//	btns.push(sc);
			return btns.join(" ");
		} else {
			return "";
		}
	}
	
	function change(value, row, index){
		if (value) {
			var btns = [];
			if(row.ifsyn=='0'){
				var mx = "未同步";
				btns.push(mx);
			}else{
				var mx = "已同步";
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
					url : '${contextPath}/management/userMg/delWechatUser',
					type : 'get',
					async : false,
					data : {
						'userid' : id
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
	
	function updateUser(id) {
			if(id){
				var url = '${contextPath}/management/userMg/updateWeCharUserIndex?userid='+id;
				art.dialog.open(url, {title: '修改',fixed: true,lock: true,height:600});
			}else{
				alert("异常");
			}
	}
	function showUser(id) {
			if(id){
				var url = '${contextPath}/management/userMg/showUserIndex?userid='+id;
				art.dialog.open(url, {title: '查看',fixed: true,lock: true,height:600});
			}else{
				alert("异常");
			}
	}
	//导出用户excel表
	function userListReprot() {
		window.open("${contextPath}/management/userMg/userListReprot", '_blank');
	}
	//导入用户excel表
	function importUser() {
		var url = '${contextPath}/management/userMg/usersListImports';
		art.dialog.open(url, {title: '导入',fixed: true,lock: true,height:200});
	}
	//一键同步用户
	function synuser(){
  			$.messager.progress();	// 显示进度条
  			$.ajax({
				url : '${contextPath}/management/userMg/synWechatUserMany',
				type : 'get',
				async : false,
				data : {
				},
				success : function(data) {
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					reloadGrid();
					art.dialog.through({title: '提示',icon: 'success',content: '同步完成',lock:true});
				},
				error : function() {
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					art.dialog.through({title: '提示',icon: 'error',content: '与服务器通信失败，请稍后再试！',lock:true});
				}
			});
		}
</script>
</html>
