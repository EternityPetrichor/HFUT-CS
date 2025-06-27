`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/17 22:22:09
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

    reg clk;
    reg sw;
    wire [3:0] data;

    led led_587 (
        .clk(clk),
        .sw(sw),
        .data(data)
    );

    initial begin
        clk = 0; 
        sw = 0;  
        #5.87;  
        sw = 1; 
    end

    always #0.587 clk = ~clk; // 学号后三位

    initial begin
        $monitor("Time = %t, clk = %b, sw = %b, data = %b", $time, clk, sw, data);
    end

    initial begin
        #1000; // 运行仿真一段时间，确保分频器有足够的时钟周期工作
        $finish; 
    end

endmodule
