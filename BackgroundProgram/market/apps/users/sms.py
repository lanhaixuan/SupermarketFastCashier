import random

import requests


def sms(payload):
    url = 'http://sms.market.alicloudapi.com/singleSendSms'
    header = {"Authorization": "APPCODE 07ced100b1ab4e749f1b51f35ea47490"}
    try:
        r = requests.get(url=url, params=payload, headers=header, timeout=30)
        r.raise_for_status()
        r.encoding = 'utf-8'
        return True
    except:
        return None


def smsInit():
    payload = {'ParamString': '{"no":"123456"}', 'RecNum': '18771135144',
               'SignName': '蓝海轩', 'TemplateCode': 'SMS_75730087'}
    phoneNum = 18771135144
    # 生成随机6位验证码
    verCode = str(int(random.random() * 1000000)).zfill(6)
    payload['ParamString'] = str({"no": "{0}".format(verCode)})
    payload['RecNum'] = str(phoneNum)
    # sms(payload)
    return verCode




