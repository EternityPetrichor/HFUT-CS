`timescale 1ns / 1ps

module x7seg_top (
    input clk,
    input s,
    output [6:0] a_to_g_0,
    output [6:0] a_to_g_1,
    output [7:0] an
);

wire rst;
wire [31:0] x;
assign rst = s;

wire clk_100Hz;//分频模块

fenping fenping_Hz(
    .clk_in(clk),
    .clk_100Hz(clk_100Hz)//分频到1c
);

openmips_min_sopc openmips_min_sopc(
//.clk(clk_100Hz),    //烤板子用
.clk(clk),   //仿真用
.rst(s),
.inst(x)
);

x7seg x1(
    x[15:0],
    clk,
    rst,
    a_to_g_0,
    an[3:0]
);

x7seg x2(
    x[31:16],
    clk,
    rst,
    a_to_g_1,
    an[7:4]
);
    
endmodule
