package pet.haotang.com.pet.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pet.haotang.com.pet.swipbackhelper.SwipeBackHelper;
import pet.haotang.com.pet.util.MProgressDialog;
import pet.haotang.com.pet.util.SharedPreferenceUtil;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/18 17:03
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected MProgressDialog mPDialog;
    protected SharedPreferenceUtil spUtil;

    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
        spUtil = SharedPreferenceUtil.getInstance(mActivity);
        mPDialog = new MProgressDialog(mActivity);
    }

    //获取布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected Activity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initParms(savedInstanceState);
        initView();
        setView();
        setListener();
        getData();
    }

    protected void setView() {

    }

    protected void getData() {

    }

    /**
     * [初始化参数]
     *
     * @param savedInstanceState
     */
    public abstract void initParms(Bundle savedInstanceState);

    /**
     * [设置监听]
     */
    protected void setListener() {
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
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
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
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
