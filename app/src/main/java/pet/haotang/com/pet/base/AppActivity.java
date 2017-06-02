package pet.haotang.com.pet.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pet.haotang.com.pet.R;
import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.swipbackhelper.SwipeListener;
import pet.haotang.com.pet.util.LogUtils;
import pet.haotang.com.pet.util.MProgressDialog;
import pet.haotang.com.pet.util.PermissionUtils;
import pet.haotang.com.pet.util.SharedPreferenceUtil;
import pet.haotang.com.pet.util.SystemUtils;
import pet.haotang.com.pet.view.SystemBarTintManager;

/**
 * Activity基类
 */
public abstract class AppActivity extends BaseActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    protected SharedPreferenceUtil spUtil;
    protected MProgressDialog mPDialog;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.ib_titlebar_back)
    ImageButton ib_titlebar_back;
    @BindView(R.id.ib_titlebar_other)
    ImageButton ib_titlebar_other;
    @BindView(R.id.tv_titlebar_other)
    TextView tv_titlebar_other;
    @BindView(R.id.rl_titlebar_right)
    RelativeLayout rl_titlebar_right;
    @BindView(R.id.tv_titlebar_title)
    TextView tv_titlebar_title;
    @BindView(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;
    @BindView(R.id.ll_children_content)
    LinearLayout ll_children_content;
    @BindView(R.id.ll_app_root)
    LinearLayout ll_app_root;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    protected static boolean isScreenRoate = false;
    protected static boolean isAllowFullScreen = false;
    protected static boolean isSwipeBack = true;
    protected static boolean isSystemBarTint = true;

    /**
     * 获取第一个fragment
     */
    protected abstract BaseFragment getFirstFragment();

    protected int getContentViewId() {
        return R.layout.activity_app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isScreenRoate){
            setScreenRoate();
        }
        if(isAllowFullScreen){
            setAllowFullScreen();
        }
        if(isSwipeBack){
            setSwipeBack();
        }
        if(isSystemBarTint){
            setSystemBarTint(R.color.a3a3636);
        }
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);
        initData();
        initParms(getIntent());
        initView();
        setView();
        setListener();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        Log.d(TAG, "onDestroy()");
    }



    private void initPermissions() {
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_RECORD_AUDIO)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_RECORD_AUDIO, PermissionUtils.CODE_RECORD_AUDIO);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_GET_ACCOUNTS)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_GET_ACCOUNTS, PermissionUtils.CODE_GET_ACCOUNTS);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_READ_PHONE_STATE)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_READ_PHONE_STATE, PermissionUtils.CODE_READ_PHONE_STATE);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_CALL_PHONE)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_CALL_PHONE, PermissionUtils.CODE_CALL_PHONE);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_CAMERA)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_CAMERA, PermissionUtils.CODE_CAMERA);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_ACCESS_FINE_LOCATION)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_ACCESS_FINE_LOCATION, PermissionUtils.CODE_ACCESS_FINE_LOCATION);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_ACCESS_COARSE_LOCATION)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_ACCESS_COARSE_LOCATION, PermissionUtils.CODE_ACCESS_COARSE_LOCATION);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE, PermissionUtils.CODE_READ_EXTERNAL_STORAGE);
        }
        if (SystemUtils.isNeedRequestPermissions(this, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            SystemUtils.requestPermission(this, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE);
        }
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(AppActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    Toast.makeText(AppActivity.this, "Result All Permission Grant", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置滑动回退
     */
    protected void setSwipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.TRANSPARENT)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(new SwipeListener() {//滑动监听
                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                        LogUtils.e("onScroll percent = " + percent + ",px = " + px);
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                        LogUtils.e("onEdgeTouch开始滑动");
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                        LogUtils.e("onScrollToClose滑动关闭");
                    }
                });
    }

    protected void setSystemBarTint(int colorBurn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorBurn);
    }

    protected void initData() {
        spUtil = SharedPreferenceUtil.getInstance(this);
        mPDialog = new MProgressDialog(this);
    }

    private void initView() {
        // 获取子布局
        View childrenView = getContentView();
        if (childrenView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, -1);
            ll_children_content.addView(childrenView, params);
        }
    }

    protected void setView() {

    }

    protected void getData() {

    }

    /**
     * [初始化参数]
     *
     * @param intent
     */
    public abstract void initParms(Intent intent);

    /**
     * [设置监听]
     */
    protected void setListener() {
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(AppActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [全屏]
     */
    public void setAllowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * [设置沉浸状态栏]
     */
    public void setSteepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [屏幕旋转]
     */
    public void setScreenRoate() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 点击右侧的文字的方法
     */
    protected abstract void clickRightTextView();


    /**
     * 点击右侧的按钮的方法
     */
    protected abstract void clickRightButton();

    /**
     * 点击左侧的按钮的方法
     */
    protected abstract void clickLeftButton();

    /**
     * 设置标题栏是否隐藏
     *
     * @param visibility
     */
    public void setTitleVisibility(int visibility) {
        rl_titlebar.setVisibility(visibility);
    }

    /**
     * 设置左侧按钮是否隐藏
     *
     * @param visibility
     */
    public void setLeftButton(int visibility) {
        ib_titlebar_back.setVisibility(visibility);
    }

    /**
     * 设置子布局是否隐藏的方法
     */
    public void setChildView(int visibility) {
        ll_children_content.setVisibility(visibility);
    }

    /**
     * 开启加载框
     */
    public void openDialog() {
        mPDialog.showDialog();
    }

    /**
     * 关闭加载框
     */
    public void closeDialog() {
        if (mPDialog.isShowing()) {
            mPDialog.closeDialog();
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_titlebar_title.setText(title);
    }

    /**
     * 设置右上角文字
     *
     * @param str
     */
    public void setRightStr(String str) {
        tv_titlebar_other.setText(str);
    }

    /**
     * 设置右侧是否隐藏
     *
     * @param visibility
     */
    public void setRight(int visibility) {
        rl_titlebar_right.setVisibility(visibility);
    }

    /**
     * 设置右侧按钮是否隐藏
     *
     * @param visibility
     */
    public void setRightButton(int visibility) {
        ib_titlebar_other.setVisibility(visibility);
    }

    /**
     * 设置右侧文字是否隐藏
     *
     * @param visibility
     */
    public void setRightTextView(int visibility) {
        tv_titlebar_other.setVisibility(visibility);
    }

    /**
     * 获取子视图的方法
     *
     * @return
     */
    public abstract View getContentView();

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @OnClick({R.id.ib_titlebar_back, R.id.ib_titlebar_other, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_titlebar_back:
                clickLeftButton();
                break;
            case R.id.ib_titlebar_other:
                clickRightButton();
                break;
            case R.id.tv_titlebar_other:
                clickRightTextView();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (isNeedCheck) {
            checkPermissions(needPermissions);
        }*/
    }

    /**
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
