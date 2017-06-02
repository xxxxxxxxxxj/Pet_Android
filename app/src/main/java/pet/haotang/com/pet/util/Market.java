package pet.haotang.com.pet.util;

import android.content.Context;

public class Market {
	public static String getMarketId(Context context) {
		
		return ChannelUtil.getChannel(context);
	}
}
