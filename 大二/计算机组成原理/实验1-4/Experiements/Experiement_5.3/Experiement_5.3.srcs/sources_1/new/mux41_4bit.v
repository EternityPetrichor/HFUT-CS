`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 14:48:37
// Design Name: 
// Module Name: mux41_4bit
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


module mux41_4bit(
    input [3:0] i0, i1, i2, i3,
    input [1:0] s,
    output reg [3:0] o
    );
    always @* begin
        case(s)
            2'b00: o = i0;
            2'b01: o = i1;
            2'b10: o = i2;
            2'b11: o = i3;
        endcase
    end
endmodule
