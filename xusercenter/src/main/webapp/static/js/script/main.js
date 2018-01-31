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

// 修改密码验证
function updatePasswordCheck() {
    var opassword = $.trim($('#opassword').val()); // 原密码
    var npassword = $.trim($('#npassword').val()); // 新密码
    var repeatnpassword = $.trim($('#repeatnpassword').val()); // 确认新密码

    if (opassword && npassword && repeatnpassword) {
        if (opassword == npassword) {
            $('#password_info').text('新密码和原密码相同');
            return false;
        }

        if (npassword == repeatnpassword) {
            return true;
        }

        $('#password_info').text('新密码输入不一致');
        return false;
    } else {
        $('#password_info').text('信息不完整');
        return false;
    }
}