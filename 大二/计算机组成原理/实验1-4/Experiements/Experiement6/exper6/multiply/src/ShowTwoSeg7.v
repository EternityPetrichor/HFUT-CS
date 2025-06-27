module ShowTwoSeg7 (
    input            clk,
    input      [3:0] seg0,
    input      [3:0] seg1,
    output reg [1:0] an,
    output reg [7:0] seg
);
    reg       state = 0;
    reg [3:0] bcd;
    always @(posedge clk) begin
        state <= state + 1;
    end

    always @(*) begin
        case (state)
            1'b0: begin
                an  = 2'b01;
                bcd = seg0;
            end
            1'b1: begin
                an  = 2'b10;
                bcd = seg1;
            end
        endcase
    end
    //8421BCD码bcd与一位数码管的8段A、B、C、D、E、F、G、DP之间的对应关系
    always @(*) begin
        case (bcd)
            4'h0:    seg = 8'hfc;
            4'h1:    seg = 8'h60;
            4'h2:    seg = 8'hda;
            4'h3:    seg = 8'hf2;
            4'h4:    seg = 8'h66;
            4'h5:    seg = 8'hb6;
            4'h6:    seg = 8'hbe;
            4'h7:    seg = 8'he0;
            4'h8:    seg = 8'hfe;
            4'h9:    seg = 8'hf6;
            // 4'ha: seg = 8'hee;
            // 4'hb: seg = 8'h3e;
            // 4'hc: seg = 8'h9c;
            // 4'hd: seg = 8'h7a;
            // 4'he: seg = 8'h9e;
            // 4'hf: seg = 8'h8e;
            default: seg = 8'h00;  //八段全熄灭  
        endcase
    end
endmodule
