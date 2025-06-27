`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2023/12/29 12:26:19
// Design Name: 
// Module Name: fenping
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

//100M分频到100Hz

module fenping(
    input clk_in,
    output  reg clk_100Hz//分频到1c
);
    
    reg [31:0] time_count_100Hz=32'd0;
    always@(posedge clk_in)
        if(time_count_100Hz==32'd10000000)begin//100M分频到100Hz，计数1000000
            time_count_100Hz<=32'd0;
            clk_100Hz<=1;
            end
        else begin
            time_count_100Hz<=time_count_100Hz+32'd1;
            clk_100Hz<=0;
            end
endmodule
