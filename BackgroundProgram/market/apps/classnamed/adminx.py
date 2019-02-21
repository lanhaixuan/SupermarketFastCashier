import xadmin

from .models import ClassUser


class ClassUserAdminx(object):
    list_display = ['imei', 'name', 'studentid', 'score', 'role', 'wifiname', 'punchtime', 'teachername']
    search_fields = ['imei', 'name', 'studentid', 'score', 'role', 'wifiname', 'punchtime', 'teachername']
    list_filter = ['imei', 'name', 'studentid', 'score', 'role', 'wifiname', 'punchtime', 'teachername']


xadmin.site.register(ClassUser, ClassUserAdminx)