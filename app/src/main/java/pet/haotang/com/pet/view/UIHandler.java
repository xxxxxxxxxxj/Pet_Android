package pet.haotang.com.pet.view;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import pet.haotang.com.pet.service.IHandler;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:回调接口，传递消息</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/18 16:28
 */
public class UIHandler extends Handler {
    private IHandler handler;//回调接口，消息传递给注册者

    public UIHandler(Looper looper) {
        super(looper);
    }

    public UIHandler(Looper looper, IHandler handler) {
        super(looper);
        this.handler = handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (handler != null) {
            handler.handleMessage(msg);//有消息，就传递
        }
    }
}
