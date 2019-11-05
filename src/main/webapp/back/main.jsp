<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--当前页面更好支持移动端-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <link rel="stylesheet" href="${app}/statics/boot/css/bootstrap.css">

    <link rel="stylesheet" href="${app}/statics/jqgrid/css/trirand/ui.jqgrid.css">

    <link rel="stylesheet" href="${app}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">

    <script src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>

    <script src="${app}/statics/boot/js/bootstrap.min.js"></script>
    <%-- 引入jqgrid核心样式--%>
    <script src="${app}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%-- 引入jqgrid国际模式--%>
    <script src="${app}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${app}/statics/jqgrid/js/ajaxfileupload.js"></script>

    <%--引入kindeditor插件--%>
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
<%--    引入echarts--%>
    <script src="${app}/echarts/echarts.min.js"></script>

    <script>

    </script>
</head>
<body>
<!--导航条-->
<div class="row">

    <nav class="navbar navbar-inverse" class="col-md-12">
        <div class="container-fluid">
            <!--导航标题-->
            <div class="navbar-header">
                <a class="navbar-brand" href="#">持明法州后台管理系统 <small>v1.0</small></a>
            </div>

            <!--导航条内容-->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">欢迎:<font color="aqua">${sessionScope.admin.username}</font></a></li>
                    <li><a href="${app}/admin/exit">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a></li>
                </ul>
            </div>
        </div>
    </nav>

</div>
<!--页面主体内容-->
<div class="container-fluid">
    <!--row-->
    <div class="row">

        <!--菜单-->
        <div class="col-sm-2">

            <!--手风琴控件-->
            <div class="panel-group" id="accordion" >

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="userPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists" aria-expanded="true" aria-controls="collapseOne">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/banner/show.jsp');" id="btn">所有轮播图</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--类别面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="categoryPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#categoryLists" aria-expanded="true" aria-controls="collapseOne">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="categoryLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/album/show.jsp');">专辑列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="bookPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookLists" aria-expanded="true" aria-controls="collapseOne">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="bookLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/article/show.jsp');">文章列表</a></li>

                            </ul>
                        </div>
                    </div>
                </div>


                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="orderPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#orderLists" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="orderLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/user/show.jsp');">用户列表</a></li>
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/user/echart.jsp');">注册趋势</a></li>



                            </ul>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="starPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#starLists" aria-expanded="true" aria-controls="collapseOne">
                                明星管理
                            </a>
                        </h4>
                    </div>
                    <div id="starLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/back/star/show.jsp');" >明星列表</a></li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--中心布局-->
        <div class="col-sm-10" id="centerLayout">

            <!--巨幕-->
            <div class="jumbotron" style="height: 0px">
                <h4 style="margin-top: -5px;color: #c12e2a">欢迎来到持明法州后台管理系统!</h4>
            </div>

            <!-- 轮播图-->

            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- 指示器 -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- 轮播图片及说明文字 -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <center><img src="${app}/img/main2.jpg" alt="图片1"></center>
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item">
                        <center><img style="height: 400px" src="${app}/img/main2.jpg" alt="图片2"></center>                        <div class="carousel-caption">
                    </div>
                    </div>
                </div>

                <!-- 控制按钮：左右切换 -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>