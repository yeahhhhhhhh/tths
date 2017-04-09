$(document).ready(function () {
	$('#content-box').height($('.active-content-text-box').innerHeight() + $('footer').height() + $('#tab-menu').innerHeight());
	// 鼠标悬浮切换class
	$('#tab-menu li a').mouseover(function () {
		$(this).addClass('active-tab-menu');
	});
	$('#tab-menu li a').mouseout(function () {
		$('#tab-menu li a').removeClass('active-tab-menu');
	});
	// 切换内容
	$('#tab-menu li a').click(function (event) {
		event.preventDefault();
		var tabIndex = $(this).attr("data-tab-index");
		$('#tab-menu li a').removeClass('current-tab-menu');
		$(this).addClass('current-tab-menu');
		$('.active-content-text-box').addClass('fadeOutDown');
		// 点击菜单后内容class变化
		setTimeout(function () {
			$('.active-content-text-box').removeClass('fadeOutDown');
			$('.content-text-box').removeClass('active-content-text-box animated fadeInUp fadeOutDown');
			$('.content-text-box[data-content-box-index="' + tabIndex + '"]').addClass('active-content-text-box animated fadeInUp');
			// 中间内容部分高度平滑改变
			setTimeout(function () {
				$('#content-box').animate({
					'height': $('.active-content-text-box').innerHeight() + $('footer').height() + $('#tab-menu').innerHeight()
				});
			},120);
		},500);
	});
});