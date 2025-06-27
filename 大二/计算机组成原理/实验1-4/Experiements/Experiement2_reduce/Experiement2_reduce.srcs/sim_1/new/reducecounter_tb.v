`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 18:48:04
// Design Name: 
// Module Name: reducecounter_tb
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


module reducecounter_tb(
    );
reg clk;
reg reset;
wire [3:0] Q;
reducecounter reducecount587(.clk(clk), .Q(Q), .reset(reset));
initial begin
    clk =0;
    reset = 0;
    #20 reset =1;
end
always #58.7 clk =~clk;
endmodule
