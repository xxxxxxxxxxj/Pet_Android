package pet.haotang.com.pet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pet.haotang.com.pet.base.AppActivity;
import pet.haotang.com.pet.base.BaseFragment;
import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

public class StartPageActivity extends AppActivity implements View.OnClickListener {
    private View view;
    private ImageView iv_landingpage;
    private Button btn_landing_tg;
    private String img_url;
    private String jump_url;
    private TimeCount time;

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
        Bundle extras = intent.getExtras();
        img_url = extras.getString("img_url");
        jump_url = extras.getString("jump_url");
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
    protected int getContentViewId() {
        return R.layout.activity_start_page;
    }

    @Override
    public View getContentView() {
        view = View.inflate(this, getContentViewId(), null);
        iv_landingpage = (ImageView) view.findViewById(R.id.iv_landingpage);
        btn_landing_tg = (Button) view.findViewById(R.id.btn_landing_tg);
        return view;
    }

    @Override
    protected void setListener() {
        super.setListener();
        btn_landing_tg.setOnClickListener(this);
        iv_landingpage.setOnClickListener(this);
    }

    @Override
    protected void setSwipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    @Override
    public void setView() {
        super.setView();
        Utils.hideBottomUIMenu(this);
        time = new TimeCount(3000, 1000);// 构造CountDownTimer对象
        GlideUtils.loadNetImg(this, img_url, R.drawable.icon_production_default, R.drawable.icon_production_default, null, iv_landingpage);
        btn_landing_tg.setVisibility(View.VISIBLE);
        time.start();
    }

    // 注册计时器
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            goNext(MainActivity.class, 0);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_landing_tg.setText("跳过  " + millisUntilFinished / 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null)
            time.cancel();
    }

    private void goNext(Class clazz, int previous) {
        time.cancel();
        Bundle bundle = new Bundle();
        bundle.putString("url", jump_url);
        bundle.putInt("previous", previous);
        startActivity(clazz, bundle);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_landing_tg:
                goNext(MainActivity.class, 0);
                break;
            case R.id.iv_landingpage:
                if (jump_url != null && !TextUtils.isEmpty(jump_url)) {
                    startActivity(new Intent(this, MainActivity.class));
                    goNext(ADActivity.class, 0);
                }
                break;
            default:
                break;
        }
    }
}
