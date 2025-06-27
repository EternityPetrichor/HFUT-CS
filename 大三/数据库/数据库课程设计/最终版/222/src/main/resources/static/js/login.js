/**
 * 登录
 */
var form;
$(function(){
     layui.use(['form' ,'layer'], function() {
         form = layui.form;
         var layer = layui.layer;
         form.on("submit(login)",function () {
             login();
             return false;
         });
         var path=window.location.href;
         if(path.indexOf("kickout")>0){
             layer.alert("您的账号已在别处登录；若不是您本人操作，请立即修改密码！",function(){
                 window.location.href="/login";
             });
         }
         //监听提交
         form.on('submit(userSubmit)', function(data){
             // TODO 校验
             formSubmit(data);
             return false;
         });
     });
 })

function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    var rememberMe = $("#rememberMe").val();
    $.post("/user/login",$("#useLogin").serialize(),function(data){
        if(data.code == 1){
            window.location.href=data.url;
        }else{
            layer.alert(data.message,function(){
                layer.closeAll();//关闭所有弹框
            });
        }
    });
}

//开通用户
function addUser(){
    openUser(null,"用户注册");
}
function openUser(data,title){
    var roleId = null;
    if(data==null || data==""){
        $("#id").val("");
    }else{
        $("#id").val(data.id);
        $("#username").val(data.sysUserName);
        $("#mobile").val(data.userPhone);
        roleId = data.roleId;
    }
    $.ajax({
        url:'/role/getRoles',
        dataType:'json',
        async: true,
        success:function(data){
            $.each(data,function(index,item){
                if(!roleId){
                    var option = new Option(item.roleName,item.id);
                }else {
                    var option = new Option(item.roleName,item.id);
                    // // 如果是之前的parentId则设置选中
                    if(item.id == roleId) {
                        option.setAttribute("selected",'true');
                    }
                }
                if(item.roleName!='系统管理员'){
                    $('#roleId').append(option);//往下拉菜单里添加元素
                }
                form.render('select'); //这个很重要
            })
        }
    });
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setUser'),
        end:function(){
            cleanUser();
        }
    });
}

//提交表单
function formSubmit(obj){
    $.ajax({
        type: "POST",
        data: $("#userForm").serialize(),
        url: "/user/setUser",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
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

function cleanUser(){
    $("#username1").val("");
    $("#mobile").val("");
    $("#password1").val("");
    $('#roleId').html("");
    $("#address").val("");
    $("#email").val("");
}
