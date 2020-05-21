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
		//问题
		data.name = $.trim($("input[name=name]").val());
		if(data.name == ''){
			showError('请输入问题!');
			return ;
		}
		//选项A
		data.a = $.trim($("input[name=a]").val());
		if(data.a == ''){
			showError('请输入选项A!');
			return ;
		}
		//选项B
		data.b = $.trim($("input[name=b]").val());
		if(data.b == ''){
			showError('请输入选项B!');
			return ;
		}
		//选项C
		data.c = $.trim($("input[name=c]").val());
		if(data.c == ''){
			showError('请输入选项C!');
			return ;
		}
		//选项D
		data.d = $.trim($("input[name=d]").val());
		if(data.d == ''){
			showError('请输入选项D!');
			return ;
		}
		//答案
		data.answer = $.trim($("input[name=answer]").val());
		if(data.answer == ''){
			showError('请输入答案!');
			return ;
		}
		$.ajax({
	        url: ctx + "question/add",
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
	 	        		location.href = ctx + "question/list";
	        		}, 3000); 
	        		
	        	}else{
	        		showError(data.data);
	        	}
	        }
	    });
	})
});