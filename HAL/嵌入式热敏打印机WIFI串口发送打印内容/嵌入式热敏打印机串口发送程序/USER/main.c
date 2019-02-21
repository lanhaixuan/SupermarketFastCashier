#include "stm32f10x.h"
#include "delay.h"
#include "Key_GPIO_Init.h"
#include "sys.h"
#include "usart.h"
#include "lcd.h" 


#include "usart2.h"
#include "HTTP_Demo.h"
#include "esp8266.h"
/************************************************
嵌入式热敏打印机串口发送程序
基于串口通信例程修改
开启两个串口uart1和uart2 串口1作为发送给打印机命令的串口 串口2作为单片机接收数据的串口 
数据从电脑端串口助手发出 经过串口2接收到单片机 再通过按键选择是否要打印 即通过串口1向打印机发送打印内容
串口2接收数据 判断数据是否结束是判断发送的命令里是否包括ED作为结尾 原例程为判断接收到回车换行符 其十六进制为0X0D 0X0A
因为该程序需要打印多行内容 所以以换行符作为结束不适用 改为以ASCII码ED为结束符 其十六进制为0X45 0X44
不能把串口1的接收程序屏蔽 会出现程序卡死在某个中断函数中
该程序不包含串口2的发送程序 该程序出现的所有printf都是通过串口1发送的
作者高小帅
************************************************/

//extern unsigned char  usart2_rcv_buf[MAX_RCV_LEN];

int main(void)
{		
	u16 t,i;  //定义两个变量 作为数组的计数值
	u16 len;	//定义数据长度的变量
	u16 length;
	u16 usart2_receive,usart1_trans;  //定义两个while循环的判断条件
	char HTTP_Buf[400];     //HTTP报文缓存区

	 
	uint16_t Getkey;  //定义键值变量
	 
	delay_init();	    	 //延时函数初始化	  
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2); //设置NVIC中断分组2:2位抢占优先级，2位响应优先级
	uart1_init(9600);	 //串口1初始化函数 其波特率为9600  
 


    USART2_Config();        //USART2用于连接ESP8266模块

    ESP8266_Init();         //ESP8266初始化
	 
	Key_GPIO_Init();
	LCD_Init();

	POINT_COLOR=BLACK;

	LCD_Clear(WHITE);




	while(1)
	{
		LCD_ShowChinese(32,30,0,350,350,21,26);//便捷收银系统
		LCD_ShowChinese(32,70,70,350,350,14,16);//请稍后
		LCD_ShowChinese(32,50,120,350,350,38,43);//正在传送数据
		
		usart2_receive=0;
		while(usart2_receive == 0)
		{
			USART2_Clear();
			len = HTTP_GetPkt(HTTP_Buf, "detail"); //HTTP组包
			length = strlen(usart2_rcv_buf);  
			USART2_Write(USART2, (unsigned char *)(HTTP_Buf), len);			//报文发送
			delay_ms(1000);

			if(Flag)  //接收完成的标志位15为1
			{	
				
				for(i=192; i<400 ; i++)
				{
					USART1_RX_BUF[i] = usart2_rcv_buf[i];  //将上位机发送的数据转存到数组变量中
				}
				Flag=0;  //清零接收完成标志位
				usart2_receive = 1;
				LCD_Clear(WHITE);
			}
		}		
		
		usart1_trans=0;
		while(usart1_trans == 0)
		{
			Getkey = Read_Key();	
			LCD_ShowChinese(32,30,0,350,350,21,26);//便捷收银系统
			LCD_ShowChinese(32,70,70,350,350,7,9);//按下1
			LCD_ShowChinese(32,50,120,350,350,10,13);//打印小票
			LCD_ShowChinese(32,70,170,350,350,48,50);//按下2
			LCD_ShowChinese(32,50,220,350,350,44,47);//取消打印
			
			if(Getkey == 1)
			{
				LCD_Clear(WHITE);
				LCD_ShowChinese(32,30,0,350,350,21,26);//便捷收银系统
				LCD_ShowChinese(32,70,70,350,350,14,16);//请稍后
				LCD_ShowChinese(32,50,120,350,350,17,20);//正在打印
//			printf("\r\n\r\n");//插入换行  两个\r\n为空一行
				for(t=0;t<400;t++)
				{
					while(USART_GetFlagStatus(USART1,USART_FLAG_TC)!=SET);//等待发送结束
					USART_SendData(USART1, USART1_RX_BUF[t]);//向串口1发送数据
					
				}
				printf("\r\n\r\n");//插入换行  两个\r\n为空一行
				USART1_RX_STA=0;
				delay_ms(1000);
				LCD_Clear(WHITE);
				LCD_ShowChinese(32,30,0,350,350,21,26);//便捷收银系统
				LCD_ShowChinese(32,50,120,350,350,27,30);//打印完成
				LCD_ShowChinese(32,0,160,350,350,31,37);//按下3返回首页
				Getkey = Read_Key();//重新获取键值 使显示界面停留在打印界面
				while(Getkey == 20 || Getkey != 3)
				{
					Getkey = Read_Key();  //当按下2时退出打印界面
				}
				usart1_trans = 1;
								
				LCD_Clear(WHITE);			
			}
			if(Getkey == 3)
			{
				usart1_trans = 1;
				USART1_RX_STA=0;
				LCD_Clear(WHITE);
			}
		}
	}	 
}

