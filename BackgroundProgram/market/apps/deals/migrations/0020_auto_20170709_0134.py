# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-07-09 01:34
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('deals', '0019_auto_20170709_0123'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='order',
            name='ordernum',
        ),
        migrations.AddField(
            model_name='order',
            name='orderid',
            field=models.IntegerField(default=None, unique=True, verbose_name='订单号'),
        ),
    ]
