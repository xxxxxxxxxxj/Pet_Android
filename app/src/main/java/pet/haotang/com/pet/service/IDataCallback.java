package pet.haotang.com.pet.service;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:数据回调接口，我们从网络端获取数据 需要通知给UI</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/18 16:24
 */
public interface IDataCallback<T> {//传入通用类型

    public abstract void onNewData(T data);

    public abstract void onError(String msg, int code);
}
