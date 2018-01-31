var global_questiononeId = '';
var global_questiontwoId = '';

$(document).ready(function() {
    $.ajax({
        type: 'POST',
        url: 'getSecurity',
        data: {},
        beforeSend: function() {
            return true;
        },
        success: function(result) {
            if (result.code == '0') {
                var security = result.data.security;

                if (security.phone) {
                    $('#bind_phone').text(security.phone);
                } else {
                    $('#bind_phone').text('无');
                }
                if (security.email) {
                    $('#bind_email').text(security.email);
                } else {
                    $('#bind_email').text('无');
                }

                if (security.questiononeId) {
                    global_questiononeId = security.questiononeId;
                }
                if (security.questiontwoId) {
                    global_questiontwoId = security.questiontwoId;
                }

                $('#answerone').val(security.answerone); // 问题1答案
                $('#questiononePrompt').val(security.questiononePrompt); // 问题1答案提示
                $('#answertwo').val(security.answertwo); // 问题2答案
                $('#questiontwoPrompt').val(security.questiontwoPrompt); // 问题2答案提示
            } else {
                alert('some error');
            }
        },
        error: function() {
            alert('some error');
        }
    });

    $('#security_menu > li').on('click', function() {
        var name = $(this).attr('name');
        if (name == 'acount_info_menu') {
            $('#security_set_password_question').hide();
            $('#security_account_info').show();
        } else if (name == 'set_password_question_menu') {


            $('#set_password_question_info').text('');
            $.ajax({
                type: 'POST',
                url: 'getQuestions',
                data: {},
                beforeSend: function() {
                    return true;
                },
                success: function(result) {
                    if (result.code == '0') {
                        var list = result.data.list;

                        if (global_questiononeId && global_questiontwoId) {
                            var oneoptions = '';
                            var twooptions = '';
                            for (var i = 0; i < list.length; i++) {
                                if (global_questiononeId == list[i].id) {
                                    oneoptions += '<option value ="' + list[i].id + '" selected>' + list[i].content + '</option>';
                                    twooptions += '<option value ="' + list[i].id + '">' + list[i].content + '</option>';
                                } else {
                                    oneoptions += '<option value ="' + list[i].id + '">' + list[i].content + '</option>';
                                    if (global_questiontwoId == list[i].id) {
                                        twooptions += '<option value ="' + list[i].id + '" selected>' + list[i].content + '</option>';
                                    } else {
                                        twooptions += '<option value ="' + list[i].id + '">' + list[i].content + '</option>';
                                    }
                                }
                            }
                            $('#questiononeId').html(oneoptions);
                            $('#questiontwoId').html(twooptions);

                            $('#set_password_question_info').css({"margin-left": "10px", "color": "#24773e"}).text('已设置');
                        } else {
                            var options = '<option value ="0" selected>----- 选择问题 -----</option>';
                            for (var i = 0; i < list.length; i++) {
                                options += '<option value ="' + list[i].id + '">' + list[i].content + '</option>';
                            }
                            $('#questiononeId').html(options);
                            $('#questiontwoId').html(options);
                        }
                        $('#security_account_info').hide();
                        $('#security_set_password_question').show();
                    } else {
                        alert('some error');
                    }
                },
                error: function() {
                    alert('some error');
                }
            });
        }
        $('#security_menu > li.active').removeClass('active');
        $(this).addClass('active');
    });

    $('#set_password_question_btn').on('click', function() {
        var id = $.trim($('#uidNumber').val()); // 用户ID
        var questiononeId = $.trim($('#questiononeId').val()); // 问题1ID
        var answerone = $.trim($('#answerone').val()); // 问题1答案
        var questiononePrompt = $.trim($('#questiononePrompt').val()); // 问题1答案提示
        var questiontwoId = $.trim($('#questiontwoId').val()); // 问题2ID
        var answertwo = $.trim($('#answertwo').val()); // 问题2答案
        var questiontwoPrompt = $.trim($('#questiontwoPrompt').val()); // 问题2答案提示

        $.ajax({
            type: 'POST',
            url: 'setQuestion',
            data: {
                id: id,
                questiononeId: questiononeId,
                answerone: answerone,
                questiononePrompt: questiononePrompt,
                questiontwoId: questiontwoId,
                answertwo: answertwo,
                questiontwoPrompt: questiontwoPrompt
            },
            beforeSend: function() {
                if (id && questiononeId && answerone && questiontwoId && answertwo) {
                    if (questiononeId == 0 || questiontwoId == 0) {
                        $('#set_password_question_info').css({"margin-left": "10px", "color": "#ec3030"}).text("问题 1 和问题 2 不能为空");
                        return false;
                    }
                    if (questiononeId == questiontwoId) {
                        $('#set_password_question_info').css({"margin-left": "10px", "color": "#ec3030"}).text("问题 1 和问题 2 不能相同");
                        return false;
                    }
                    return true;
                }
                $('#set_password_question_info').css({"margin-left": "10px", "color": "#ec3030"}).text("信息不完整");
                return false;
            },
            success: function(result) {
                if (result.code == '0') {
                    global_questiononeId = questiononeId;
                    global_questiontwoId = questiontwoId;
                    $('#set_password_question_info').css({"margin-left": "10px", "color": "#24773e"}).text('密保问题设置成功');
                } else {
                    $('#set_password_question_info').css({"margin-left": "10px", "color": "#ec3030"}).text(result.desc);
                }
            },
            error: function() {
                $('#set_password_question_info').css({"margin-left": "10px", "color": "#ec3030"}).text(result.desc);
            }
        });
    });
});