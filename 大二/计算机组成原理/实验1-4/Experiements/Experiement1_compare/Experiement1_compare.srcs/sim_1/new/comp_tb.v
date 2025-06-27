`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 14:53:06
// Design Name: 
// Module Name: comp_tb
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


module comp_tb(
    );
    reg CLK,RST;
    reg [1:0] A,B;
    wire AEQB,AGTB,ALTB;
    comp comp2022217587(.CLK(CLK),.RST(RST),.A(A),.B(B),.AGTB(AGTB),.ALTB(ALTB),
    .AEQB(AEQB));
    initial begin
        CLK <=0;
        RST <=0;
        A <=2'b00;
        B <=2'b00;
        #10
        RST <=1;
        #10
        A <=2'b01;
        #10
        B <=2'b01;
        #10
        B <=2'b11;
        #10
        A <=2'b11;
    end
    always #10 CLK =~CLK;

endmodule
