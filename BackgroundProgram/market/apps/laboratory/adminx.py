import xadmin

from .models import Device, Devicemove, Lab, User, Userlabrelated


class DeviceAdminx(object):
    list_display = ['device_fromlabid', 'device_image', 'device_inlabid', 'device_name', 'device_no', 'device_type', 'add_time']
    search_fields = ['device_fromlabid', 'device_image', 'device_inlabid', 'device_name', 'device_no', 'device_type']
    list_filter = ['device_fromlabid', 'device_image', 'device_inlabid', 'device_name', 'device_no', 'device_type', 'add_time']


class DevicdmoveAdminx(object):
    list_display = ['device_id', 'devicemove_fromlabid', 'devicemove_img', 'devicemove_time', 'devicemove_tolabid', 'user_id']
    search_fields = ['device_id', 'devicemove_fromlabid', 'devicemove_img', 'devicemove_time', 'devicemove_tolabid', 'user_id']
    list_filter = ['device_id', 'devicemove_fromlabid', 'devicemove_img', 'devicemove_time', 'devicemove_tolabid', 'user_id']


class LabAdminx(object):
    list_display = ['lab_address', 'lab_getuiid', 'lab_img', 'lab_name', 'lab_no']
    search_fields = ['lab_address', 'lab_getuiid', 'lab_img', 'lab_name', 'lab_no']
    list_filter = ['lab_address', 'lab_getuiid', 'lab_img', 'lab_name', 'lab_no']


class UserAdminx(object):
    list_display = ['user_getuid', 'user_name', 'user_role', 'user_tel']
    search_fields = ['user_getuid', 'user_name', 'user_role', 'user_tel']
    list_filter = ['user_getuid', 'user_name', 'user_role', 'user_tel']


class UserlabrelatedAdminx(object):
    list_display = ['lab_id', 'user_id']
    search_fields = ['lab_id', 'user_id']
    list_filter = ['lab_id', 'user_id']


xadmin.site.register(Device, DeviceAdminx)
xadmin.site.register(Devicemove, DevicdmoveAdminx)
xadmin.site.register(Lab, LabAdminx)
xadmin.site.register(User, UserAdminx)
xadmin.site.register(Userlabrelated, UserlabrelatedAdminx)

