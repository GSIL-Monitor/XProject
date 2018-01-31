$(document).ready(function() {
    $('#opassword').on('focus', function() {
        $('#password_info').text('');
    });

    $('#npassword').on('focus', function() {
        $('#password_info').text('');
    });

    $('#repeatnpassword').on('focus', function() {
        $('#password_info').text('');
    });
});

// 修改密码
function updatePassword() {
    var uid = $.trim($('#uid').val()); // 用户名
    var o = $.trim($('#o').val()); // 公司Code
    var opassword = $.trim($('#opassword').val()); // 原密码
    var npassword = $.trim($('#npassword').val()); // 新密码
    var repeatnpassword = $.trim($('#repeatnpassword').val()); // 确认新密码

    if (opassword && npassword && repeatnpassword) {
        if (opassword == npassword) {
            $('#password_info').text('新密码和原密码相同');
        } else if (npassword != repeatnpassword) {
            $('#password_info').text('新密码输入不一致');
        } else {
            $.ajax({
                type: 'POST',
                url: 'updatePassword',
                data: {
                    uid: uid,
                    o: o,
                    opassword: opassword,
                    npassword: npassword
                },
                beforeSend: function() {
                    return true;
                },
                success: function(result) {
                    if (result.code == '0') {
                        window.location.href = 'tologin?uid=' + uid + '&info=密码修改成功，请重新登录';
                    } else {
                        $('#password_info').text(result.desc);
                    }
                },
                error: function() {
                    alert('some error');
                }
            });
        }
    } else {
        $('#password_info').text('信息不完整');
    }
}