`timescale 1ns / 1ps

module Bit8_to_8421 (
    input      [ 7:0] a,
    output reg [15:0] bcd
);
    reg [3:0] temp1, temp2, temp3, temp4;
    always @(*) begin
        temp1 <= a / 1000;
        temp2 <= (a - temp1 * 1000) / 100;
        temp3 <= (a - temp1 * 1000 - temp2 * 100) / 10;
        temp4 <= a - temp1 * 1000 - temp2 * 100 - temp3 * 10;
        bcd   <= {temp1, temp2, temp3, temp4};
    end
endmodule
