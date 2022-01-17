/*
 * Copyright 2011-2013 HTTL Team.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.rxjava.apikit.httl.spi.interceptors;

import top.rxjava.apikit.httl.Context;
import top.rxjava.apikit.httl.spi.Interceptor;
import top.rxjava.apikit.httl.spi.Listener;
import top.rxjava.apikit.httl.spi.Logger;
import top.rxjava.apikit.httl.util.Optional;
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

import java.io.IOException;
import java.text.ParseException;

/**
 * Listener Interceptor. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setInterceptor(Interceptor)
 * @see InterpretedTranslator#setInterceptor(Interceptor)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ListenerInterceptor implements Interceptor {

	private Listener beforeListener;

	private Listener afterListener;

	private Logger logger;

	/**
	 * httl.properties: before.listeners=httl.spi.listeners.ExtendsListener
	 */
	@Optional
	public void setBeforeListener(Listener listener) {
		this.beforeListener = listener;
	}

	/**
	 * httl.properties: after.listeners=httl.spi.listeners.ExtendsListener
	 */
	@Optional
	public void setAfterListener(Listener listener) {
		this.afterListener = listener;
	}

	/**
	 * httl.properties: loggers=httl.spi.loggers.Log4jListener
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void render(Context context, Listener listener) throws IOException, ParseException {
		if (beforeListener != null) {
			try {
				beforeListener.render(context);
			} catch (Exception e) {
				if (logger != null && logger.isErrorEnabled()) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		try {
			listener.render(context);
		} finally {
			if (afterListener != null) {
				try {
					afterListener.render(context);
				} catch (Exception e) {
					if (logger != null && logger.isErrorEnabled()) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

}