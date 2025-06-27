`timescale 1ns / 1ps
//控制8个数码管动态显示的模块。segn[3:0]是8421BCD码。
module ShowEightSeg7 (
    input            clk,
    input      [3:0] seg0,
    input      [3:0] seg1,
    input      [3:0] seg2,
    input      [3:0] seg3,
    input      [3:0] seg4,
    input      [3:0] seg5,
    input      [3:0] seg6,
    input      [3:0] seg7,
    output reg [7:0] an,
    output reg [7:0] segout_1,
    output reg [7:0] segout_2
);
    reg [2:0] state = 0;
    reg [3:0] bcd = 0;
    always @(posedge clk) begin
        state <= state + 1;
    end
    always @(*) begin
        case (state)
            0: begin
                an  = 8'b00000001;
                bcd = seg0;
            end
            1: begin
                an  = 8'b00000010;
                bcd = seg1;
            end
            2: begin
                an  = 8'b00000100;
                bcd = seg2;
            end
            3: begin
                an  = 8'b00001000;
                bcd = seg3;
            end
            4: begin
                an  = 8'b00010000;
                bcd = seg4;
            end
            5: begin
                an  = 8'b00100000;
                bcd = seg5;
            end
            6: begin
                an  = 8'b01000000;
                bcd = seg6;
            end
            7: begin
                an  = 8'b10000000;
                bcd = seg7;
            end
            default: begin
                an  = 8'b0000000;
                bcd = 4'b0000;
            end
        endcase
    end
    always @(*) begin
        if (state < 4) begin
            case (bcd)
                4'h0:    segout_1 = 8'hfc;
                4'h1:    segout_1 = 8'h60;
                4'h2:    segout_1 = 8'hda;
                4'h3:    segout_1 = 8'hf2;
                4'h4:    segout_1 = 8'h66;
                4'h5:    segout_1 = 8'hb6;
                4'h6:    segout_1 = 8'hbe;
                4'h7:    segout_1 = 8'he0;
                4'h8:    segout_1 = 8'hfe;
                4'h9:    segout_1 = 8'hf6;
                default: segout_1 = 8'h00;
            endcase
            segout_2 = 8'h00;
        end else begin
            case (bcd)
                4'h0:    segout_2 = 8'hfc;
                4'h1:    segout_2 = 8'h60;
                4'h2:    segout_2 = 8'hda;
                4'h3:    segout_2 = 8'hf2;
                4'h4:    segout_2 = 8'h66;
                4'h5:    segout_2 = 8'hb6;
                4'h6:    segout_2 = 8'hbe;
                4'h7:    segout_2 = 8'he0;
                4'h8:    segout_2 = 8'hfe;
                4'h9:    segout_2 = 8'hf6;
                default: segout_2 = 8'h00;
            endcase
            segout_1 = 8'h00;
        end
    end
endmodule
