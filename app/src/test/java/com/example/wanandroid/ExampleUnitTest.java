package com.example.wanandroid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() {
//		assertEquals(4, 2 + 2);
		
		test(new C() {
		});
		
	}
	
	private void test(Object a) {
		if (a instanceof A) {
			System.out.println("A");
		}
		if (a instanceof B) {
			System.out.println("B");
		}
		if (a instanceof C) {
			System.out.println("C");
		}
		
		if (myInstanceof(a, A.class.getName())) {
			System.out.println("~ A");
		}
		if (myInstanceof(a, B.class.getName())) {
			System.out.println("~ B");
		}
		if (myInstanceof(a, C.class.getName())) {
			System.out.println("~ C");
		}
	}
	
	private static boolean myInstanceof(Object obj, String name) {
		try {
			return Class.forName(name).isInstance(obj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}