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
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

import java.io.IOException;
import java.text.ParseException;

/**
 * FirstInterceptor. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setInterceptor(Interceptor)
 * @see InterpretedTranslator#setInterceptor(Interceptor)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class FirstInterceptor extends TemplateInterceptor {

	public void render(Context context, Listener listener)
			throws IOException, ParseException {
		if (context.getLevel() > 1) { 
			listener.render(context);
			return;
		}
		super.render(context, listener);
	}

}