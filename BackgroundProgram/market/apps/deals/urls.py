from django.conf.urls import url, include
from deals.views import GoodsViewSet, CustomersViewSet, MobileVerfViewSet, OrderViewSet, OrderItemsViewSet
from rest_framework.routers import DefaultRouter


# 导入settings中media文件, 里面存放图片信息
from django.conf import settings
from django.conf.urls.static import static
from . import views

# 设置路由
router = DefaultRouter()
router.register(r'goods', GoodsViewSet)
# router.register(r'mobileverf', MobileVerfViewSet)
# router.register(r'customers', CustomersViewSet)
# router.register(r'order', OrderViewSet)
router.register(r'orderitems', OrderItemsViewSet)



urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'^detail/$', views.orderdetailed, name='orderdetailed'),
    url(r'^index/$', views.index, name='index'),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)


