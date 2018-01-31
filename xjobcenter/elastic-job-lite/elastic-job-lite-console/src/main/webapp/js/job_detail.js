$(function() {
    renderSettings();
    bindSubmitJobSettingsForm();
    $('[href="#settings"]').click(function() {
        renderSettings();
    });
    $('[href="#servers"]').click(function() {
        renderServers();
    });
    $('[href="#execution_info"]').click(function() {
        renderExecution();
    });
    $('[href="#execution_log"]').click(function() {
   	    renderExecutionLog();
    });
    $('#is-success').change(function() {
        renderExecutionLog();
    });
    bindTriggerButtons();
    bindTriggerAllButtons();
    bindPauseButtons();
    bindPauseAllButtons();
    bindResumeButtons();
    bindResumeAllButton();
    bindShutdownButtons();
    bindRemoveButtons();
    bindDisableButtons();
    bindEnableButtons();
});

function renderSettings() {
    $.get("job/settings", {jobName : $("#job-name").text()}, function (data) {
        $("#jobType").attr("value", data.jobType);
        $("#jobClass").attr("value", data.jobClass);
        $("#shardingTotalCount").attr("value", data.shardingTotalCount);
        $("#cron").attr("value", data.cron);
        $("#shardingItemParameters").text(data.shardingItemParameters);
        $("#jobParameter").attr("value", data.jobParameter);
        $("#monitorExecution").attr("checked", data.monitorExecution);
        $("#failover").attr("checked", data.failover);
        $("#misfire").attr("checked", data.misfire);
        $("#streamingProcess").attr("checked", data.streamingProcess);
        $("#maxTimeDiffSeconds").attr("value", data.maxTimeDiffSeconds);
        $("#monitorPort").attr("value", data.monitorPort);
        $("#jobShardingStrategyClass").attr("value", data.jobShardingStrategyClass);
        $("#executorServiceHandler").attr("value", data.jobProperties["executor_service_handler"]);
        $("#jobExceptionHandler").attr("value", data.jobProperties["job_exception_handler"]);
        $("#description").text(data.description);
        if (!data.monitorExecution) {
            $("#execution_info_tab").addClass("disabled");
        }
        $("#scriptCommandLine").attr("value", data.scriptCommandLine);
    });
}

function bindSubmitJobSettingsForm() {
    $("#job-settings-form").submit(function(event) {
        event.preventDefault();
        var jobName = $("#job-name").text();
        var jobType = $("#jobType").val();
        var jobClass = $("#jobClass").val();
        var shardingTotalCount = $("#shardingTotalCount").val();
        var jobParameter = $("#jobParameter").val();
        var cron = $("#cron").val();
        var streamingProcess = $("#streamingProcess").prop("checked");
        var maxTimeDiffSeconds = $("#maxTimeDiffSeconds").val();
        var monitorPort = $("#monitorPort").val();
        var monitorExecution = $("#monitorExecution").prop("checked");
        var failover = $("#failover").prop("checked");
        var misfire = $("#misfire").prop("checked");
        var driver = $("#driver").val();
        var url = $("#url").val();
        var username = $("#username").val();
        var password = $("#password").val();
        var logLevel = $("#logLevel").val();
        var shardingItemParameters = $("#shardingItemParameters").val();
        var jobShardingStrategyClass = $("#jobShardingStrategyClass").val();
        var scriptCommandLine = $("#scriptCommandLine").val();
        var executorServiceHandler = $("#executorServiceHandler").val();
        var jobExceptionHandler = $("#jobExceptionHandler").val();
        var description = $("#description").val();
        var postJson = {jobName: jobName, jobType : jobType, jobClass : jobClass, shardingTotalCount: shardingTotalCount, jobParameter: jobParameter, cron: cron, streamingProcess: streamingProcess, maxTimeDiffSeconds: maxTimeDiffSeconds, monitorPort: monitorPort, monitorExecution: monitorExecution, failover: failover, misfire: misfire, shardingItemParameters: shardingItemParameters, jobShardingStrategyClass: jobShardingStrategyClass, jobProperties: {"executor_service_handler": executorServiceHandler, "job_exception_handler": jobExceptionHandler}, description: description, scriptCommandLine: scriptCommandLine};
        $.post("job/settings", postJson, function() {
            showSuccessDialog();
            if (monitorExecution) {
                $("#execution_info_tab").removeClass("disabled");
            } else {
                $("#execution_info_tab").addClass("disabled");
            }
        });
    });
}

