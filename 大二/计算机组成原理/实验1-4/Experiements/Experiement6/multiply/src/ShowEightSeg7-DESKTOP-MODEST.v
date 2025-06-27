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
    output reg [7:0] segout_0,
    output reg [7:0] segout_1
);
    reg [3:0] state;
    reg [3:0] bcd;
    reg       cs = 0;  // 片选
    always @(posedge clk) begin
        state <= state + 1;
        cs <= (state < 4'b0100) ? 0 : 1;
    end
    always @(*) begin
        case (state)
            4'b0000: begin
                an  = 8'b00000001;
                bcd = seg0;
            end
            4'b0001: begin
                an  = 8'b00000010;
                bcd = seg1;
            end
            4'b0010: begin
                an  = 8'b00000100;
                bcd = seg2;
            end
            4'b0011: begin
                an  = 8'b00001000;
                bcd = seg3;
            end
            4'b0100: begin
                an  = 8'b00010000;
                bcd = seg4;
            end
            4'b0101: begin
                an  = 8'b00100000;
                bcd = seg5;
            end
            4'b0110: begin
                an  = 8'b01000000;
                bcd = seg6;
            end
            4'b0111: begin
                an  = 8'b10000000;
                bcd = seg7;
            end
            default: begin
                an  = 8'b00000000;
                bcd = 4'b0000;
            end
        endcase
    end
    always @(*) begin
        if (cs == 0) begin
            case (bcd)
                4'h0:    segout_0 = 8'hfc;// 0
                4'h1:    segout_0 = 8'h60;// 1
                4'h2:    segout_0 = 8'hda;// 2
                4'h3:    segout_0 = 8'hf2;// 3
                4'h4:    segout_0 = 8'h66;// 4
                4'h5:    segout_0 = 8'hb6;// 5
                4'h6:    segout_0 = 8'hbe;// 6
                4'h7:    segout_0 = 8'he0;// 7
                4'h8:    segout_0 = 8'hfe;// 8
                4'h9:    segout_0 = 8'hf6;// 9
                default: segout_0 = 8'h00;// 0
            endcase
        end else begin
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
        end
    end
    initial begin
        state = 0;
    end
endmodule
