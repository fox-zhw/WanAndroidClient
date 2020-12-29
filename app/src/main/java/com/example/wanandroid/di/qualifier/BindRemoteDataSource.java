package com.example.wanandroid.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author 52512
 * @date 2020/12/29
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface BindRemoteDataSource {
}
