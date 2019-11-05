<%@page pageEncoding="UTF-8" %>
<script>
    //页面加载完
    //页面加载完
    $(function () {
        $("#album-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/album/findAll',
            datatype : "json",
            height : 300,
            colNames : [ '编号', '名称', '封面', '集数', '评分', '作者', '播音员', '简介', '创建日期' ],
            colModel : [
                {name : 'id',hidden:true},
                {name : 'title',editable:true, align: 'center'},
                {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/back/album/image/"+rows.cover+"'>";
                    }},
                {name : 'count', align: 'center'},
                {name : 'score',editable:true, align: 'center'},
                {name : 'starId',editable:true, align: 'center',edittype:"select",
                    editoptions:{dataUrl:"${pageContext.request.contextPath}/star/findAll"}
                    ,formatter:function (value,option,rows) {
                        return rows.star.nickname;
                    }
                    },
                {name : 'broadcast',editable:true, align: 'center'},
                {name : 'brief',editable:true, align: 'center'},
                {name : 'createDate', align: 'center'}

            ],
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 2,
            rowList : [ 3, 5, 10],
            pager : '#album-page',
            viewrecords : true,
            subGrid : true,
            caption : "所有专辑列表",
            editurl : "${pageContext.request.contextPath}/album/edit",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/chapter/selectAll?albumId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '文件名', '大小', '时长','播音员', '创建时间','在线播放' ],
                        colModel : [
                            {name : "id",hidden:true},
                            {name : "name", align: 'center',editable:true,edittype:"file"},
                            {name : "size", align: 'center'},
                            {name : "duration", align: 'center'},
                            {name : "bradcast", align: 'center',editable:true},
                            {name : "createDate", align: 'center'},
                            {name : "opertion", align: 'center',width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/back/album/music/"+rows.name+"' >\n" +
                                        "</audio>";}}

                        ],
                        styleUI:"Bootstrap",
                        autowidth:true,
                        rowNum : 2,
                        rowList : [ 2, 5, 10],
                        viewrecords : true,
                        pager : pager_id,

                        height : '100%',
                        editurl : "${pageContext.request.contextPath}/chapter/edit?albumId=" + id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{},{
                    //控制添加
                        closeAfterAdd:true,
                        afterSubmit:function (data){
                            console.log(data);
                            var status = data.responseJSON.status;
                            var id = data.responseJSON.message;
                            if(status){
                                $.ajaxFileUpload({

                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    type:"post",
                                    fileElementId:"name",
                                    data:{id:id},
                                    success:function (response) {
                                        //自动刷新jqgrid表格
                                        $("#album-show-table").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "123";
                        }
                    });
            }

        }).jqGrid('navGrid', '#album-page', {
            add : true,
            edit : false,
            del : false,
            search:false
        },{},{
            closeAfterAdd:true,
            afterSubmit:function (data){
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#album-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

          

     });

    });
    function gettypes(){

//动态生成select内容

        var str="";

        $.ajax({

            type:"post",

            async:false,

            url:"${pageContext.request.contextPath}/star/findAll",

            success:function(data){

                if (data != null) {

                    var jsonobj=eval(data);

                    var length=jsonobj.length;

                    for(var i=0;i<length;i++){

                        if(i!=length-1){

                            str+=jsonobj[i].nickname+":"+jsonobj[i].nickname+";";

                        }else{

                            str+=jsonobj[i].nickname+":"+jsonobj[i].nickname;

                        }

                    }



                }



            }

        });

        return str;

    }






</script>


<h3>展示所有的专辑</h3>
</div>
<table id="album-show-table"></table>
<div id="album-page" style="height: 40px;"></div>