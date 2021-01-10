package com.example.wanandroid.rx;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author 52512
 * @date 2021/1/7
 */
public class RxBus {
	
	private final FlowableProcessor<Object> bus;
	
	private RxBus() {
		bus = PublishProcessor.create().toSerialized();
	}
	
	public static RxBus get() {
		return RxBusHolder.INSTANCE;
	}
	
	private static class RxBusHolder {
		private static final RxBus INSTANCE = new RxBus();
	}
	
	public void post(Object o) {
		bus.onNext(o);
	}
	
	public <T> Flowable<T> toFlowable(Class<T> eventType) {
		return bus.ofType(eventType);
	}
}
