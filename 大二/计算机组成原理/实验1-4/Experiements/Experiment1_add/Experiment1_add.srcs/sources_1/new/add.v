`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 16:52:25
// Design Name: 
// Module Name: add
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


module add(cout,sum,a,b,cin);
input a,b;
input cin;
output sum;
output cout;
reg sum;
reg cout;
always @(a or b or cin) begin
    {cout,sum}=a+b+cin;
end
endmodule
