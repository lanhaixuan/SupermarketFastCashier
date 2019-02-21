from rest_framework import viewsets
from django.http import HttpResponse
from django.shortcuts import render


from .sms import smsInit
from .models import Goods, Customers, MobileVerf, Order, OrderItems
from .serializers import GoodsSerializerMode, CustomersSerailizer, MobileVerfSerailizer, OrderSerailizer, OrderItemsSerailizer


class GoodsViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializerMode


class CustomersViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Customers.objects.all()
    serializer_class = CustomersSerailizer

    def get_parser_context(self, http_request):
        content = MobileVerf.objects.all()[0]
        # c = Customers.objects.filter(mobile=content.phonenum).exists()
        if Customers.objects.filter(mobile=content.phonenum).exists() is False:
            customer = Customers()
            customer.mobile = content.phonenum
            customer.save()


class MobileVerfViewSet(viewsets.ModelViewSet):
    queryset = MobileVerf.objects.all()
    serializer_class = MobileVerfSerailizer

    def perform_create(self, serializer):
        phone = serializer.validated_data['phonenum']
        serializer.save(code=smsInit(phone))


class OrderViewSet(viewsets.ModelViewSet):
    queryset = Order.objects.all()
    serializer_class = OrderSerailizer


class OrderItemsViewSet(viewsets.ModelViewSet):
    queryset = OrderItems.objects.all()
    serializer_class = OrderItemsSerailizer

    def perform_create(self, serializer):
        serializer.save()
        if serializer.validated_data.get('is_end', ''):
            num = serializer.validated_data.get('orderid')
            filt = OrderItems.objects.filter(orderid=num)
            amout = 0
            for f in filt:
                price = serializer.validated_data.get('goodprice')
                count = serializer.validated_data.get('quantity')
                amout += price * float(count)
            order = Order.objects.all()[0]
            order.amount = amout
            order.save()


def orderdetailed(request):
    orderId = OrderItems.objects.order_by('-add_time').all()[0].orderid
    orderitem = OrderItems.objects.filter(orderid=orderId)
    items = []
    amout = 0.0
    items.append('< \r\n')
    head = '      超市快捷收银系统\n\n商品名      价格        数量\n'
    items.append(head.encode('gbk'))
    for item in orderitem:
        ordername = str(item.goodsname)
        orderprice = item.goodprice
        orderquantity = item.quantity
        amout += orderprice * orderquantity
        items.append((ordername + ' ' + str(orderprice) + '   ' + str(orderquantity) + '\n').encode('gbk'))
    foot = '总价: '
    foot += str(amout) + '\n'
    items.append(foot.encode('gbk'))
    items.append('>\r\n')
    return HttpResponse(items)


def index(request):
    orderId = OrderItems.objects.order_by('-add_time').all()[0].orderid
    orderitem = OrderItems.objects.filter(orderid=orderId)
    return render(request, 'index.html', {'orderitem': orderitem})


