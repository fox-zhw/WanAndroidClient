package com.example.wanandroid.rx;

import com.example.wanandroid.base.event.Event;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

/**
 * @author 52512
 * @date 2021/1/7
 */
public class RxBusTest {
	
	@Before
	public void setUp() throws Exception {
		RxBus.get().toFlowable(Event.class)
				.subscribe(new Consumer<Event>() {
					@Override
					public void accept(Event event) throws Exception {
						System.out.println(event.getContentIfNotHandled());
					}
				});
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void get() {
		RxBus.get().post(new Event<>(2));
	}
	
	@Test
	public void post() {
	}
	
	@Test
	public void toFlowable() {
	}
}