`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/04/18 21:01:00
// Design Name: 
// Module Name: InputControl
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


module InputControl(
    input wire [7:0] sw, 
    output wire [3:0] A, 
    output wire [3:0] B 
    );
    assign A = sw[3:0];
    assign B = sw[7:4];
endmodule
