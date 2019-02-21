
from rest_framework import viewsets

from .models import ClassUser
from .serializers import ClassUserSerailizer


class ClassUserViewSet(viewsets.ModelViewSet):
    queryset = ClassUser.objects.all()
    serializer_class = ClassUserSerailizer


