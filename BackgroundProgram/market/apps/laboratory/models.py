from django.db import models
from datetime import datetime


class Device(models.Model):
    device_fromlabid = models.IntegerField(verbose_name='原存放地址ID')
    device_image = models.ImageField(upload_to='device/', verbose_name='设备图片')
    device_inlabid = models.IntegerField(verbose_name='现存放地址ID')
    device_name = models.CharField(max_length=50, verbose_name='设备名称')
    device_no = models.CharField(max_length=50, verbose_name='设备编号')
    device_type = models.CharField(max_length=50, verbose_name='设备类型')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '实验室设备'
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.id)


class Devicemove(models.Model):
    device_id = models.IntegerField(verbose_name='设备ID')
    devicemove_fromlabid = models.IntegerField(verbose_name='移动实验编号')
    devicemove_img = models.ImageField(verbose_name='摄像头拍照图片')
    devicemove_time = models.DateTimeField(default=datetime.now, verbose_name='设备移动时间')
    devicemove_tolabid = models.IntegerField(verbose_name='移入实验编号')
    user_id = models.IntegerField(verbose_name='用户ID')

    class Meta:
        verbose_name = '移动设备记录'
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.id)


class Lab(models.Model):
    lab_address = models.CharField(max_length=50, verbose_name='实验室地址')
    lab_getuiid = models.CharField(verbose_name='单片机推送ID' ,max_length=50)
    lab_img = models.ImageField(verbose_name='实验室图片', upload_to='lab/')
    lab_name = models.CharField(max_length=50, verbose_name='实验室名称')
    lab_no = models.CharField(max_length=50, verbose_name='实验室编号')

    class Meta:
        verbose_name = '实验室'
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.id)


class User(models.Model):
    user_getuid = models.CharField(max_length=50, verbose_name='个推ID')
    user_name = models.CharField(max_length=50, verbose_name='用户名')
    user_role = models.IntegerField(verbose_name='角色')
    user_tel = models.CharField(verbose_name='手机号码', unique=True, max_length=50)

    class Meta:
        verbose_name = '人员'
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.id)


class Userlabrelated(models.Model):
    lab_id = models.IntegerField(verbose_name='实验室ID', unique=True)
    user_id = models.IntegerField(verbose_name='用户ID', unique=True)

    class Meta:
        verbose_name = '人员-实验室关联表'
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.id)







