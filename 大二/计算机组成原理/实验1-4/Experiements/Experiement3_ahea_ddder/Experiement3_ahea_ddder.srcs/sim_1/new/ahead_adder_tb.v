`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/17 16:36:52
// Design Name: 
// Module Name: ahead_adder_tb
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

module ahead_adder_tb;
    reg [3:0] A;
    reg [3:0] B;
    reg c_in;
    wire [3:0] F;
    wire c_out;
    ahead_adder ahead_adder_587 (
        .A(A), 
        .B(B), 
        .c_in(c_in), 
        .F(F), 
        .c_out(c_out)
    );

    initial begin
        A = 4'b0000;
        B = 4'b0000;
        c_in = 0;

        #587;   //学号后三位
        A = 4'b1010;
        B = 4'b1001;
        c_in = 1;

        #587;
        A = 4'b0010;
        B = 4'b1110;
        c_in = 0;

        #587;
        A = 4'b0110;
        B = 4'b1101;
        c_in = 1;

        #587;
        A = 4'b1010;
        B = 4'b1001;
        c_in = 0;

        #587;
        $finish; 
    end
    
    initial begin
        $monitor("Time=%t, A=%b, B=%b, c_in=%b --> F=%b, c_out=%b", $time, A, B, c_in, F, c_out);
    end
endmodule

