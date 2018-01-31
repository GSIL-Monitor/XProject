$(document).ready(function() {
    // 清空提示信息
    $('input').on("focus", function() {
        $('#regist_info').text('');
    });
});

// 注册验证
function registCheck() {
    var username = $.trim($('#username').val()); // 用户名
    var password = $.trim($('#password').val()); // 密码
    var repeatpassword = $.trim($('#repeatpassword').val()); // 确认密码

    if (username && password && repeatpassword) {
        if (password == repeatpassword) {
            return true;
        }
        $('#regist_info').text('密码不一致');
        return false;
    } else {
        $('#regist_info').text('注册信息不完整');
        return false;
    }
}