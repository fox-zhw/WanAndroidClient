package com.example.wanandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wanandroid.App;
import com.example.wanandroid.di.AppModule;
import com.example.wanandroid.di.AppModule_ProvideDataRepositoryFactory;

/**
 * @author 52512
 * @date 2021/1/18
 */
public class ImageLoader {
	
	/**
	 * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
	 *
	 * @param context context
	 * @param url image url
	 * @param iv imageView
	 */
	public static void load(Context context, String url, ImageView iv) {
		
//		if (!App.getContext().getDataManager().getNoImageState()) {
			Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.DATA).into(iv);
//		}
	}
}
