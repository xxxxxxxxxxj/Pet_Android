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
import pet.haotang.com.pet.entity.MainPetCircle;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 19:00
 */
public class MainPetCircleAdapter extends EasyRVAdapter<MainPetCircle> {
    public MainPetCircleAdapter(Context context, List<MainPetCircle> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, MainPetCircle mainPetCircle) {
        ImageView civ_item_mainfragpetcircleimg = viewHolder
                .getView(R.id.civ_item_mainfragpetcircleimg);
        ImageView iv_mainfrag_petcircle = viewHolder
                .getView(R.id.iv_mainfrag_petcircle);
        TextView tv_item_mainfragpetcircle = viewHolder
                .getView(R.id.tv_item_mainfragpetcircle);
        TextView tv_item_mainfragpetcircle_ydl = viewHolder
                .getView(R.id.tv_item_mainfragpetcircle_ydl);
        TextView tv_item_mainfragpetcircle_title = viewHolder
                .getView(R.id.tv_item_mainfragpetcircle_title);
        if (mainPetCircle != null) {
            GlideUtils.loadNetImg(mContext, mainPetCircle.pic,R.drawable.icon_production_default,R.drawable.icon_production_default,null,civ_item_mainfragpetcircleimg);
            GlideUtils.loadNetImg(mContext, mainPetCircle.imgUrl,R.drawable.icon_production_default,R.drawable.icon_production_default,null,iv_mainfrag_petcircle);
            Utils.setText(tv_item_mainfragpetcircle, mainPetCircle.groupName,
                    "", View.VISIBLE, View.INVISIBLE);
            Utils.setText(tv_item_mainfragpetcircle_ydl,
                    mainPetCircle.isReadNum + "", "", View.VISIBLE,
                    View.INVISIBLE);
            Utils.setText(tv_item_mainfragpetcircle_title,
                    mainPetCircle.summary, "", View.VISIBLE, View.INVISIBLE);
        }
    }

}
