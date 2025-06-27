`timescale 1ns / 1ps

module tb_MULTU_4bit ();
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

    // 数码管扫描时钟设定
    reg clk0 = 0;
    always begin
        #(XH / 2) clk0 <= ~clk0;
    end
    wire seg_clk;
    CLK_DIV u_CLK_DIV (
        .clk0(clk0),
        .clk (seg_clk)
    );

    // 将 a, b, z 转换为 8421BCD 码
    wire [ 7:0] a_bcd;
    wire [ 7:0] b_bcd;
    wire [15:0] z_bcd;
    Bit4_to_8421 u_Bit4_to_8421_1 (
        .a  (a),
        .bcd(a_bcd)
    );
    Bit4_to_8421 u_Bit4_to_8421_2 (
        .a  (b),
        .bcd(b_bcd)
    );
    Bit4_to_8421 u_Bit4_to_8421_3 (
        .a  (z[7:4]),
        .bcd(z_bcd[15:8])
    );
    Bit4_to_8421 u_Bit4_to_8421_4 (
        .a  (z[3:0]),
        .bcd(z_bcd[7:0])
    );

    // 显示被乘数、乘数和乘积
    wire [7:0] an;
    wire [7:0] seg_output_0;
    wire [7:0] seg_output_1;
    ShowEightSeg7 u_ShowEightSeg7 (
        .clk     (seg_clk),
        .seg0    (a_bcd[7:4]),
        .seg1    (a_bcd[3:0]),
        .seg2    (b_bcd[7:4]),
        .seg3    (b_bcd[3:0]),
        .seg4    (z_bcd[15:12]),
        .seg5    (z_bcd[11:8]),
        .seg6    (z_bcd[7:4]),
        .seg7    (z_bcd[3:0]),
        .an      (an),
        .segout_0(seg_output_0),
        .segout_1(seg_output_1)
    );

    // 初始化
    initial begin
        reset = 1;
        a <= 4'b0100;
        b <= 4'b0101;
        #XH;
        a <= 4'b0000;
        b <= 4'b0000;
        #XH;
        a <= 4'b0011;
        b <= 4'b0001;
        #XH;
        a <= 4'b1111;
        b <= 4'b1111;
        #XH;
        a <= 4'b0010;
        b <= 4'b1010;
        #XH;
        a <= 4'b1010;
        b <= 4'b0010;
        #XH;
        a <= 4'b0100;
        b <= 4'b0010;
        #XH;
        a <= 4'b1110;
        b <= 4'b0001;
        #XH;
    end
endmodule
