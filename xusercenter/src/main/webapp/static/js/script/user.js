$(document).ready(function() {
    $('#user_info_update_btn').on('click', function() {
        $('#user_info_display').hide();
        $('#user_info_update').show();
    });
    $('#user_info_save_btn').on('click', function() {
        var id = $.trim($('#userId').val()); // 用户ID
        var name = $.trim($('#name').val()); // 姓名
        var phone = $.trim($('#phone').val()); // 手机
        var email = $.trim($('#email').val()); // 邮箱
        $.ajax({
            type: 'POST',
            url: 'updateUserInfo',
            data: {
                id: id,
                name: name,
                phone: phone,
                email: email
            },
            beforeSend: function() {
                return true;
            },
            success: function(result) {
                if (result.code == '0') {
                    window.location.href = "touser";
                } else {
                    alert('some error');
                }
            },
            error: function() {
                alert('some error');
            }
        });
    });
});