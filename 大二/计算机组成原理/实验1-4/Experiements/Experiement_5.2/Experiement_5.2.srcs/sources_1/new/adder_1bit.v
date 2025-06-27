`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/08 22:11:47
// Design Name: 
// Module Name: adder_1bit
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

// 1位全加器
module adder_1bit(
    input a,
    input b,
    input ci,
    output s,
    output co
    );
    and m0(c1,a,b);
    and m1(c2,b,ci);
    and m2(c3,a,ci);
    xor m3(s1,a,b);
    xor m4(s,s1,ci);
    or m5(co,c1,c2,c3);
endmodule
