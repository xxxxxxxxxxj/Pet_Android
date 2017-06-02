package pet.haotang.com.pet.util;

import android.content.Context;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:接口管理工具类</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/21 19:00
 */
public class UrlContans {
    public static String getBaseUrl() {
        //return "https://test.chongwuhome.cn/";// 测试环境
        //return "http://demo.cwjia.cn/";// demo环境
        return "https://api.cwjia.cn/";// 正式环境
    }

    /**
     * 狗证相关接口域名
     *
     * @return
     */
    public static String getWebBaseUrl() {
        return "http://192.168.0.247/";// 测试环境
        //return "http://m.cwjia.cn/";// 正式环境
    }

    /**
     * 首页
     */
    public static final String homePageUrl = getBaseUrl() + "pet/user/homePage";
    /**
     * 自动登录
     */
    public static final String autoLoginUrl = getBaseUrl() + "pet/user/autoLogin";

    /**
     * 检测更新
     */
    public static String getUpdateUrl(Context context) {
        return getBaseUrl() + "static/content/update_android_"
                + SystemUtils.getCurrentVersion(context) + ".html?time=" + System.currentTimeMillis() + "&system=" + SystemUtils.getSource()
                + "_" + SystemUtils.getCurrentVersion(context);
    }

    /**
     * 获取首页数据
     */
    public static final String getHomeActivityPageUrl = getBaseUrl() + "pet/user/getHomeActivityPage";
    /**
     * 获取首页底部icon以及红点数据
     */
    public static final String indexUrl = getBaseUrl() + "pet/user/index";
    /**
     * 注册推送
     */
    public static final String registUserDeviceUrl = getBaseUrl() + "pet/user/registUserDevice";
    /**
     * 获取广告
     */
    public static final String startPageUrl = getBaseUrl() + "pet/user/startPage";
}
