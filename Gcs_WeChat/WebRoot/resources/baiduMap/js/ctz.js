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
