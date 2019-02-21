#include "stm32f10x.h"
#include "stm32f10x_usart.h"
#include "stm32f10x_exti.h"
#include "misc.h"
#include "usart2.h"
#include "stm32f10x.h"
#include "stdio.h"
#include "stdlib.h"
#include "string.h"
#include "delay.h"

volatile unsigned char  gprs_ready_flag = 0;
volatile unsigned char  gprs_ready_count = 0;

unsigned char  usart2_rcv_buf[MAX_RCV_LEN];
volatile unsigned int   usart2_rcv_len = 0;

int Flag = 0;

/*
 *  @brief USART2初始化函数
 */
void USART2_Config(void)
{
    //GPIO端口设置
  GPIO_InitTypeDef GPIO_InitStructure;
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure;
	 
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_USART2, ENABLE);	//使能USART1，GPIOA时钟
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
  
	//USART1_TX   GPIOA.9
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2; //PA.9
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;	//复用推挽输出
  GPIO_Init(GPIOA, &GPIO_InitStructure);//初始化GPIOA.9
   
  //USART1_RX	  GPIOA.10初始化
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_3;//PA10
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;//浮空输入
  GPIO_Init(GPIOA, &GPIO_InitStructure);//初始化GPIOA.10  

  //Usart1 NVIC 配置
	NVIC_InitStructure.NVIC_IRQChannel = USART2_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority=3 ;//抢占优先级3
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 3;		//子优先级3
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;			//IRQ通道使能
	NVIC_Init(&NVIC_InitStructure);	//根据指定的参数初始化VIC寄存器
  
   //USART 初始化设置

	USART_InitStructure.USART_BaudRate = 115200;//串口波特率
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;//字长为8位数据格式
	USART_InitStructure.USART_StopBits = USART_StopBits_1;//一个停止位
	USART_InitStructure.USART_Parity = USART_Parity_No;//无奇偶校验位
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;//无硬件数据流控制
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;	//收发模式

  USART_Init(USART2, &USART_InitStructure); //初始化串口1
  USART_ITConfig(USART2, USART_IT_RXNE, ENABLE);//开启串口接受中断
  USART_Cmd(USART2, ENABLE);                    //使能串口1 
}

/*
*  @brief USART2串口接收状态初始化
*/
void USART2_Clear(void)
{
		memset(usart2_rcv_buf, 0, sizeof(usart2_rcv_buf));
    usart2_rcv_len = 0;
}

/*
*  @brief USART2串口发送api
*/
void USART2_Write(USART_TypeDef* USARTx, uint8_t *Data, uint8_t len)
{
    uint8_t i;
    USART_ClearFlag(USARTx, USART_FLAG_TC);
    for(i = 0; i < len; i++)
    {
        USART_SendData(USARTx, *Data++);
        while( USART_GetFlagStatus(USARTx, USART_FLAG_TC) == RESET );
    }
}

/*
 *  @brief USART2串口发送AT命令用
 *  @para  cmd  AT命令
 *  @para  result 预期的正确返回信息
 *  @para  timeOut延时时间,ms
 */
void SendCmd(char* cmd, char* result, int timeOut)
{
    while(1)
    {
        USART2_Clear();
        USART2_Write(USART2, (unsigned char *)cmd, strlen((const char *)cmd));
        delay_ms(timeOut);
 //       printf("%s %d cmd:%s,rsp:%s\n", __func__, __LINE__, cmd, usart2_rcv_buf);
        if((NULL != strstr((const char *)usart2_rcv_buf, result)))	//判断是否有预期的结果
        {
            break;
        }
        else
        {
            delay_ms(100);
        }
    }
}

#if 1
/*
 *  @brief 返回USART2已接收的数据长度
 */
uint32_t USART2_GetRcvNum(void)
{
    return usart2_rcv_len;
}

/*
 *  @brief 返回USART2已接收的数据到buf，长度为rcv_len
 */
void  USART2_GetRcvData(uint8_t *buf, uint32_t rcv_len)
{
    if(buf)
    {
        memcpy(buf, usart2_rcv_buf, rcv_len);
    }
    USART2_Clear();
}
#endif


/**
  * @brief  This function handles usart2 global interrupt request.
  * @param  None
  * @retval : None
  */
void USART2_IRQHandler(void)
{
    unsigned int data;
#if 1
    if(USART2->SR & 0x0F)
    {
        // See if we have some kind of error
        // Clear interrupt (do nothing about it!)
        data = USART2->DR;
    }
    else if(USART2->SR & USART_FLAG_RXNE)   //Receive Data Reg Full Flag
    {
        //GPIO_SetBits(GPIOC,GPIO_Pin_0|GPIO_Pin_1|GPIO_Pin_2|GPIO_Pin_3);
        data = USART2->DR;
        usart2_rcv_buf[usart2_rcv_len++] = data;
		if(data == '>') Flag = 1;
		if(usart2_rcv_len >= MAX_RCV_LEN - 1)
			usart2_rcv_len = 0;
        //usart1_rcv_buf[usart1_rcv_len++]=data;
        //usart1_putrxchar(data);       //Insert received character into buffer
    }
    else
    {
        ;
    }
#endif
}
