`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 17:41:31
// Design Name: 
// Module Name: addcount
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


module addcount(
    clk,
    reset,
    Q
);
input clk;
input reset;
output Q;
reg [3:0] Q;

always @(posedge clk or negedge reset) begin 
    if (!reset) Q <= 4'b0000; 
    else Q <= Q + 1; 
end

endmodule
