/**
 * 用户管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;
debugger
        tableIns=table.render({
            elem: '#ordertList',
            url:'/order/getOrderList',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type:'numbers'}
                ,{field:'id', title:'订单编号',align:'center'}
                ,{field:'username', title:'客户名',align:'center'}
                ,{field:'pname', title:'产品名称',align:'center'}
                ,{field:'num', title:'订单数',align:'center'}
                ,{field:'allprice', title:'总价',align:'center'}
                ,{field:'status', title: '审核状态',align:'center'}
                ,{field:'createTime', title:'审核通过时间',align:'center'}
                ,{field:'orderTime', title: '下单时间',align:'center'}
                ,{title:'操作',align:'center', toolbar:'#optBar'}
            ]],
            done: function(res, curr, count){
                debugger
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='status']").children().each(function(){
                    if($(this).text()=='1'){
                        $(this).text("审核通过")
                    }else if($(this).text()=='2'){
                        $(this).text("审核不通过")
                    }else{
                        $(this).text("未审核")
                    }
                });
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(orderTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                delOrder(data,data.id);
            } else if(obj.event === 'edit'){
                //编辑
                openOrder(data,"订单审核");
            }else if(obj.event === 'recover'){
                //恢复
                recoverUser(data,data.id);
            }
        });

        //监听提交
        form.on('submit(orderSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });
    });

    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
});

//提交表单
function formSubmit(obj){
    var aa = $("#orderForm").serialize();
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/order/appOrder",
        success: function (data) {debugger
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.msg);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

//订单审核
function openOrder(){
    openOrder(null,"订单审核");
}
function openOrder(data,title){
    if(data==null || data==""){
        $("#id").val("");
    }else{
        $("#id").val(data.id);
        $("#pid").val(data.pid);
        $("#num").val(data.num);
    }
    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setOrder'),
        end:function(){
            cleanUser();
        }
    });
}

function delOrder(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除'+id+'订单吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/order/delOrder",{"id":id},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}
//恢复
function recoverUser(obj,id) {
    if(null!=id){
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/user/updateUserStatus",{"id":id,"status":1},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}

function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function cleanUser(){
    $("#username").val("");
    $("#mobile").val("");
    $("#password").val("");
    $('#roleId').html("");
}