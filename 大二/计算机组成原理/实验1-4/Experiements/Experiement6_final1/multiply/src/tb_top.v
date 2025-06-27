
module tb_top;
    // parameter XH = 25000000;
    parameter XH = 1000000;
    // Inputs
    reg        clk;
    reg  [3:0] data1;
    reg  [3:0] data2;
    // Outputs
    wire [7:0] an;
    wire [7:0] mul;
    wire [7:0] result;
    // 实例化 MULTU_4bit 模块
    top u_top (
        .clk   (clk),
        .data1 (data1),
        .data2 (data2),
        .an    (an),
        .mul   (mul),
        .result(result)
    );
    initial begin
        clk = 0;
        #XH;
        data1 <= 4'b1111;
        data2 <= 4'b0010;
        #XH;
        // data1 <= 4'b0100;
        // data2 <= 4'b0100;
        // #XH;
        // data1 <= 4'b0010;
        // data2 <= 4'b1000;
        // #XH;
        // data1 <= 4'b1111;
        // data2 <= 4'b0001;
        // #XH;
    end
    always begin
        #XH;
        clk = ~clk;
    end

endmodule
