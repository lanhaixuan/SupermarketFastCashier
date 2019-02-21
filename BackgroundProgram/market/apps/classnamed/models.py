from django.db import models
from datetime import datetime


class ClassUser(models.Model):

    imei = models.CharField(max_length=30, verbose_name='IMEI')
    name = models.CharField(max_length=30, verbose_name='姓名')
    studentid = models.CharField(max_length=30, verbose_name='学号')
    score = models.FloatField(verbose_name='分数')
    role = models.CharField(choices=(('student', '学生'), ('teacher', '教师')), default='student', max_length=20)
    wifiname = models.CharField(max_length=30, verbose_name='wifi名称')
    punchtime = models.DateTimeField(default=datetime.now, verbose_name='打卡时间')
    teachername = models.CharField(max_length=30, verbose_name='教师姓名')

    class Meta:
        verbose_name = '个人信息'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.imei



