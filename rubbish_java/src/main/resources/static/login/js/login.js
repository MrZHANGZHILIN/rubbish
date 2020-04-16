$(function(){
	var loginId = $.cookie('loginId');
	var password = $.cookie('password');
	$("input[name=loginId]").val(loginId);
	$("input[name=password]").val(password);
	if(password != '')
		$('input[name=remember]').prop('checked',true);  
	//刷新验证码
	$(".refresh_captcha").on("click", function(){
		$(this).attr("src", "/getCaptcha?random="+new Date().getTime());
	})
	//按回车登录
	$(document).keydown(function(e){ 
		var curKey = e.which; 
	    if(curKey == 13){ 
	    	$(".sub-btn").click(); 
	        return false; 
	    } 
    }); 
	//提交
	$(".sub-btn").on("click", function(){
		var loginId = $.trim($("input[name=loginId]").val());
		var password = $.trim($("input[name=password]").val());
		var captcha = $.trim($("input[name=captcha]").val());
		if(loginId == ''){
			alert('请输入登录账号');
			return ;
		}
		if(password == ''){
			alert('请输入登录密码');
			return ;
		}
		if(captcha == ''){
			alert('请输入图形验证码');
			return ;
		}
		
		$.ajax({
	        url: "/doLogin",
	        async : true,
	        type: "post",
	        dataType: "json",
	        data: {"loginId":loginId, "pwd":password, "captcha":captcha},
	        success: function (data) {
	        	if(data.code != 200){
	        		alert(data.data);
	        		$(".refresh_captcha").trigger("click");
	        		$("input[name=captcha]").val("");
	        		return ;
	        	}
	        	$.cookie('loginId', loginId);
	        	if($('input[name=remember]').is(':checked')) {
	        		$.cookie('password', password);
	        	}else{
	        		$.cookie('password', '');
	        	}
	        	location.href = "/index";
	        }
    	});
	})
});