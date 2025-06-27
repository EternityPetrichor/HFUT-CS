`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/18 10:32:23
// Design Name: 
// Module Name: led_tb
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module led_tb;

    reg CLK;
    wire [1:0] led_out;

    // 被测试的模块实例化
    led led_587 (
        .CLK(CLK),
        .led_out(led_out)
    );

    // 生成时钟信号
    initial begin
        CLK = 0;
        forever #5.87 CLK = ~CLK;  // 使用学号后三位作为时钟周期的一半
    end

    initial begin
        $monitor("Time = %t, CLK = %b, led_out = %b", $time, CLK, led_out);

        #1000000000;  
        $finish;
    end

endmodule
