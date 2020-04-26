package MNSDK.inface;

import com.mn.bean.restfull.AuthenticationBean;
import com.mn.bean.restfull.BaseBean;
import com.mn.bean.restfull.CloudAlarmsBean;
import com.mn.bean.restfull.DevListSortBean;
import com.mn.bean.restfull.DevOnlineBean;
import com.mn.bean.restfull.DevStateInfoBean;
import com.mn.bean.restfull.DeviceInfoBean;
import com.mn.bean.restfull.FavoritePointSaveBean;
import com.mn.bean.restfull.FavoritesInfoBean;
import com.mn.bean.restfull.FirmwareVerBean;
import com.mn.bean.restfull.LoginBean;
import com.mn.bean.restfull.FavoriteDelteBean;
import com.mn.bean.restfull.MeToOtherBean;
import com.mn.bean.restfull.OtherToMeBean;
import com.mn.bean.restfull.PushconfigBean;
import com.mn.bean.restfull.Record24AlarmBean;
import com.mn.bean.restfull.ShareUserListBean;
import com.mn.bean.restfull.SharedHistoryBean;
import com.mn.bean.restfull.UpdateDeviceCoverBean;
import com.mn.bean.restfull.WaitingShareDevBean;

/**
 * Created by Administrator on 2020/1/2 0002.
 */

public class MNKitInterface {

    public interface LoginCallBack {
        void onLoginFailed(String msg);

        void onLoginSuccess(LoginBean response);
    }

    public interface DevListCallBack {
        void onGetDevListFailed(String msg);

        void onGetDevListSuccess(DevListSortBean msg);
    }

    public interface GetDevceBySnCallBack {
        void onGetDevceBySnFailed(String msg);

        void onGetDevceBySnSuc(DeviceInfoBean response);
    }

    public interface DeviceStateInfoCallBack {

        void onGetDeviceStateFailed(String message);

        void onGetDeviceStateSuc(DevStateInfoBean response);
    }

    public interface DeviceBindViewCallBack {
        void onBindDeviceFailed(String message);

        void onBindDeviceSuc(BaseBean response);
    }

    public interface BindShareDeviceViewCallBack {
        void onBindShareDeviceViewFailed(String msg);

        void onBindShareDeviceViewSuc(BaseBean response);
    }

    public interface ModifyDeviceNameCallBack {
        void onModifyDeviceNameFailed(String message);

        void onModifyDeviceNameSuc(BaseBean response);
    }

    public interface UpdateDeviceCoverCallBack {
        void onUpdateDeviceCoverFailed(String msg, int id);

        void onUpdateDeviceCoverSuc(UpdateDeviceCoverBean response, int id);
    }

    public interface DevOnlineStateCallBack {
        void onGetOnLineStateSucc(DevOnlineBean result);

        void onGetOnLineStateFailed(String msg);
    }


    public interface CloudAlarmsCallBack {
        void onGetCloudAlarmsFailed(String msg);

        void onGetCloudAlarmsSuc(CloudAlarmsBean response);
    }


    public interface AuthenticationUrlCallBack {
        void onAuthenticationUrlSuc(AuthenticationBean url);

        void onAuthenticationUrlFailed(String msg);
    }


    public interface UnbindDeviceCallBack {
        void onUnbindDeviceFailed(String message);

        void onUnbindDeviceSuc(BaseBean response);
    }

    public interface UnBindShareDeviceCallBack {
        void onUnBindShareDeviceFailed(String localizedMessage);

        void onUnBindShareDeviceSuc(BaseBean response);
    }

    public interface CancelShareDeviceCallBack {
        void onCancelShareDeviceFailed(String localizedMessage);

        void onCancelShareDeviceSuc(BaseBean response);
    }

    public interface GetDevPushconfigCallBack {
        void onGetDevPushconfigFailed(String msg);

        void onGetDevPushconfigSuc(PushconfigBean response);
    }

