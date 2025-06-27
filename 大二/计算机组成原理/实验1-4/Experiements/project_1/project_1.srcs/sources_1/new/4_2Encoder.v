`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/14 18:10:38
// Design Name: 
// Module Name: 4_2Encoder
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

module	encoder4_2(q,d); 
input[3:0] 	d;
output[1:0]	q;
reg[1:0]  q;
always@(d) begin 
case(d)
4'b0111:	q<=2'b11;
4'b1011:	q<=2'b10;
4'b1101:	q<=2'b01;
4'b1110:	q<=2'b00;
default:	q<=2'bzz; 
endcase
end endmodule

//module encoder4_2(
//    input [3:0] d,
//    output reg [1:0] q
//);
//always @(d) begin
//    case(d)
//        4'b0111: q <= 2'b11;
//        4'b1011: q <= 2'b10;
//        4'b1101: q <= 2'b01;
//        4'b1110: q <= 2'b00;
//        default: q <= 2'b00;
//    endcase
//end

//endmodule
