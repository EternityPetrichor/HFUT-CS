`timescale 1ns / 1ps

module top (
    input  [3:0] data1,
    input  [3:0] data2,
    output [7:0] an,
    output [7:0] mul,
    output [7:0] result
);
    parameter XH = 25000000;
    reg        reset = 0;
    reg        clk = 0;
    wire [7:0] z;
    // 乘法器模块
    MULTU_4bit u_MULTU_4bit (
        .clk  (clk),
        .reset(reset),
        .a    (data1),
        .b    (data2),
        .z    (z)
    );
    // 将 data1, data2, result 转换为 8421BCD 码
    wire [ 7:0] data1_bcd;
    wire [ 7:0] data2_bcd;
    wire [15:0] z_bcd;
    Bit4_to_8421 u_Bit4_to_8421_1 (
        .a  (data1),
        .bcd(data1_bcd)
    );
    Bit4_to_8421 u_Bit4_to_8421_2 (
        .a  (data2),
        .bcd(data2_bcd)
    );
    Bit8_to_8421 u_Bit8_to_8421 (
        .a  (z),
        .bcd(z_bcd)
    );
    // 数码管显示被乘数、乘数和结果
    ShowEightSeg7 u_ShowEightSeg7 (
        .clk     (clk),
        .seg0    (data1_bcd[7:4]),
        .seg1    (data1_bcd[3:0]),
        .seg2    (data2_bcd[7:4]),
        .seg3    (data2_bcd[3:0]),
        .seg4    (z_bcd[15:12]),
        .seg5    (z_bcd[11:8]),
        .seg6    (z_bcd[7:4]),
        .seg7    (z_bcd[3:0]),
        .an      (an),
        .segout_1(mul),
        .segout_2(result)
    );

    initial begin
        reset = 1;
    end
    always begin
        #(XH / 2) clk <= ~clk;
    end
endmodule
