<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
        $("#user-show-table").jqGrid({
            url : "${pageContext.request.contextPath}/user/selectAll",
            datatype : "json",
            colNames : [ '编号', '名称', '昵称', '地址', '签名','头像',"性别",'状态','创建时间','操作'],
            colModel : [
                {name : 'id',hidden:true},
                {name : 'username'},
                {name : 'nickname'},
                {name : 'address'},
                {name : 'sign',hidden:true},
                {name : 'photo',edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/back/user/image/"+rows.photo+"'>";
                    }},
                {name : 'sex'},
                {name : 'status'},
                {name : 'createDate'},
                {name : 'operation',formatter:function (value,option,rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\">修改</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"del('"+rows.id+"')\" class='btn btn-danger'>删除</a>";
                    }}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            rowNum : 2,
            rowList : [ 2, 4, 6 ],
            pager : '#user-page',
            viewrecords : true,
            caption : "用户列表列表",
            editurl : "someurl.php"
        }).navGrid("#user-page", {edit : false,add : false,del : false,search:false});
    })

    function openModal(oper,id) {

        if("edit"==oper){
            var user = $("#user-show-table").jqGrid("getRowData",id);
            console.log(user);
            $("#user-id").val(user.id)
            $("#user-username").val(user.username);
            $("#user-nickname").val(user.nickname);
            $("#user-address").val(user.address);
            $("#user-sign").val(user.sign);
            $("#user-sex").val(user.sex);
            $("#user-status").append(

               " <option value='正常'>正常</option>"

                ).append(" <option value='冻结'>冻结</option>")


        }
        $("#user-modal").modal("show");
    }
    function regis() {
        $("#regis-id").val("")
        $("#regis-username").val("");
        $("#regis-nickname").val("");
        $("#regis-address").val("");
        $("#regis-sign").val("");
        $("#regis-sex").val("");
        $("#regis-status").append(

            " <option value='正常'>正常</option>"

        ).append(" <option value='冻结'>冻结</option>");
        $("#regis-code").val("");
        $("#regis-modal").modal("show");

    }
    function findCode() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/findCode",
            type:"post",

            datatype:"json",
            success:function () {
                $("#user-show-table").trigger("reloadGrid");


            }
        })

    }
    function del(id) {

        $.ajax({
            url:"${pageContext.request.contextPath}/user/del",
            type:"post",
            data:{id:id},
            datatype:"json",
            success:function () {
                $("#user-show-table").trigger("reloadGrid");


            }
        })
    }
    function add() {

        $.ajax({
            url:"${pageContext.request.contextPath}/user/add",
            type:"post",
            data:$("#regis-form").serialize(),
            datatype:"json",
            success:function () {
                $("#user-show-table").trigger("reloadGrid");


            }
        })
    }

    function edit() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/edit",
            type:"post",
            data:$("#user-form").serialize(),
            datatype:"json",
            success:function () {
                $("#user-show-table ").trigger("reloadGrid");


            }
        })
    }


function save() {
    $.ajax({
        url:"${pageContext.request.contextPath}/user/export",
        type:"post",
        datatype:"json",
        success:function () {



        }
    })
}



</script>



<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="#" onclick="save()">导出用户</a></li>
    <li role="presentation"><a href="#" onclick="regis()">用户注册</a></li>

</ul>
<table id="user-show-table"></table>
<div id="user-page" style="height: 40px"></div>


<div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用户操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="user-form">
                    <input type="hidden" name="id" id="user-id">
                    <div class="form-group">
                        <label for="user-username" class="col-sm-2 control-label">用户名称</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" class="form-control" id="user-username" placeholder="请输入用户名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-nickname" class="col-sm-2 control-label">用户昵称</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" class="form-control" id="user-nickname" placeholder="请输入用户昵称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="user-address" class="col-sm-2 control-label">用户地址</label>
                        <div class="col-sm-10">
                            <input type="text" name="address" class="form-control" id="user-address" placeholder="请输入用户地址">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-sign" class="col-sm-2 control-label">用户签名</label>
                        <div class="col-sm-10">
                            <input type="text" name="sign" class="form-control" id="user-sign" placeholder="请输入用户签名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-sex" class="col-sm-2 control-label">用户性别</label>
                        <div class="col-sm-10">
                            <input type="text" name="sex" class="form-control" id="user-sex" placeholder="请输入用户性别">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-status" class="col-sm-2 control-label">用户状态</label>
                        <div class="col-sm-10">
                        <select id="user-status" style="width: 537px" name="status">

                                 </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="edit()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--注册时使用的模态框--%>
<div id="regis-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用户注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="regis-form">
                    <input type="hidden" name="id" id="regis-id">
                    <div class="form-group">
                        <label for="regis-username" class="col-sm-2 control-label">用户名称</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" class="form-control" id="regis-username" placeholder="请输入用户名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-nickname" class="col-sm-2 control-label">用户昵称</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" class="form-control" id="regis-nickname" placeholder="请输入用户昵称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-password" class="col-sm-2 control-label">用户昵称</label>
                        <div class="col-sm-10">
                            <input type="text" name="password" class="form-control" id="regis-password" placeholder="请输入用户密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-address" class="col-sm-2 control-label">用户地址</label>
                        <div class="col-sm-10">
                            <input type="text" name="address" class="form-control" id="regis-address" placeholder="请输入用户地址">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-sign" class="col-sm-2 control-label">用户签名</label>
                        <div class="col-sm-10">
                            <input type="text" name="sign" class="form-control" id="regis-sign" placeholder="请输入用户签名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-sex" class="col-sm-2 control-label">用户性别</label>
                        <div class="col-sm-10">
                            <input type="text" name="sex" class="form-control" id="regis-sex" placeholder="请输入用户性别">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-status" class="col-sm-2 control-label">用户状态</label>
                        <div class="col-sm-10">
                            <select id="regis-status" style="width: 537px" name="status">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="regis-code" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-6">
                            <input type="text" name="code" class="form-control" id="regis-code" placeholder="请输入验证码">
                        </div>
                        <div class="col-sm-4">
                            <h5 style="color: red" onclick="findCode()">获取验证码</h5>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="add()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->