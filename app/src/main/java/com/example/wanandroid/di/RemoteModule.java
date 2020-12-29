package com.example.wanandroid.di;

import android.content.Context;

import com.example.wanandroid.BuildConfig;
import com.example.wanandroid.Constants;
import com.example.wanandroid.data.remote.api.GeeksApis;
import com.example.wanandroid.performance.net.OkHttpDns;
import com.example.wanandroid.performance.net.OkHttpEventListener;
import com.example.wanandroid.util.CommonUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 52512
 * @date 2020/12/29
 */
@Module
@InstallIn(ApplicationComponent.class)
public class RemoteModule {
	
	@Singleton
	@Provides
	GeeksApis provideGeeksApi(Retrofit retrofit) {
		return retrofit.create(GeeksApis.class);
	}
	
	@Singleton
	@Provides
	Retrofit providerRetrofit(Retrofit.Builder builder, OkHttpClient client) {
		return builder
				.baseUrl(GeeksApis.HOST)
				.client(client)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}
	
	@Singleton
	@Provides
	Retrofit.Builder provideRetrofitBuilder() {
		return new Retrofit.Builder();
	}
	
	@Singleton
	@Provides
	OkHttpClient provideClient(@ApplicationContext Context context, OkHttpClient.Builder builder) {
		// 网络请求质量监控
//		builder.eventListenerFactory(OkHttpEventListener.FACTORY);
		// httpDns 优化
//		builder.dns(OkHttpDns.getIns(context));
		
//		File cacheFile = new File(Constants.PATH_CACHE);
//		Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//		Interceptor cacheInterceptor = chain -> {
//			Request request = chain.request();
//			if (!CommonUtils.isNetworkConnected()) {
//				// 无网时强制使用数据缓存，以提升用户体验。
//				request = request.newBuilder()
//						.cacheControl(CacheControl.FORCE_CACHE)
//						.build();
//			}
//			Response response = chain.proceed(request);
//			if (CommonUtils.isNetworkConnected()) {
//				int maxAge = 0;
//				// 有网络时, 不缓存, 最大保存时长为0
//				response.newBuilder()
//						.header("Cache-Control", "public, max-age=" + maxAge)
//						.removeHeader("Pragma")
//						.build();
//			} else {
//				// 无网络时，设置超时为4周
//				int maxStale = 60 * 60 * 24 * 28;
//				response.newBuilder()
//						.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//						.removeHeader("Pragma")
//						.build();
//			}
//			return response;
//		};
		
		// 缓存优化
//		builder.addNetworkInterceptor(cacheInterceptor);
//		builder.addInterceptor(cacheInterceptor);
//		builder.cache(cache);
		
//		if (BuildConfig.DEBUG) {
//			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//			builder.addInterceptor(loggingInterceptor);
//			builder.addNetworkInterceptor(new StethoInterceptor());
//		}
		
		//设置超时
		builder.connectTimeout(10, TimeUnit.SECONDS);
		builder.readTimeout(20, TimeUnit.SECONDS);
		builder.writeTimeout(20, TimeUnit.SECONDS);
		//错误重连
//		builder.retryOnConnectionFailure(true);
		//cookie认证
//		builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)));
		
//		return RetrofitUrlManager.getInstance().with(builder).build();
		return builder.build();
	}
	
	@Singleton
	@Provides
	OkHttpClient.Builder provideOkHttpBuilder() {
		return new OkHttpClient.Builder();
	}
}
