$(document).ready(function() {
    // 用户退出确定
    $(document).on('click', '#logout_confirm_btn', function() {
        window.location.href="logout";
    });
    // 关闭弹出框重置
    $('#jobcenterModal').on('hidden.bs.modal', function () {
        $(this).removeData('bs.modal');
        $('div.modal-content').css({'left': 0, 'width': '600px'});
    });
});

// 用户退出确认页面
function logout() {
    var width = 400; // 弹出框宽度
    $('div.modal-content').css({'top': '100px', 'left': (600 - width)/2, 'width': width + 'px'});
    $("#usercenterModal").modal({
        backdrop: 'static',
        remote: 'tologoutcfm'
    });
}