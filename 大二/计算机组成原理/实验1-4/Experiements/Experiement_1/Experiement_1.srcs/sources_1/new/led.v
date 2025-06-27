`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/14 18:01:48
// Design Name: 
// Module Name: led
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


module  led(  input  CLK   ,   output reg[1:0]   led_out     );
reg [32:0]   count=0;
parameter T1MS=50000000; always@(posedge CLK) begin
count<=count+1; if(count==T1MS) begin
count<=0; end
if(count<25000000) begin led_out<=2'b01; end
else begin
led_out<=2'b10; end
end 
endmodule

