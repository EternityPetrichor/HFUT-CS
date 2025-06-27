`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 14:36:21
// Design Name: 
// Module Name: and_4bit
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


module and_4bit(
    input [3:0] a,
    input [3:0] b,
    output reg[3:0] c
    );
    always @* begin
        c = a & b;  
    end
endmodule
