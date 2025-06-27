`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/08 22:56:44
// Design Name: 
// Module Name: addsub_4bit_tb
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


module addsub_4bit_t();
    //Input
    reg [3:0] A;
    reg [3:0] B;
    reg ctrl;

    //Output
    wire [3:0] s;
    wire co;

    //例化：
    addsub_4bit ab4(
        .a(A),
        .b(B),
        .ctrl(ctrl),
        .s(s),
        .co(co)
    );
    initial begin
        A = 4'b0000;
        B = 4'b0000;
        ctrl = 0;
    end
    //ctrl为0为加法 ctrl为0时表示减法
    always begin
        //延迟时间是学号后三位 加法测试
        #58.7 A = 4'b0011; B = 4'b0010; ctrl = 0;
        //减法测试
        #58.7 A = 4'b1100; B = 4'b0001; ctrl = 1;
        //进位测试
        #58.7 A = 4'b1111; B = 4'b0001; ctrl = 0;
        //借位测试
        #58.7 A = 4'b0010; B = 4'b0100; ctrl = 1;
    end


endmodule
