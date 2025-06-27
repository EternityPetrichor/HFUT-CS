`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/15 22:59:02
// Design Name: 
// Module Name: encoder4_2_tb
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


module encoder4_2_tb(

    );
reg [3:0] d;
wire [1:0] q;
encoder4_2 encoder(
    .d(d),
    .q(q)
);
initial begin
    d <= 4'b0111;
    #10
    d <= 4'b1011;
    #10
    d <= 4'b1101;
    #10
    d <= 4'b1110;
end

endmodule
