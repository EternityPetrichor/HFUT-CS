`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/16 14:36:12
// Design Name: 
// Module Name: compare
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


module comp (
    CLK, RST, A, B, AGTB, ALTB, AEQB
);
input CLK, RST;
input [1:0] A, B;
output reg AGTB, ALTB, AEQB;

always @(posedge CLK or negedge RST) begin
    if (!RST) begin
        AGTB <= 0;
        AEQB <= 0;
        ALTB <= 0;
    end else begin
        if (A > B) begin
            AGTB <= 1;
            AEQB <= 0;
            ALTB <= 0;
        end else if (A == B) begin
            AGTB <= 0;
            AEQB <= 1;
            ALTB <= 0;
        end else begin // A < B
            AGTB <= 0;
            AEQB <= 0;
            ALTB <= 1;
        end
    end
end
endmodule

