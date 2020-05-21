$(function(){
    
    //显示错误信息
	var showError = function(msg){
		swal({
			  type: 'error',
			  title: msg,
			  showConfirmButton: false,
			  timer: 2000
		})
	}
	//上传logo
	$(".img-outer-box input[type=file]").bind("change", function(){
		var formData = new FormData();
		formData.append('file', $(this)[0].files[0]);
		$.ajax({
	        url: ctx + "common/uploadImg",
	        async : true,
	        type: "post",
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        data: formData,
	        success: function (data) {
	        	if(data.error != 0){
	        		return ;
	        	}
	        	var url = data.data[0];
	        	var html = '<img src="'+url+'">';
	        	$(".img-outer-box .content").html(html);
	        }
    	});
	});
	//提交
	$(".sub-btn").on("click", function(){
		var data = {};
		//名称
		data.name = $.trim($("input[name=name]").val());
		if(data.name == ''){
			showError('请输入名称!');
			return ;
		}
		//描述
		data.des = $.trim($("textarea[name=des]").val());
		if(data.des == ''){
			showError('请输入描述!');
			return ;
		}
		//指导
		data.guide = $.trim($("textarea[name=guide]").val());
		if(data.guide == ''){
			showError('请输入指导!');
			return ;
		}
		//排序
		var reg = /^[0-9]+$/;
		data.order = $.trim($("input[name=order]").val());
		if(data.order == ''){
			showError('请输排序!');
			return ;
		}
		if(!reg.test(data.order)){
	    	showError('排序只能是整数!');
	    	return ;
	    }
		//logo
		var $img = $(".img-box .content img");
		if($img.length == 0){
			showError('请设置图片!');
			return ;
		}
		data.img = $img.attr("src");
		data.dbid = $.trim($("input[name=dbid]").val());
		data.img.replace('/rubbish/', '');
		$.ajax({
	        url: ctx + "category/update",
	        async : true,
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function (data) {
	        	if(data.code == 200){
	        		swal({
	 	      			   type: 'success',
	 	      			   title: "修改成功!",
	 	      			   showConfirmButton: false,
	 	      			   timer: 3000
	 	      		    })
	        		setTimeout(function(){
	 	        		location.href = ctx + "category/list";
	        		}, 3000); 
	        		
	        	}else{
	        		showError(data.data);
	        	}
	        }
	    });
	})
});