// 定义三角柱模块  
module triangle_prism(base_width, height) {  
    // 三角形底面的三个二维顶点（只包含x和y坐标）  
    points2D = [  
        [-base_width/2, 0],  
        [base_width/2, 0],  
        [0, base_width * sqrt(3)/2]  
    ];  
      
    // 绘制并挤出三角形形成三角柱  
    linear_extrude(height = height) {  
        polygon(points = points2D); // 使用2D点集绘制三角形底面  
    }  
}
// 定义圆柱按键的模块  
module button_1() {  
    union() {  
        // 按键的底部，使用较大的圆柱体  
        cylinder(h = 6, r = 10, center = true);  
          
        
    }  
}  
//定义长方体按键的模块
module button_2() {  
    cube([20, 20, 6], center = true);
} 
//定义数字点模块
module num_point() {  
    union() {  
        // 按键的底部，使用较大的圆柱体  
        cylinder(h = 7, r = 1, center = true);  
          
        
    }  
}
//定义符号模块
module notation() {  
    cube([8, 3, 7], center = true);  
}

// 定义计算器托盘的模块  
module keyboard_tray() {  
    cube([100, 150, 10], center = true);  
} 
// 定义计算器的屏幕  
module screen() {  
    cube([80, 20, 6], center = true);  
}
  
// 定义计算器模块  
module calc() {  
    union() {  
        // 计算器托盘  
        translate([0, 0, -5]) keyboard_tray();  
        translate([0, 60, 1]) screen();
        // 按键布局
      //第一排   
        translate([-11, 35, 0]) button_1();
        translate([-11, 38, 0]) num_point();
        translate([-11, 35, 0]) notation();
       translate([-11, 32, 0]) num_point();
        
        translate([11, 35, 0]) button_1();
        translate([11, 35, 0]) {
            rotate(45,0,0)
            notation();
         }
        translate([11, 35, 0]) {
            rotate(-45,0,0)
            notation();
         }
        
        translate([32, 35, 0]) button_1();
        translate([32, 35, 0]) notation();
        translate([29, 35, 0]) {
           rotate(90,0,0)
           triangle_prism(base_width = 5, height = 3.5); 
          }
        
        //第二排
        translate([-32, 13, 0]) button_1();
        translate([-32, 17.5, 0]) num_point();
        translate([-30, 12, 0]) num_point();
        translate([-34, 12, 0]) num_point();
         translate([-30, 9, 0]) num_point();
        translate([-34, 9, 0]) num_point();
         translate([-30, 15, 0]) num_point();
        translate([-34, 15, 0]) num_point();
        translate([-11, 13, 0]) button_1();
        translate([-9, 18, 0]) num_point();
        translate([-13, 18, 0]) num_point();
        translate([-9, 15, 0]) num_point();
        translate([-13, 15, 0]) num_point();
        translate([-9, 12, 0]) num_point();
        translate([-13, 12, 0]) num_point();
        translate([-9, 9, 0]) num_point();
        translate([-13, 9, 0]) num_point();
         
        translate([11, 13, 0]) button_1();
        translate([11, 19, 0]) num_point();
        translate([9, 13, 0]) num_point();
        translate([13, 13, 0]) num_point();
        translate([9, 10, 0]) num_point();
        translate([13, 10, 0]) num_point();
        translate([9, 16, 0]) num_point();
        translate([13, 16, 0]) num_point();
        translate([9, 7, 0]) num_point();
        translate([13, 7, 0]) num_point();
        
        translate([32, 13, 0]) button_1();
        translate([32, 13, 0]) notation();
        
        //第三排
        translate([-32, -9, 0]) button_1();
        translate([-30, -7, 0]) num_point();
        translate([-34, -7, 0]) num_point();
        translate([-34, -11, 0]) num_point();
        translate([-30, -11, 0]) num_point();
        translate([-11, -9, 0]) button_1();
        translate([-11, -9, 0]) num_point();
        translate([-9, -7, 0]) num_point();
        translate([-9, -11, 0]) num_point();
        translate([-13, -7, 0]) num_point();
        translate([-13, -11, 0]) num_point();
        translate([11, -9, 0]) button_1();
        translate([9, -9, 0]) num_point();
        translate([13, -9, 0]) num_point();
        translate([9, -12, 0]) num_point();
        translate([13, -12, 0]) num_point();
        translate([9, -6, 0]) num_point();
        translate([13, -6, 0]) num_point();
        translate([32, -9, 0]) button_1();
        translate([32, -9, 0]) notation();
        translate([32, -9, 0]) {
            rotate(90,0,0)
            notation();
         }

        //第四排
        translate([-32, -31, 0]) button_1();
        translate([-32, -31, 0]) num_point();     
        translate([-11, -31, 0]) button_1();
        translate([-14, -31, 0]) num_point();
        translate([-8, -31, 0]) num_point();
        translate([11, -31, 0]) button_1();
        translate([7, -31, 0]) num_point();
        translate([11, -31, 0]) num_point();
        translate([15, -31, 0]) num_point();
        translate([32, -42, 0])button_2();
        translate([32, -40, 0])  notation();
        translate([32, -46, 0])  notation();
        translate([32, -31, 0]) button_1();   
        
        //第五排
        translate([-32, -53, 0])  button_1();
        translate([-22, -53, 0])  button_2();
        translate([-12, -53, 0])  button_1();   
        translate([11, -53, 0]) button_1();
        translate([11, -56, 0]) num_point(); 
        translate([32, -53, 0]) button_1();  
          
    }  
}  
  
// 渲染键盘模型  
calc();