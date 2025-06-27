`timescale 1ns / 1ps
//数码管动态扫描所需要的时钟模块
module CLK_DIV (
    input      clk0,
    output reg clk
);
    parameter N = 32'd51200, WIDTH = 32 - 1;
    reg [WIDTH : 0] number = 0;
    always @(posedge clk) begin
        if (number == N - 1) begin
            number <= 0;
            clk <= ~clk;
        end else begin
            number <= number + 1;
        end
    end
endmodule
