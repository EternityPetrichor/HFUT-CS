`timescale 1ns / 1ps

module MULTU_16bit (
    input         clk,    // 乘法器时钟信号
    input         reset,
    input  [15:0] a,      // 输入a(被乘数)
    input  [15:0] b,      // 输入b(乘数)
    output [31:0] z       // 乘积输出z
);
    reg [31:0] temp;
    reg [31:0] stored0;
    reg [31:0] stored1;
    reg [31:0] stored2;
    reg [31:0] stored3;
    reg [31:0] stored4;
    reg [31:0] stored5;
    reg [31:0] stored6;
    reg [31:0] stored7;
    reg [31:0] stored8;
    reg [31:0] stored9;
    reg [31:0] stored10;
    reg [31:0] stored11;
    reg [31:0] stored12;
    reg [31:0] stored13;
    reg [31:0] stored14;
    reg [31:0] stored15;

    reg [31:0] add0_1;
    reg [31:0] add2_3;
    reg [31:0] add4_5;
    reg [31:0] add6_7;
    reg [31:0] add8_9;
    reg [31:0] add10_11;
    reg [31:0] add12_13;
    reg [31:0] add14_15;

    reg [31:0] add0t1_2t3;
    reg [31:0] add4t5_6t7;
    reg [31:0] add8t9_10t11;
    reg [31:0] add12t13_14t15;
    reg [31:0] add0t3_4t7;
    reg [31:0] add8t11_12t15;
    reg [31:0] add0t7_8t15;

    always @(posedge clk or negedge reset) begin
        if (~reset) begin  // reset 置零
            temp           <= 0;
            stored0        <= 0;
            stored1        <= 0;
            stored2        <= 0;
            stored3        <= 0;
            stored4        <= 0;
            stored5        <= 0;
            stored6        <= 0;
            stored7        <= 0;
            stored8        <= 0;
            stored9        <= 0;
            stored10       <= 0;
            stored11       <= 0;
            stored12       <= 0;
            stored13       <= 0;
            stored14       <= 0;
            stored15       <= 0;
            add0_1         <= 0;
            add2_3         <= 0;
            add4_5         <= 0;
            add6_7         <= 0;
            add8_9         <= 0;
            add10_11       <= 0;
            add12_13       <= 0;
            add14_15       <= 0;
            add0t1_2t3     <= 0;
            add4t5_6t7     <= 0;
            add8t9_10t11   <= 0;
            add12t13_14t15 <= 0;

            add0t3_4t7     <= 0;
            add8t11_12t15  <= 0;
        end else begin  //通过字符拼接方式表示出中间相乘值,并相加
            stored0        <= b[0] ? {16'b0, a} : 16'b0;
            stored1        <= b[1] ? {15'b0, a, 1'b0} : 16'b0;
            stored2        <= b[2] ? {14'b0, a, 2'b0} : 16'b0;
            stored3        <= b[3] ? {13'b0, a, 3'b0} : 16'b0;
            stored4        <= b[4] ? {12'b0, a, 4'b0} : 16'b0;
            stored5        <= b[5] ? {11'b0, a, 5'b0} : 16'b0;
            stored6        <= b[6] ? {10'b0, a, 6'b0} : 16'b0;
            stored7        <= b[7] ? {9'b0, a, 7'b0} : 16'b0;
            stored8        <= b[8] ? {8'b0, a, 8'b0} : 16'b0;
            stored9        <= b[9] ? {7'b0, a, 9'b0} : 16'b0;
            stored10       <= b[10] ? {6'b0, a, 10'b0} : 16'b0;
            stored11       <= b[11] ? {5'b0, a, 11'b0} : 16'b0;
            stored12       <= b[12] ? {4'b0, a, 12'b0} : 16'b0;
            stored13       <= b[13] ? {3'b0, a, 13'b0} : 16'b0;
            stored14       <= b[14] ? {2'b0, a, 14'b0} : 16'b0;
            stored15       <= b[15] ? {1'b0, a, 15'b0} : 16'b0;
            add0_1         <= stored1 + stored0;
            add2_3         <= stored2 + stored3;
            add4_5         <= stored4 + stored5;
            add6_7         <= stored6 + stored7;
            add8_9         <= stored9 + stored8;
            add10_11       <= stored10 + stored11;
            add12_13       <= stored12 + stored13;
            add14_15       <= stored14 + stored15;
            add0t1_2t3     <= add0_1 + add2_3;
            add4t5_6t7     <= add4_5 + add6_7;
            add8t9_10t11   <= add8_9 + add10_11;
            add12t13_14t15 <= add12_13 + add14_15;
            add0t3_4t7     <= add0_1 + add2_3 + add4_5 + add6_7;
            add8t11_12t15  <= add8_9 + add10_11 + add12_13 + add14_15;
            temp           <= add0t3_4t7 + add8t11_12t15;
        end
    end
    assign z = temp;
endmodule
