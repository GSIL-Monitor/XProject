$(document).ready(function() {

    $('#username').on('focus', function() {
        $('#first_step_info').text('');
    });

    $('#answerone').on('focus', function() {
        $('#second_step_info').text('');
    });
    $('#answertwo').on('focus', function() {
        $('#second_step_info').text('');
    });

    $('#newpassword').on('focus', function() {
        $('#third_step_info').text('');
    });
    $('#repeatnewpassword').on('focus', function() {
        $('#third_step_info').text('');
    });

    $('#first_step_btn').on('click', function() {
        var username = $.trim($('#username').val()); // 用户名
        $.ajax({
            type: 'POST',
            url: 'getPasswordQuestion',
            data: {
                username: username
            },
            beforeSend: function() {
                if (username) {
                    return true;
                } else {
                    $('#username').val(username);
                    $('#first_step_info').text("用户名不能为空！");
                    return false;
                }
            },
            success: function(result) {
                if (result.code == '0') {
                    $('#first_step').hide();

                    var data = result.data;

                    $('#securityId').val(data.securityId);
                    if (data.questiononePrompt) {
                        $('#question_one').text(data.questionone + '( 提示: ' + data.questiononePrompt + " )");
                    } else {
                        $('#question_one').text(data.questionone);
                    }
                    if (data.questiontwoPrompt) {
                        $('#question_two').text(data.questiontwo + '( 提示: ' + data.questiontwoPrompt + " )");
                    } else {
                        $('#question_two').text(data.questiontwo);
                    }

                    $('#second_step').show();
                } else {
                    $('#first_step_info').text(result.desc);
                }
            },
            error: function() {
                $('#first_step_info').text("some error");
            }
        });
    });

    $('#second_step_btn').on('click', function() {
        var securityId = $.trim($('#securityId').val()); // 安全ID
        var answerone = $.trim($('#answerone').val()); // 问题 1 答案
        var answertwo = $.trim($('#answertwo').val()); // 问题 2 答案

        $.ajax({
            type: 'POST',
            url: 'validateAnswer',
            data: {
                securityId: securityId,
                answerone: answerone,
                answertwo: answertwo
            },
            beforeSend: function() {
                if (securityId && answerone && answertwo) {
                    return true;
                } else {
                    $('#answerone').val(answerone);
                    $('#answertwo').val(answertwo);
                    $('#second_step_info').text("请回答密保问题");
                    return false;
                }
            },
            success: function(result) {
                if (result.code == '0') {
                    $('#second_step').hide();
                    $('#userId').val(securityId);
                    $('#third_step').show();
                } else {
                    $('#second_step_info').text(result.desc);
                }
            },
            error: function() {
                $('#second_step_info').text("some error");
            }
        });
    });

    $('#password_reset_btn').on('click', function() {
        var userId = $.trim($('#userId').val()); // 用户ID
        var newpassword = $.trim($('#newpassword').val()); // 新密码
        var repeatnewpassword = $.trim($('#repeatnewpassword').val()); // 确认新密码

        $.ajax({
            type: 'POST',
            url: 'resetPassword',
            data: {
                userId: userId,
                password: newpassword
            },
            beforeSend: function() {
                if (userId && newpassword && repeatnewpassword) {
                    if (newpassword != repeatnewpassword) {
                        $('#third_step_info').text("密码不一致");
                        return false;
                    }

                    return true;
                } else {
                    $('#third_step_info').text("信息不完整");
                    return false;
                }
            },
            success: function(result) {
                if (result.code == '0') {
                    $('#third_step').hide();
                    var data = result.data;
                    $('#resetpasswd_to_login').html('<a href="tologin?username=' + data.username + '"><i class="icon-hand-right"></i> 点击登录</a>')
                    $('#reset_password_success').show();
                } else {
                    $('#third_step_info').text(result.desc);
                }
            },
            error: function() {
                $('#third_step_info').text("some error");
            }
        });
    });

});