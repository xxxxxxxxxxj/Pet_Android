package pet.haotang.com.pet.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pet.haotang.com.pet.R;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVAdapter;
import pet.haotang.com.pet.easyadapter.recyclerview.EasyRVHolder;
import pet.haotang.com.pet.entity.MainCharacteristicService;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 19:14
 */
public class MainCharacteristicServiceAdapter extends EasyRVAdapter<MainCharacteristicService> {
    public MainCharacteristicServiceAdapter(Context context, List<MainCharacteristicService> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, MainCharacteristicService MainCharacteristicService) {
        ImageView iv_item_mainfrag_charaser = viewHolder
                .getView(R.id.iv_item_mainfrag_charaser);
        TextView tv_item_mainfrag_charaser_title = viewHolder
                .getView(R.id.tv_item_mainfrag_charaser_title);
        TextView tv_item_mainfrag_charaser_des = viewHolder
                .getView(R.id.tv_item_mainfrag_charaser_des);
        TextView tv_item_mainfrag_charaser_activity = viewHolder
                .getView(R.id.tv_item_mainfrag_charaser_activity);
        TextView tv_item_mainfrag_charaser_price = viewHolder
                .getView(R.id.tv_item_mainfrag_charaser_price);
        TextView tv_item_mainfrag_charaser_num = viewHolder
                .getView(R.id.tv_item_mainfrag_charaser_num);
        ImageView iv_item_mainfrag_hotornew = viewHolder
                .getView(R.id.iv_item_mainfrag_hotornew);
        if (MainCharacteristicService != null) {
            if (MainCharacteristicService.hotOrNew == 0) {
                iv_item_mainfrag_hotornew.setVisibility(View.GONE);
            } else if (MainCharacteristicService.hotOrNew == 1) {
                iv_item_mainfrag_hotornew.setVisibility(View.VISIBLE);
                iv_item_mainfrag_hotornew
                        .setImageResource(R.drawable.iv_item_mainfrag_hot);
            } else if (MainCharacteristicService.hotOrNew == 2) {
                iv_item_mainfrag_hotornew.setVisibility(View.VISIBLE);
                iv_item_mainfrag_hotornew.setVisibility(View.VISIBLE);
                iv_item_mainfrag_hotornew
                        .setImageResource(R.drawable.iv_item_mainfrag_new);
            }
            GlideUtils.loadNetImg(mContext,MainCharacteristicService.pic,R.drawable.icon_production_default,R.drawable.icon_production_default,null,iv_item_mainfrag_charaser);
            Utils.setText(tv_item_mainfrag_charaser_title,
                    MainCharacteristicService.name, "", View.VISIBLE,
                    View.INVISIBLE);
            Utils.setText(tv_item_mainfrag_charaser_des,
                    MainCharacteristicService.content, "", View.VISIBLE,
                    View.INVISIBLE);
            String text = "¥" + MainCharacteristicService.minPrice
                    + MainCharacteristicService.txtPrice;
            SpannableString ss = new SpannableString(text);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                            .getColor(R.color.a999999)),
                    MainCharacteristicService.minPrice.length() + 1, ss
                            .length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0, 1,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3),
                    ss.length() - 1, ss.length(),
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tv_item_mainfrag_charaser_price.setText(ss);
            Utils.setText(tv_item_mainfrag_charaser_num, "已售 "
                            + MainCharacteristicService.totalSoldCount, "",
                    View.VISIBLE, View.INVISIBLE);
        }
    }

}
