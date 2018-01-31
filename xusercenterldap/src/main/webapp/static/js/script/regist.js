$(document).ready(function() {
    // 清空提示信息
    $('input').on("focus", function() {
        $('#regist_info').text('');
    });
});

// 注册
function regist() {
    var uid = $.trim($('#uid').val()); // 用户名
    var userPassword = $.trim($('#userPassword').val()); // 密码
    var repeatUserPassword = $.trim($('#repeatUserPassword').val()); // 确认密码
    var o = $.trim($('#o').val()); // 公司Code
    var cn = $.trim($('#cn').val()); // 姓名
    var mobile = $.trim($('#mobile').val()); // 手机
    var mail = $.trim($('#mail').val()); // 邮箱

    if (uid && userPassword && repeatUserPassword && o && cn && mobile && mail) {
        if (userPassword != repeatUserPassword) {
            $('#regist_info').text('密码不一致');
        } else {
            $.ajax({
                type: 'POST',
                url: 'regist',
                data: {
                    uid: uid,
                    userPassword: userPassword,
                    o: o,
                    cn: cn,
                    mobile: mobile,
                    mail: mail
                },
                beforeSend: function() {
                    return true;
                },
                success: function(result) {
                    if (result.code != '0') {
                        $('#userPassword').val(''); // 密码
                        $('#repeatUserPassword').val(''); // 确认密码
                        $('#regist_info').text(result.desc);
                    } else {
                        window.location.href = 'tologin?uid=' + uid;
                    }
                },
                error: function() {
                    alert('some error');
                }
            });
        }
    } else {
        $('#regist_info').text('注册信息不完整');
    }
}