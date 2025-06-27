`include "D:/作业/计算机体系结构/CPUtest/defines.v"
`timescale 1ns/1ps

module x7seg_top_tb();

  reg     CLOCK_50;
  reg     rst;
  
       
  initial begin
    CLOCK_50 = 1'b0;
    forever #10 CLOCK_50 = ~CLOCK_50;
  end
      
  initial begin
    rst = `RstEnable;
    #105 rst= `RstDisable;
    #4000 $stop;
  end
       
  x7seg_top x7seg_top0(
		.clk(CLOCK_50),
		.s(rst)
	);

endmodule

