from django.db import models
from datetime import datetime


class UserProfile(models.Model):
    nick_name = models.CharField(max_length=50, verbose_name="昵称", default='')
    birthday = models.DateField(verbose_name='生日', null=True, blank=True)
    gender = models.CharField(choices=(('male', '男'), ('female', '女')), default='female', max_length=7, verbose_name="性别")
    address = models.CharField(max_length=100, default='')
    mobile = models.CharField(max_length=11, default='', unique=True, verbose_name="手机号")
    image = models.ImageField(upload_to='media/%Y/%m', default='media/default.png', max_length=100)

    class Meta:
        verbose_name = '用户信息'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.mobile


class MobileVerifyRecord(models.Model):
    code = models.CharField(max_length=20, verbose_name='验证码')
    mobile = models.CharField(max_length=11, null=True, blank=True, verbose_name="手机号")
    send_time = models.DateTimeField(default=datetime.now, verbose_name='发送时间')

    class Meta:
        verbose_name = "电话验证码"
        verbose_name_plural = verbose_name
        ordering = ["-send_time"]

    def __str__(self):
        return '{0}({1})'.format(self.code, self.mobile)


