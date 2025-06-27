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

    var optionchartZhe = {
        title: {
            text: '商品订单'
        },
        tooltip: {},
        legend: { //顶部显示 与series中的数据类型的name一致
            data: ['销量', '产量', '营业额', '单价']
        },
        xAxis: {
            // type: 'category',
            // boundaryGap: false, //从起点开始
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '销量',
            type: 'line', //线性
            data: [145, 230, 701, 734, 1090, 1130, 1120],
        }, {
            name: '产量',
            type: 'line', //线性
            data: [720, 832, 801, 834, 1190, 1230, 1220],
        }, {
            smooth: true, //曲线 默认折线
            name: '营业额',
            type: 'line', //线性
            data: [820, 932, 901, 934, 1290, 1330, 1320],
        }, {
            smooth: true, //曲线
            name: '单价',
            type: 'line', //线性
            data: [220, 332, 401, 534, 690, 730, 820],
        }]
    };

    var optionchartBing = {
        title: {
            text: '商品订单',
            subtext: '纯属虚构', //副标题
            x: 'center' //标题居中
        },
        tooltip: {
            // trigger: 'item' //悬浮显示对比
        },
        legend: {
            orient: 'vertical', //类型垂直,默认水平
            left: 'left', //类型区分在左 默认居中
            data: ['单价', '总价', '销量', '产量']
        },
        series: [{
            type: 'pie', //饼状
            radius: '60%', //圆的大小
            center: ['50%', '50%'], //居中
            data: [{
                value: 335,
                name: '单价'
            },
                {
                    value: 310,
                    name: '总价'
                },
                {
                    value: 234,
                    name: '销量'
                },
                {
                    value: 135,
                    name: '产量'
                }
            ]
        }]
    };

});