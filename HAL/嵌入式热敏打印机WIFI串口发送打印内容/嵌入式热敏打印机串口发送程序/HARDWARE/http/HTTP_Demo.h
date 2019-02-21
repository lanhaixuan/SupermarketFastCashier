#ifndef HTTP_DEMO_H_H
#define HTTP_DEMO_H_H

#include <string.h>
#include <stdio.h>
#include <stdint.h>

uint32_t HTTP_PostPkt(char *pkt, char *key, char *devid, char *dsid, char *val);
uint16_t HTTP_GetPkt(char *pkt, char *path);


#endif

