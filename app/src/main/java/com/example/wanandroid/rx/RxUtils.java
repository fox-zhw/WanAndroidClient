package com.example.wanandroid.rx;

import com.example.wanandroid.data.bean.BaseResponse;
import com.example.wanandroid.data.remote.exception.OtherException;
import com.example.wanandroid.util.CommonUtils;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 52512
 * @date 2020/12/13
 */
public class RxUtils {
	
	/**
	 * 统一线程处理
	 *
	 * @param <T> 指定的泛型类型
	 * @return ObservableTransformer
	 */
	public static <T> ObservableTransformer<T, T> transObservable() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> upstream) {
				return upstream.subscribeOn(Schedulers.io())
						/*.observeOn(AndroidSchedulers.mainThread())*/;
			}
		};
	}
	
	/**
	 * 统一线程处理
	 *
	 * @param <T> 指定的泛型类型
	 * @return FlowableTransformer
	 */
	public static <T> FlowableTransformer<T, T> transFlowable() {
		return new FlowableTransformer<T, T>() {
			@Override
			public Publisher<T> apply(Flowable<T> upstream) {
				return upstream.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread());
			}
		};
	}
	
	public static CompletableTransformer transCompletable() {
		return new CompletableTransformer() {
			@Override
			public CompletableSource apply(Completable upstream) {
				return upstream.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread());
			}
		};
	}
	
	/**
	 * 统一返回结果处理
	 * @param <T> 指定的泛型类型
	 * @return ObservableTransformer
	 */
	public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
		return new ObservableTransformer<BaseResponse<T>, T>() {
			@Override
			public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
				return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
					@Override
					public ObservableSource<T> apply(BaseResponse<T> baseResponse) throws Exception {
						if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
								&& baseResponse.getData() != null) {
							return createData(baseResponse.getData());
						} else {
							return Observable.error(new OtherException());
						}
					}
				});
			}
		};
	}
	
	/**
	 * 得到 Observable
	 * @param <T> 指定的泛型类型
	 * @return Observable
	 */
	private static <T> Observable<T> createData(final T t) {
		return Observable.create(emitter -> {
			try {
				emitter.onNext(t);
				emitter.onComplete();
			} catch (Exception e) {
				emitter.onError(e);
			}
		});
	}
}
