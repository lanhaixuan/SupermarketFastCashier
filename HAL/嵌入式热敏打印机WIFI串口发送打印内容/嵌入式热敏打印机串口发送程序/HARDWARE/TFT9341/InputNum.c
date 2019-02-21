#include "InputNum.h"

delay_ms(30);
Key_1 = Get_Key_Init();	
delay_ms(30);
ShowNum(Key_1);
delay_ms(30);
while(Key_1 != 15)
{
	Key_1 = Get_Key_Init();
	delay_ms(30);					
 	ShowNum(Key_1);
	delay_ms(30);
	while(Key_1 < 10)
		{
			delay_ms(30);
			t1[a] = Key_1;													
			a++;
			Key_1 = Get_Key_Init();
			delay_ms(30);
			ShowNum(Key_1);
			delay_ms(30);
		}
	while(Key_1 == 10)
		{
			while(Key_1 != 15)
			{
			delay_ms(30);
			Key_1 = Get_Key_Init();
			delay_ms(30);
			ShowNum(Key_1);
			delay_ms(30);											
			while(Key_1 < 10)
				{
					delay_ms(30); 													
					t2[b] = Key_1;
					b++;
					Key_1 = Get_Key_Init();	
					delay_ms(30);
					ShowNum(Key_1);
					delay_ms(30);
				}
					while(Key_1 == 11)
						{
							while(Key_1 != 15)
								{
									delay_ms(30);
									Key_1 = Get_Key_Init();
									delay_ms(30);
									ShowNum(Key_1);
									delay_ms(30);						
									while(Key_1 < 10)
										{
											delay_ms(30);
											t3[c] = Key_1;													
											c++;
											Key_1 = Get_Key_Init();
											delay_ms(30);
											ShowNum(Key_1);
											delay_ms(30);															
										}
									while(Key_1 == 10)
										{
											while(Key_1 != 15)
											{
												delay_ms(30);
											Key_1 = Get_Key_Init();
											delay_ms(30);
											ShowNum(Key_1);
											delay_ms(30);														
											while(Key_1 < 10)
												{
													delay_ms(30); 													
													t4[d] = Key_1;
													d++;
													Key_1 = Get_Key_Init();	
													delay_ms(30);
													ShowNum(Key_1);
													delay_ms(30);
												}
											}																
										}								
								}														
						}											
			}											
		}								
}
t_1=a; 
t_2=b;
t_3=c; 
t_4=d;
	for(s=0;s<a;s++)
{ 
	xn+=t1[s]*math(t_1);
	t_1--;          
}
	for(s=0;s<b;s++)
{ 
	yn+=t2[s]*math(t_2);
	t_2--;          
}
	for(s=0;s<c;s++)
{ 
	xs+=t3[s]*math(t_3);
	t_3--;          
}
	for(s=0;s<d;s++)
{ 
	ys+=t4[s]*math(t_4);
	t_4--;          
}