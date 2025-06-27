`timescale 1ns / 1ps

module Bit4_to_8421 (
    input      [3:0] a,
    output reg [7:0] bcd
);
    reg [3:0] temp1, temp2;
    always @(*) begin
        temp1 <= a / 10;
        temp2 <= a - temp1 * 10;
        bcd   <= {temp1, temp2};
    end
endmodule
