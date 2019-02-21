import xadmin
from .models import MobileVerifyRecord


class MobileVerifyRecordAdmin(object):
    list_display = ['code', 'mobile', 'send_time']
    search_fields = ['code', 'mobile']
    list_filter = ['code', 'mobile', 'send_time']


xadmin.site.register(MobileVerifyRecord, MobileVerifyRecordAdmin)