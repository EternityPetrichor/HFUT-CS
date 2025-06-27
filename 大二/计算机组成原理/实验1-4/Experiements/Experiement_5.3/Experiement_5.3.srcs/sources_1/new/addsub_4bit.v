`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 14:51:56
// Design Name: 
// Module Name: addsub_4bit
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


module addsub_4bit(
    input [3:0] a, b,
    input ctrl,
    output [3:0] s,
    output co
    );
    // 例化四个一位加减法器
    addsub_1bit as0(.a(a[0]),.b(b[0]),.ci(ctrl),.ctrl(ctrl),.s(s[0]),.co(co0));
    addsub_1bit as1(.a(a[1]),.b(b[1]),.ci(co0),.ctrl(ctrl),.s(s[1]),.co(co1));
    addsub_1bit as2(.a(a[2]),.b(b[2]),.ci(co1),.ctrl(ctrl),.s(s[2]),.co(co2));
    addsub_1bit as3(.a(a[3]),.b(b[3]),.ci(co2),.ctrl(ctrl),.s(s[3]),.co(co3));
    // 输出
    not not0(nc,ctrl);
    not not1(nco, co3);
    and and0(a0,co3,nc);
    and and1(a1, nco, ctrl);
    or or0(co, a0,a1);
endmodule

