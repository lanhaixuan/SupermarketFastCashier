from rest_framework import serializers

from .models import ClassUser


class ClassUserSerailizer(serializers.ModelSerializer):

    class Meta:
        model = ClassUser
        fields = ('imei', 'name', 'studentid', 'score', 'role', 'wifiname', 'punchtime', 'teachername')

