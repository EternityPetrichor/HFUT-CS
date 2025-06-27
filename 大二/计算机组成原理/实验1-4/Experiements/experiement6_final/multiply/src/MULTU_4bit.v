`timescale 1ns / 1ps

module MULTU_4bit (
    input        clk,    // 乘法器时钟信号
    input        reset,
    input  [3:0] a,      // 输入a(被乘数)
    input  [3:0] b,      // 输入b(乘数)
    output [7:0] z       // 乘积输出z
);
    reg [7:0] temp;  // 申请寄存器
    reg [7:0] stored0;
    reg [7:0] stored1;
    reg [7:0] stored2;
    reg [7:0] stored3;
    reg [7:0] add0_1;
    reg [7:0] add2_3;
    always @(posedge clk or negedge reset) begin
        if (~reset) begin  // reset 置零
            temp    <= 0;
            stored0 <= 0;
            stored1 <= 0;
            stored2 <= 0;
            stored3 <= 0;
            add0_1  <= 0;
            add2_3  <= 0;
        end else begin  // 通过字符拼接方式表示出中间相乘值,并相加
            stored0 <= b[0] ? {4'b0, a} : 8'b0;
            stored1 <= b[1] ? {3'b0, a, 1'b0} : 8'b0;
            stored2 <= b[2] ? {2'b0, a, 2'b0} : 8'b0;
            stored3 <= b[3] ? {1'b0, a, 3'b0} : 8'b0;
            add0_1  <= stored1 + stored0;
            add2_3  <= stored2 + stored3;
            temp    <= add0_1 + add2_3;
        end
    end
    assign z = temp;
endmodule
