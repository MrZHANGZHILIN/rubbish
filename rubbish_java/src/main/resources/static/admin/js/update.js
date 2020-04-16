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
		//密码
		data.password = $.trim($("input[name=password]").val());
		if(data.password == ''){
			showError('请输入密码!');
			return ;
		}
		//状态
		data.status = $.trim($("select[name=status]").val());
		data.dbid = $.trim($("input[name=dbid]").val());
		$.ajax({
	        url: "/admin/update",
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
	 	        		location.href = "/admin/list";
	        		}, 3000); 
	        		
	        	}else{
	        		showError(data.data);
	        	}
	        }
	    });
	})
    // bindDelEventForBranch();
});