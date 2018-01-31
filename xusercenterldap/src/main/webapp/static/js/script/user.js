$(document).ready(function() {
    $('#user_info_update_btn').on('click', function() {
        $('#user_info_display').hide();
        $('#user_info_update').show();
    });
    $('#user_info_save_btn').on('click', function() {
        var uid = $.trim($('#uid').val()); // 用户名
        var o = $.trim($('#o').val()); // 公司Code
        var cn = $.trim($('#cn').val()); // 姓名
        var mobile = $.trim($('#mobile').val()); // 手机
        var mail = $.trim($('#mail').val()); // 邮箱

        if (uid && o && cn && mobile && mail) {
            $.ajax({
                type: 'POST',
                url: 'updateUserInfo',
                data: {
                    uid: uid,
                    o: o,
                    cn: cn,
                    mobile: mobile,
                    mail: mail
                },
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    if (result.code == '0') {
                        $('#display_cn').text(cn);
                        $('#display_mobile').text(mobile);
                        $('#display_mail').text(mail);

                        $('#user_info_update').hide();
                        $('#user_info_display').show();
                    } else {
                        $('#update_user_info').text(result.desc);
                    }
                },
                error: function () {
                    alert('some error');
                }
            });
        } else {
            $('#update_user_info').text('信息不完整');
        }
    });
});