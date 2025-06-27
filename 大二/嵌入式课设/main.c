#include "stdio.h"
#include "buzzer.h"
void timer_init(unsigned long utimer,unsigned long uprescaler,unsigned long udivider,unsigned long utcntb,unsigned long utcmpb);


volatile int count = -1;
volatile int K1 = 0;
volatile int K2 = 0;
volatile int K3 = 0;
volatile int K4 = 0;
volatile int flag = 0;
int sign = 0;

void Led_on(){//点亮所有灯
	GPKDAT &= ~(1<<7);
	GPKDAT &= ~(1<<6);
	GPKDAT &= ~(1<<5);
	GPKDAT &= ~(1<<4);			
}
void Led_off(){//熄灭所有灯
	GPKDAT |= 1<<7;
	GPKDAT |= 1<<6;			
	GPKDAT |= 1<<5;
	GPKDAT |= 1<<4;		
}

int main()
{
	init_uart();

	// 初始化buzzer
	buzzer_init();
	int dat = 0;

	// 配置GPK4-7为输出功能
	GPKCON0 = 0x11110000;	
	
	// 所有LED熄灭
	GPKDAT = 0x000000f0;
	
	// 配置GPN为输入功能
	GPNCON = 0;
	
	// 轮询的方式查询按键事件
	while(1)
	{
		dat = GPNDAT;
		// KEY2被按下
		if(!(dat & (1<<1))&& K1==0){		
			GPKDAT &= ~(1<<5);
			K2 = 1;	
		}
		// KEY3被按下
		if(!(dat & (1<<2))&& K1==0) {
			GPKDAT &= ~(1<<6);		
			K3 = 1;
		}	
		
		// KEY1被按下
		if(!(dat & (1<<0))){				
			K1 = 1;	
			if((K3==1&&K2==0)||(K3==0&&K2==1)){
				GPKDAT = 0x000000f0;
				count = (K3 * 2 + K2) * 2;
					timer_init(0,65,4,62500,0);
				
								}			
				else{
					GPKDAT = 0x000000f0;
					count = 1;					
					timer_init(0,65,4,62500/2,0);
					}
	
		}		
		
		// KEY4被按下
		if(!(dat & (1<<3))){			
			K4 = 1;
			if (K4) {
				K1 = 0;
				K2 = 0;
				K3 = 0;
				GPKDAT |= 1<<7;
				GPKDAT |= 1<<6;			
				GPKDAT |= 1<<5;
				GPKDAT |= 1<<4;
				timer_init(0,65,4,0,0);
				buzzer_off();
				//K4 = 0;
			}
			//GPKDAT |= 1<<7;
		
		}
			

	}
	return 0;
}
