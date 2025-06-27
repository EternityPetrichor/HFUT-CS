`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/17 15:06:57
// Design Name: 
// Module Name: FSM
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


module fsm (
    input Clock, 
    input Reset, 
    input A, 
    output reg F, 
    output reg G
);
    reg [3:0] state;
    parameter Idle  = 4'b1000,
              Start = 4'b0100,
              Stop  = 4'b0010,
              Clear = 4'b0001;
    
    always @(posedge Clock or negedge Reset) begin
        if (!Reset) begin
            state <= Idle;
            F <= 0;
            G <= 0;
        end
        else begin
            case (state)
                Idle: begin
                    if (A) begin
                        state <= Start;
                        G <= 0;
                    end
                    else begin
                        state <= Idle;
                    end
                end
                
                Start: begin
                    if (!A) begin
                        state <= Stop;
                    end
                    else begin
                        state <= Start;
                    end
                end
                
                Stop: begin
                    if (A) begin
                        state <= Clear;
                        F <= 1;
                    end
                    else begin
                        state <= Stop;
                    end
                end
                
                Clear: begin
                    if (!A) begin
                        state <= Idle;
                        F <= 0;
                        G <= 1;
                    end
                    else begin
                        state <= Clear;
                    end
                end
                
                default: begin
                    state <= Idle;
                end
            endcase
        end
    end
endmodule
