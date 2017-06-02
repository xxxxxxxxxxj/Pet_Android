package pet.haotang.com.pet.base;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import pet.haotang.com.pet.util.ExampleUtil;
import pet.haotang.com.pet.util.LogUtils;
import pet.haotang.com.pet.util.SharedPreferenceUtil;
import pet.haotang.com.pet.util.SystemUtils;

/**
 * <p>
 * Title:MApplication
 * </p>
 * <p>
 * Description:主干
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 *
 * @author 徐俊
 * @date 2016-8-15 下午9:02:59
 */
public class MApplication extends Application {
    public static ArrayList<Activity> listAppoint;
    private static final int MSG_SET_ALIASANDTAGS = 1001;
    Set<String> tagSet = new LinkedHashSet<String>();
    private SharedPreferenceUtil spUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        spUtil = SharedPreferenceUtil.getInstance(this);
        listAppoint = new ArrayList<Activity>();
        initImageLoader();
        initOkGo();
        Fresco.initialize(getApplicationContext());
        JPushInterface.setDebugMode(LogUtils.isShowLog);
        JPushInterface.init(this);
        setaliasAndTags();
        String JPushId = JPushInterface.getRegistrationID(getApplicationContext());
        LogUtils.e("JPushId = " + JPushId);
        if (!JPushId.isEmpty()) {
            LogUtils.e("JPushId = " + JPushId);
            SystemUtils.savePushID(getApplicationContext(), JPushId);
        }
    }

    private void setaliasAndTags() {
        // 获取电话号码
        String cellphone = spUtil.getString("cellphone", "");
        LogUtils.e("cellphone = " + cellphone);
        if (cellphone != null && !TextUtils.isEmpty(cellphone)) {
            // 检查 tag 的有效性
            if (!ExampleUtil.isValidTagAndAlias(cellphone)) {
                LogUtils.e("Invalid format");
            } else {
                tagSet.add(cellphone);
            }
            // 设置别名和tag
            JPushInterface.setAliasAndTags(getApplicationContext(), cellphone,
                    tagSet, mAliasCallback);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIASANDTAGS:
                    LogUtils.e("Set aliasandtags in handler.");
                    TagAndAlias tagAndAlias = (TagAndAlias) msg.obj;
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            tagAndAlias.getAlias(), tagAndAlias.getTags(),
                            mAliasCallback);
                    break;
                default:
                    LogUtils.e("Unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    LogUtils.e("设置别名:" + logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogUtils.e("设置别名:" + logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MSG_SET_ALIASANDTAGS,
                                        new TagAndAlias(alias, tags)), 1000 * 60);
                    } else {
                        LogUtils.e("设置别名:" + "No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    LogUtils.e("设置别名:" + logs);
            }
        }
    };

    class TagAndAlias {
        private String alias;
        private Set<String> tags;

        public TagAndAlias(String alias, Set<String> tags) {
            super();
            this.alias = alias;
            this.tags = tags;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public Set<String> getTags() {
            return tags;
        }

        public void setTags(Set<String> tags) {
            this.tags = tags;
        }
    }

    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
       /* HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        String cellPhone = SharedPreferenceUtil.getInstance(getApplicationContext()).getString(
                "cellphone", "");
        String imei = SystemUtils.getIMEI(getApplicationContext());
        String system = SystemUtils.getSource() + "_" + SystemUtils.getCurrentVersion(getApplicationContext());
        HttpParams params = new HttpParams();
        //param支持中文,直接传,不要自己编码
        if (cellPhone != null && !TextUtils.isEmpty(cellPhone)) {
            params.put("cellPhone", cellPhone);
        }
        if (imei != null && !TextUtils.isEmpty(imei)) {
            params.put("imei", imei);
        }
        if (system != null && !TextUtils.isEmpty(system)) {
            params.put("system", system);
        }
        params.put("phoneModel", android.os.Build.BRAND + " "
                + android.os.Build.MODEL);
        params.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);*/
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                               //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })
            //这两行同上，不需要就不要加入
            // .addCommonHeaders(headers)  //设置全局公共头
            //.addCommonParams(params);   //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化ImageLoader
    public void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(720, 1280)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(720, 1280, null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
                // .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout
                // (5
                // s),
                // readTimeout
                // (30
                // s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
    }
}
