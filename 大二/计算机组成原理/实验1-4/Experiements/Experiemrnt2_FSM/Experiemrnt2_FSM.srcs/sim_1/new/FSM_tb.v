`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/03/17 15:31:40
// Design Name: 
// Module Name: FSM_tb
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


module FSM_tb();
reg Clock;
reg Reset;
reg A;
wire F;
wire G;
fsm Fsm_587(
    .Clock(Clock),
    .Reset(Reset),
    .A(A),
    .F(F),
    .G(G)
);
always #58.7 Clock = ~Clock; //学号后三位
initial begin
    Clock <=0;
    Reset <=1;
    A <=0;
    #5.87 Reset<=0; //当第一个Clock上升沿出现时state为1000 F为0 G也为0
    //之后将A设置为1在下一个
    #117.4 Reset<=1; //保证后面的状态可以全部展现
    A<=1; #117.4;
    A<=0; #117.4;
    A<=1; #117.4; //学号*2
    A<=0; #117.4;
    #117.4
    $finish;
end
 initial begin
        $monitor("Time=%t, Reset=%b, A=%b, State=%b, F=%b, G=%b", $time, Reset, A,Fsm_587.state, F, G);
    end
endmodule
