//////////////////////////////////////////////////////////////////////
////                                                              ////
//// Copyright (C) 2014 leishangwen@163.com                       ////
////                                                              ////
//// This source file may be used and distributed without         ////
//// restriction provided that this copyright statement is not    ////
//// removed from the file and that any derivative work contains  ////
//// the original copyright notice and the associated disclaimer. ////
////                                                              ////
//// This source file is free software; you can redistribute it   ////
//// and/or modify it under the terms of the GNU Lesser General   ////
//// Public License as published by the Free Software Foundation; ////
//// either version 2.1 of the License, or (at your option) any   ////
//// later version.                                               ////
////                                                              ////
//// This source is distributed in the hope that it will be       ////
//// useful, but WITHOUT ANY WARRANTY; without even the implied   ////
//// warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR      ////
//// PURPOSE.  See the GNU Lesser General Public License for more ////
//// details.                                                     ////
////                                                              ////
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
// Module:  inst_rom
// File:    inst_rom.v
// Author:  Lei Silei
// E-mail:  doilove2021@163.com
// Description: 指令存储器
// Revision: 1.0
//////////////////////////////////////////////////////////////////////

`include "defines.v"

module inst_rom(

//	input	wire										clk,
	input wire										ce,
	input wire[`InstAddrBus]			addr,
	output reg[`InstBus]					inst
	
);


reg [`InstBus] inst_mem [0:`InstMemNum-1];

initial begin
//    inst_mem[0] = 32'h3402ffff;//s2,s3賦值,yigezhouqi 
//    inst_mem[1] = 32'h00021400;
//    inst_mem[2] = 32'h3442fff1;//R2  被赋值为-15
//    inst_mem[3] = 32'h34030011;//R3  被赋值为17
//    inst_mem[4] = 32'h0043001a;//3zhongchufa
//    inst_mem[5] = 32'h0043001b;
//    inst_mem[6] = 32'h0062001a;

      inst_mem[0] = 32'h34021100;
      inst_mem[1] = 32'h34420020;
      inst_mem[2] = 32'h34424400;
      inst_mem[3] = 32'h34420044;//数据前推解决数据冲突测试
    
      inst_mem[4] = 32'h3402000f;//R2  被赋值为15                   
      //inst_mem[7] = 32'h3442fff1;//R2  被赋值为-15           
      inst_mem[5] = 32'h34030011;//R3  被赋值为17
      //inst_mem[6] = 32'h24620000;
      //inst_mem[6] = 32'h34030011;   
      //乘法指令，乘累加指令
      inst_mem[6] = 32'h00620018;    //乘法 结果255
      inst_mem[7] = 32'h70620000;    //乘累加 结果 510
         
      inst_mem[8] = 32'h34020000;
      inst_mem[9] = 32'h3402ffff;//R2,R3賦值
      inst_mem[10] = 32'h00021400;       
      inst_mem[11] = 32'h3442fff1;//R2  被赋值为-15           
      inst_mem[12] = 32'h0043001a;//3 种除法     
      inst_mem[13] = 32'h0043001b;                        
      inst_mem[14] = 32'h0062001a;
      
      //inst_mem[15] = 32'h3402000e;
      //inst_mem[16] = 32'h43020006;
                          
      
end

	always @ (*) begin
		if (ce == `ChipDisable) begin
			inst <= `ZeroWord;
	  end else if(addr[`InstMemNumLog2+1:2] < 15)begin
		  inst <= inst_mem[addr[`InstMemNumLog2+1:2]];
		end else begin
		   inst <= `ZeroWord;
		   end
	end

endmodule