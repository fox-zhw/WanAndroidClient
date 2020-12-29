package com.example.wanandroid.data.remote;

import com.example.wanandroid.data.bean.BaseResponse;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.data.remote.api.GeeksApis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhaohw
 * @date 2020/12/29
 */
public class HttpHelperImplTest {
	private HttpHelperImpl mHttpHelper;
	@Before
	public void setUp() throws Exception {
		OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
		okHttpBuilder.connectTimeout(10, TimeUnit.SECONDS);
		okHttpBuilder.readTimeout(20, TimeUnit.SECONDS);
		okHttpBuilder.writeTimeout(20, TimeUnit.SECONDS);
		OkHttpClient okHttpClient = okHttpBuilder.build();
		
		Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
		Retrofit retrofit = retrofitBuilder.baseUrl("https://www.wanandroid.com/")
				.client(okHttpClient)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		GeeksApis geeksApis = retrofit.create(GeeksApis.class);
		
		mHttpHelper = new HttpHelperImpl(geeksApis);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void getFeedArticleList() {
		Disposable subscribe = mHttpHelper.getFeedArticleList(0)
				.subscribe(new Consumer<BaseResponse<FeedArticleListData>>() {
					@Override
					public void accept(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) throws Exception {
						System.out.println(feedArticleListDataBaseResponse.getData());
					}
				});
	}
	
	@Test
	public void getSearchList() {
		mHttpHelper.getSearchList(0, "s")
				.subscribe(new Consumer<BaseResponse<FeedArticleListData>>() {
					@Override
					public void accept(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) throws Exception {
						System.out.println(feedArticleListDataBaseResponse.getData());
					}
				});
	}
	
	@Test
	public void getTopSearchData() {
	}
	
	@Test
	public void getUsefulSites() {
	}
	
	@Test
	public void getKnowledgeHierarchyData() {
	}
	
	@Test
	public void getKnowledgeHierarchyDetailData() {
	}
	
	@Test
	public void getNavigationListData() {
	}
	
	@Test
	public void getProjectClassifyData() {
	}
	
	@Test
	public void getProjectListData() {
	}
	
	@Test
	public void getWxAuthorListData() {
	}
	
	@Test
	public void getWxSumData() {
	}
	
	@Test
	public void getWxSearchSumData() {
	}
	
	@Test
	public void getLoginData() {
	}
	
	@Test
	public void getRegisterData() {
	}
	
	@Test
	public void logout() {
	}
	
	@Test
	public void addCollectArticle() {
	}
	
	@Test
	public void addCollectOutsideArticle() {
	}
	
	@Test
	public void getCollectList() {
	}
	
	@Test
	public void cancelCollectPageArticle() {
	}
	
	@Test
	public void cancelCollectArticle() {
	}
	
	@Test
	public void getBannerData() {
	}
}