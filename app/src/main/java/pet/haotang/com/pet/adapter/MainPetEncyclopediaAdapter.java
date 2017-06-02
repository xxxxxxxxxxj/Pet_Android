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
import pet.haotang.com.pet.entity.MainPetEncyclopedia;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 19:33
 */
public class MainPetEncyclopediaAdapter extends EasyRVAdapter<MainPetEncyclopedia> {
    public MainPetEncyclopediaAdapter(Context context, List<MainPetEncyclopedia> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, MainPetEncyclopedia mainPetEncyclopedia) {
        ImageView iv_item_mainfragpetencyclopedia = viewHolder
                .getView(R.id.iv_item_mainfragpetencyclopedia);
        TextView tv_item_mainfragpetencyclopedia_title = viewHolder
                .getView(R.id.tv_item_mainfragpetencyclopedia_title);
        if (mainPetEncyclopedia != null) {
            GlideUtils.loadNetImg(mContext, mainPetEncyclopedia.img,R.drawable.icon_production_default,R.drawable.icon_production_default,null,iv_item_mainfragpetencyclopedia);
            Utils.setText(tv_item_mainfragpetencyclopedia_title,
                    mainPetEncyclopedia.title, "", View.VISIBLE, View.INVISIBLE);
        }
    }

}
