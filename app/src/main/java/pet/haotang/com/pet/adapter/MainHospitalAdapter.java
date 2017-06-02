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
import pet.haotang.com.pet.entity.MainCharacteristicService;
import pet.haotang.com.pet.entity.MainHospital;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Utils;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 19:36
 */
public class MainHospitalAdapter extends EasyRVAdapter<MainHospital> {
    public MainHospitalAdapter(Context context, List<MainHospital> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, MainHospital mainHospital) {
        ImageView iv_item_mainfraghospital = viewHolder
                .getView(R.id.iv_item_mainfraghospital);
        TextView tv_item_mainfraghospital_title = viewHolder
                .getView(R.id.tv_item_mainfraghospital_title);
        TextView tv_item_mainfraghospital_address = viewHolder
                .getView(R.id.tv_item_mainfraghospital_address);
        if (mainHospital != null) {
            GlideUtils.loadNetImg(mContext, mainHospital.avatar,R.drawable.icon_production_default,R.drawable.icon_production_default,null,iv_item_mainfraghospital);
            Utils.setText(tv_item_mainfraghospital_title,
                    mainHospital.name, "", View.VISIBLE,
                    View.INVISIBLE);
            Utils.setText(tv_item_mainfraghospital_address,
                    mainHospital.addr, "", View.VISIBLE,
                    View.INVISIBLE);
        }
    }

}
