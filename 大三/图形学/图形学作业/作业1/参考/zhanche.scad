// 战车尺寸参数  
body_length = 120; // 车体长度  
body_width = 60;   // 车体宽度  
body_height = 30;  // 车体高度  
wheel_radius = 10; // 车轮半径  
wheel_thickness = 5; // 车轮厚度  
wheel_clearance = 5; // 车轮与车身之间的间隙  
turret_radius = 10;  // 炮塔半径  
turret_height = 20;  // 炮塔高度  
cannon_length = 50;  // 炮管长度  
cannon_width = 3;    // 炮管宽度  
  
// 定义车轮  
module wheel() {  
    cylinder(h = wheel_thickness, r = wheel_radius, center = true);  
}  
  
// 定义炮塔  
module turret() {  
    union() {  
        cylinder(h = turret_height, r = turret_radius, center = true); // 炮塔主体  
        translate([0, 0, turret_height]) {  
            cylinder(h = cannon_length, r = cannon_width / 2, center = true); // 炮管  
        }  
    }  
}  
  
// 定义战车  
module tank() {  
    union() {  
        // 车体  
        cube([body_length, body_width, body_height], center = true);  
          
        // 车轮（假设战车有四个车轮）  
        // 前轮  
        translate([-(body_width/2 - wheel_radius), body_length/4, body_height/2 - wheel_clearance/2]) rotate([90, 0, 0]) wheel(); // 前左车轮  
        translate[(body_width/2 - wheel_radius), body_length/4, body_height/2 - wheel_clearance/2]) rotate([90, 0, 0]) wheel(); // 前右车轮  
        // 后轮  
        translate([-(body_width/2 - wheel_radius), -body_length/4, body_height/2 - wheel_clearance/2]) rotate([90, 0, 0]) wheel(); // 后左车轮  
        translate[(body_width/2 - wheel_radius), -body_length/4, body_height/2 - wheel_clearance/2]) rotate([90, 0, 0]) wheel(); // 后右车轮  
          
        // 炮塔（假设在车体中心）  
        translate([0, 0, body_height + turret_height/2 + 5]) rotate([0, 0, 45]) turret(); // 炮塔放置在车体上  
    }  
}  
  
// 渲染战车模型  
tank();