from rest_framework import serializers


from deals.models import Goods, Customers, MobileVerf, Order, OrderItems


class GoodsSerializerMode(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = Goods
        fields = '__all__'


class CustomersSerailizer(serializers.HyperlinkedModelSerializer):
    id = serializers.ReadOnlyField()
    class Meta:
        model = Customers
        fields = ('id','mobile', 'nickname', 'image', 'url')


class MobileVerfSerailizer(serializers.HyperlinkedModelSerializer):
    code = serializers.ReadOnlyField()
    class Meta:
        model = MobileVerf
        fields = ('id', 'code', 'phonenum', 'url')


class OrderSerailizer(serializers.ModelSerializer):
    amount = serializers.ReadOnlyField()
    class Meta:
        model = Order
        fields = ('id', 'ordernum','owner', 'amount')


class OrderItemsSerailizer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = OrderItems
        fields = '__all__'
        read_only_fields = ('id', )






