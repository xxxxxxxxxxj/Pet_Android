package pet.haotang.com.pet.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

//import com.umeng.analytics.MobclickAgent;

/**
 * 事件统计方法
 *
 * @author 乔彬
 * @Umeng：友盟统计SDK版本5.0以上，更新时间2015-10-19；
 * @time 2015-10-19
 */
public class UmengStatistics {
    /**
     * 统计开关
     */
    private static boolean isStatistics = true;

    /**
     * 自定义事件次数统计参数获取
     *
     * @param context
     * @param eventId 统计事件唯一标识
     * @param map     获取事件相关数据，
     *                map中的key和value 都不能使用特殊字符，key 不能超过128个字节，value 不能超过256个字节
     */
    public static void UmengEventStatistics(Context context, String eventId, HashMap<String, String> map) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onEvent(context, eventId, map);

    }


    /**
     * 自定义事件只统计次数
     *
     * @param context
     * @param eventId 统计事件唯一标识
     */
    public static void UmengEventStatistics(Context context, String eventId) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onEvent(context, eventId);
    }

    /**
     * 自定义事件时长统计 计时开始
     *注意：onEventBegin(...),onEventEnd(...)以及onEventDuration(...)后续不再支持，不建议使用。
     - 如需统计使用时长，开发者需要自己上传时长数据（如示例中的duration所示），时长数据会在计算事件的数值型部分展示；
     - 如果使用了onEventBegin(...),onEventEnd(...)以及onEventDuration（...）方法，时长数据会在计算事件的字符串型中显示。
     * @param context
     * @param eventId 统计事件唯一标识 目前不推荐使用
     */
    /*public static void UmengEventBegin(Context context, String eventId) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onEventBegin(context, eventId);
    }*/

    /**
     * 自定义事件时长统计 计时结束
     *
     * @param context
     * @param eventId 统计事件唯一标识 目前不推荐使用
     *                注意：onEventBegin(...),onEventEnd(...)以及onEventDuration(...)后续不再支持，不建议使用。
    - 如需统计使用时长，开发者需要自己上传时长数据（如示例中的duration所示），时长数据会在计算事件的数值型部分展示；
    - 如果使用了onEventBegin(...),onEventEnd(...)以及onEventDuration（...）方法，时长数据会在计算事件的字符串型中显示。
     */
    /*public static void UmengEventEnd(Context context, String eventId) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onEventEnd(context, eventId);
    }*/

    /**
     * onResume方法 supre.onRsume()后面添加
     * 每个activity只能写一次
     *
     * @param context
     */
    public static void UmengOnResume(Context context) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onResume(context);
    }

    /**
     * onPause方法 supre.onPause()后面添加
     * 每个activity只能写一次
     *
     * @param context
     */
    public static void UmengOnPause(Context context) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onPause(context);
    }

    /**
     * 打开页面注意放在UmengOnResume（）方法前面
     *
     * @param pageName
     */
    public static void onPageStart(String pageName) {
        if (!isStatistics) {
            return;
        }

        MobclickAgent.onPageStart(pageName);
    }


    /**
     * 打开页面注意放在UmengOnpause（）方法前面
     *
     * @param pageName 要和start名字一致
     */
    public static void onPageEnd(String pageName) {
        if (!isStatistics) {
            return;
        }

        MobclickAgent.onPageEnd(pageName);
    }
}
