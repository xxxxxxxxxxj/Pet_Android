package pet.haotang.com.pet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.umeng.analytics.MobclickAgent;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import pet.haotang.com.pet.base.BaseFragment;
import pet.haotang.com.pet.base.BaseFragmentActivity;
import pet.haotang.com.pet.fragment.MainFragment;
import pet.haotang.com.pet.fragment.MyFragment;
import pet.haotang.com.pet.fragment.OrderFragment;
import pet.haotang.com.pet.fragment.PetCircleOrSelectFragment;
import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.util.CacheKeyUtils;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Global;
import pet.haotang.com.pet.util.LogUtils;
import pet.haotang.com.pet.util.Market;
import pet.haotang.com.pet.util.OkGoUtils;
import pet.haotang.com.pet.util.SystemUtils;
import pet.haotang.com.pet.util.ToastUtil;
import pet.haotang.com.pet.util.UpdateUtil;
import pet.haotang.com.pet.util.UrlContans;
import pet.haotang.com.pet.util.Utils;

/**
 * 首页
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView iv_main_one;
    private View vw_main_one;
    private TextView tv_main_one;
    private LinearLayout ll_main_one;
    private ImageView iv_main_two;
    private View vw_main_two;
    private TextView tv_main_two;
    private LinearLayout ll_main_two;
    private ImageView iv_main_three;
    private View vw_main_three;
    private TextView tv_main_three;
    private LinearLayout ll_main_three;
    private ImageView iv_main_four;
    private View vw_main_four;
    private TextView tv_main_four;
    private LinearLayout ll_main_four;
    private LinearLayout ll_main_bottm;
    private FrameLayout fl_main_content;
    private long exitTime = 0;
    private View view;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private PetCircleOrSelectFragment petCircleOrSelectFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;
    private int selection;
    private String activityPic = "";
    private String activityUrl = "";
    private String countType = "";
    private String HomeActivityPageId = "";
    private String updateTime = "";
    private List<String> pressImgList = new ArrayList<String>();
    private List<String> noPressImgList = new ArrayList<String>();
    private int mypetId;
    public static int[] petServices = new int[4];
    private List<AdInfo> advList = new ArrayList<AdInfo>();
    private List<String> advImgList = new ArrayList<String>();
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private boolean isFirst = true;

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        String oneNormalurl = null;
        String twoNormalurl = null;
        String threeNormalurl = null;
        String fourNormalurl = null;
        if (noPressImgList != null && noPressImgList.size() == 4) {
            oneNormalurl = noPressImgList.get(0);
            twoNormalurl = noPressImgList.get(1);
            threeNormalurl = noPressImgList.get(2);
            fourNormalurl = noPressImgList.get(3);
        }
        GlideUtils.loadNetImg(this,oneNormalurl,R.drawable.tab_home_normal,R.drawable.tab_home_normal,null,iv_main_one);
        GlideUtils.loadNetImg(this,twoNormalurl,R.drawable.tab_knowledge_normal,R.drawable.tab_knowledge_normal,null,iv_main_two);
        GlideUtils.loadNetImg(this,threeNormalurl,R.drawable.tab_order_normal,R.drawable.tab_order_normal,null,iv_main_three);
        GlideUtils.loadNetImg(this,fourNormalurl,R.drawable.tab_my_normal,R.drawable.tab_my_normal,null,iv_main_four);
        tv_main_one.setTextColor(Color.parseColor("#666666"));
        tv_main_two.setTextColor(Color.parseColor("#666666"));
        tv_main_three.setTextColor(Color.parseColor("#666666"));
        tv_main_four.setTextColor(Color.parseColor("#666666"));
    }

    public void setSelect(int selection) {
        this.selection = selection;
    }

    public void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.fl_main_content, mainFragment);
                } else {
                    transaction.show(mainFragment);
                }
                String onePassedurl = null;
                if (pressImgList != null && pressImgList.size() == 4) {
                    onePassedurl = pressImgList.get(0);
                }
                GlideUtils.loadNetImg(this,onePassedurl,R.drawable.tab_home_passed,R.drawable.tab_home_passed,null,iv_main_one);
                tv_main_one.setTextColor(Color.parseColor("#D1494F"));
                break;
            case 1:
                if (petCircleOrSelectFragment == null) {
                    petCircleOrSelectFragment = new PetCircleOrSelectFragment();
                    transaction.add(R.id.fl_main_content, petCircleOrSelectFragment);
                } else {
                    transaction.show(petCircleOrSelectFragment);
                }
                String twoPassedurl = null;
                if (pressImgList != null && pressImgList.size() == 4) {
                    twoPassedurl = pressImgList.get(1);
                }
                GlideUtils.loadNetImg(this,twoPassedurl,R.drawable.tab_knowledge_passed,R.drawable.tab_knowledge_passed,null,iv_main_two);
                tv_main_two.setTextColor(Color.parseColor("#D1494F"));
                break;
            case 2:
                if (orderFragment == null) {
                    orderFragment = new OrderFragment();
                    transaction.add(R.id.fl_main_content, orderFragment);
                } else {
                    transaction.show(orderFragment);
                }
                String threePassedurl = null;
                if (pressImgList != null && pressImgList.size() == 4) {
                    threePassedurl = pressImgList.get(2);
                }
                GlideUtils.loadNetImg(this,threePassedurl,R.drawable.tab_order_passed,R.drawable.tab_order_passed,null,iv_main_three);
                tv_main_three.setTextColor(Color.parseColor("#D1494F"));
                break;
            case 3:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.fl_main_content, myFragment);
                } else {
                    transaction.show(myFragment);
                }
                String fourPassedurl = null;
                if (pressImgList != null && pressImgList.size() == 4) {
                    fourPassedurl = pressImgList.get(3);
                }
                GlideUtils.loadNetImg(this,fourPassedurl,R.drawable.tab_my_passed,R.drawable.tab_my_passed,null,iv_main_four);
                tv_main_four.setTextColor(Color.parseColor("#D1494F"));
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (petCircleOrSelectFragment != null) {
            transaction.hide(petCircleOrSelectFragment);
        }
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    public MainActivity() {
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        fragmentManager = getFragmentManager();
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    public void initParms(Intent intent) {

    }

    @Override
    public void setListener() {
        super.setListener();
        ll_main_one.setOnClickListener(this);
        ll_main_two.setOnClickListener(this);
        ll_main_three.setOnClickListener(this);
        ll_main_four.setOnClickListener(this);
    }

    @Override
    protected void clickRightTextView() {
        startActivity(TestActivity.class);
    }

    @Override
    protected void clickRightButton() {
        ToastUtil.showToastShortBottom(this, "点击了右边的按钮");
    }

    @Override
    protected void clickLeftButton() {
        exit();
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public View getContentView() {
        view = View.inflate(this, getContentViewId(), null);
        iv_main_one = (ImageView) view.findViewById(R.id.iv_main_one);
        vw_main_one = (View) view.findViewById(R.id.vw_main_one);
        tv_main_one = (TextView) view.findViewById(R.id.tv_main_one);
        ll_main_one = (LinearLayout) view.findViewById(R.id.ll_main_one);
        iv_main_two = (ImageView) view.findViewById(R.id.iv_main_two);
        vw_main_two = (View) view.findViewById(R.id.vw_main_two);
        tv_main_two = (TextView) view.findViewById(R.id.tv_main_two);
        ll_main_two = (LinearLayout) view.findViewById(R.id.ll_main_two);
        iv_main_three = (ImageView) view.findViewById(R.id.iv_main_three);
        vw_main_three = (View) view.findViewById(R.id.vw_main_three);
        tv_main_three = (TextView) view.findViewById(R.id.tv_main_three);
        ll_main_three = (LinearLayout) view.findViewById(R.id.ll_main_three);
        iv_main_four = (ImageView) view.findViewById(R.id.iv_main_four);
        vw_main_four = (View) view.findViewById(R.id.vw_main_four);
        tv_main_four = (TextView) view.findViewById(R.id.tv_main_four);
        ll_main_four = (LinearLayout) view.findViewById(R.id.ll_main_four);
        ll_main_bottm = (LinearLayout) view.findViewById(R.id.ll_main_bottm);
        fl_main_content = (FrameLayout) view.findViewById(R.id.fl_main_content);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleVisibility(View.GONE);
        setTabSelection(0);
    }

    @Override
    protected void setSwipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    //返回键返回事件
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToastShortBottom(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MobclickAgent.onKillProcess(this);
            onDestroy();
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);// 统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_one:
                setTabSelection(0);
                break;
            case R.id.ll_main_two:
                setTabSelection(1);
                break;
            case R.id.ll_main_three:
                setTabSelection(2);
                break;
            case R.id.ll_main_four:
                setTabSelection(3);
                break;
        }
    }

    @Override
    protected void setView() {
        super.setView();
        initLocation();
        //开始定位
        startLocation();
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    protected AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                lng = location.getLongitude();
                lat = location.getLatitude();
                LogUtils.e("lat = "+lat);
                LogUtils.e("lng = "+lng);
                if (isFirst) {
                    isFirst = false;
                    /**
                     * 自动登录
                     */
                    RequestAutoLoginData();
                    /**
                     * 注册推送
                     */
                    RequestRegistUserDeviceData();
                }
                stopLocation();
            } else {
                ToastUtil.showToastShortBottom(MainActivity.this, "定位失败，loc is null");
            }
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    protected void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    protected void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    protected void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void getData() {
        super.getData();
        /**
         * 检测更新
         */
        RequestUpdateData();
        /**
         * 获取首页数据
         */
        RequestIndexData();
        /**
         * 获取首页活动
         */
        RequestGetHomeActivityPageData();
    }

    private void RequestRegistUserDeviceData() {
        HttpParams params = new HttpParams();
        params.put("devToken", SystemUtils.getPushID(MainActivity.this));
        params.put("userId", spUtil.getInt("userid", 0));
        if (lat > 0)
            params.put("lat", lat);
        if (lng > 0)
            params.put("lng", lng);
        params.put("channelId", Market.getMarketId(MainActivity.this));
        OkGoUtils.Get_String(MainActivity.this, UrlContans.registUserDeviceUrl, CacheKeyUtils.Key_RegistUserDeviceUrl, params, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestRegistUserDeviceData response.code() = "+response.code());
                LogUtils.e("RequestRegistUserDeviceData s = "+s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        int resultCode = jobj.getInt("code");
                        String msg = jobj.getString("msg");
                        if (0 == resultCode) {
                            LogUtils.e("推送注册成功");
                        } else {
                            if (Utils.isStrNull(msg)) {
                                ToastUtil.showToastShortBottom(MainActivity.this, msg);
                            }
                        }
                    } catch (JSONException e) {
                        ToastUtil.showToastShortBottom(MainActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(MainActivity.this, "请求失败");
            }
        });
    }

    private void RequestAutoLoginData() {
        HttpParams params = new HttpParams();
        params.put("id", spUtil.getInt("userid", 0));
        if (lat != 0 || lng != 0) {
            params.put("lat", lat);
            params.put("lng", lng);
        }
        params.put("channelId", Market.getMarketId(MainActivity.this));
        OkGoUtils.Get_String(MainActivity.this, UrlContans.autoLoginUrl, CacheKeyUtils.Key_AutoLoginUrl, params, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestAutoLoginData response.code() = "+response.code());
                LogUtils.e("RequestAutoLoginData s = "+s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        int resultCode = jobj.getInt("code");
                        String msg = jobj.getString("msg");
                        if (0 == resultCode) {
                            if (jobj.has("data") && !jobj.isNull("data")) {
                                JSONObject jData = jobj.getJSONObject("data");
                                if (jData.has("user") && !jData.isNull("user")) {
                                    JSONObject jUser = jData.getJSONObject("user");
                                    if (jUser.has("areacode")
                                            && !jUser.isNull("areacode")) {
                                        spUtil.saveInt("upRegionId",
                                                jUser.getInt("areacode"));
                                    } else {
                                        spUtil.removeData("upRegionId");
                                    }
                                    if (jUser.has("shopName")
                                            && !jUser.isNull("shopName")) {
                                        spUtil.saveString("upShopName",
                                                jUser.getString("shopName"));
                                    } else {
                                        spUtil.removeData("upShopName");
                                    }
                                    if (jUser.has("shopId") && !jUser.isNull("shopId")) {
                                        spUtil.saveInt("shopid", jUser.getInt("shopId"));
                                    } else {
                                        spUtil.removeData("shopid");
                                    }
                                    if (jUser.has("areaId") && !jUser.isNull("areaId")) {
                                        spUtil.saveInt("areaid", jUser.getInt("areaId"));
                                    } else {
                                        spUtil.removeData("areaid");
                                    }
                                    if (jUser.has("areaName")
                                            && !jUser.isNull("areaName")) {
                                        spUtil.saveString("areaname",
                                                jUser.getString("areaName"));
                                    } else {
                                        spUtil.removeData("areaname");
                                    }
                                    if (jUser.has("homeAddress")
                                            && !jUser.isNull("homeAddress")) {
                                        JSONObject jAddr = jUser
                                                .getJSONObject("homeAddress");
                                        if (jAddr.has("Customer_AddressId")
                                                && !jAddr.isNull("Customer_AddressId")) {
                                            spUtil.saveInt("addressid",
                                                    jAddr.getInt("Customer_AddressId"));
                                        }
                                        if (jAddr.has("lat") && !jAddr.isNull("lat")) {
                                            spUtil.saveString("lat",
                                                    jAddr.getDouble("lat") + "");
                                        }
                                        if (jAddr.has("lng") && !jAddr.isNull("lng")) {
                                            spUtil.saveString("lng",
                                                    jAddr.getDouble("lng") + "");
                                        }
                                        if (jAddr.has("address")
                                                && !jAddr.isNull("address")) {
                                            spUtil.saveString("address",
                                                    jAddr.getString("address"));
                                        }
                                    } else {
                                        spUtil.removeData("addressid");
                                        spUtil.removeData("lat");
                                        spUtil.removeData("lng");
                                        spUtil.removeData("address");
                                    }
                                    if (jUser.has("inviteCode")
                                            && !jUser.isNull("inviteCode")) {
                                        spUtil.saveString("invitecode",
                                                jUser.getString("inviteCode"));
                                    }
                                    if (jUser.has("cellPhone")
                                            && !jUser.isNull("cellPhone")) {
                                        spUtil.saveString("cellphone",
                                                jUser.getString("cellPhone"));
                                    }
                                    if (jUser.has("payWay") && !jUser.isNull("payWay")) {
                                        spUtil.saveInt("payway", jUser.getInt("payWay"));
                                    }
                                    if (jUser.has("userName")
                                            && !jUser.isNull("userName")) {
                                        spUtil.saveString("username",
                                                jUser.getString("userName"));
                                    }
                                    if (jUser.has("avatarPath")
                                            && !jUser.isNull("avatarPath")) {
                                        spUtil.saveString("userimage",
                                                jUser.getString("avatarPath"));
                                    }
                                    if (jUser.has("UserId") && !jUser.isNull("UserId")) {
                                        spUtil.saveInt("userid", jUser.getInt("UserId"));
                                    }
                                    if (jUser.has("pet") && !jUser.isNull("pet")) {
                                        JSONObject jPet = jUser.getJSONObject("pet");
                                        if (jPet.has("isCerti")
                                                && !jPet.isNull("isCerti")) {
                                            spUtil.saveInt("isCerti",
                                                    jPet.getInt("isCerti"));
                                        }
                                        if (jPet.has("PetId") && !jPet.isNull("PetId")) {
                                            spUtil.saveInt("petid",
                                                    jPet.getInt("PetId"));
                                        }
                                        if (jPet.has("mypetId") && !jPet.isNull("mypetId")) {
                                            mypetId = jPet.getInt("mypetId");
                                        } else {
                                            mypetId = 0;
                                        }
                                        if (jPet.has("petKind")
                                                && !jPet.isNull("petKind")) {
                                            spUtil.saveInt("petkind",
                                                    jPet.getInt("petKind"));
                                        }
                                        if (jPet.has("petName")
                                                && !jPet.isNull("petName")) {
                                            spUtil.saveString("petname",
                                                    jPet.getString("petName"));
                                        }
                                        if (jPet.has("avatarPath")
                                                && !jPet.isNull("avatarPath")) {
                                            spUtil.saveString("petimage",
                                                    jPet.getString("avatarPath"));
                                            // 保存选择的宠物的头像
                                            spUtil.saveString(
                                                    "petimg",
                                                    UrlContans.getBaseUrl()
                                                            + jPet.getString("avatarPath"));
                                        }
                                        if (jPet.has("availService")
                                                && !jPet.isNull("availService")) {
                                            JSONArray jarr = jPet
                                                    .getJSONArray("availService");
                                            if (jarr.length() > 0) {
                                                petServices = new int[jarr.length()];
                                                for (int i = 0; i < jarr.length(); i++) {
                                                    petServices[i] = jarr.getInt(i);
                                                }
                                            }
                                        }
                                    } else {
                                        spUtil.removeData("isCerti");
                                        spUtil.removeData("petid");
                                        spUtil.removeData("petKind");
                                        spUtil.removeData("petname");
                                        spUtil.removeData("petimage");
                                    }
                                    if (jUser.has("myPet") && !jUser.isNull("myPet")) {
                                        JSONObject jMyPet = jUser
                                                .getJSONObject("myPet");
                                        if (mypetId > 0 && jMyPet.has("petId") && !jMyPet.isNull("petId")) {
                                            spUtil.saveInt("petid",
                                                    jMyPet.getInt("petId"));
                                        }
                                        if (jMyPet.has("CustomerPetId")
                                                && !jMyPet.isNull("CustomerPetId")) {
                                            spUtil.saveInt("customerpetid",
                                                    jMyPet.getInt("CustomerPetId"));
                                        } else {
                                            spUtil.removeData("customerpetid");
                                        }
                                        if (jMyPet.has("CustomerPetId")
                                                && !jMyPet.isNull("CustomerPetId")) {
                                            spUtil.saveInt("customerpetid",
                                                    jMyPet.getInt("CustomerPetId"));
                                        } else {
                                            spUtil.removeData("customerpetid");
                                        }
                                        if (jMyPet.has("nickName")
                                                && !jMyPet.isNull("nickName")) {
                                            spUtil.saveString("customerpetname",
                                                    jMyPet.getString("nickName"));
                                        } else {
                                            spUtil.removeData("customerpetname");
                                        }
                                        if (jMyPet.has("avatarPath")
                                                && !jMyPet.isNull("avatarPath")) {
                                            String mypetImage = jMyPet
                                                    .getString("avatarPath");
                                            if (!mypetImage.startsWith("http://")
                                                    && !mypetImage
                                                    .startsWith("https://")) {
                                                spUtil.saveString(
                                                        "mypetImage",
                                                        UrlContans.getBaseUrl()
                                                                + jMyPet.getString("avatarPath"));
                                            } else {
                                                spUtil.saveString("mypetImage",
                                                        jMyPet.getString("avatarPath"));
                                            }
                                        } else {
                                            spUtil.removeData("mypetImage");
                                        }
                                    } else {
                                        spUtil.removeData("customerpetname");
                                        spUtil.removeData("customerpetid");
                                        spUtil.removeData("mypetImage");
                                    }
                                }
                            }
                        } else {
                            spUtil.removeData("cellphone");
                            spUtil.removeData("username");
                            spUtil.removeData("userimage");
                            spUtil.removeData("userid");
                            spUtil.removeData("payway");
                            spUtil.removeData("petid");
                            spUtil.removeData("petkind");
                            spUtil.removeData("petname");
                            spUtil.removeData("petimage");
                            spUtil.removeData("addressid");
                            spUtil.removeData("lat");
                            spUtil.removeData("lng");
                            spUtil.removeData("address");
                            spUtil.removeData("serviceloc");
                            if (Utils.isStrNull(msg)) {
                                ToastUtil.showToastShortBottom(MainActivity.this, msg);
                            }
                        }
                    } catch (JSONException e) {
                        ToastUtil.showToastShortBottom(MainActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(MainActivity.this, "请求失败");
            }
        });
    }

    private void RequestGetHomeActivityPageData() {
        OkGoUtils.Get_String(MainActivity.this, UrlContans.getHomeActivityPageUrl, CacheKeyUtils.Key_GetHomeActivityPageUrl, null, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestGetHomeActivityPageData response.code() = "+response.code());
                LogUtils.e("RequestGetHomeActivityPageData s = "+s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        if (jobj != null) {
                            if (jobj.has("code") && !jobj.isNull("code")) {
                                int resultCode = jobj.getInt("code");
                                String msg = jobj.getString("msg");
                                if (0 == resultCode) {
                                    if (jobj.has("data") && !jobj.isNull("data")) {
                                        JSONObject objectData = jobj.getJSONObject("data");
                                        if (objectData.has("homeActivityPage")
                                                && !objectData.isNull("homeActivityPage")) {
                                            JSONObject objectPage = objectData
                                                    .getJSONObject("homeActivityPage");
                                            if (objectPage.has("activityPic")
                                                    && !objectPage.isNull("activityPic")) {
                                                activityPic = objectPage
                                                        .getString("activityPic");
                                            }
                                            if (objectPage.has("activityUrl")
                                                    && !objectPage.isNull("activityUrl")) {
                                                activityUrl = objectPage
                                                        .getString("activityUrl");
                                            }
                                            if (objectPage.has("countType")
                                                    && !objectPage.isNull("countType")) {
                                                countType = objectPage.getString("countType");
                                            }
                                            if (objectPage.has("HomeActivityPageId")
                                                    && !objectPage.isNull("HomeActivityPageId")) {
                                                HomeActivityPageId = objectPage.getString("HomeActivityPageId");
                                            }
                                            advList.clear();
                                            advImgList.clear();
                                            AdInfo adInfo = new AdInfo();
                                            adInfo.setActivityImg(activityPic);
                                            advImgList.add(activityPic);
                                            adInfo.setUrl(activityUrl);
                                            adInfo.setAdId(HomeActivityPageId);
                                            advList.add(adInfo);

                                            /*AdManager adManager = new AdManager(MainActivity.this, advList);
                                            adManager.setOverScreen(true)
                                                    .setPageTransformer(new DepthPageTransformer());
                                                    adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);*/

                                            /*Utils.setAdialog(MainActivity.this, advList, true, new DepthPageTransformer(), 100, 0.75f,
                                                    Color.parseColor("#AA333333"), true, true, 15, 5, new AdManager.OnImageClickListener() {
                                                        @Override
                                                        public void onImageClick(View view, AdInfo advInfo) {
                                                            if (advInfo != null) {
                                                                if (Utils.isStrNull(advInfo.getUrl())) {
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("url", advInfo.getUrl());
                                                                    startActivity(ADActivity.class, bundle);
                                                                } else {
                                                                    Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                }
                                                            } else {
                                                                Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                            }
                                                        }
                                                    }, new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                        }
                                                    });*/

                                            if (objectPage.has("updateTime")
                                                    && !objectPage.isNull("updateTime")) {
                                                updateTime = objectPage.getString("updateTime");
                                                if (spUtil.getString("updateTime", "").equals(
                                                        "")) {// 第一次启动
                                                    Utils.setAdialog(MainActivity.this, advList, true, new DepthPageTransformer(), 100, 0.75f,
                                                            Color.parseColor("#AA333333"), true, true, 15, 5, new AdManager.OnImageClickListener() {
                                                                @Override
                                                                public void onImageClick(View view, AdInfo advInfo) {
                                                                    if (advInfo != null) {
                                                                        if (Utils.isStrNull(advInfo.getUrl())) {
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("url", advInfo.getUrl());
                                                                            startActivity(ADActivity.class, bundle);
                                                                        } else {
                                                                            Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                        }
                                                                    } else {
                                                                        Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                    }
                                                                }
                                                            }, new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                }
                                                            });
                                                    spUtil.saveString("updateTime", updateTime);
                                                } else {// 非第一次
                                                    if (spUtil.getString("updateTime", "").equals(updateTime)) {// 本地存的跟服务器返回一致// 这里判断type// 1 或者 0 // 判断跳转
                                                        if (countType.equals("0")) {

                                                        } else if (countType.equals("1")) {
                                                            spUtil.saveString("updateTime", updateTime);
                                                            Utils.setAdialog(MainActivity.this, advList, true, new DepthPageTransformer(), 100, 0.75f,
                                                                    Color.parseColor("#AA333333"), true, true, 15, 5, new AdManager.OnImageClickListener() {
                                                                        @Override
                                                                        public void onImageClick(View view, AdInfo advInfo) {
                                                                            if (advInfo != null) {
                                                                                if (Utils.isStrNull(advInfo.getUrl())) {
                                                                                    Bundle bundle = new Bundle();
                                                                                    bundle.putString("url", advInfo.getUrl());
                                                                                    startActivity(ADActivity.class, bundle);
                                                                                } else {
                                                                                    Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                                }
                                                                            } else {
                                                                                Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                            }
                                                                        }
                                                                    }, new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                        }
                                                                    });
                                                        }
                                                    } else {// 这里时间不一致 说明服务器刷新了 弹窗活动
                                                        spUtil.saveString("updateTime", updateTime);
                                                        Utils.setAdialog(MainActivity.this, advList, true, new DepthPageTransformer(), 100, 0.75f,
                                                                Color.parseColor("#AA333333"), true, true, 15, 5, new AdManager.OnImageClickListener() {
                                                                    @Override
                                                                    public void onImageClick(View view, AdInfo advInfo) {
                                                                        if (advInfo != null) {
                                                                            if (Utils.isStrNull(advInfo.getUrl())) {
                                                                                Bundle bundle = new Bundle();
                                                                                bundle.putString("url", advInfo.getUrl());
                                                                                startActivity(ADActivity.class, bundle);
                                                                            } else {
                                                                                Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                            }
                                                                        } else {
                                                                            Utils.imageZoom(MainActivity.this, 0, advImgList.toArray(new String[advImgList.size()]));
                                                                        }
                                                                    }
                                                                }, new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (Utils.isStrNull(msg)) {
                                        ToastUtil.showToastShortCenter(MainActivity.this,
                                                jobj.getString("msg"));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToastShortBottom(MainActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(MainActivity.this, "请求失败");
            }
        });
    }

    private void setBottomIconText(int i, String str) {
        if (Utils.isStrNull(str)) {
            switch (i) {
                case 0:
                    tv_main_one.setText(str);
                    break;
                case 1:
                    tv_main_two.setText(str);
                    break;
                case 2:
                    tv_main_three.setText(str);
                    break;
                case 3:
                    tv_main_four.setText(str);
                    break;
            }
        }
    }

    private void RequestIndexData() {
        OkGoUtils.Get_String(MainActivity.this, UrlContans.indexUrl, CacheKeyUtils.Key_IndexUrl, null, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestIndexData response.code() = "+response.code());
                LogUtils.e("RequestIndexData s = "+s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        if (jobj != null) {
                            if (jobj.has("code") && !jobj.isNull("code")) {
                                int resultCode = jobj.getInt("code");
                                String msg = jobj.getString("msg");
                                if (0 == resultCode) {
                                    if (jobj.has("data") && !jobj.isNull("data")) {
                                        JSONObject jdata = jobj.getJSONObject("data");
                                        if (jdata.has("nToBeComment")
                                                && !jdata.isNull("nToBeComment")) {
                                            if (jdata.getInt("nToBeComment") > 0) {
                                                vw_main_three.setVisibility(View.VISIBLE);
                                            } else {
                                                vw_main_three.setVisibility(View.GONE);
                                            }
                                        } else {
                                            vw_main_three.setVisibility(View.GONE);
                                        }
                                        if (jdata.has("index") && !jdata.isNull("index")) {
                                            JSONObject jindex = jdata.getJSONObject("index");
                                            if (jindex.has("bottom") && !jindex.isNull("bottom")) {
                                                JSONObject jbottom = jindex.getJSONObject("bottom");
                                                if (jbottom.has("list") && !jbottom.isNull("list")) {
                                                    pressImgList.clear();
                                                    noPressImgList.clear();
                                                    JSONArray jlist = jbottom.getJSONArray("list");
                                                    for (int i = 0; i < jlist.length(); i++) {
                                                        JSONObject jitem = jlist.getJSONObject(i);
                                                        if (jitem.has("title")
                                                                && !jitem.isNull("title"))
                                                            setBottomIconText(i, jitem.getString("title"));
                                                        if (jitem.has("pic")
                                                                && !jitem.isNull("pic")) {
                                                            noPressImgList.add(jitem
                                                                    .getString("pic"));
                                                        }
                                                        if (jitem.has("picH")
                                                                && !jitem.isNull("picH")) {
                                                            String string = jitem.getString("picH");
                                                            if (string != null
                                                                    && !TextUtils.isEmpty(string)) {
                                                                pressImgList.add(string);
                                                            }
                                                        }
                                                    }
                                                    clearSelection();
                                                    setTabSelection(0);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (Utils.isStrNull(msg)) {
                                        ToastUtil.showToastShortCenter(MainActivity.this,
                                                jobj.getString("msg"));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToastShortBottom(MainActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(MainActivity.this, "请求失败");
            }
        });
    }

    private void RequestUpdateData() {
        OkGoUtils.Get_String(MainActivity.this, UrlContans.getUpdateUrl(MainActivity.this), CacheKeyUtils.Key_UpdateUrl, null, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestUpdateData response.code() = "+response.code());
                LogUtils.e("RequestUpdateData s = "+s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        if (jobj != null) {
                            if (jobj.has("code") && !jobj.isNull("code")) {
                                int resultCode = jobj.getInt("code");
                                String msg = jobj.getString("msg");
                                if (0 == resultCode) {
                                    if (jobj.has("data") && !jobj.isNull("data")) {
                                        String latestVersion = null;
                                        String downloadPath = null;
                                        String versionHint = null;
                                        boolean isUpgrade = false;
                                        JSONObject jData = jobj.getJSONObject("data");
                                        if (jData.has("nversion")
                                                && !jData.isNull("nversion")) {
                                            latestVersion = jData.getString("nversion");
                                        }
                                        if (jData.has("download")
                                                && !jData.isNull("download")) {
                                            downloadPath = jData.getString("download");
                                        }
                                        if (jData.has("text") && !jData.isNull("text")) {
                                            versionHint = jData.getString("text");
                                        }
                                        if (jData.has("mandate")
                                                && !jData.isNull("mandate")) {
                                            isUpgrade = jData.getBoolean("mandate");
                                        }
                                        if (latestVersion != null
                                                && !TextUtils.isEmpty(latestVersion)) {
                                            boolean isLatest = UpdateUtil
                                                    .compareVersion(
                                                            latestVersion,
                                                            Global.getCurrentVersion(MainActivity.this));
                                            if (isLatest) {// 需要下载安装最新版
                                                if (downloadPath != null
                                                        && !TextUtils
                                                        .isEmpty(downloadPath)) {
                                                    if (isUpgrade) {
                                                        // 强制升级
                                                        UpdateUtil.showForceUpgradeDialog(MainActivity.this,
                                                                versionHint,
                                                                downloadPath,
                                                                latestVersion);
                                                    } else {
                                                        // 非强制升级
                                                        UpdateUtil.showUpgradeDialog(MainActivity.this, versionHint,
                                                                downloadPath,
                                                                latestVersion);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (Utils.isStrNull(msg)) {
                                        ToastUtil.showToastShortCenter(MainActivity.this,
                                                jobj.getString("msg"));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToastShortBottom(MainActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(MainActivity.this, "请求失败");
            }
        });
    }
}
