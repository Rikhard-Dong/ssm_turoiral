<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Index</title>
    <link href="${pageContext.request.contextPath}/resource/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <h1>INFO</h1>
        </div>
        <div class="col-md-12">
            <hr>
        </div>
    </div>
    <%-- 信息提示 --%>
    <div class="row">
        <div class="col-md-10 col-md-offset-1" id="msg">

        </div>
    </div>
    <%-- 输入表单 --%>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <form class="form-inline" id="infoForm" style="display: inline">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" placeholder="输入用户名" name="username">
                </div>
                <div class="form-group">
                    <label for="content">Content</label>
                    <input type="text" class="form-control" id="content" placeholder="输入内容" name="content">
                </div>
            </form>
            <button id="submitBtn" class="btn btn-info">提交</button>
        </div>
    </div>
    <br>
    <%-- 内容显示 --%>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <table class="table table-hover" id="infos">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Content</th>
                    <th>CreateTime</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <%-- 分页信息 --%>
        <div class="col-md-10 col-md-offset-1" id="pagination">
        </div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/bootstrap.min.js"></script>

<script>
    /* 记录当前页数 */
    var pageNum = 1;

    /* 记录总页数 */
    var pages;
    var total;

    /* 数据填充到表格中 */
    $(function () {
        /* 默认加载第一页 */
        toPage(1);
    });
    <%-- 提交表单给服务器处理 --%>
    $('#submitBtn').click(function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/info/add',
            method: 'POST',
            data: $('#infoForm').serialize(),
            success: function (result) {
                console.log(result);
                showMsg('#msg', result);
                toPage(pages + 1);
            }
        });
        $('#infoForm')[0].reset();

    });

    /* 删除信息 */
    function delInfo(url, id) {
        $.ajax({
            url: url,
            method: "get",
            data: {
                id: id
            },
            success: function (result) {
                console.log(result);
                showMsg("#msg", result);
                toPage(pageNum);
            }
        })

    }

    /*
    * 绑定删除按钮删除事件
    * 不能直接使用$('.').click()方法来绑定点击事件
    * 因为.click方法是加载页面时绑定, 但是所有的数据包括删除按钮都是通过加载页面后
    * 通过ajax异步获取, 所以这样绑定不了, 要使用下面的方法
    * */
    $(document).on("click", ".infoDelBtn", function () {
        var url = '${pageContext.request.contextPath}/info/del';
        var id = $(this).attr("info-id");
        console.log("url is " + url + " id is" + id);
        delInfo(url, id);
    });

    /* 展示执行结果 */
    function showMsg(element, result) {
        var div = $('<div class="alert alert-dismissable" role="alert"></div>');
        var button = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
        var strong = $('<strong></strong>');
        var message = $('<span></span>').text(result.message);

        if (result.success) {
            div.addClass("alert-success");
            strong.text("Success: ");
        } else {
            div.addClass('alert-error');
            strong.text("Error: ");
        }
        div.append(button)
            .append(strong)
            .append(message);
        /* 先清空表单内容 */
        $(element).empty().append(div);
    }


    /*
    * 表格跳转到第几页
    */
    function toPage(page) {
        $.ajax({
            url: '${pageContext.request.contextPath}/info/list',
            method: 'GET',
            data: {
                'page': page
            },
            success: function (result) {
                console.log(result);
                if (result.success) {
                    pageNum = result.data.pageInfo.pageNum;
                    pages = result.data.pageInfo.pages;
                    tatol = result.data.pageInfo.total;
                    loadTable(result.data.pageInfo.list);
                    loadPagintion(result.data.pageInfo);
                }
            }
        })
    }

    /* 加载表格数据 */
    function loadTable(infos) {
        /* 首先清空原有数据 */
        $('#infos tbody').empty();
        $.each(infos, function (index, element) {
            var username = $('<td></td>').append(element.username);
            var content = $('<td></td>').append(element.content);
            var createTime = $('<td></td>').append(formatTime(element.createTime));
            var delBtn = $('<button></button>').addClass("btn btn-danger infoDelBtn")
                .append("删除");
            delBtn.attr("info-id", element.id);
            var delTh = $('<td></td>').append(delBtn);

            $('<tr></tr>').append(username)
                .append(content)
                .append(createTime)
                .append(delTh)
                .appendTo($('#infos tbody'));
        });
    }

    /* 加载分页信息 */
    function loadPagintion(pageInfo) {
        $('#pagination').empty();
        /*
          <ul class="pagination">
            <li>
              <a href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <li><a href="#">1</a></li>
            <li>
              <a href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        * */

        var ul = $("<ul></ul>").addClass("pagination");
        var head = $("<li></li>").append($("<a></a>").append("首页"));
        if (pageInfo.isFirstPage) {
            head.addClass("disabled");
        } else {
            head.click(function () {
                toPage(1);
            });
        }
        var pre = $("<li></li>").append($("<a></a>").append($('<span></span>').append("&laquo;")));
        if (pageInfo.hasPreviousPage === false) {
            pre.addClass("disabled");
        } else {
            pre.click(function () {
                toPage(pageInfo.prePage);
            })
        }
        ul.append(head)
            .append(pre);

        $.each(pageInfo.navigatepageNums, function (index, element) {
            /* 获取到合理的分页 */
            // console.log(element);
            var li = $("<li></li>").append($("<a></a>").append(element));
            if (pageNum === element) {
                li.addClass("active");
            }
            li.click(function () {
                toPage(element);
            });
            ul.append(li);
        });


        var next = $("<li></li>").append($("<a></a>").append($('<span></span>').append("&raquo;")));
        if (pageInfo.hasNextPage === false) {
            next.addClass("disabled");
        } else {
            next.click(function () {
                toPage(pageInfo.nextPage);
            })
        }
        var tail = $('<li></li>').append($("<a></a>").append("末页"));
        if (pageInfo.isLastPage) {
            tail.addClass("disabled");
        } else {
            tail.click(function () {
                toPage(pages);
            })
        }
        ul.append(next)
            .append(tail)
            .appendTo($('#pagination'));


    }

    /* 格式化时间, 将毫秒数转换成时间格式 */
    function formatTime(time) {
        var date = new Date(time);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();

        var result = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return result;
    }

</script>
</body>
</html>
