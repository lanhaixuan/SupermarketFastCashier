from datetime import datetime

from django.db import models


class Goods(models.Model):
    isbn = models.CharField(max_length=20, verbose_name="商品编码", primary_key=True, unique=True)
    name = models.CharField(max_length=50, verbose_name="商品名称")
    price = models.FloatField(verbose_name="商品价格")
    ingredient = models.CharField(max_length=150, default="", verbose_name="配料", null=True, blank=True)
    shelflife = models.IntegerField(verbose_name="保质期", null=True, blank=True)
    productiondate = models.DateTimeField(default=datetime.now, verbose_name="生产日期")
    goodstype = models.CharField(default="", max_length=20, verbose_name="商品类型")
    image = models.ImageField(upload_to='media/', null=True, verbose_name="商品图片")
    address = models.CharField(max_length=100, verbose_name="商品位置", default='')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = "商品信息"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name

class MobileVerf(models.Model):
    code = models.CharField(max_length=8, verbose_name="短信验证码")
    phonenum = models.CharField(max_length=11, verbose_name="手机号")
    add_time = models.DateTimeField(default=datetime.now, verbose_name="更新时间")

    class Meta:
        verbose_name = "手机验证码"
        verbose_name_plural = verbose_name
        ordering = ['-add_time']

    def __str__(self):
        return self.code

class Customers(models.Model):
    mobile = models.CharField(max_length=11, verbose_name="手机号码")
    nickname = models.CharField(max_length=50, verbose_name="昵称")
    image = models.ImageField(upload_to='media/', verbose_name="图像")
    add_time = models.DateTimeField(default=datetime.now, verbose_name="添加时间")

    class Meta:
        verbose_name = "用户信息"
        verbose_name_plural = verbose_name
        ordering = ['-add_time']

    def __str__(self):
        return self.mobile


class Order(models.Model):
    ordernum = models.IntegerField(verbose_name="订单号",default=None, unique=True)
    owner = models.ForeignKey(Customers, verbose_name='客户')
    amount = models.FloatField(verbose_name="订单总价", default=0)

    class Meta:
        verbose_name = "用户订单"
        verbose_name_plural = verbose_name
        ordering = ['-id']

    def __str__(self):
        return str(self.ordernum)


class OrderItems(models.Model):
    orderid = models.IntegerField(verbose_name="订单号")
    goodsname = models.CharField(max_length=100, verbose_name="商品名")
    goodprice = models.FloatField(verbose_name="商品价格", default=0)
    quantity = models.IntegerField(verbose_name="商品数量", default=0)
    imageurl = models.URLField(null=True, verbose_name="图片地址")
    is_degauss = models.CharField(verbose_name="是否消磁", choices=(('ok', '消磁'),('no', '未消磁')), max_length=3, default='no')
    is_pay = models.BooleanField(verbose_name="是否支付", default=False)
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = "订单详细"
        verbose_name_plural = verbose_name

    def __str__(self):
        return str(self.orderid)










