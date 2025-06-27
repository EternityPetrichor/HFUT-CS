layui.config({base:'/js/'}).extend({ echarts: 'echarts/echarts'}).use(['jquery', 'form', 'layer', 'laydate', 'table','layedit','upload','element', 'echarts', 'carousel'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    carousel = layui.carousel;
    echarts = layui.echarts;
    var form = layui.form;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var table = layui.table;
    var layedit = layui.layedit;
    var upload = layui.upload;

    var chartZhu = echarts.init(document.getElementById('EchartZhu'));
    var EchartBing = echarts.init(document.getElementById('EchartBing'));
    var EchartLine = echarts.init(document.getElementById('EchartLine'));
    var EchartPfsBing = echarts.init(document.getElementById('EchartPfsBing'));
    //指定图表配置项和数据
    //获取用户数
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/count/getAllUser",
        success: function (data) {
                $("#allUser").html(data.allnum+'人');
                $("#newUser").html(data.newnum+'位');
                $("#newproductnum").html(data.newproductnum);
                $("#allproductnum").html(data.allproductnum);
                $("#newordernum").html(data.newordernum);
                $("#allordernum").html(data.allordernum);
                $("#newmoneynum").html(data.newmoneynum);
                $("#allmoneynum").html(data.allmoneynum);
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });

    //获取销量和销售额
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/count/getSaleOrder",
        success: function (data) {debugger
           var allprice=data.allprice;
           var orders=data.orders;
           var pname=data.pname;
            var optionchart = {
                title: {
                    text: '家具销量与销售额分析图'
                },
                tooltip: {},
                legend: {
                    data: ['销量']
                },
                xAxis: {
                    data: pname
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name: '销量',
                    type: 'bar', //柱状
                    data: orders,
                    itemStyle: {
                        normal: { //柱子颜色
                            color: 'red'
                        }
                    },
                },{
                    name:'销售额',
                    type:'bar',
                    data:allprice,
                    itemStyle:{
                        normal:{
                            color:'blue'
                        }
                    }
                }]
            };
            chartZhu.setOption(optionchart, true);
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
//饼
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/count/getSaleBing",
        success: function (data) {
            var pname = data.pname;
            var order = data.order;
            var optionchartBing = {
                title: {
                    text: '家具订单',
                    subtext: '家具订单占比图', //副标题
                    x: 'center' //标题居中
                },
                tooltip: {
                    // trigger: 'item' //悬浮显示对比
                },
                legend: {
                    orient: 'vertical', //类型垂直,默认水平
                    left: 'left', //类型区分在左 默认居中
                    data: pname
                },
                series: [{
                    type: 'pie', //饼状
                    radius: '60%', //圆的大小
                    center: ['50%', '50%'], //居中
                    data: order
                }]
            };
            EchartBing.setOption(optionchartBing, true);
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });

    //PFS饼
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/count/getPfsBing",
        success: function (data) {
            var pname = data.pname;
            var order = data.order;
            var optionchartBing = {
                title: {
                    text: '销售员交易情况分析',
                    subtext: '销售员交易情况占比图', //副标题
                    x: 'center' //标题居中
                },
                tooltip: {
                    // trigger: 'item' //悬浮显示对比
                },
                legend: {
                    orient: 'vertical', //类型垂直,默认水平
                    left: 'left', //类型区分在左 默认居中
                    data: pname
                },
                series: [{
                    type: 'pie', //饼状
                    radius: '60%', //圆的大小
                    center: ['50%', '50%'], //居中
                    data: order
                }]
            };
            EchartPfsBing.setOption(optionchartBing, true);
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
    //获取用户数
    $.ajax({
        type: "POST",
        data: $("#orderForm").serialize(),
        url: "/count/getSaleLine",
        success: function (data) {
            var ordertime = data.ordertime;
            var allprice = data.allprice;
            var optionchartLine = {
                title: {
                    text: '家具销售额趋势分析'
                },
                tooltip: {},
                legend: { //顶部显示 与series中的数据类型的name一致
                    data: ['销售额']
                },
                xAxis: {
                    // type: 'category',
                    // boundaryGap: false, //从起点开始
                    data: ordertime
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name: '销量',
                    type: 'line', //线性
                    data: allprice,
                }]
            };

            EchartLine.setOption(optionchartLine, true);
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
});