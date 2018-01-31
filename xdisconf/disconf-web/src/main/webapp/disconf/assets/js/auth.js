(function ($) {

    getSession();

    var userId = getUrlParam('userId');
    var username = getUrlParam('username');

    //
    // 获取权限信息
    //
    $.ajax({
        type: "GET",
        url: "/api/account/authlist",
        data: {
            userId: userId
        }
    }).done(
        function (data) {
            if (data.success === "true") {
                var envs = data.result.envs;
                var envInputs = '';
                $.each(envs, function (index, item) {
                    if (item.hashAuth) {
                        envInputs += '<input name="env" type="checkbox" value="' + item.envId + '" checked="checked" /> ' + item.envName + ' 环境<br/>';
                    } else {
                        envInputs += '<input name="env" type="checkbox" value="' + item.envId + '" /> ' + item.envName + ' 环境<br/>';
                    }
                });

                var apps = data.result.apps;
                var appInputs = '';
                $.each(apps, function (index, item) {
                    if (item.hashAuth) {
                        appInputs += '<input name="app" type="checkbox" value="' + item.appId + '" checked="checked" /> ' + item.appName + '<br/>';
                    } else {
                        appInputs += '<input name="app" type="checkbox" value="' + item.appId + '" /> ' + item.appName + '<br/>';
                    }
                });

                var roles = data.result.roles;
                var roleSelect = '<select id="roleId">';
                $.each(roles, function (index, item) {
                    if (item.hashRole) {
                        roleSelect += '<option value="' + item.roleId + '" selected = selected">' + item.roleName + '</option>';
                    } else {
                        roleSelect += '<option value="' + item.roleId + '">' + item.roleName + '</option>';
                    }
                });
                roleSelect += '</select>';

                $('#user_info').html('用户: ' + username);
                $('#auth_envs').html(envInputs);
                $('#auth_apps').html(appInputs);
                $('#auth_roles').html(roleSelect);

                $('#userId').val(userId);
            }
        }
    );

    $('#submitAuth').click(function() {
        var userId = $('#userId').val();

        var envs = '';
        $("input:checkbox[name='env']:checked").each(function() {
            envs += $(this).val() + ',';
        });
        if (envs != '') {
            envs = envs.substring(0, envs.length-1);
        }

        var apps = '';
        $("input:checkbox[name='app']:checked").each(function() {
            apps += $(this).val() + ',';
        });
        if (apps != '') {
            apps = apps.substring(0, apps.length-1);
        }

        var roleId = $('#roleId').val();

        $.ajax({
            type: "GET",
            url: "/api/account/setauth",
            data: {
                userId: userId,
                envs: envs,
                apps: apps,
                roleId: roleId
            }
        }).done(
            function (data) {
                if (data.success === "true") {
                    $('#setauth_info').text('权限分配成功');
                }
            }
        );
    });

})(jQuery);

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  // 匹配目标参数
    if (r != null) return decodeURIComponent(r[2]); return null; // 返回参数值
}