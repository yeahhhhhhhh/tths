$(document).ready(function () {
	// 选择文件事件
	$("#information-setting .update-head").change(function () {
		// 头像预览
		var objUrl = getObjectURL(this.files[0]);
		if (objUrl) {
			$(".head-img").attr("src", objUrl);
		}
		//存储文件url
		function getObjectURL (file) {
			var url = null ; 
			if (window.createObjectURL != undefined) { // basic
				url = window.createObjectURL(file) ;
			} else if (window.URL != undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file) ;
			} else if (window.webkitURL != undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file) ;
			}
			return url ;
		};
	})
	// 富文本编辑器
	//实例化编辑器
    var um = UM.getEditor('myEditor');
    um.ready(function (){
    	// var UMEDITOR_HOME_URL = "/umeditor/";
    	var content = um.getContentTxt();
    	console.log(content);
    });


    // 资源管理
    var addResourceDom = $('<li>' +
							'<div class="add-resource-box">' +
								'<input type="text" class="add-resource-title" placeholder="请输入标题...">' +
								'<input type="file" class="add-resource-file">' +
								'<div class="operation-btn-box">' +
									'<button class="confirm">确认</button>' +
									'<button class="cancel">取消</button>' +
								'</div>' +
							'</div>' +
						'</li>');
    // 添加
    $('.resource-content-box .add-btn').click(function () {
    	$('.resource-content-box .resource-list-ul').prepend(addResourceDom);
    	// 确认
    	$('.add-resource-box .confirm').click(function () {
    		alert("提交成功！");
   		});
    	// 取消
    	$('.add-resource-box .cancel').click(function () {
    		$('.resource-content-box .resource-list-ul li:eq(0)').remove();
   		});
    });
    // 编辑
    $('.resource-content-box .edit').click(function () {
    	// $('.resource-content-box .resource-list-ul').prepend(addResourceDom);
    	console.log($(this).parent().parent().index());
    	// 确认
    	$('.add-resource-box .confirm').click(function () {
    		alert("提交成功！");
   		});
    	// 取消
    	$('.add-resource-box .cancel').click(function () {
    		$('.resource-content-box .resource-list-ul li:eq(0)').remove();
   		});
    });
    
})