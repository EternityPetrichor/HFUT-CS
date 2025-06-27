`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 16:56:08
// Design Name: 
// Module Name: add_tb
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

module add_tb();
    reg a, b, cin; 
    wire sum, cout; 
    add add587(.cout(cout), .sum(sum), .a(a), .b(b), .cin(cin));

    initial begin
        a = 0;
        b = 0;
        cin = 0;
        repeat (10) begin
            #58.7 {a, b, cin} = {a, b, cin} + 5;//我的学号是2022217587 设置时间为58.7毫秒 
        end
    end
endmodule

