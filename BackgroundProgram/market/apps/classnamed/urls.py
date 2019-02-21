from django.conf.urls import url, include
from rest_framework.routers import DefaultRouter
from django.conf import settings
from django.conf.urls.static import static

from .views import ClassUserViewSet


router = DefaultRouter()
router.register(r'classuser', ClassUserViewSet)


urlpatterns = [
    url(r'^', include(router.urls)),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)



