package com.example.wanandroid.data;

import com.example.wanandroid.data.bean.BaseResponse;
import com.example.wanandroid.data.bean.HistoryData;
import com.example.wanandroid.data.bean.hierarchy.KnowledgeHierarchyData;
import com.example.wanandroid.data.bean.main.banner.BannerData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.data.bean.main.login.LoginData;
import com.example.wanandroid.data.bean.main.search.TopSearchData;
import com.example.wanandroid.data.bean.main.search.UsefulSiteData;
import com.example.wanandroid.data.bean.navigation.NavigationListData;
import com.example.wanandroid.data.bean.project.ProjectClassifyData;
import com.example.wanandroid.data.bean.project.ProjectListData;
import com.example.wanandroid.data.bean.wx.WxAuthor;
import com.example.wanandroid.data.local.db.DbHelper;
import com.example.wanandroid.data.remote.HttpHelper;
import com.example.wanandroid.data.sp.PreferenceHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author 52512
 * @date 2020/12/13
 */
public class DataRepository implements HttpHelper, DbHelper, PreferenceHelper {
	@Override
	public List<HistoryData> addHistoryData(String data) {
		return null;
	}
	
	@Override
	public void clearHistoryData() {
	
	}
	
	@Override
	public List<HistoryData> loadAllHistoryData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(int page, int cid) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<ProjectListData>> getProjectListData(int page, int cid) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<WxAuthor>>> getWxAuthorListData() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getWxSumData(int id, int page) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getWxSearchSumData(int id, int page, String k) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String rePassword) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<LoginData>> logout() {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(String title, String author, String link) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> getCollectList(int page) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
		return null;
	}
	
	@Override
	public Observable<BaseResponse<List<BannerData>>> getBannerData() {
		return null;
	}
	
	@Override
	public void setLoginAccount(String account) {
	
	}
	
	@Override
	public void setLoginPassword(String password) {
	
	}
	
	@Override
	public String getLoginAccount() {
		return null;
	}
	
	@Override
	public String getLoginPassword() {
		return null;
	}
	
	@Override
	public void setLoginStatus(boolean isLogin) {
	
	}
	
	@Override
	public boolean getLoginStatus() {
		return false;
	}
	
	@Override
	public void setCookie(String domain, String cookie) {
	
	}
	
	@Override
	public String getCookie(String domain) {
		return null;
	}
	
	@Override
	public void setCurrentPage(int position) {
	
	}
	
	@Override
	public int getCurrentPage() {
		return 0;
	}
	
	@Override
	public void setProjectCurrentPage(int position) {
	
	}
	
	@Override
	public int getProjectCurrentPage() {
		return 0;
	}
	
	@Override
	public boolean getAutoCacheState() {
		return false;
	}
	
	@Override
	public boolean getNoImageState() {
		return false;
	}
	
	@Override
	public boolean getNightModeState() {
		return false;
	}
	
	@Override
	public void setNightModeState(boolean b) {
	
	}
	
	@Override
	public void setNoImageState(boolean b) {
	
	}
	
	@Override
	public void setAutoCacheState(boolean b) {
	
	}
}
