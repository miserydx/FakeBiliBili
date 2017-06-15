package com.bilibili.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Android_ZzT on 17/6/15.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalApis {
}
