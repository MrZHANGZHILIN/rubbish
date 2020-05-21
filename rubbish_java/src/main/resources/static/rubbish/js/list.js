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
	//删除
	$(document).on('click', '.delete-btn', function(){
		var dbid = $(this).attr("data-dbid");
		swal({
	  		  title: '删除垃圾',
	  		  text: '确认要删除该垃圾吗?',
	  		  showCancelButton: true,
	  		  confirmButtonText: '确认',
	  		  cancelButtonText: '取消'
		  	}).then((result) => {
	  		    if(result.value){
	  		    	$.ajax({
	  			        url: ctx + "rubbish/delete",
	  			        async : true,
	  			        type: "post",
	  			        dataType: "json",
	  			        data: {dbids: dbid},
	  			        success: function (data) {
	  			        	if(data.code == 200){
	  			        		swal({
	  			 	      			   type: 'success',
	  			 	      			   title: "删除成功!",
	  			 	      			   showConfirmButton: false,
	  			 	      			   timer: 3000
	  			 	      		    })
	  			        		setTimeout(function(){
	  			        			var pageNo = $(".pagination .current").html();
	  			        			$(".search-box input[name=p]").val(pageNo);
	  			        			$(".search-box button[type=submit]").trigger("click");
	  			        		}, 3000);

	  			        	}else{
	  			        		showError(data.data);
	  			        	}
	  			        }
	  			    });
	  		    }
	    })
	});
});
