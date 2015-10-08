;(function(map) {
	
	// 坐标系
	var marker = [
		{point: "114.252474|30.616365", image: "xx", content: "<button onclick='go()'>到这里去</button>"},
		{point: "114.190276|30.609794", image: "xx", content: ""},
		{point: "114.190276|30.509794", image: "xx", content: ""}
	];

	var m = new map();
	m.createMap();
	m.setMapEvent();
	m.addMarker(marker);

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
	var createMap = function() {
		var point = new BMap.Point(114.252474, 30.616365);
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
				_marker.addEventListener("click", function(e) {
					this.openInfoWindow(iw);
				});
			})()
		}
			
	}

	return map;
	
}($));