`timescale 1ns / 1ps

module tb_MULTU_4bit;
    parameter XH = 2022217597;
    // Inputs
    reg        clk = 0;
    reg        reset = 0;
    reg  [3:0] a;
    reg  [3:0] b;
    // Outputs
    wire [7:0] z;
    // 模拟使用时钟信号
    always begin
        #(XH / 2) clk <= ~clk;
    end
    // 实例化 MULTU_4bit 模块
    MULTU_4bit u_MULTU_4bit (
        .clk  (clk),
        .reset(reset),
        .a    (a),
        .b    (b),
        .z    (z)
    );
    // 初始化
    initial begin
        reset = 1;
        #XH;
        a <= 4'b0011;
        b <= 4'b0001;
        #XH;
        a <= 4'b0100;
        b <= 4'b0100;
        #XH;
        a <= 4'b0000;
        b <= 4'b0000;
        #XH;
        a <= 4'b0010;
        b <= 4'b1000;
        #XH;
        a <= 4'b1111;
        b <= 4'b0001;
        #XH;
    end
endmodule
