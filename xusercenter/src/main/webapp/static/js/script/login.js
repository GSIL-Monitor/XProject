$(document).ready(function() {
	
	$('#username').on('focus', function() {
		$('#login_info').text('');
	});
	
	$('#password').on('focus', function() {
		$('#login_info').text('');
	});
	
});

// 登录验证
function loginCheck() {
	var username = $.trim($('#username').val()); // 用户名
	var password = $.trim($('#password').val()); // 密码
	
	if (username && password) {
		return true;
	} else {
		$('#login_info').text('用户名或密码不能为空');
		return false;
	}
}