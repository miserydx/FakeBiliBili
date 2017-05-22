package com.bilibili.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface WeChatApi {
}
