(function ($) {

    getSession();

    //
    // 获取用户信息
    //
    $.ajax({
        type: "GET",
        url: "/api/account/list"
    }).done(
        function (data) {
            if (data.success === "true") {
                var result = data.page.result;
                var trs = '';
                $.each(result, function (index, item) {
                    if ('admin' != item.name) {
                        trs += '<tr><td>' + item.id + '</td>';
                        trs += '<td>' + item.name + '</td>';
                        trs += '<td>' + item.envList + '</td>';
                        trs += '<td>' + item.appList + '</td>';
                        trs += '<td>' + item.role + '</td>';
                        trs += '<td><a href="/disconf/auth.html?userId=' + item.id + '&username=' + item.name + '"><button>分配权限</button></a></td>';
                    }
                });
                $("#userBody").html(trs);
            }
        }
    );

})(jQuery);
