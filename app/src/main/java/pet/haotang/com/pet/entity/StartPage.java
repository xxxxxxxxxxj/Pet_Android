package pet.haotang.com.pet.entity;

import org.json.JSONObject;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/31 10:43
 */
public class StartPage {
    public String img_url;
    public String jump_url;
    public String version;

    public static StartPage json2Entity(JSONObject json) {
        StartPage startPage = new StartPage();
        try {
            if (json.has("img_url") && !json.isNull("img_url")) {
                startPage.img_url = json.getString("img_url");
            }
            if (json.has("jump_url") && !json.isNull("jump_url")) {
                startPage.jump_url = json.getString("jump_url");
            }
            if (json.has("version") && !json.isNull("version")) {
                startPage.version = json.getString("version");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return startPage;
    }
}
