package pet.haotang.com.pet.util;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:接口请求工具类</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/23 14:05
 */
public class OkGoUtils {
    /**
     * 基本的网络请求get
     */
    public static void Get_String(Context context, String url, String cacheKey, HttpParams params, StringCallback stringCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.get(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(stringCallback);
    }

    private static void initCommonParams(Context context, HttpParams params) {
        String cellPhone = SharedPreferenceUtil.getInstance(context).getString(
                "cellphone", "");
        //String imei = SystemUtils.getIMEI(context);
        String system = SystemUtils.getSource() + "_" + SystemUtils.getCurrentVersion(context);
        //param支持中文,直接传,不要自己编码
        if ("15717155675" != null && !TextUtils.isEmpty("15717155675")) {
            params.put("cellPhone", "15717155675");
        }
        if ("A00000598A8C45" != null && !TextUtils.isEmpty("A00000598A8C45")) {
            params.put("imei", "A00000598A8C45");
        }
        if (system != null && !TextUtils.isEmpty(system)) {
            params.put("system", system);
        }
        params.put("phoneModel", android.os.Build.BRAND + " "
                + android.os.Build.MODEL);
        params.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);
    }

    /**
     * 基本的网络请求post
     */
    public static void Post_String(Context context, String url, String cacheKey, HttpParams params, StringCallback stringCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(stringCallback);
    }

    /**
     * 请求Bitmap对象get
     */
    public static void Get_Bitmap(Context context, String url, String cacheKey, HttpParams params, BitmapCallback bitmapCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.get(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(bitmapCallback);
    }

    /**
     * 请求Bitmap对象post
     */
    public static void Post_Bitmap(Context context, String url, String cacheKey, HttpParams params, BitmapCallback bitmapCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(bitmapCallback);
    }


    /**
     * 请求文件下载get
     */
    public static void Get_File(Context context, String url, String cacheKey, HttpParams params, FileCallback fileCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.get(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(fileCallback);
    }

    /**
     * 请求文件下载post
     */
    public static void Post_File(Context context, String url, String cacheKey, HttpParams params, FileCallback fileCallback) {
        if (params == null) {
            params = new HttpParams();
        }
        initCommonParams(context, params);
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .params(params)
                .execute(fileCallback);
    }

    public void Example() {
        /*一次普通请求所有能配置的参数，真实使用时不需要配置这么多，按自己的需要选择性的使用即可
                params添加参数的时候,最后一个isReplace为可选参数,默认为true,即代表相同key的时候,后添加的会覆盖先前添加的
        多文件和多参数的表单上传，同时支持进度监听
        为单个请求设置超时，比如涉及到文件的需要设置读写等待时间多一点。
        OkGo.post(Urls.URL_METHOD)    // 请求方式和请求url, get请求不需要拼接参数，支持get，post，put，delete，head，options请求
                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .connTimeOut(10000)      // 设置当前请求的连接超时时间
                .readTimeOut(10000)      // 设置当前请求的读取超时时间
                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
                .cacheKey("cacheKey")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheTime(5000)         // 缓存的过期时间,单位毫秒
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) // 缓存模式，详细请看第四部分，缓存介绍
                .addInterceptor(interceptor)            		// 添加自定义拦截器
                .headers("header1", "headerValue1")     		// 添加请求头参数
                .headers("header2", "headerValue2")     		// 支持多请求头参数同时添加
                .params("param1", "paramValue1")        		// 添加请求参数
                .params("param2", "paramValue2")        		// 支持多请求参数同时添加
                .params("file1", new File("filepath1")) 		// 可以添加文件上传
                .params("file2", new File("filepath2")) 		// 支持多文件同时添加上传
                .addUrlParams("key", List<String> values) 	// 这里支持一个key传多个参数
                .addFileParams("key", List<File> files)		// 这里支持一个key传多个文件
                .addFileWrapperParams("key", List<HttpParams.FileWrapper> fileWrappers)//这里支持一个key传多个文件
                //这里给出的泛型为 ServerModel，同时传递一个泛型的 class对象，即可自动将数据结果转成对象返回
                .execute(new DialogCallback<ServerModel>(this) {
                    @Override
                    public void onBefore(BaseRequest request) {
                        // UI线程 请求网络之前调用
                        // 可以显示对话框，添加/修改/移除 请求参数
                    }

                    @Override
                    public ServerModel convertSuccess(Response response) throws Exception{
                        // 子线程，可以做耗时操作
                        // 根据传递进来的 response 对象，把数据解析成需要的 ServerModel 类型并返回
                        // 可以根据自己的需要，抛出异常，在onError中处理
                        return null;
                    }

                    @Override
                    public void parseError(Call call, IOException e) {
                        // 子线程，可以做耗时操作
                        // 用于网络错误时在子线程中执行数据耗时操作,子类可以根据自己的需要重写此方法
                    }

                    @Override
                    public void onSuccess(ServerModel serverModel, Call call, Response response) {
                        // UI 线程，请求成功后回调
                        // ServerModel 返回泛型约定的实体类型参数
                        // call        本次网络的请求信息，如果需要查看请求头或请求参数可以从此对象获取
                        // response    本次网络访问的结果对象，包含了响应头，响应码等
                    }

                    @Override
                    public void onCacheSuccess(ServerModel serverModel, Call call) {
                        // UI 线程，缓存读取成功后回调
                        // serverModel 返回泛型约定的实体类型参数
                        // call        本次网络的请求信息
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        // UI 线程，请求失败后回调
                        // call        本次网络的请求对象，可以根据该对象拿到 request
                        // response    本次网络访问的结果对象，包含了响应头，响应码等
                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        // UI 线程，读取缓存失败后回调
                        // call        本次网络的请求对象，可以根据该对象拿到 request
                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
                    }

                    @Override
                    public void onAfter(ServerModel serverModel, Exception e) {
                        // UI 线程，请求结束后回调，无论网络请求成功还是失败，都会调用，可以用于关闭显示对话框
                        // ServerModel 返回泛型约定的实体类型参数，如果网络请求失败，该对象为　null
                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        // UI 线程，文件上传过程中回调，只有请求方式包含请求体才回调（GET,HEAD不会回调）
                        // currentSize  当前上传的大小（单位字节）
                        // totalSize 　 需要上传的总大小（单位字节）
                        // progress     当前上传的进度，范围　0.0f ~ 1.0f
                        // networkSpeed 当前上传的网速（单位秒）
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        // UI 线程，文件下载过程中回调
                        //参数含义同　上传相同
                    }
                });*/
    }

}
