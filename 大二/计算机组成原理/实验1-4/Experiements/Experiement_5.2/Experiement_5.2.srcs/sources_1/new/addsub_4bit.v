`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/08 22:40:14
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
    input [3:0] a,
    input [3:0] b,
    input ctrl,
    output [3:0] s,
    output co
    );
    //实现4个加减法器的例化
    addsub_1bit as0
    (.a(a[0]),.b(b[0]),.ctrl(ctrl),.ci(ctrl),.s(s[0]),.co(co0));
    addsub_1bit as1
    (.a(a[1]),.b(b[1]),.ctrl(ctrl),.ci(co0),.s(s[1]),.co(co1));
    addsub_1bit as2
    (.a(a[2]),.b(b[2]),.ctrl(ctrl),.ci(co1),.s(s[2]),.co(co2));
    addsub_1bit as3
    (.a(a[3]),.b(b[3]),.ctrl(ctrl),.ci(co2),.s(s[3]),.co(co3));
    //进位输出
    not not0(n1,ctrl);
    not not1(n2,co3);
    and and0(a0,co3,n1);
    and and1(a1,n2,ctrl);
    or or0(co,a0,a1);
endmodule


