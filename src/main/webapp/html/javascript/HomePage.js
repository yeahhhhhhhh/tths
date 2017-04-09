$(document).ready(function () {
	var URLHead = 'http://119.29.234.36:8080/tths';
	var editPosition = $('<li class="edit-sort">'+
							'<a href="#" javascript:void(0)>编辑'+
								'<img src="style/images/edit-sort.png" alt="edit-sort-icon" class="sort-icon">'+
							'</a>'+
						'</li>');
	var addTeacher = $('<div class="add-teacher-box">' +
							'<a href="#" class="add-teacher">添加教师'+
								'<img src="style/images/add.png" alt="添加" class="sort-icon">'+
							'</a>'+
						'</div>');
	$('.teacher-list-box').append($('<div class="teacher-block">'+
					'<a href="#">'+
						'<img src="style/images/zt.jpg" alt="教师照片">'+
						'<div class="teacher-name">张挺ttt</div>'+
					'</a>'+
				'</div>'));
	// 动态插入教师列表
	$.post(URLHead + '/user/findTeachers', {
		nowPage: 1,
		listCount: 24
	}, function(response) {
		var data = JSON.parse(response);
		// for () {

		// }
		console.log(data);
	});
	// 判断是否是管理员登录，动态添加编辑职称、添加教师DOM节点
	if (1) {
		$(".teacher-list-box").before(addTeacher);
		$("#sidebar-ul").prepend(editPosition);
	}
	// 引用jqpaginator库实现分页功能
	 $.jqPaginator('.pagination', {
        totalPages: 10,
        visiblePages: 5,
        currentPage: 1,
        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">&laquo;</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">&raquo;</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        // 页面修改时当前页面
        onPageChange: function (num) {
           	currentPage = num;
       		$(".list-ul").empty("li");
       		// 发送任务列表显示信息请求
			// $.post('http://119.29.234.36:8080/bcbg/BookReleaseListServlet',
			// {
			// 	keyword: keyword,
			// 	// 当前页数（通过分页按钮控制）
			// 	nowPage: currentPage,
			// 	// 一次性返回数据数量
			// 	bookListCount:5,
			// 	type:0
			// },function(data){
			// 	var response = JSON.parse(data);
			// 	var body = response.body;
			// 	var bookLists = response.body.bookList;
			// 	var length = body.bookList.length;
			// 	for(var i=0; i<length; i++){
			// 		// 动态插入
			// 		 $("<li class=\"list-li\">" + 
			// 				"<a href=\"#\" class=\"to-details-a\">" +
			// 					"<div class=\"books-view-infor-container\">" +
			// 						"<img src=\"" + "../images/default-book.jpg\"class=\"book-img\"/>" +
			// 						"<!-- 书籍信息 -->" +
			// 						"<div class=\"books-infor-content-container\">" +
			// 							"<div class=\"books-infor-content-header brief\">" +
			// 								"<div class=\"book-name\">" +
			// 									"<img src=\"../images/book-name.png\" class=\"icon\">" +
			// 									"<span>" + bookLists[i].bookName +"</span>" +
			// 								"</div>" +
			// 							"</div>" +
			// 							"<div class=\"promulgator-container brief\">" +
			// 								"<img src=\"../images/user.png\" class=\"icon\">" +
			// 								"<span>" + bookLists[i].book_author + "</span>" +
			// 							"</div>" +
			// 							"<div class=\"release-time brief\">" +
			// 								"<img src=\"../images/time.png\" class=\"icon\">" +
			// 								"<span>" + bookLists[i].bookdate + "</span>	" +
			// 							"</div>" +
			// 							"<div class=\"books-infor-content\">" +
			// 								"<img src=\"../images/content.png\" class=\"icon\">" +
			// 								"<span>" + bookLists[i].bookIntroduce + "</span>" +	
			// 							"</div>" +
			// 						"</div>" +
			// 					"</div>" +
			// 				"</a>" +
			// 			"</li>").appendTo($(".list-ul"));
			// 		// 书籍图片
			// 		if(bookLists[i].bookaddress){
			// 			$(".book-img").eq(i).attr("src", "http://119.29.234.36:8080/bcbg" + bookLists[i].bookaddress)
			// 		}
			// 	}
			// 	// 详情链接点击事件
			// 	$(".to-details-a").click(function(){
			// 		// 获取当前点击对象的序号
			// 		var order = $(this).parent().index();
			// 		$(".to-details-a").attr("href","book-details.html?" +  bookLists[order].bid);
					
			// 	});
			// });
        }
    });
});