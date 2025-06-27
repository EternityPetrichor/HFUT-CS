`timescale 1ns / 1ps

module led(
    input clk, sw,
    output reg [3:0] data // 输出定义为寄存器
);
    reg clk1s = 0; // 初始化clk1s
    parameter max = 10; // 定义max为10，以便快速测试
    reg [1:0] state = 2'b00; // 初始状态为00
    reg [30:0] n = 0; // 初始化计数器n

    initial begin
        clk1s =0;
        n = 0;
        state = 2'b00;
        data = 4'b000;
    end

    always @(posedge clk) begin
        if (n >= max) begin // 使用 >= 以确保在n等于max时触发
            clk1s <= ~clk1s; // 翻转clk1s
            n <= 0; // 重置n
        end else n <= n + 1; // 否则，增加n
    end

    always @(posedge clk1s) begin
        case (state) 
            2'b00: begin
                state <= 2'b01;
                data <= sw ? 4'b1000 : 4'b0111;
            end
            2'b01: begin
                state <= 2'b10;
                data <= sw ? 4'b0100 : 4'b1011;
            end
            2'b10: begin
                state <= 2'b11;
                data <= sw ? 4'b0010 : 4'b1101;
            end
            2'b11: begin
                state <= 2'b00;
                data <= sw ? 4'b0001 : 4'b1110;
            end
        endcase
    end
endmodule
