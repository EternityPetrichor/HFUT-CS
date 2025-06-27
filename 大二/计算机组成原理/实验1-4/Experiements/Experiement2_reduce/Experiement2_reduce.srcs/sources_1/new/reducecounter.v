`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 18:44:27
// Design Name: 
// Module Name: reducecounter
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


module reducecounter(
    clk,
    reset,
    Q
    );
input clk;
input reset;
output Q;
reg [3:0] Q;
always @(posedge clk or negedge reset) begin 
    if (!reset) Q <= 4'b1111; 
    else Q <= Q - 1; 
end
endmodule
