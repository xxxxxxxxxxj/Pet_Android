package pet.haotang.com.pet.entity;

import java.io.Serializable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:数据交互模型</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/18 16:22
 */
public class MData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String type;
    public T dataList;//多种类型数据，一般是List集合
}
