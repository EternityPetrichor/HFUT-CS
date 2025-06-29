#include "stdio.h"
#include "buzzer.h"
#define GPKDATA (*((volatile unsigned long *)0x7F008808))
#define GPKCON0     		(*((volatile unsigned long *)0x7F008800))
#define GPKDATA     			(*((volatile unsigned long *)0x7F008808))
#define FLAG_BUZZER     			(*((volatile unsigned long *)0x00000001))
#define EINT0CON0  			(*((volatile unsigned long *)0x7F008900))
#define EINT0MASK  			(*((volatile unsigned long *)0x7F008920))
#define EINT0PEND  			(*((volatile unsigned long *)0x7F008924))
#define PRIORITY 	    	(*((volatile unsigned long *)0x7F008280))
#define SERVICE     		(*((volatile unsigned long *)0x7F008284))
#define SERVICEPEND 		(*((volatile unsigned long *)0x7F008288))
#define VIC0IRQSTATUS  		(*((volatile unsigned long *)0x71200000))
#define VIC0FIQSTATUS  		(*((volatile unsigned long *)0x71200004))
#define VIC0RAWINTR    		(*((volatile unsigned long *)0x71200008))
#define VIC0INTSELECT  		(*((volatile unsigned long *)0x7120000c))
#define VIC0INTENABLE  		(*((volatile unsigned long *)0x71200010))
#define VIC0INTENCLEAR 		(*((volatile unsigned long *)0x71200014))
#define VIC0PROTECTION 		(*((volatile unsigned long *)0x71200020))
#define VIC0SWPRIORITYMASK 	(*((volatile unsigned long *)0x71200024))
#define VIC0PRIORITYDAISY  	(*((volatile unsigned long *)0x71200028))
#define VIC0ADDRESS        	(*((volatile unsigned long *)0x71200f00))

#define		PWMTIMER_BASE			(0x7F006000)
#define		TCFG0    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x00)) )
#define		TCFG1    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x04)) )
#define		TCON      	( *((volatile unsigned long *)(PWMTIMER_BASE+0x08)) )
#define		TCNTB0    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x0C)) )
#define		TCMPB0    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x10)) )
#define		TCNTO0    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x14)) )
#define		TCNTB1    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x18)) )
#define		TCMPB1    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x1C)) )
#define		TCNTO1    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x20)) )
#define		TCNTB2    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x24)) )
#define		TCMPB2    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x28)) )
#define		TCNTO2    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x2C)) )
#define		TCNTB3    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x30)) )
#define		TCMPB3    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x34)) )
#define		TCNTO3    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x38)) )
#define		TCNTB4    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x3C)) )
#define		TCNTO4    	( *((volatile unsigned long *)(PWMTIMER_BASE+0x40)) )
#define		TINT_CSTAT 	( *((volatile unsigned long *)(PWMTIMER_BASE+0x44)) )

void timer_init(unsigned long utimer,unsigned long uprescaler,unsigned long udivider,unsigned long utcntb,unsigned long utcmpb);
typedef void (isr) (void);
extern void asm_timer_irq();
extern int count;
extern int K1;
extern int K2;
extern int K3;
extern int K4;
void irq_init(void)
{
	/* 在中断控制器里使能timer0中断 */
	VIC0INTENABLE |= (1<<23);

	VIC0INTSELECT =0;

	isr** isr_array = (isr**)(0x7120015C);

	isr_array[0] = (isr*)asm_timer_irq;

	/*将GPK4-GPK7配置为输出口*/
	GPKCON0 = 0x11110000;
	
	/*熄灭四个LED灯*/
	GPKDATA = 0xff;
}

// timer0中断的中断处理函数
void do_irq()
{	

	unsigned long uTmp;
	
	if (K4 == 0) {
		
		if (count == 0){
			GPKDAT =0xff;
			K1 = 0;
			K2 = 0;
			K3 = 0;
			--count;
			FLAG_BUZZER = 0;
			timer_init(0,65,4,0,0);
		}
		
		if(count != -1){
            //以下为闪烁操作
		
			if ((K2==1 && K3==1)||(K2==0&&K3==0))
			{	//此步为亮
				if(count==1)
				{
				FLAG_BUZZER = 1;
				GPKDAT &= ~(1 << 4);
				count+=1;
				}
				else if(count==2)
				{
				FLAG_BUZZER = 0;
				GPKDAT |= 1 << 4;
				count+=1;
				}
				else if(count==3)
				{
				FLAG_BUZZER = 1;
				GPKDAT &= ~(1 << 5);
				count+=1;
				}
				else if(count==4)
				{
				FLAG_BUZZER = 0;
				GPKDAT |= 1 << 5;
				count+=1;
				}
				else if(count==5)
				{
				FLAG_BUZZER = 1;
				GPKDAT &= ~(1 << 6);
				count+=1;
				}
				else if(count==6)
				{
				FLAG_BUZZER = 0;
				GPKDAT |= 1 << 6;
				count+=1;
				}
				else if(count==7)
				{
				FLAG_BUZZER = 1;
				GPKDAT &= ~(1 << 7);
				count+=1;
				}
				else if(count==8)
				{
				FLAG_BUZZER = 0;
				GPKDAT |= 1 << 7;
				count=1;
				}
			}
			else
			{
				//GPKDATA = 0xff;
				if (count % 2 == 0) {
					FLAG_BUZZER = 1;
					GPKDAT &= ~(1 << 4);
					GPKDAT &= ~(1 << 6);
				}
				//此步为灭
				else {
					FLAG_BUZZER = 0;
					GPKDAT |= 1 << 6;
					GPKDAT |= 1 << 4;
				}
				--count;
			}
		}
		
		if (FLAG_BUZZER) {
			buzzer_on();
		}
		else {
			buzzer_off();
		}
	}
	else {
		K4 = 0;
	}
	//清timer0的中断状态寄存器
	uTmp = TINT_CSTAT;
	TINT_CSTAT = uTmp;
	VIC0ADDRESS=0x0;	
}

// 初始化timer
void timer_init(unsigned long utimer,unsigned long uprescaler,unsigned long udivider,unsigned long utcntb,unsigned long utcmpb)
{
	unsigned long temp0;

	// 定时器的输入时钟 = PCLK / ( {prescaler value + 1} ) / {divider value} = PCLK/(65+1)/16=62500hz

	//设置预分频系数为66
	temp0 = TCFG0;
	temp0 = (temp0 & (~(0xff00ff))) | (uprescaler<<0);
	TCFG0 = temp0;

	// 16分频
	temp0 = TCFG1;
	temp0 = (temp0 & (~(0xf<<4*utimer))& (~(1<<20))) |(udivider<<4*utimer);
	TCFG1 = temp0;

	// 1s = 62500hz
	TCNTB0 = utcntb;
	TCMPB0 = utcmpb;

	// 手动更新
	TCON |= 1<<1;

	// 清手动更新位
	TCON &= ~(1<<1);

	// 自动加载和启动timer0
	TCON |= (1<<0)|(1<<3);

	// 使能timer0中断
	temp0 = TINT_CSTAT;
	temp0 = (temp0 & (~(1<<utimer)))|(1<<(utimer));
	TINT_CSTAT = temp0;
}


