# from django.conf.urls import url, include
# from users.views import UserViewSet, MobileVerViewSet
# from rest_framework.routers import DefaultRouter
#
#
# # 导入settings中media文件, 里面存放图片信息
# from django.conf import settings
# from django.conf.urls.static import static
#
# # 设置路由
# router = DefaultRouter()
# router.register(r'users', UserViewSet)
# router.register(r'mobilever', MobileVerViewSet)
#
#
#
# urlpatterns = [
#     url(r'^users/', include(router.urls)),
# ] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
#
#
