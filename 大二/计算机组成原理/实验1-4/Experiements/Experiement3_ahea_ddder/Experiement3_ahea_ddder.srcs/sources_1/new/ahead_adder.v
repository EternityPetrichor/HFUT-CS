`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/17 16:35:43
// Design Name: 
// Module Name: ahead_adder
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


module ahead_adder(
    input [3:0] A,
    input [3:0] B,
    input c_in,
    output [3:0] F,
    output c_out
);

    wire [3:0] G; 
    wire [3:0] P; 


    wire [3:1] C;

    assign G = A & B;       
    assign P = A ^ B;  

    assign F[0] = P[0] ^ c_in;

    assign F[1] = P[1] ^ (G[0] | (P[0] & c_in));
    assign F[2] = P[2] ^ (G[1] | (P[1] & G[0]) | (P[2] & P[1] & c_in));
    assign F[3] = P[3] ^ (G[2] | (P[2] & G[1]) | (P[3] & P[2] & G[0]) | (P[3] & P[2] & P[1] & c_in));

    assign c_out = G[3] | (P[3] & G[2]) | (P[3] & P[2] & G[1]) | (P[3] & P[2] & P[1] & G[0]) | (P[3] & P[2] & P[1] & P[0] & c_in);

endmodule