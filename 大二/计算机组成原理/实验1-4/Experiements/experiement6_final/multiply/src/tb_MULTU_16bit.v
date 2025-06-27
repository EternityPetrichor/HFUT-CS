`timescale 1ns / 1ps

module tb_MULTU_16bit ();
    parameter XH = 2022217597;
    // Inputs
    reg         clk = 0;
    reg         reset = 0;
    reg  [15:0] a;
    reg  [15:0] b;
    // Outputs
    wire [31:0] z;
    wire [ 7:0] an;
    wire        clk1;
    // 初始化
    initial begin
        reset = 1;
        a <= 16'b0000000000000000;
        b <= 16'b0000000000000000;
        #XH;
        a <= 16'b0000000000000001;
        b <= 16'b1111111100000000;
        #XH;
        a <= 16'b0000000010110011;
        b <= 16'b0000000000000001;
        #XH;
        a <= 16'b0000000000000001;
        b <= 16'b0000000010101010;
        #XH;
        a <= 16'b0000000011111111;
        b <= 16'b0000000011111111;
        #XH;
        a <= 16'b0000000010101010;
        b <= 16'b0000000010000000;
        #XH;
        a <= 16'b0000000010110100;
        b <= 16'b0000000011010000;
        #XH;
        a <= 16'b0000000010001110;
        b <= 16'b0000000011100000;
        #XH;
    end
    // 时钟信号
    always begin
        #(XH / 2) clk = ~clk;
    end
    // 实例化 MULTU_16bit 模块
    MULTU_16bit u_MULTU_16bit (
        .clk  (clk),
        .reset(reset),
        .a    (a),
        .b    (b),
        .z    (z)
    );
endmodule
