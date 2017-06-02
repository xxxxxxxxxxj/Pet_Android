package pet.haotang.com.pet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import pet.haotang.com.pet.R;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:加载图片工具类</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/20 18:20
 */
public class GlideUtils {
    private Context mContext;
    private String url;
    private ImageView image;

    // 显示数据
    private int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, mContext.getResources().getDisplayMetrics());
    private int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, mContext.getResources().getDisplayMetrics());

    public void load() {
        Glide.with(mContext).load(url).into(image);

        //（2）加载资源图片
        Glide.with(mContext).load(R.drawable.logo).into(image);

        //（3）加载本地图片
        String path = Environment.getExternalStorageDirectory() + "/meinv1.jpg";
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Glide.with(mContext).load(uri).into(image);

        // （4）加载网络gif
        String gifUrl = "http://b.hiphotos.baidu.com/zhidao/pic/item/faedab64034f78f066abccc57b310a55b3191c67.jpg";
        Glide.with(mContext).load(gifUrl).placeholder(R.mipmap.ic_launcher).into(image);

        // （5）加载资源gif
        Glide.with(mContext).load(R.drawable.loading).asGif().placeholder(R.mipmap.ic_launcher).into(image);

        //（6）加载本地gif
        String gifPath = Environment.getExternalStorageDirectory() + "/meinv2.jpg";
        File gifFile = new File(gifPath);
        Glide.with(mContext).load(gifFile).placeholder(R.mipmap.ic_launcher).into(image);

        //（7）加载本地小视频和快照
        String videoPath = Environment.getExternalStorageDirectory() + "/video.mp4";
        File videoFile = new File(videoPath);
        Glide.with(mContext).load(Uri.fromFile(videoFile)).placeholder(R.mipmap.ic_launcher).into(image);

        //（8）设置缩略图比例,然后，先加载缩略图，再加载原图
        String urlPath = Environment.getExternalStorageDirectory() + "/meinv1.jpg";
        Glide.with(mContext).load(new File(urlPath)).thumbnail(0.1f).centerCrop().placeholder(R.mipmap.ic_launcher).into(image);

        //（9）先建立一个缩略图对象，然后，先加载缩略图，再加载原图
        DrawableRequestBuilder thumbnailRequest = Glide.with(mContext).load(new File(urlPath));
        Glide.with(mContext).load(Uri.fromFile(videoFile)).thumbnail(thumbnailRequest).centerCrop().placeholder(R.mipmap.ic_launcher).into(image);

        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.ic_launcher) //占位图
                .error(R.mipmap.ic_launcher)  //出错的占位图
                .override(width, height) //图片显示的分辨率 ，像素值 可以转化为DP再设置
                .animate(R.anim.slide_in_right)//图片加载动画
                .centerCrop()//CenterCrop()会缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分。ImageVIew会被完全填充满，但是图片可能不能完全显示出。
                .fitCenter()//fitCenter()会缩放图片让两边都相等或小于ImageView的所需求的边框。图片会被完整显示，可能不能完全填充整个ImageView。
                .into(image);
    }

    public static void loadNetImgBitMapTransform(int position,Context mContext, String imgUrl, int placeholderResourceId, int errorResourceId,ImageView image) {
        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlContans.getBaseUrl() + imgUrl;
            }
        }
        /**
         * 图片变换
         */
        switch (position+1) {
            case 1: {
                int width = Utils.dip2px(mContext, 133.33f);
                int height = Utils.dip2px(mContext, 126.33f);
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .override(width, height)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable.mask_starfish))
                        .into(image);
                break;
            }
            case 2: {
                int width = Utils.dip2px(mContext, 150.0f);
                int height = Utils.dip2px(mContext, 100.0f);
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .override(width, height)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable.mask_chat_right))
                        .into(image);
                break;
            }
            case 3:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.TOP))
                        .into(image);
                break;
            case 4:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new CropTransformation(mContext, 300, 100))
                        .into(image);
                break;
            case 5:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM))
                        .into(image);

                break;
            case 6:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(image);
                break;
            case 7:
                Glide.with(mContext)
                        .load(imgUrl)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(image);
                break;
            case 8:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(80, 255, 0, 0)))
                        .into(image);
                break;
            case 9:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(image);
                break;
            case 10:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0,
                                RoundedCornersTransformation.CornerType.BOTTOM))
                        .into(image);
                break;
            case 11:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new BlurTransformation(mContext, 25))
                        .into(image);
                break;
            case 12:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(image);
                break;
            case 13:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new SepiaFilterTransformation(mContext))
                        .into(image);
                break;
            case 14:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new ContrastFilterTransformation(mContext, 2.0f))
                        .into(image);
                break;
            case 15:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new InvertFilterTransformation(mContext))
                        .into(image);
                break;
            case 16:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new PixelationFilterTransformation(mContext, 20))
                        .into(image);
                break;
            case 17:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new SketchFilterTransformation(mContext))
                        .into(image);
                break;
            case 18:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(
                                new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f)))
                        .into(image);
                break;
            case 19:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, 0.5f))
                        .into(image);
                break;
            case 20:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new KuwaharaFilterTransformation(mContext, 25))
                        .into(image);
                break;
            case 21:
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId) //占位图
                        .error(errorResourceId)  //出错的占位图
                        .bitmapTransform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                                new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                        .into(image);
                break;
        }
    }

    public static void loadNetImg(Context mContext, String imgUrl, int placeholderResourceId, int errorResourceId, Transformation<Bitmap> transformation, ImageView imageView) {
        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlContans.getBaseUrl() + imgUrl;
            }
        }
        DrawableRequestBuilder<String> stringDrawableRequestBuilder = Glide.with(mContext)
                .load(imgUrl)
                .placeholder(placeholderResourceId) //占位图
                .error(errorResourceId);  //出错的占位图
        if (transformation != null) {
            stringDrawableRequestBuilder.bitmapTransform(transformation);
        } else {
            stringDrawableRequestBuilder.fitCenter();//fitCenter()会缩放图片让两边都相等或小于ImageView的所需求的边框。图片会被完整显示，可能不能完全填充整个ImageView。
        }
        stringDrawableRequestBuilder.into(imageView);
    }
}
