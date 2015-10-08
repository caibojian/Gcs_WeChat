		<!-- basic styles -->
			<link href="${contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet" />
			<link rel="stylesheet" href="${contextPath}/resources/assets/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		
		<!--[if IE 7]>
		  <link rel="stylesheet" href="${contextPath}/resources/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
		
		<!-- ace styles -->
			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace.min.css" />
			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-rtl.min.css" />
			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-skins.min.css" />
			<!--[if lte IE 8]>
		  		<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-ie.min.css" />
			<![endif]-->
				<script src="${contextPath}/resources/assets/js/ace-extra.min.js"></script>
			<!--[if lt IE 9]>
				<script src="${contextPath}/resources/assets/js/html5shiv.js"></script>
				<script src="${contextPath}/resources/assets/js/respond.min.js"></script>
			<![endif]-->
		<!--[if !IE]> -->
			<script type="text/javascript" src="${contextPath}/resources/assets/js/jquery-2.0.3.min.js"></script>
		<!-- <![endif]-->

		<!--[if IE]>
			<script src="${contextPath}/resources/assets/js/jquery-1.10.2.min.js"></script>
		<![endif]-->
		<!-- basic scripts -->
			<script type="text/javascript">
				if(typeof JSON == 'undefined'){
					$('head').append($("<script type='text/javascript' src='${contextPath}/resources/js/json2.js'>"));
				}
			</script>
			<script type="text/javascript">
				if("ontouchend" in document) document.write("<script src='${contextPath}/resources/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
			</script>
			<script type="text/javascript" src="${contextPath}/resources/assets/js/bootstrap.min.js"></script>
			
			<script type="text/javascript" src="${contextPath}/resources/assets/js/typeahead-bs2.min.js"></script>
			
			<!--[if lte IE 8]>
		  		<script src="${contextPath}/resources/assets/js/excanvas.min.js"></script>
			<![endif]-->
			<!-- page specific plugin scripts -->
		 
		<!-- ace scripts -->

			<script src="${contextPath}/resources/assets/js/ace-elements.min.js"></script>
			
			<script src="${contextPath}/resources/assets/js/ace.min.js"></script>
			
			<!-- artdialog-->
			<script type="text/javascript" src="${contextPath}/resources/artDialog/artDialog.js?skin=black"></script>
			<script type="text/javascript" src="${contextPath}/resources/artDialog/plugins/iframeTools.js"></script>
