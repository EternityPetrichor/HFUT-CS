`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 17:43:30
// Design Name: 
// Module Name: addcount_tb
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


module addcount_tb();
    reg clk;
    reg reset; 
    wire [3:0] Q;
    addcount addcount587(.clk(clk), .Q(Q), .reset(reset)); 

    initial begin
        clk = 0; 
        reset = 0; 
        #20 reset = 1; 

    end
    always #58.7 clk = ~clk;//学号后三位
endmodule