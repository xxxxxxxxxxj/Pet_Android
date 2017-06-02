package pet.haotang.com.pet.entity;

import com.youth.banner.Banner;

import org.json.JSONObject;

import pet.haotang.com.pet.util.UrlContans;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 16:54
 */
public class BannerBean {
    public String url;
    public String imgurl;

    public static BannerBean json2Entity(JSONObject jobj){
        BannerBean b = new BannerBean();
        try {
            if(jobj.has("url")&&!jobj.isNull("url")){
                b.url=jobj.getString("url");
            }
            if(jobj.has("pic")&&!jobj.isNull("pic")){
                b.imgurl= UrlContans.getBaseUrl()+jobj.getString("pic");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return b;
    }
}
