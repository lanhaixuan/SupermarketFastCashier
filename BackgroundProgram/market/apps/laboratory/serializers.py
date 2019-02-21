from rest_framework import serializers

from .models import Device, Devicemove, Lab, User, Userlabrelated


class DeviceSerializersMode(serializers.ModelSerializer):

    class Meta:
        model = Device
        fields = '__all__'
        read_only_fields = ('id',)


class DevicemoveSerializerMode(serializers.ModelSerializer):

    class Meta:
        model = Devicemove
        fields = '__all__'
        read_only_fields = ('id',)


class LabSerizlizerMode(serializers.ModelSerializer):

    class Meta:
        model = Lab
        fields = '__all__'
        read_only_fields = ('id',)


class UserSerizlizerMode(serializers.ModelSerializer):
    id = serializers.ReadOnlyField()
    class Meta:
        model = User
        fields = '__all__'
        read_only_fields = ('id',)


class UserlabrelatedSerizlizerMode(serializers.ModelSerializer):

    class Meta:
        model = Userlabrelated
        fields = '__all__'
        read_only_fields = ('id',)




