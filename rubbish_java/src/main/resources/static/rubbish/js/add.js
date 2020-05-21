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
	//提交
	$(".sub-btn").on("click", function(){
		var data = {};
		//名称
		data.name = $.trim($("input[name=name]").val());
		if(data.name == ''){
			showError('请输入名称!');
			return ;
		}
		//分类
		data.categoryId = $.trim($("select[name=categoryId]").val());
		if(data.categoryId == ''){
			showError('请选择分类!');
			return ;
		}
		//热门
		data.hot = $.trim($("select[name=hot]").val());
		$.ajax({
	        url: ctx + "rubbish/add",
	        async : true,
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function (data) {
	        	if(data.code == 200){
	        		swal({
	 	      			   type: 'success',
	 	      			   title: "创建成功!",
	 	      			   showConfirmButton: false,
	 	      			   timer: 3000
	 	      		    })
	        		setTimeout(function(){
	 	        		location.href = ctx + "rubbish/list";
	        		}, 3000); 
	        		
	        	}else{
	        		showError(data.data);
	        	}
	        }
	    });
	})
});