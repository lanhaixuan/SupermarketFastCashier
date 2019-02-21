from rest_framework import serializers
from rest_framework import viewsets

from users.models import UserProfile, MobileVerifyRecord
from users.serializers import UserProfileSerializerMode, MobileVerifyRecordSerializerMode



class UserViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = UserProfile
    serializer_class = UserProfileSerializerMode


class MobileVerViewSet(viewsets.ModelViewSet):
    queryset = MobileVerifyRecord
    serializer_class = MobileVerifyRecordSerializerMode

    def perform_create(self, serializer):
        serializer.save(code="1313")






