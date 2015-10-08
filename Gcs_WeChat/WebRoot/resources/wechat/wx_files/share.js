var url = document.location.href;

if (typeof g_url != "undefined" && g_url != "")
{
	url = g_url;
}
var weChatBridgeReady={
	//初始化
	init:function()
	{
		weChatBridgeReady.bindShareWithApp();
		weChatBridgeReady.bindShareWithTimeline();
		weChatBridgeReady.bindShareWithWeibo();
	},
	
	//appmsg 分享
	bindShareWithApp:function()
	{

		WeixinJSBridge.on("menu:share:appmessage",function(b)
		{
			WeixinJSBridge.invoke("sendAppMessage",
			{
				appid:"",
				img_url:contentModel.img_url,
				img_width:"65",
				img_height:"65",
				link:url,
				desc:contentModel.app_name + contentModel.desc,
				title:contentModel.title
			},
			function(d)
			{
				WeixinJSBridge.log(d.err_msg);
			})
		})
	},
	
	//朋友圈分享
	bindShareWithTimeline:function()
	{
		// var url = document.location.href;
		
		WeixinJSBridge.on("menu:share:timeline",function(b)
		{
			WeixinJSBridge.invoke("shareTimeline",
			{
				img_url:contentModel.img_url,
				img_width:"65",
				img_height:"65",
				link:url,
				desc:contentModel.app_name + ":" + contentModel.title,
				title:contentModel.app_name + ":" + contentModel.title
			},
			
			function(d)
			{
				WeixinJSBridge.log(d.err_msg);
			})
		})
	},
	
	//微博分享
	bindShareWithWeibo:function()
	{
		var url = document.location.href;

		WeixinJSBridge.on('menu:share:weibo',function(argv) {
			WeixinJSBridge.invoke('shareWeibo',
			{
				"content" : contentModel.title + "||" + contentModel.app_name + "微信公众号" + contentModel.desc + url,  // 分享到微薄的内容
				"url" : url // 分享连接地址
            },function(res){
                WeixinJSBridge.log(res.err_msg);
			});
		})
	}
};


if(typeof WeixinJSBridge!="undefined"&&WeixinJSBridge.invoke)
{
	weChatBridgeReady.init();
}else
{
	document.addEventListener("WeixinJSBridgeReady",weChatBridgeReady.init);
};



var img_width = 0;
var img_height = 0;
var img_url = null;


//获取屏幕参数
function get_screen_size()
{
    return {"width":document.body.clientWidth, "height": document.body.clientHeight};
}

//获取屏幕参数
function get_screen_size()
{
    return {"width":document.body.clientWidth, "height": document.body.clientHeight};
}


   Zepto(function($) {
       $(".cz_sign").bind("click", function(){
            var src = $(this).attr("data-src");
            img_show(src);
       });

       $(".info .img img").bind("click", function() {
            var src = $(this).attr("src");
            var url_split = src.split("&");
            var size = "size=4";
            

            img_width = 0;
            img_height = 0;


            if (typeof src == "undefined" || src.lastIndexOf("illegal_default_200.jpg") > 0 || src == "") {
                return false;
            }

            if(window.screen.availWidth >= 640) {
                size = "size=6";
            }

            img_url = url_split[0] + "&" + size + "&" + url_split[2] + "&" + url_split[3] + "&" + url_split[4];
            
            $(".img_loading").show();

            img_show(img_url);
            
        })
   });

//图片展示
    function img_show(img_url)
    {
        var img = new Image();
        

        $(".float_layer_bigImg .img_layer").css("display","none");
        
        set_layer_bg();

        $(".float_layer_bigImg").show();
        $(".float_layer_bigImg .close").bind("click", function(){
            $(".float_layer_bigImg").hide();
        });
        
        

        img.src = img_url;

        if (img.complete) 
        {
            img_width = img.width;
            img_height = img.height;

            img_bg_auto(img_width, img_height);           
            $(".float_layer_bigImg .img_layer").attr("src", img_url);
            $(".float_layer_bigImg .img_layer").css("display","block");
            $(".img_loading").hide();

        }else 
        {
            img.onload = function () 
            {
                img.onload = null;
                img_width = img.width;
                img_height = img.height;
                
                img_bg_auto(img_width, img_height);
                $(".float_layer_bigImg .img_layer").attr("src", img_url);
                $(".float_layer_bigImg .img_layer").css("display","block");
                $(".img_loading").hide();
            };
        }
    }

    //图片适应
    function img_auto(img_width, img_height) 
    {
        if (img_width == 0 || img_height == 0)
        {
            return;
        }

        var size = get_screen_size();

        var screen_width = size.width;
        var screen_height = size.height;


        //alert(img_width + " " + img_height);

        var th = 0; //屏幕高度计算

        var img_layer = $(".float_layer_bigImg .img_layer");

        if (screen_height > img_height && screen_width > img_width)
        {
            //图片不需要调整，只需要居中即可
            img_layer.css({
                "top": Math.floor((screen_height-img_height)/2),
                "left": Math.floor((screen_width-img_width)/2)
            });
        }else if (img_width/img_height > screen_width/screen_height)
        {
            //图片有超出，需要按宽压缩
            th = Math.floor(screen_width*img_height/img_width);

            img_layer.css({
                "width" : screen_width - 4,
                "height" : th,
                "top": Math.floor((screen_height-th)/2),
                "left": 2
            });
            
        }else
        {
            //图片有超出，需要按高压缩
            th = Math.floor(screen_height*img_width/img_height);

            img_layer.css({
                "width" : th,
                "height" : screen_height - 4,
                "top": 2,
                "left": Math.floor((screen_width-th)/2)
            });
            
        }
    }
    
    //背景适应
    function img_bg_auto(img_width, img_height)
    {
        //先阶段性调整一下
        set_layer_bg();

        setTimeout(function(){
            set_layer_bg();
            img_auto(img_width, img_height);
            //alert($("body").height());
        },300);
    }
    
    //设置layer的背景
    function set_layer_bg()
    {
        var size = get_screen_size();
        var tmp_h = Math.max(size.height , $("body").height());
        var tmp_w = Math.max(size.width , $("body").width());
        $(".float_layer_bigImg").css({"height": tmp_h,"width":tmp_w});
    }

    // 手机旋转
    function orientationChange(){
            

            if($(".float_layer_bigImg").css("display") == "none") {
                    return false;
            }

            switch(window.orientation) { 
            case 0:
            case -90:
            case 90:
            case 180:
                img_bg_auto(img_width, img_height);
                break; 
            default:
            break;
            
        } 
    }
     //
     window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", orientationChange, false); 