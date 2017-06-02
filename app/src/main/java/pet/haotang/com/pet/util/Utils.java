package pet.haotang.com.pet.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.youth.banner.Banner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pet.haotang.com.pet.R;
import pet.haotang.com.pet.bannerimageloader.GlideImageLoader;
import pet.haotang.com.pet.photoview.ImagePagerActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/19 11:01
 */
public class Utils {

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static void setAdialog(Activity mContext, List<AdInfo> advInfoListList, boolean overScreen, ViewPager.PageTransformer pageTransformer,
                                  int padding, float widthPerHeight, int backViewColor, boolean animBackViewTransparent, boolean dialogCloseable,
                                  double bounciness, double speed,
                                  AdManager.OnImageClickListener onImageClickListener, View.OnClickListener onClickListener
    ) {
        AdManager adManager = new AdManager(mContext, advInfoListList);
        adManager
/**
 * 设置弹窗背景全屏显示还是在内容区域显示
 */
                .setOverScreen(overScreen)
/**
 * 设置ViewPager的滑动动画
 */
                .setPageTransformer(pageTransformer)
/**
 * 设置弹窗距离屏幕两侧的距离（单位dp）
 */
                .setPadding(padding)
/**
 * 设置弹窗的宽高比
 */
                .setWidthPerHeight(widthPerHeight)
/**
 * 设置弹窗的背景色（当弹窗背景设置透明时，此设置失效）
 */
                .setBackViewColor(backViewColor)
/**
 * 设置弹窗背景是否透明
 */
                .setAnimBackViewTransparent(animBackViewTransparent)
/**
 * 设置弹窗关闭图标是否可见
 */
                .setDialogCloseable(dialogCloseable)
/**
 * 设置弹窗弹性滑动弹性值
 */
                .setBounciness(bounciness)
/**
 * 设置弹窗弹性滑动速度值
 */
                .setSpeed(speed)
/**
 * 设定弹窗点击事件回调
 */
                .setOnImageClickListener(onImageClickListener)
/**
 * 设定关闭按钮点击事件回调
 */
                .setOnCloseClickListener(onClickListener)
/**
 * 开始执行弹窗的显示操作，可传值为0-360，0表示从右开始弹出，逆时针方向，也可以传入自定义的方向值
 */
                .showAdDialog(AdConstant.ANIM_UP_TO_DOWN);
    }

    public static void startBanner(Banner banner, int bannerStyle, List<String> imgUrls, Class<? extends ViewPager.PageTransformer> transformer, List<String> titles, boolean isAutoPlay, int delayTime, int type) {
        if (banner != null) {
            //设置banner样式
            banner.setBannerStyle(bannerStyle);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            if (imgUrls != null && imgUrls.size() > 0) {
                banner.setImages(imgUrls);
            }
            banner.setImages(imgUrls);
            //设置banner动画效果
            banner.setBannerAnimation(transformer);
            //设置标题集合（当banner样式有显示title时）
            if (titles != null && titles.size() > 0) {
                banner.setBannerTitles(titles);
            }
            //设置自动轮播，默认为true
            banner.isAutoPlay(isAutoPlay);
            //设置轮播时间
            banner.setDelayTime(delayTime);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(type);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    public static void setStringTextGone(TextView tv, String str) {
        if (str != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public static enum JSON_TYPE {
        /**
         * JSONObject
         */
        JSON_TYPE_OBJECT,
        /**
         * JSONArray
         */
        JSON_TYPE_ARRAY,
        /**
         * 不是JSON格式的字符串
         */
        JSON_TYPE_ERROR
    }

    public static JSON_TYPE getJSONType(String str) {
        if (TextUtils.isEmpty(str)) {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }

        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        if (firstChar == '{') {
            return JSON_TYPE.JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return JSON_TYPE.JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }

    public static void setText(TextView tv, String str, String defaultStr,
                               int visibilt, int defaultVisibilt) {
        if (str != null && !TextUtils.isEmpty(str)) {
            tv.setText(str);
            tv.setVisibility(visibilt);
        } else {
            tv.setText(defaultStr);
            tv.setVisibility(defaultVisibilt);
        }
    }

    public static boolean isStrNull(String str) {
        boolean bool = false;
        if (str != null && !TextUtils.isEmpty(str)) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    public static boolean checkLogin(Context mContext) {
        boolean bool;
        String cellphone = SharedPreferenceUtil.getInstance(mContext)
                .getString("cellphone", "");
        int userid = SharedPreferenceUtil.getInstance(mContext).getInt(
                "userid", 0);
        if (cellphone != null && !cellphone.isEmpty() && userid > 0) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * RxJava方式保存图片到本地
     *
     * @param context
     * @param mImageView
     * @param petCircle
     * @param fileName
     */
    public static void savePic(final Context context,
                               final ImageView mImageView, final File petCircle,
                               final String fileName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(
                new String[]{context.getResources().getString(
                        R.string.save_picture)},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveImageView(getViewBitmap(mImageView), petCircle,
                                context, fileName);
                    }
                });
        builder.show();
    }

    private static class SaveObservable implements
            Observable.OnSubscribe<String> {
        private Bitmap drawingCache = null;
        private File petCircle;
        private String fileName;

        public SaveObservable(Bitmap drawingCache, File petCircle,
                              String fileName) {
            this.drawingCache = drawingCache;
            this.petCircle = petCircle;
            this.fileName = fileName;
        }

        @Override
        public void call(Subscriber<? super String> subscriber) {
            if (drawingCache == null) {
                subscriber.onError(new NullPointerException(
                        "imageview的bitmap获取为null,请确认imageview显示图片了"));
            } else {
                try {
                    File imageFile = new File(this.petCircle, this.fileName);
                    FileOutputStream outStream;
                    outStream = new FileOutputStream(imageFile);
                    drawingCache.compress(Bitmap.CompressFormat.JPEG, 100,
                            outStream);
                    subscriber.onNext(Environment.getExternalStorageDirectory()
                            .getPath());
                    subscriber.onCompleted();
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }
    }

    private static class SaveSubscriber extends Subscriber<String> {
        private Context context;

        public SaveSubscriber(Context context) {
            this.context = context;
        }

        @Override
        public void onCompleted() {
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Log.i(getClass().getSimpleName(), e.toString());
            Toast.makeText(context, "保存失败——> " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(String s) {/*
                                     * Toast.makeText(context, "保存路径为：-->  " +
									 * s, Toast.LENGTH_SHORT) .show();
									 */
        }
    }

    private static void saveImageView(Bitmap drawingCache, File petCircle,
                                      Context context, String fileName) {
        Observable
                .create(new SaveObservable(drawingCache, petCircle, fileName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SaveSubscriber(context));
    }

    /**
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    private static Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static void imageZoom(Context context, int position, String[] urls) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    @SuppressLint("NewApi")
    public static void hideBottomUIMenu(Activity context) {
        // 隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower
            // api
            View v = context.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
