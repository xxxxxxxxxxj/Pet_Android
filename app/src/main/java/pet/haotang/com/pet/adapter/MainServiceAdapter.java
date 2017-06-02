package pet.haotang.com.pet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pet.haotang.com.pet.R;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVAdapter;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVHolder;
import pet.haotang.com.pet.entity.MainService;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.SharedPreferenceUtil;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 18:15
 */
public class MainServiceAdapter extends EasyRVAdapter<MainService> {
    private SharedPreferenceUtil spUtil;

    public MainServiceAdapter(Context context, List<MainService> list, int... layoutIds) {
        super(context, list, layoutIds);
        spUtil = SharedPreferenceUtil.getInstance(context);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, MainService mainService) {
        ImageView sriv_mainserviceitem_img = viewHolder.getView(R.id.sriv_mainserviceitem_img);
        ImageView iv_mainserviceitem_hot = viewHolder.getView(R.id.iv_mainserviceitem_hot);
        TextView tv_mainserviceitem_name = viewHolder.getView(R.id.tv_mainserviceitem_name);
        if (mainService != null) {
            GlideUtils.loadNetImgBitMapTransform(position, mContext, mainService.strimg, R.drawable.icon_production_default, R.drawable.icon_production_default, sriv_mainserviceitem_img);
            Utils.setText(tv_mainserviceitem_name, mainService.name, "", View.VISIBLE,
                    View.VISIBLE);
            if (mainService.newOrHot == 0) {// 非热门最新
                iv_mainserviceitem_hot.setVisibility(View.GONE);
            } else if (mainService.newOrHot == 1 || mainService.newOrHot == 2) {
                if (mainService.newOrHot == 1) {//热门
                    int hotPoint = spUtil.getInt("hotPoint" + mainService.point, 0);
                    if (hotPoint >= 3) {
                        iv_mainserviceitem_hot.setVisibility(View.GONE);
                    } else {
                        iv_mainserviceitem_hot.setVisibility(View.VISIBLE);
                        iv_mainserviceitem_hot
                                .setBackgroundResource(R.drawable.icon_hot_mainservice);
                    }
                } else if (mainService.newOrHot == 2) {//最新
                    int newPoint = spUtil.getInt("newPoint" + mainService.point, 0);
                    if (newPoint >= 3) {
                        iv_mainserviceitem_hot.setVisibility(View.GONE);
                    } else {
                        iv_mainserviceitem_hot.setVisibility(View.VISIBLE);
                        iv_mainserviceitem_hot
                                .setBackgroundResource(R.drawable.icon_new_mainservice);
                    }
                }
            }
        }
    }

}
