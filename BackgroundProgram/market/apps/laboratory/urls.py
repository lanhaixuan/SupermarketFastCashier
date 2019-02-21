from django.conf.urls import url, include
from rest_framework.routers import DefaultRouter

from django.conf import settings
from django.conf.urls.static import static
from .views import DeviceViewSet, DevicemoveViewSet, LabViewSet, UserViewSet, UserlabrelatedViewSet


router = DefaultRouter()
router.register(r'device', DeviceViewSet)
router.register(r'devicemove', DevicemoveViewSet)
router.register(r'lab', LabViewSet)
router.register(r'user', UserViewSet)
router.register(r'userlabrelated', UserlabrelatedViewSet)


urlpatterns = [
    url(r'^', include(router.urls)),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)