function renderServers() {
    $.get("job/servers", {jobName : $("#job-name").text()}, function (data) {
        $("#servers tbody").empty();
        for (var i = 0;i < data.length;i++) {
            var status = data[i].status;
            var baseTd = "<td>" + data[i].ip + "</td><td>" + data[i].hostName + "</td><td>" + status + "</td><td>" + data[i].sharding + "</td>";
            var operationTd = "";
            var triggerButton = "<button operation='trigger' class='btn btn-success' ip='" + data[i].ip + "'>触发</button>";
            var resumeButton = "<button operation='resume' class='btn btn-success' ip='" + data[i].ip + "'>恢复</button>";
            var pauseButton = "<button operation='pause' class='btn btn-warning' ip='" + data[i].ip + "'" + ">暂停</button>";
            var shutdownButton = "<button operation='shutdown' class='btn btn-danger' ip='" + data[i].ip + "'>关闭</button>";
            var removeButton = "<button operation='remove' class='btn btn-danger' ip='" + data[i].ip + "'>删除</button>";
            var disableButton = "<button operation='disable' class='btn btn-danger' ip='" + data[i].ip + "'>失效</button>";
            var enableButton = "<button operation='enable' class='btn btn-success' ip='" + data[i].ip + "'>生效</button>";
            operationTd = triggerButton + "&nbsp;";
            if ("PAUSED" === status) {
                operationTd = operationTd + resumeButton + "&nbsp;";
            } else if ("DISABLED" !== status && "CRASHED" !== status && "SHUTDOWN" !== status) {
                operationTd = operationTd + pauseButton + "&nbsp;";
            }
            if ("SHUTDOWN" !== status) {
                operationTd = operationTd + shutdownButton + "&nbsp;";
            }
            if ("SHUTDOWN" === status || "CRASHED" === status) {
                operationTd = removeButton + "&nbsp;";
            }
            if("DISABLED" == status) {
                operationTd = operationTd + enableButton;
            } else if ("CRASHED" !== status && "SHUTDOWN" !== status){
                operationTd = operationTd + disableButton;
            }
            operationTd = "<td>" + operationTd + "</td>";
            var trClass = "";
            if ("READY" === status) {
                trClass = "info";
            } else if ("RUNNING" === status) {
                trClass = "success";
            } else if ("DISABLED" === status || "PAUSED" === status) {
                trClass = "warning";
            } else if ("CRASHED" === status || "SHUTDOWN" === status) {
                trClass = "danger";
            }
            $("#servers tbody").append("<tr class='" + trClass + "'>" + baseTd + operationTd + "</tr>");
        }
    });
}

