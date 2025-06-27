`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/17 18:10:18
// Design Name: 
// Module Name: MULTU
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


module MULTU(
    input clk,    //乘法器时钟信号
    input reset,
    input [ 15:0]  a,      //输入a(被乘数)
    input [15:0]  b,      //输入b(乘数)
    output [ 31:0]  z     //乘积输出z
    );
    reg [31:0] temp;    //申请寄存器
    reg [31:0] stored [15:0];
    reg [31:0] addi_i1 [7:0];
    reg [31:0] additi1_i2ti3[3:0];
    reg [31:0] add[0:1];  //最后一位加法寄存器
    integer i;
    always @(posedge clk or negedge reset) begin
        // 初始化
        if(reset) begin
            temp <= 0;
            for (i = 0;i < 16;i = i + 1) begin
                stored[i] = 0;
            end
            for (i = 0;i < 8;i = i + 1) begin
                addi_i1[i] = 0;
            end
            for (i = 0;i < 4;i = i + 1) begin
                additi1_i2ti3[i] = 0;
            end
        end
        // 计算过程
        else begin
            stored[0] = b[0] ? {16'b0, a} : 32'b0;
            stored[1] = b[1] ? {15'b0, a, 1'b0} : 32'b0;
            stored[2] = b[2] ? {14'b0, a, 2'b0} : 32'b0;
            stored[3] = b[3] ? {13'b0, a, 3'b0} : 32'b0;
            stored[4] = b[4] ? {12'b0, a, 4'b0} : 32'b0;
            stored[5] = b[5] ? {11'b0, a, 5'b0} : 32'b0;
            stored[6] = b[6] ? {10'b0, a, 6'b0} : 32'b0;
            stored[7] = b[7] ? {9'b0, a, 7'b0} : 32'b0;
            stored[8] = b[8] ? {8'b0, a, 8'b0} : 32'b0;
            stored[9] = b[9] ? {7'b0, a, 9'b0} : 32'b0;
            stored[10] = b[10] ? {6'b0, a, 10'b0} : 32'b0;
            stored[11] = b[11] ? {5'b0, a, 11'b0} : 32'b0;
            stored[12] = b[12] ? {4'b0, a, 12'b0} : 32'b0;
            stored[13] = b[13] ? {3'b0, a, 13'b0} : 32'b0;
            stored[14] = b[14] ? {2'b0, a, 14'b0} : 32'b0;
            stored[15] = b[15] ? {1'b0, a, 15'b0} : 32'b0;
            for(i = 0;i < 8;i = i + 1)begin
                addi_i1[i] = stored[2 * i] + stored[2 * i + 1];
            end
            for(i = 0;i < 4;i = i + 1) begin
                additi1_i2ti3[i] = addi_i1[2 * i] + addi_i1[2 * i + 1]; 
            end
            for(i = 0;i < 2;i = i + 1) begin
                add[i] = additi1_i2ti3[2 * i] + additi1_i2ti3[2 * i + 1];
            end
            temp = add[0] + add[1];
        end
    end
    assign z = temp;
endmodule

