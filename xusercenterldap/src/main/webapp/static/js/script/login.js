$(document).ready(function() {

    $.ajax({
        type: 'POST',
        url: 'getLoginTicket',
        data: {},
        beforeSend: function() {
            return true;
        },
        success: function(lt) {
			$('#lt').val(lt);
        },
        error: function() {
            alert('some error');
        }
    });

	$('#uid').on('focus', function() {
		$('#login_info').text('');
	});
	
	$('#userPassword').on('focus', function() {
		$('#login_info').text('');
	});
	
});

// 登录
function login() {
	var uid = $.trim($('#uid').val()); // 用户名
	var userPassword = $.trim($('#userPassword').val()); // 密码
    var lt = $.trim($('#lt').val()); // 登录票据
    var service = getUrlParam('service'); // 应用地址
	
	if (uid && userPassword) {
        $.ajax({
            type: 'POST',
            url: 'login',
            data: {
                uid: uid,
                userPassword: userPassword,
				lt: lt,
                service: service
            },
            beforeSend: function() {
                return true;
            },
            success: function(result) {
                if (result.code != '0') {
                    $('#lt').val(result.data.lt);
                    $('#login_info').text(result.desc);
                } else {
                	if (service) {
                        window.location.href = service + '?ticket=' + result.data.ticket;
					} else {
                        window.location.href = 'tomain';
					}
                }
            },
            error: function() {
                alert('some error');
            }
        });
	} else {
		$('#login_info').text('用户名或密码不能为空');
	}
}