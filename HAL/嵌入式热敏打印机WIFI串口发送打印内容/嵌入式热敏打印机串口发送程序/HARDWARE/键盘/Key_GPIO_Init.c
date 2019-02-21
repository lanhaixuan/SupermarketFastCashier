#include "Key_GPIO_Init.h"
#include "sys.h"
#include "delay.h"

extern uint16_t Key_1;   //给出几个变量 用于检测键盘值 实际函数并没有用到
extern uint16_t Key_2;
extern uint16_t Key_3;
extern uint16_t Key_4;
extern uint16_t Key_5;
extern uint16_t Key_6;
extern uint16_t Key_7;
extern uint16_t Key_8;

void Key_GPIO_Init()  //键盘GPIO短端口初始化函数
{
	GPIO_InitTypeDef GPIO_InitStructure;  //端口结结构体
	
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOG,ENABLE);  //开启端口G的时钟
	
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD;  //设置为下拉输入 在没有输入时为低电平  上拉输入为没有输入时呈现高电平
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_5 | GPIO_Pin_6 | GPIO_Pin_7 | GPIO_Pin_8;  //开启G端口的5 6 7 8 作为输入端口
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;  //速度为50MHz
	GPIO_Init(GPIOG,&GPIO_InitStructure);  //使能端口输入的初始化
	
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;  //设置为通用推挽输出模式
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_1 | GPIO_Pin_2 | GPIO_Pin_3 | GPIO_Pin_4;  //开启G端口1 2 3 4 作为输出端口
	GPIO_Init(GPIOG,&GPIO_InitStructure);  //使能端口输出的初始化
	
	GPIO_ResetBits(GPIOG,GPIO_Pin_1);  //输出端口置低
	GPIO_ResetBits(GPIOG,GPIO_Pin_2);
	GPIO_ResetBits(GPIOG,GPIO_Pin_3);
	GPIO_ResetBits(GPIOG,GPIO_Pin_4);
	GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5);   //读取输入端口的值
  GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6);
	GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7);
	GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8);
}

int Read_Key()  //?áè??ü?μ3?ê??ˉoˉêy
{
	/************************************************
	 读第一排键值 将G4口置高 123口置低 判断哪一个键被按下 返回相应的数值
	************************************************/
	
  GPIO_SetBits(GPIOG,GPIO_Pin_4);
	GPIO_ResetBits(GPIOG,GPIO_Pin_1);
	GPIO_ResetBits(GPIOG,GPIO_Pin_2);
	GPIO_ResetBits(GPIOG,GPIO_Pin_3);
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_4) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
		{
			return 1;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_4) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
		{
			return 2;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_4) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
		{
			return 3;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_4) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
		{
			return 4;
		}
	}
	
	/************************************************
	读第二排键值 将G3口置高 124口置低 判断哪一个键被按下 返回相应的数值
	************************************************/
	GPIO_SetBits(GPIOG,GPIO_Pin_3);
	GPIO_ResetBits(GPIOG,GPIO_Pin_1);
	GPIO_ResetBits(GPIOG,GPIO_Pin_2);
	GPIO_ResetBits(GPIOG,GPIO_Pin_4);
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_3) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
		{
			return 5;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_3) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
		{
			return 6;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_3) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
		{
			return 7;
		}
	}
	
	if((GPIO_ReadOutputDataBit(GPIOG,GPIO_Pin_3) & GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5)) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
		{
			return 8;
		}
	}
	
		/************************************************
	 读第三排键值 将G2口置高 134口置低 判断哪一个键被按下 返回相应的数值
	************************************************/
	GPIO_SetBits(GPIOG,GPIO_Pin_2);
	GPIO_ResetBits(GPIOG,GPIO_Pin_1);
	GPIO_ResetBits(GPIOG,GPIO_Pin_3);
	GPIO_ResetBits(GPIOG,GPIO_Pin_4);
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
		{
			return 9;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
		{
			return 0;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
		{
			return 10;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
		{
			return 11;
		}
	}
	
	/************************************************
	 读第四排键值 将G1口置高 234口置低 判断哪一个键被按下 返回相应的数值
	************************************************/
	GPIO_SetBits(GPIOG,GPIO_Pin_1);
	GPIO_ResetBits(GPIOG,GPIO_Pin_2);
	GPIO_ResetBits(GPIOG,GPIO_Pin_3);
	GPIO_ResetBits(GPIOG,GPIO_Pin_4);
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_8) == 1)
		{
			return 12;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_7) == 1)
		{
			return 13;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_6) == 1)
		{
			return 14;
		}
	}
	
	if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
	{
		delay_ms(30);
		if(GPIO_ReadInputDataBit(GPIOG,GPIO_Pin_5) == 1)
		{
			return 15;
		}
	}
	
	return 20;
}



