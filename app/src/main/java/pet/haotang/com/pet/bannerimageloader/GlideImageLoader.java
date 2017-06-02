package pet.haotang.com.pet.bannerimageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import pet.haotang.com.pet.R;
import pet.haotang.com.pet.util.GlideUtils;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if(path instanceof  String){
            GlideUtils.loadNetImg(context, path.toString(),R.drawable.icon_production_default,R.drawable.icon_production_default,null,imageView);
        }else{
            Glide.with(context)
                    .load(path)
                    .placeholder(R.drawable.icon_production_default) //占位图
                    .error(R.drawable.icon_production_default)  //出错的占位图
                    .fitCenter()//fitCenter()会缩放图片让两边都相等或小于ImageView的所需求的边框。图片会被完整显示，可能不能完全填充整个ImageView。
                    .crossFade()
                    .into(imageView);
        }
    }
}
