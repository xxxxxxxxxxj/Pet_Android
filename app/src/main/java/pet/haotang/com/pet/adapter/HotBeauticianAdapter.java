package pet.haotang.com.pet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pet.haotang.com.pet.R;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVAdapter;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVHolder;
import pet.haotang.com.pet.entity.Beautician;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 18:46
 */
public class HotBeauticianAdapter extends EasyRVAdapter<Beautician> {
    public HotBeauticianAdapter(Context context, List<Beautician> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, Beautician beautician) {
        ImageView iv_mainbeauticianitem_photo = viewHolder
                .getView(R.id.iv_mainbeauticianitem_photo);
        TextView tv_mainbeauticianitem_name = viewHolder
                .getView(R.id.tv_mainbeauticianitem_name);
        TextView tv_mainbeauticianitem_sign = viewHolder
                .getView(R.id.tv_mainbeauticianitem_sign);
        LinearLayout ll_item_mainfrag_hotbeau = viewHolder
                .getView(R.id.ll_item_mainfrag_hotbeau);
        if (beautician != null) {
            if (beautician.colorId == 0) {
                ll_item_mainfrag_hotbeau.setBackgroundColor(mContext
                        .getResources().getColor(R.color.aECF4F5));
            } else if (beautician.colorId == 1) {
                ll_item_mainfrag_hotbeau.setBackgroundColor(mContext
                        .getResources().getColor(R.color.aEAE8F2));
            } else if (beautician.colorId == 2) {
                ll_item_mainfrag_hotbeau.setBackgroundColor(mContext
                        .getResources().getColor(R.color.aF8F1E8));
            }
            GlideUtils.loadNetImg(mContext, beautician.image, R.drawable.icon_production_default, R.drawable.icon_production_default, new CropCircleTransformation(mContext), iv_mainbeauticianitem_photo);
            Utils.setText(tv_mainbeauticianitem_name, beautician.name, "",
                    View.VISIBLE, View.GONE);
            Utils.setText(tv_mainbeauticianitem_sign, beautician.levelname
                    + "美容师", "", View.VISIBLE, View.GONE);
        }
    }
}
