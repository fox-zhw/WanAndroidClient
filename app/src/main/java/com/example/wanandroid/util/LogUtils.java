package com.example.wanandroid.util;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * @author 52512
 * @date 2020/12/15
 */
public class LogUtils {
	
	public static void init() {
		Timber.plant(new MyTree());
	}
	
	/** Log an info message with optional format args. */
	public static void i(@NonNls String message, Object... args) {
		Timber.i(message, args);
	}
	
	/** Log an info exception and a message with optional format args. */
	public static void i(Throwable t, @NonNls String message, Object... args) {
		Timber.i(t, message, args);
	}
	
	/** Log an info exception. */
	public static void i(Throwable t) {
		Timber.i(t);
	}
	
	/** Log an error message with optional format args. */
	public static void e(@NonNls String message, Object... args) {
		Timber.e(message, args);
	}
	
	/** Log an error exception and a message with optional format args. */
	public static void e(Throwable t, @NonNls String message, Object... args) {
		Timber.e(t, message, args);
	}
	
	/** Log an error exception. */
	public static void e(Throwable t) {
		Timber.e(t);
	}
	
	static class MyTree extends Timber.DebugTree{
		@Override
		protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
			return super.createStackElementTag(element);
		}
		
		@Override
		protected void log(int priority, String tag, @NotNull String message, Throwable t) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			int index = 7;
			String className = stackTrace[index].getFileName();
			int lineNumber = stackTrace[index].getLineNumber();
			StringBuilder sbMsg = new StringBuilder();
			sbMsg.append("[")
					.append("-zhw-")
					.append("] ")
					.append("(")
					.append(className)
					.append(":")
					.append(lineNumber)
					.append(") ")
					.append(message);
			
			super.log(priority, tag, sbMsg.toString(), t);
		}
	}
}
