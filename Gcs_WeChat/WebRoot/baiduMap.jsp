<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${contextPath}/resources/islider/islider.css">
		<link rel="stylesheet" href="${contextPath}/resources/baiduMap/css/style.css">
		<style>
	        body {
	            margin: 0;
	            padding: 0;
	            background: #333;
	            overflow: hidden;
	        }
	
	        /*ul wrapper*/
	        #iSlider-wrapper {
	            height: 85%;
	            width: 100%;
	            overflow: hidden;
	            position: absolute;
	            z-index: 99;
	        }
	        
	        .text {
	        	top:85%;
	         	height: 15%;
	            width: 100%;
	            overflow: hidden;
	            position: absolute;
	            z-index: 100;
	        }
	
	        #iSlider-wrapper ul {
	            list-style: none;
	            margin: 0;
	            padding: 0;
	            height: 100%;
	            overflow: hidden;
	        }
	
	        #iSlider-wrapper li {
	            position: absolute;
	            margin: 0;
	            padding: 0;
	            height: 100%;
	            overflow: hidden;
	            display: -webkit-box;
	            -webkit-box-pack: center;
	            -webkit-box-align: center;
	            list-style: none;
	        }
	
	        #iSlider-wrapper li img {
	            max-width: 100%;
	            max-height: 100%;
	        }
	
	    </style>
		<script type="text/javascript" src="${contextPath}/resources/assets/js/jquery-2.0.3.min.js"></script>
	</head>

	<body>

		<div id="map"></div>
		<script src="http://api.map.baidu.com/api?v=2.0&ak=kZHuUKCUUcxyK0m2OswLH4pc"></script>
		<script src="${contextPath}/resources/Demo_files/jweixin-1.js"></script>
		<script src="${contextPath}/resources/islider/islider_core.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/artDialog/artDialog.js?skin=black"></script>
		<script type="text/javascript" src="${contextPath}/resources/artDialog/plugins/iframeTools.js"></script>
		
		<script>
		  /*
		   * 注意：
		   * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
		   * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
		   * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
		   *
		   * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
		   * 邮箱地址：weixin-open@qq.com
		   * 邮件主题：【微信JS-SDK反馈】具体问题
		   * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
		   */
		  
		   
		   
		  wx.config({
			  debug: false,
			  appId: '${appId}',
			  timestamp: '${timestamp}',
			  nonceStr: '${nonceStr}',
			  signature: '${signature}',
			  jsApiList: [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'hideMenuItems',
				'showMenuItems',
				'hideAllNonBaseMenuItem',
				'showAllNonBaseMenuItem',
				'translateVoice',
				'startRecord',
				'stopRecord',
				'onRecordEnd',
				'playVoice',
				'pauseVoice',
				'stopVoice',
				'uploadVoice',
				'downloadVoice',
				'chooseImage',
				'previewImage',
				'uploadImage',
				'downloadImage',
				'getNetworkType',
				'openLocation',
				'getLocation',
				'hideOptionMenu',
				'showOptionMenu',
				'closeWindow',
				'scanQRCode',
				'chooseWXPay',
				'openProductSpecificView',
				'addCard',
				'chooseCard',
				'openCard'
			  ]
		  });
		</script>

		<script >
		
			
			;(function(exports){
	
				var $ = { version: '0.0.1.beta'};
				
				var data = $.data = [];
				
				function isType(type) {
				  return function(obj) {
					return {}.toString.call(obj) == "[object " + type + "]";
				  }
				}
				
				$.isObject = isType("Object");
				$.isString = isType("String");
				$.isArray  = Array.isArray || isType("Array");
				
				$.get = function(selectors) {
					
					if (!$.isString(selectors)) 
						throw Error('传入参数只能为字符串.');
					
					if (data.length == 0) {
						var selector = document.querySelector(selectors);
						data.push([selectors, selector]);
						return selector;
					}
					
					for (var i in data) {
						if (data[i][0] == selectors) {
							return data[i][1];
						} else {
							var selector = document.querySelector(selectors);
							data.push([selectors, selector]);
							return selector;
						}
					}
				}
				
				exports.$ = $;
				
			})(window);

		</script>
		<script >
			
			;(function(map) {
				wx.ready(function () {
					// 7 地理位置接口
					  // 7.2 获取当前地理位置
					    wx.getLocation({
					      success: function (res) {
					        var m = new map();
							// 坐标系
							var marker = [];
							//获取事故地点坐标
							jQuery.ajax({    	
								type  : "get", 
								async : false,
							    url   : '${contextPath}/management/wechat/getPointData',
								success:function(data){
									for (var m in data.msg) {
										var points = {point: ""+data.msg[m].longitude+"|"+data.msg[m].latitude+"", 
												image: "${contextPath}/resources/baiduMap/img/location2.ico", 
												content: "<div id='iSlider-wrapper' ></div><div class='text'>报警人:"+data.msg[m].name+";电话:"+data.msg[m].phone+"</br>时间:"+data.msg[m].regTime+"</div>",
												id:""+data.msg[m].ac_id+""};
										marker.push(points);
									}
								}
							});
							
							jQuery.ajax({    	
								type  : "get", 
								async : false,
							    url   : '${contextPath}/management/wechat/changePointData',
							    data  : {
							    	x : res.longitude,
							    	y : res.latitude
							    },
								success:function(data){
									var data = eval('(' + data + ')'); 
									var point = {point: ""+data.result[0].x+"|"+data.result[0].y+"", image: "${contextPath}/resources/baiduMap/img/location1.ico", content: "我的位置"};
									marker.push(point);
									
									m.createMap(data.result[0].x,data.result[0].y);
									m.setMapEvent();
									m.addMarker(marker);
									//是否显示路况提示面板
									var ctrl = new BMapLib.TrafficControl({
							           		showPanel: true //是否显示路况提示面板
							       		});      
							       	m.addControl(ctrl);
							       	ctrl.setAnchor(BMAP_ANCHOR_BOTTOM_LEFT);
									var date_obj = new Date();
									ctrl.showTraffic( { 
										predictDate : { hour:date_obj.getHours(), weekday : ( date_obj.getDay() == 0 ) ? 7 : date_obj.getDay() }
									} )
								}
							});
							
					      },
					      cancel: function (res) {
					        alert('用户拒绝授权获取地理位置');
					      }
					    });
				});
				
			})(function($) {
				
				function map() {
					this.m 				= new BMap.Map("map");
					this.createMap 		= createMap;
					this.setMapEvent 	= setMapEvent;
					this.addMapControl 	= addMapControl;
					this.addMarker		= addMarker;
				};
				
				
				/**
				 * 初始化地图
				 * @name createMap
				 */
				var createMap = function(longitude,latitude) {
					var point = new BMap.Point(longitude, latitude);
					this.m.centerAndZoom(point, 14);
				}
				
				/**
				 * 设置地图事件
				 * @name setMapEvent
				 */
				var setMapEvent = function() {
					this.m.enableDragging();
					this.m.enableScrollWheelZoom();
					this.m.enableDoubleClickZoom();	
					this.m.enableKeyboard();
				}
				
				/**
				 * 添加地图控件
				 * @name addMapControl 
				 */
				var addMapControl = function() {
					var ctrl_nav = new BMap.
						NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
					this.m.addControl(ctrl_nav);
					var ctrl_ove = new BMap.
						OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
					this.m.addControl(ctrl_ove);
					var ctrl_sca = new BMap.
						ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
					this.m.addControl(ctrl_sca);
				}
				
				/**
				 * 创建地图标记 
				 * @name addMarker
				 * @param {Object} data
				 */
				var addMarker = function(data) {	
					
					if (!$.isArray(data))
						throw Error('传入参数只能为数组.');
					
					for (var i = 0, len = data.length; i < len; i++) {
						
						var json 	= data[i];
						var point 	= new BMap.Point(json.point.split("|")[0],
											json.point.split("|")[1]);
						var icon 	= new BMap.Icon(json.image, new BMap.Size(32,32))
						var marker 	= new BMap.Marker(point, {icon: icon});
						// var marker 	= new BMap.Marker(point);
						this.m.addOverlay(marker);
						(function() {
							var _index 	= i;
							var _marker = marker;
							var iw = new BMap.InfoWindow(data[_index].content);
							iw.setWidth(250);
							iw.setHeight(300);
							_marker.addEventListener("click", function(e) {
								this.openInfoWindow(iw);
								/* art.dialog({
									title: 'hello world!',
							 		content: "<div id='iSlider-wrapper'></div>",
							 		width:"100",
							 		height:"100"
								}); */
								//alert(data[_index].id);
								//alert("${contextPath}/management/wechat/getImage?AC_ID="+data[_index].id+"&Mark_Label=1");
								var list = [];
								jQuery.ajax({    	
									type  : "get", 
									async : false,
								    url   : '${contextPath}/management/wechat/getImage',
								    data  : {
								    	AC_ID : data[_index].id
								    },
									success:function(data){
										list = [
									            {content: "${contextPath}/resources/islider/img/"+data.msg+"_1.png"}, 
									            {content: "${contextPath}/resources/islider/img/"+data.msg+"_2.png"}, 
									            {content: "${contextPath}/resources/islider/img/"+data.msg+"_3.png"}, 
									            
									        ];
									}
								});
								//alert(document.getElementById("iSlider-wrapper"));
								setTimeout(function(){
									var islider = new iSlider({
							            type: 'pic',
							            data: list,
							            dom: document.getElementById("iSlider-wrapper"),
							            isLooping: true,
							            animateType: 'default'
							        });
									
								}, 300);
						        
							        
							});
						})()
					}
						
				}
				 
				
				return map;
				
			}($));
		</script>
	</body>

</html>