package pet.haotang.com.pet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.lzy.okgo.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;
import pet.haotang.com.pet.base.AppActivity;
import pet.haotang.com.pet.base.BaseFragment;
import pet.haotang.com.pet.entity.StartPage;
import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.util.CacheKeyUtils;
import pet.haotang.com.pet.util.LogUtils;
import pet.haotang.com.pet.util.OkGoUtils;
import pet.haotang.com.pet.util.SharedPreferenceUtil;
import pet.haotang.com.pet.util.ToastUtil;
import pet.haotang.com.pet.util.UrlContans;
import pet.haotang.com.pet.util.Utils;

public class FlashActivity extends AppActivity {
    private View view;
    private String cellphone;
    private boolean guide;
    protected String img_url;
    protected String jump_url;
    private Timer timer;
    private TimerTask task;
    private static int FLASH_DELAYEDTIME = 3000;
    private List<StartPage> listStartPage = new ArrayList<StartPage>();

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleVisibility(View.GONE);
        setSystemBarTint(R.color.actionbar_bg);
    }

    @Override
    public void initParms(Intent intent) {

    }

    @Override
    protected void clickRightTextView() {

    }

    @Override
    protected void clickRightButton() {

    }

    @Override
    protected void clickLeftButton() {

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
        return R.layout.activity_flash;
    }

    @Override
    protected void setView() {
        super.setView();
        Utils.hideBottomUIMenu(this);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // 结束你的activity
            finish();
            return;
        }
        MobclickAgent.setDebugMode(true);
        // 禁止默认统计方式
        MobclickAgent.openActivityDurationTrack(false);
        spUtil = SharedPreferenceUtil.getInstance(this);
        cellphone = spUtil.getString("cellphone", "");
        guide = spUtil.getBoolean("guide", false);
        spUtil.saveBoolean("isExit", true);
        spUtil.saveBoolean("isRestart", true);
        spUtil.saveBoolean("PETINFODELAYEDGONE", true);
        if (guide) {
            getData();
        } else {
            initTimer(0);
        }
    }

    @Override
    public View getContentView() {
        view = View.inflate(this, getContentViewId(), null);
        return view;
    }

    private void initTimer(final int flag) {
        task = new TimerTask() {
            @Override
            public void run() {
                if (guide) {
                    if (flag == 1) {
                        goNext(StartPageActivity.class);
                    } else {
                        goNext(MainActivity.class);
                    }
                } else {
                    goNext(GuideActivity.class);
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, FLASH_DELAYEDTIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
        if (task != null)
            task.cancel();
    }

    protected void getData() {
        super.getData();
        /**
         * 获取广告
         */
        RequestStartPageData();
    }

    private void RequestStartPageData() {
        OkGoUtils.Get_String(FlashActivity.this, UrlContans.startPageUrl, CacheKeyUtils.Key_StartPageUrl, null, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestStartPageData response.code() = " + response.code());
                LogUtils.e("RequestStartPageData s = " + s);
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
                                        if (jdata.has("spcList")
                                                && !jdata.isNull("spcList")) {
                                            JSONArray jarrSpcList = jdata
                                                    .getJSONArray("spcList");
                                            if (jarrSpcList.length() > 0) {
                                                listStartPage.clear();
                                                for (int i = 0; i < jarrSpcList.length(); i++) {
                                                    listStartPage.add(StartPage
                                                            .json2Entity(jarrSpcList
                                                                    .getJSONObject(i)));
                                                }
                                                StartPage startPage = listStartPage.get(0);
                                                if (startPage != null) {
                                                    img_url = startPage.img_url;
                                                    jump_url = startPage.jump_url;
                                                    if (img_url != null
                                                            && !TextUtils.isEmpty(img_url)) {
                                                        initTimer(1);
                                                    } else {
                                                        initTimer(0);
                                                    }

                                                } else {
                                                    initTimer(0);
                                                }
                                            } else {
                                                initTimer(0);
                                            }
                                        } else {
                                            initTimer(0);
                                        }
                                    } else {
                                        initTimer(0);
                                    }
                                } else {
                                    initTimer(0);
                                    if (Utils.isStrNull(msg)) {
                                        ToastUtil.showToastShortCenter(FlashActivity.this,
                                                jobj.getString("msg"));
                                    }
                                }
                            } else {
                                initTimer(0);
                            }
                        } else {
                            initTimer(0);
                        }
                    } catch (Exception e) {
                        initTimer(0);
                        e.printStackTrace();
                        ToastUtil.showToastShortBottom(FlashActivity.this, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                initTimer(0);
                ToastUtil.showToastShortBottom(FlashActivity.this, "请求失败");
            }
        });
    }

    private void goNext(Class clazz) {
        Bundle bundle = new Bundle();
        bundle.putString("img_url", img_url);
        bundle.putString("jump_url", jump_url);
        startActivity(clazz, bundle);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FlashActivity");// 统计页面(仅有Activity的应用中SDK自动调用，不需要
        MobclickAgent.onResume(this); // 统计时长
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FlashActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }
}