    public interface SetDevPushconfigCallBack {
        void onSetDevPushconfigSuc(BaseBean response);

        void onSetDevPushconfigFailed(String msg);
    }

    public interface AuthcodeCallBack {
        void onGetAuthcodeSuc(BaseBean result);

        void onGetAuthcodeFailed(String msg);
    }

    public interface RegiterUserCallBack {
        void onRegiterUserSuc(BaseBean result);

        void onRegiterUserFailed(String msg);
    }


    public interface SetUserPasswordCallBack {
        void onSetUserPasswordSuc(BaseBean result);

        void onSetUserPasswordFailed(String msg);
    }

    public interface GetFavoritePointsInfoCallBack {
        void onGetFavoritePointsInfoSuc(FavoritesInfoBean bean);

        void onGetFavoritePointsInfoFailed(String msg);
    }

    public interface DelteFavoritePointsCallBack {
        void onDelteFavoritePointsSuc(FavoriteDelteBean bean);

        void onDelteFavoritePointsFailed(String o);
    }

    public interface SaveFavoritePointsCallBack {
        void onSaveFavoritePointsSuc(FavoritePointSaveBean bean);

        void onSaveFavoritePointsFailed(String msg);
    }

    public interface GetInviteShareUsersCallBack {
        void onGetInviteShareUsersSuc(ShareUserListBean response);

        void onGetInviteShareUsersFailed(String msg);
    }


    public interface GetSharedHistoryCallBack {
        void onGetSharedHistoryFailed(String msg);

        void onGetSharedHistorySuc(SharedHistoryBean response);
    }

    public interface ShareDevToAccountCallBack {
        void onSharedDevToAccountFailed(String msg);

        void onSharedDevToAccountSuc(BaseBean baseBean);
    }

    public interface GetShareDevQrCodeCallBack {
        void onGetShareDevQrCodeSuc(byte[] bytes);

        void onGetShareDevQrCodeFailed(String msg);
    }


    public interface GetShareDevListsCallBack {
        void onGetShareDevListsSuc(MeToOtherBean bean);

        void onGetShareDevListsFailed(String msg);
    }

    public interface GetOhterShareDevListsCallBack {
        void onGetOhterShareDevListsSuc(OtherToMeBean response);

        void onGetOhterShareDevListsFailed(String msg);
    }

    public interface UpdateShareDeviceAuthorityCallBack {
        void onUpdateShareDeviceAuthoritySuc();

        void onUpdateShareDeviceAuthorityFailed(String msg);
    }

    public interface Get24HCloudRecordCallBack {
        void onGet24HCloudRecordFailed(String msg);

        void onGet24HCloudRecordSuc(Record24AlarmBean response);
    }

    public interface DelAlarmsCallBack {
        void onDelAlarmsSuc();

        void onDelAlarmsFailed(String msg);
    }

    public interface DelAlarmsByTimeCallBack {
        void onDelAlarmsByTimeSuc();

        void onDelAlarmsByTimeFailed(String msg);
    }

    public interface AlarmModifyStateCallBack {
        void onModifyStateFailed(String string);

        void onModifyStateSuc();
    }

    public interface AlarmModifyStateByTimeCallBack {
        void onModifyStateByTimeFailed(String string);

        void onModifyStateByTimeSuc();
    }

    public interface GetFirmwareVerCallBack {
        void onGetFirmwareVerSuc(FirmwareVerBean verBean);

        void onGetFirmwareVerFailed(String msg);
    }

    public interface GetShareWaitingDevCallBack {
        void onGetShareWaitingDevFailed(String msg);

        void onGetShareWaitingDevSuc(WaitingShareDevBean response);
    }

    public interface ReceivedShareDeviceCallBack {
        void onReceivedShareDeviceSuc(BaseBean response, int received);

        void onReceivedShareDeviceFailed(String msg, int received);
    }

}
