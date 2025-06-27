`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 14:53:11
// Design Name: 
// Module Name: addsub_4bitmux
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


module addsub_4bitmux(
    input [1:0] s,
    input [3:0] a, b,
    output [3:0]o,
    output co
    );
    // 声明数据线
    wire[3:0] st, c0, c1;
    wire cot;
    // 例化元件，并连接数据线
    addsub_4bit addsub4b(.a(a),.b(b),.ctrl(s[0]),.s(st),.co(cot));
    and_4bit and4b(.a(a),.b(b),.c(c0));
    or_4bit or4b(.a(a),.b(b),.c(c1));
    mux41_4bit mux41b4(.i0(st),.i1(st),.i2(c0),.i3(c1),.s(s),.o(o));
    mux41_1bit mux41(.i0(cot),.i1(cot),.i2(1'b0),.i3(1'b0),.s(s),.o(co));  
endmodule
