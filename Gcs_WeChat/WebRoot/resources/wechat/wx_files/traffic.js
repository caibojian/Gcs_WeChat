function blockUI(className, html, timeout)
{
    if($('.floating_layer').length > 0)
    {
        $('#floating_content').removeClass().addClass(className).html(html);
        $('.floating_layer').show();
    }
    else
    {
        var height = $("body").height();
        var client_height = document.documentElement.clientHeight;
        var value_of_height = null;
        if(height > client_height) {
            value_of_height = height;
        } else {
            value_of_height = client_height;
        }
        var tpl = '<div class="floating_layer" style="height:'+value_of_height+'"><div class="outer"><div class="cz_slideup_box"><div id="floating_content" class="'+className+'">'+html+'</div></div></div></div>';
        $('body').append(tpl);
    }

    timeout = timeout || 0;
    if(timeout)
    {
        setTimeout("unblockUI()", timeout);
    }

    return  false;
}

function unblockUI()
{
    $('.floating_layer').hide();
}

//车辆违法查询加关注
function setFocus(dom)
{
    var core = $(dom).attr('_core');
    var engine = $(dom).attr('_engine');
    var href = $(dom).attr('_href');
    var token = $(dom).attr('_token');

    blockUI('loadding', '关注中...');
    $.post(href, {core: core, engine:engine, MOD_CSRF_TOKEN:token}, function(R){

        var tips = '';
        var code = parseInt(R);
        if(code == 0)
        {
            tips = '关注成功';
            $('#focusBox').hide();
        }
        else if(code == -1)
        {
            tips = '参数错误';
        }
        else if(code == -2)
        {
            tips = '行驶证已被绑定';
        }
        else if(code == -3)
        {
            tips = '关注失败，请检查输入的信息是否正确';
        }
        else if(code == -4)
        {
            tips = '数据库操作失败';
        }

        blockUI('tips', tips, 2000);
    });

    return false;
}

function cancelFocus()
{
    $('#focusBox').hide();
}
