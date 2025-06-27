/**
 * 用户管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;
        tableIns=table.render({
            elem: '#productList',
            url:'/product/getProductList',
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
                ,{field:'pname', title:'商品名称',align:'center'}
                ,{field:'price', title:'价格',align:'price'}
                ,{field:'stock', title:'库存数量',align:'stock'}
                ,{field:'pType', title:'产品类型',align:'pType'}
                ,{field:'description', title:'产品描述',align:'description'}
                ,{field:'picture', title: '图片',align:'picture',templet:'#imgTpl'}
                ,{field:'unit', title: '产地',align:'unit'}
                ,{title:'操作',align:'center', toolbar:'#optBar'}
            ]],
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='userStatus']").children().each(function(){
                    if($(this).text()=='1'){
                        $(this).text("有效")
                    }else if($(this).text()=='0'){
                        $(this).text("失效")
                    }
                });
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(productTable)', function(obj) {
                // var checkStatus = table.checkStatus(obj.config.id);
                var data = obj.data;
                switch (obj.event) {
                    case 'del':
                        delProduct(data, data.id, data.pname);
                        break;
                    case 'edit':
                        openProduct(data, "编辑");
                        break;
                    case 'setOrders':
                        openOrders(data, "登记出库");
                        break;
                    case 'recover':
                        recoverUser(data, data.id);
                        break;
                }
            });

        //监听提交
        form.on('submit(productSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });

        form.on('submit(ordersSubmit)', function(data){
            // TODO 校验
            ordersSubmit(data);
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
    debugger
    var aa = $("#productForm").serialize();
    $.ajax({
        type: "POST",
        data: $("#productForm").serialize(),
        url: "/product/setProduct",
        success: function (data) {
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

//订单
function ordersSubmit(obj){
    debugger
    var  aa = JSON.stringify($("#ordersForm").serializeObject());
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify($("#ordersForm").serializeObject()),
        url: "/order/addOrders",
        dataType:"json",
        success: function (data) {
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

//开通用户
function openProduct(){
    openProduct(null,"新增产品");
}
function openProduct(data,title){
    if(data==null || data==""){
        $("#id").val("");
        $('#demo1').attr('src', "");
    }else{
        $("#id").val(data.id);
        $("#pname1").val(data.pname);
        $("#price").val(data.price);
        $("#stock").val(data.stock);
        $("#pType").val(data.pType);
        $("#description").val(data.description);
        $("#picture").val(data.picture);
        $("#unit").val(data.unit);
        $('#demo1').attr('src', data.picture);
        productId = data.productId;
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
        content:$('#setProduct'),
        end:function(){
            cleanUser();
        }
    });
}
//下单
function openOrders(){
    openOrders(null,"添加定单");
}
function openOrders(data,title){
    var roleId = null;
    if(data==null || data==""){
        $("#id2").val("");
    }else{
        $("#id2").val(data.id);
        $("#pname2").val(data.pname);
        $("#price2").val(data.price);
        $("#stock2").val(data.stock);
        $("#pType2").val(data.pType);
        $("#description2").val(data.description);
        $("#picture2").val(data.picture);
        $("#unit2").val(data.unit);
        productId = data.productId;
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
        content:$('#setOrders'),
        end:function(){
            cleanUser();
        }
    });
}

function delProduct(obj,id,name) {
    var currentUser=$("#currentUser").html();
    if(null!=id){
        if(currentUser==id){
            layer.alert("对不起，您不能执行删除自己的操作！");
        }else{
            layer.confirm('您确定要删除'+name+'产品吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/product/delProduct",{"id":id},function(data){
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

$.prototype.serializeObject = function () {
    var a,o,h,i,e;
    a = this.serializeArray();
    o={};
    h=o.hasOwnProperty;
    for(i=0;i<a.length;i++){
        e=a[i];
        if(!h.call(o,e.name)){
            o[e.name]=e.value;
        }
    }
    return o;
}

layui.use(['upload', 'element', 'layer'], function(){
    var $ = layui.jquery
        ,upload = layui.upload
        ,element = layui.element
        ,layer = layui.layer;
//常规使用 - 普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        ,url: '/file/upload' //改成您自己的上传接口
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            $("#picture").val(res.url);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #ff2222;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
        //进度条
        ,progress: function(n, elem, e){
            element.progress('demo', n + '%'); //可配合 layui 进度条元素使用
            if(n == 100){
                layer.msg('上传完毕', {icon: 1});
            }
        }
    });
});

