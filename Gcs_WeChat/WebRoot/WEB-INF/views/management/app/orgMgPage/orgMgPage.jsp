<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>组织管理</title>
		<meta name="description" content="长江委水资源论证与取水许可台账系统" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/jquery/jqueryeasyui/themes/bootstrap/easyui.css">
		<link rel="stylesheet" href="${contextPath}/resources/assets/css/jquery-ui-1.10.3.full.min.css" />
		<script src="${contextPath}/resources/jquery/jqueryeasyui/jquery.easyui.min.js"></script>
	</head>
	<body>
		<div class="page-content" id="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-box">
						<div class="widget-header widget-header-flat">
							<h4 class="lighter">
								<i class="icon-star orange"></i>
									组织管理
							</h4>
						</div>
					</div>					
				</div>
			</div>	
			<div class="col-xs-12 col-sm-3 widget-container-span ui-sortable">
				<div class="widget-box">
					<div class="widget-header widget-header-small header-color-dark">
						<h6 class="smaller">组织部门列表</h6>
					</div>
					<div class="widget-body">
						<div class="widget-main">
							<div class="alert alert-info" style="height: 580px;">
				       			<ul id="zzinfo" class="easyui-tree" style="margin-top: 5px;" data-options="url:'${contextPath}/management/organizationMG/treeData',method:'post'"></ul>	
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-9 widget-container-span ui-sortable">
				<div class="widget-box">
					<div class="widget-header header-color-dark">
						<h5 class="bigger lighter">
							<i class="icon-table"></i>
										组织信息
						</h5>
						<div class="widget-toolbar no-border">
							<button class="btn btn-xs btn-primary" id="addButton">添加组织</button>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>组织ID</th>
										<th>组织名称</th>
										<th class="hidden-480">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${orglist}" var="org">
										<tr>
											<td>${org.id}</td>
											<td>${org.name}</td>
											<td>
												<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
													<button class="btn btn-xs btn-info"  onclick="updateOrg('${org.id}')">
														<i class="icon-edit bigger-120"></i>
															修改
													</button>
													<button class="btn btn-xs btn-danger"  onclick="delOrg('${org.id}')">
														<i class="icon-trash bigger-120"></i>
															删除
													</button>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>			
		<script type="text/javascript">
			  $(".page-content").css("height",window.screen.height-150<=520?520:window.screen.height-150);
		</script>
		<script src="${contextPath}/resources/assets/js/jquery-ui-1.10.3.full.min.js"></script>
		<script src="${contextPath}/resources/assets/js/jquery.ui.touch-punch.min.js"></script>

<script type="text/javascript">
	var nodeid='';
	var oTable;
	
	function updateOrg(id){
		art.dialog.open('${contextPath}/management/organizationMG/updateOrgIndex?id='+id, {title: '更新',fixed: true,lock: true,height:320});
		}
		
	function delOrg(id){
		 art.dialog({
		    content: '是否确定删除改数据？',
			    ok: function () {
				$.post("${contextPath}/management/organizationMG/delOrgDate?id="+id);
			      return true;
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
				});
			}
			$('#addButton').on('click', function () {
				openWindow('${contextPath}/management/organizationMG/addOrgIndex?id='+nodeid,'添加');
			});
			
			function openWindow(url,title){
				if(nodeid!=''){
					art.dialog.open(url, {title: title,fixed: true,lock: true,height:320});
				}else{
					art.dialog({
					    icon: 'warning',
					    content: '请选择区/街道！'
					});
				}
			}
			
			function tableReload(){
				//oTable.fnReloadAjax('${contextPath}/management/appManage/showOrgData?id='+nodeid);
				$('#zzinfo').tree("reload");
				//oTable.fnReloadAjax(); //使用默认
			}
			 
			$('#zzinfo').tree({
				onClick: function(node){
					nodeid = node.id;
					// 在用户点击的时候提示
						//oTable.fnReloadAjax('${contextPath}/management/appManage/showOrgData?id='+node.id);
						//alert(nodeid);
						//reloadGrid();
						//oTable.fnReloadAjax(); //使用默认
					//$('#xzqh').val(node.id);
				}
			});
		 function reloadGrid(){
				 $.ajax({
			       url:'${contextPath}/management/organizationMG/showOrgData', //后台处理程序
			       type:'post',         //数据发送方式
			       success:function (date){
			   			
				        }
				 });
			}
</script>
	</body>
</html>
