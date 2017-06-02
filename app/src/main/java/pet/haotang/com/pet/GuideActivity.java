package pet.haotang.com.pet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import pet.haotang.com.pet.adapter.GuideAdapter;
import pet.haotang.com.pet.base.AppActivity;
import pet.haotang.com.pet.base.BaseFragment;
import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.util.Utils;

public class GuideActivity extends AppActivity implements View.OnClickListener {
    private View view;
    private ViewPager vp;
    private ImageButton ibNext;
    private int[] imagesIds;
    private ArrayList<ImageView> imageList;
    private GuideAdapter adapter;

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
    protected void setView() {
        super.setView();
        Utils.hideBottomUIMenu(this);
        imagesIds = new int[]{R.drawable.guide1, R.drawable.guide3};
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imagesIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(Utils.readBitMap(this, imagesIds[i]));
            imageList.add(iv);
        }
        adapter = new GuideAdapter(this, imageList);
        vp.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setListener() {
        super.setListener();
        ibNext.setOnClickListener(this);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == imagesIds.length - 1) {
                    ibNext.setVisibility(View.VISIBLE);
                } else {
                    ibNext.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public View getContentView() {
        view = View.inflate(this, getContentViewId(), null);
        vp = (ViewPager) view.findViewById(R.id.vp_guide_pager);
        ibNext = (ImageButton) view.findViewById(R.id.ib_guide_next);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_guide_next:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
        }
    }
}
