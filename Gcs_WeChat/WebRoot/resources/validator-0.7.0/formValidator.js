var instance;
function initFormValidator(id){
	$("#"+id).validator({
		debug:0,//0 || false: 关闭调试信息1 || true: 启用调试信息2: 启用调试信息，并且不论表单是否验证成功都提交表单，便于对比后端的验证
		timely:1,//0 || false: 关闭实时验证，将只在提交表单的时候进行验证 1 || true: 启用实时验证，在字段失去焦点后验证该字段2: 启用实时验证，在输入的同时验证该字段
		theme:'yellow_right',//主题名字，用于设置一个表单验证的主题样式。zh_CN.js中配置了几个主题，可以作为参考'yellow_right_effect' 
		stopOnError :false,//是否在验证出错时停止继续验证，默认不停止
		focusInvalid :true,//是否自动让第一个出错的输入框获得的焦点，默认获得
		focusCleanup :false,//是否在输入框获得焦点的时候清除消息，默认不清除
		//ignore :':hidden',//指定需要忽略验证的元素的jQuery选择器
		//display://自定义消息中{0}的显示替换名称
		rules :{
			//自定义验证函数，具有最大的灵活性，没有什么不能验证
			dhRule: function(element, param, field) {          
					return /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(element.value) || '请检查电话号码格式';      
				},
			ybRule:function(element, param, field){
				return /^[1-9]\d{5}$/.test(element.value) || '请检查邮编格式';
			}
			//简单配置正则及错误消息，及其方便
			//another: [/^\w*$/, '请输入字母或下划线']
		},
		messages: {
			required: "{0}不能为空"
		}
	});
	instance= $("#"+id).validator().data("validator");
}