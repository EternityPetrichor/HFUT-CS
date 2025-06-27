`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/09 15:13:30
// Design Name: 
// Module Name: addsub_4bitmux_tb
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


module addsub_4bitmux_tb();
    reg [1:0] s;
    reg [3:0] a; 
    reg [3:0] b;
    wire [3:0] o;
    wire co;
    addsub_4bitmux addsub4bmux(.s(s), .a(a), .b(b), .o(o), .co(co));
    initial begin
        #0
        s = 2'b00;
        a = 4'b0000;
        b = 4'b0000;
        #58.7 
        s = 2'b00;
        a = 9; 
        b = 4'b0110;
        #58.7
        s = 2'b01; 
        a = 4'b1010; 
        b = 4'b0110;
        #58.7
        s = 2'b10; 
        a = 4'b0101;
        b = 4'b0011;
        #58.7
        s = 2'b11; 
        a = 4'b0101; 
        b = 4'b0011;
        #10
        $finish;
    end
endmodule
