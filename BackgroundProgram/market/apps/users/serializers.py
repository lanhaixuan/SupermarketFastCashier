from rest_framework import serializers

from users.models import MobileVerifyRecord, UserProfile


class UserProfileSerializerMode(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('id', 'mobile', 'gender',)


class MobileVerifyRecordSerializerMode(serializers.ModelSerializer):
    code = serializers.ReadOnlyField()
    class Meta:
        model = MobileVerifyRecord
        fields = ('id', 'code', 'mobile', 'send_time',)

