`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 14:51:15
// Design Name: 
// Module Name: addsub_1bit
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


module addsub_1bit(
    input a,
    input b,
    input ctrl,
    input ci,
    output s,
    output co
    );
    xor x(xor0, b, ctrl);
    adder_1bit adder(.a(a), .b(xor0), .ci(ci), .s(s), .co(co));
endmodule

