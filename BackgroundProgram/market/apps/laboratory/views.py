from rest_framework import viewsets

from .models import Device, Devicemove, Lab, User, Userlabrelated
from .serializers import DeviceSerializersMode, DevicemoveSerializerMode, LabSerizlizerMode, UserSerizlizerMode, UserlabrelatedSerizlizerMode


class DeviceViewSet(viewsets.ModelViewSet):
    queryset = Device.objects.all()
    serializer_class = DeviceSerializersMode


class DevicemoveViewSet(viewsets.ModelViewSet):
    queryset = Devicemove.objects.all()
    serializer_class = DevicemoveSerializerMode


class LabViewSet(viewsets.ModelViewSet):
    queryset = Lab.objects.all()
    serializer_class = LabSerizlizerMode


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerizlizerMode


class UserlabrelatedViewSet(viewsets.ModelViewSet):
    queryset = Userlabrelated.objects.all()
    serializer_class = UserlabrelatedSerizlizerMode



