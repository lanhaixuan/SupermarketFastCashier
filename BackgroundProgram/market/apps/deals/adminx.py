import xadmin
from xadmin import views


from .models import Goods, Customers, MobileVerf, Order, OrderItems


class BaseSetting(object):
    enable_themes = True
    use_bootswatch = True


class GlobalSetting(object):
    site_title = "超市便捷购物管理系统"
    site_footer = "超市便捷购物"
    menu_style = 'accordion'


class GoodsAdminx(object):
    list_display = ['isbn', 'name', 'price', 'ingredient', 'shelflife', 'productiondate', 'goodstype', 'image', 'address', 'add_time']
    search_fields = ['isbn', 'name', 'price', 'ingredient', 'shelflife', 'productiondate', 'goodstype', 'image', 'address']
    list_filter = ['isbn', 'name', 'price', 'ingredient', 'shelflife', 'productiondate', 'goodstype', 'image', 'address', 'add_time']


class CustomersAdminx(object):
    list_display = ['mobile', 'nickname', 'image']
    search_fields = ['mobile', 'nickname']
    list_filter = ['mobile', 'nickname', 'image']


class MobileVerfAdminx(object):
    list_display = ['code', 'phonenum']
    search_fields = ['code', 'phonenum']
    list_filter = ['code', 'phonenum']


class OrderAdminx(object):
    list_display = ['orderid', 'owner', 'amount']
    search_fields = ['orderid', 'owner', 'amount']
    list_filter = ['orderid', 'owner', 'amount']


class OrderItemsAdminx(object):
    list_display = ['orderid', 'goodsname', 'goodprice', 'quantity', 'imageurl', 'is_degauss', 'is_pay', 'add_time']
    search_fields = ['orderid', 'goodsname', 'goodprice', 'quantity', 'imageurl', 'is_degauss', 'is_pay']
    list_filter = ['orderid', 'goodsname', 'goodprice', 'quantity', 'imageurl', 'is_degauss', 'is_pay', 'add_time']


xadmin.site.register(Goods, GoodsAdminx)
xadmin.site.register(views.BaseAdminView, BaseSetting)
xadmin.site.register(views.CommAdminView, GlobalSetting)
# xadmin.site.register(Customers, CustomersAdminx)
# xadmin.site.register(MobileVerf, MobileVerfAdminx)
# xadmin.site.register(Order,OrderAdminx)
xadmin.site.register(OrderItems, OrderItemsAdminx)

