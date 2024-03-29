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
	  		  title: '删除分类',
	  		  text: '确认要删除该分类吗?',
	  		  showCancelButton: true,
	  		  confirmButtonText: '确认',
	  		  cancelButtonText: '取消'
		  	}).then((result) => {
	  		    if(result.value){
	  		    	$.ajax({
	  			        url: ctx + "category/delete.html",
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
	})
});