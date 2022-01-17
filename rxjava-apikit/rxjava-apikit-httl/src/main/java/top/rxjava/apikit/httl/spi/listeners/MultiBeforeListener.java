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
package top.rxjava.apikit.httl.spi.listeners;

import top.rxjava.apikit.httl.spi.Listener;
import top.rxjava.apikit.httl.util.Reqiured;
import top.rxjava.apikit.httl.spi.interceptors.ListenerInterceptor;

/**
 * MultiListener. (SPI, Singleton, ThreadSafe)
 * 
 * @see ListenerInterceptor#setBeforeListener(Listener)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiBeforeListener extends MultiListener {

	/**
	 * httl.properties: before.listeners=httl.spi.listeners.ExtendsListener
	 */
	@Reqiured
	public void setBeforeListeners(Listener[] listeners) {
		super.setListeners(listeners);
	}

}