function bindTriggerButtons() {
    $(document).on("click", "button[operation='trigger'][data-toggle!='modal']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/trigger", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindTriggerAllButtons() {
    $(document).on("click", "#trigger-all-jobs-btn", function() {
        var jobName = $("#job-name").text();
        $.post("job/triggerAll/name", {jobName : jobName}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindPauseButtons() {
    $(document).on("click", "button[operation='pause'][data-toggle!='modal']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/pause", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindPauseAllButtons() {
    $(document).on("click", "#pause-all-jobs-btn", function() {
        var jobName = $("#job-name").text();
        $.post("job/pauseAll/name", {jobName : jobName}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindResumeButtons() {
    $(document).on("click", "button[operation='resume']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/resume", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindResumeAllButton() {
    $(document).on("click", "#resume-all-jobs-btn", function() {
        var jobName = $("#job-name").text();
        $.post("job/resumeAll/name", {jobName : jobName}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function renderExecution() {
    $.get("job/execution", {jobName : $("#job-name").text()}, function (data) {
        $("#execution tbody").empty();
        for (var i = 0;i < data.length;i++) {
            var status = data[i].status;
            var failoverIp = null == data[i].failoverIp ? "-" : data[i].failoverIp;
            var lastBeginTime = null == data[i].lastBeginTime ? null : new Date(data[i].lastBeginTime).toLocaleString();
            var lastCompleteTime = null == data[i].lastCompleteTime ? null : new Date(data[i].lastCompleteTime).toLocaleString();
            var nextFireTime = null == data[i].nextFireTime ? null : new Date(data[i].nextFireTime).toLocaleString();
            var baseTd = "<td>" + data[i].item + "</td><td>" + status + "</td><td>" + failoverIp + "</td><td>" + lastBeginTime + "</td><td>" + lastCompleteTime + "</td><td>" + nextFireTime + "</td>";
            var trClass = "";
            if ("RUNNING" === status) {
                trClass = "success";
            } else if ("COMPLETED" === status) {
                trClass = "info";
            } else if ("PENDING" === status) {
                trClass = "warning";
            }
            $("#execution tbody").append("<tr class='" + trClass + "'>" + baseTd + "</tr>");
        }
    });
}

function renderExecutionLog() {
    $.get("job/log", {jobName : $("#job-name").text(), isSuccess: $("#is-success").val(), pageNo: 1}, function (data) {
        $("#log tbody").empty();
        var list = data.list;

        for (var i = 0; i < list.length; i++) {
        	var baseTd = '<td>' + list[i].taskId + '</td>';
        	baseTd += '<td>' + list[i].ip + '</td>';
        	baseTd += '<td>' + list[i].shardingItem + '</td>';
        	baseTd += '<td>' + list[i].executionSource + '</td>';
            baseTd += '<td>' + list[i].isSuccess + '</td>';
        	if (list[i].failureCause == null) {
        		baseTd += '<td></td>';
        	} else {
        		baseTd += '<td>' + list[i].failureCause + '</td>';
        	}
        	baseTd += '<td>' + list[i].startTime + '</td>';
        	if (list[i].completeTime == null) {
        		baseTd += '<td></td>';
        	} else {
        		baseTd += '<td>' + list[i].completeTime + '</td>';
        	}
        	baseTd += '<td>' + list[i].sec + '</td>';
        	
        	$("#log tbody").append("<tr class='info'>" + baseTd + "</tr>");
        }
        var page = data.page;
        html = '<div class="main-page">';
        html += '<div class="pull-left" style="padding-left: 5px;">';
        html += '<input type="hidden" id="log_pageNo" value="' + page.pageNo + '">';
        html += '<ul class="pull-right pagination pagination-sm">';
        html += '<li class="disabled"><span style="margin-right: 5px; color: #888;">[每页显示: ' + page.pageSize + ' 总记录: ' + page.totalRecord + ' 位置: ' + page.pageNo + '/' + page.totalPage + ']</span></li>';
        if (page.hasPre) {
            html += '<li><a href="javascript:listLogContent(1);">首页</a></li>';
            html += '<li><a href="javascript:listLogContent(' + page.pagePre + ');">上一页</a></li>';
        } else {
            html += '<li class="disabled"><a href="javascript:;">首页</a></li>';
            html += '<li class="disabled"><a href="javascript:;">上一页</a></li>';
        }
        var pages = page.pages;
        for (var j = 0; j < pages.length; j++) {
            if (pages[j] == page.pageNo) {
                html += '<li class="active"><a href="javascript:;">' + pages[j] + '</a></li>';
            } else {
                html += '<li><a href="javascript:listLogContent(' + pages[j] + ');">' + pages[j] + '</a></li>';
            }
        }
        if (page.hasNext) {
            html += '<li><a href="javascript:listLogContent(' + page.pageNext + ');">下一页</a></li>';
            html += '<li><a href="javascript:listLogContent(' + page.totalPage + ');">尾页</a></li>';
        } else {
            html += '<li class="disabled"><a href="javascript:;">下一页</a></li>';
            html += '<li class="disabled"><a href="javascript:;">尾页</a></li>';
        }
        html += '</ul>';
        html += '</div></div>';

        $('#log_sep_page').html(html);
    });
}

function listLogContent(pageNo) {
    $.get("job/log", {jobName : $("#job-name").text(), isSuccess: $("#is-success").val(), pageNo: pageNo}, function (data) {
        $("#log tbody").empty();
        var list = data.list;

        for (var i = 0; i < list.length; i++) {
            var baseTd = '<td>' + list[i].taskId + '</td>';
            baseTd += '<td>' + list[i].ip + '</td>';
            baseTd += '<td>' + list[i].shardingItem + '</td>';
            baseTd += '<td>' + list[i].executionSource + '</td>';
            baseTd += '<td>' + list[i].isSuccess + '</td>';
            if (list[i].failureCause == null) {
                baseTd += '<td></td>';
            } else {
                baseTd += '<td>' + list[i].failureCause + '</td>';
            }
            baseTd += '<td>' + list[i].startTime + '</td>';
            if (list[i].completeTime == null) {
                baseTd += '<td></td>';
            } else {
                baseTd += '<td>' + list[i].completeTime + '</td>';
            }
            baseTd += '<td>' + list[i].sec + '</td>';

            $("#log tbody").append("<tr class='info'>" + baseTd + "</tr>");
        }
        var page = data.page;
        html = '<div class="main-page">';
        html += '<div class="pull-left" style="padding-left: 5px;">';
        html += '<ul class="pull-right pagination pagination-sm">';
        html += '<li><span style="margin-right: 5px; color: #888;">[每页显示: ' + page.pageSize + ' 总记录: ' + page.totalRecord + ' 位置: ' + page.pageNo + '/' + page.totalPage + ']</span></li>';
        if (page.hasPre) {
            html += '<li><a href="javascript:listLogContent(1);">首页</a></li>';
            html += '<li><a href="javascript:listLogContent(' + page.pagePre + ');">上一页</a></li>';
        } else {
            html += '<li class="disabled"><a href="javascript:;">首页</a></li>';
            html += '<li class="disabled"><a href="javascript:;">上一页</a></li>';
        }
        var pages = page.pages;
        for (var j = 0; j < pages.length; j++) {
            if (pages[j] == page.pageNo) {
                html += '<li class="active"><a href="javascript:;">' + pages[j] + '</a></li>';
            } else {
                html += '<li><a href="javascript:listLogContent(' + pages[j] + ');">' + pages[j] + '</a></li>';
            }
        }
        if (page.hasNext) {
            html += '<li><a href="javascript:listLogContent(' + page.pageNext + ');">下一页</a></li>';
            html += '<li><a href="javascript:listLogContent(' + page.totalPage + ');">尾页</a></li>';
        } else {
            html += '<li class="disabled"><a href="javascript:;">下一页</a></li>';
            html += '<li class="disabled"><a href="javascript:;">尾页</a></li>';
        }
        html += '</ul>';
        html += '</div></div>';

        $('#log_sep_page').html(html);
    });
}

function bindShutdownButtons() {
    $(document).on("click", "button[operation='shutdown']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/shutdown", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindRemoveButtons() {
    $(document).on("click", "button[operation='remove']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/remove", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function (data) {
            if (data.length > 0) {
                showFailureDialog("remove-job-failure-dialog");
            } else {
                showSuccessDialog();
            }
            renderServers();
        });
    });
}

function bindDisableButtons() {
    $(document).on("click", "button[operation='disable']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/disable", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}

function bindEnableButtons() {
    $(document).on("click", "button[operation='enable']", function(event) {
        var jobName = $("#job-name").text();
        $.post("job/enable", {jobName : jobName, ip : $(event.currentTarget).attr("ip")}, function () {
            renderServers();
            showSuccessDialog();
        });
    });
}
