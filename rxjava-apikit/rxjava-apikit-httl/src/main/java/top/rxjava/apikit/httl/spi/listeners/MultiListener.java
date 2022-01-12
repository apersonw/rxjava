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

import lombok.extern.slf4j.Slf4j;
import top.rxjava.apikit.httl.Context;
import top.rxjava.apikit.httl.spi.Listener;

import java.io.IOException;
import java.text.ParseException;

/**
 * MultiListener. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.interceptors.ListenerInterceptor#setBeforeListener(Listener)
 * @see top.rxjava.apikit.httl.spi.interceptors.ListenerInterceptor#setAfterListener(Listener)
 */
@Slf4j
public class MultiListener implements Listener {

    private Listener[] listeners;

    /**
     * httl.properties: listeners=httl.spi.listeners.ExtendsListener
     */
    public void setListeners(Listener[] listeners) {
        if (listeners != null && listeners.length > 0
                && this.listeners != null && this.listeners.length > 0) {
            Listener[] oldListeners = this.listeners;
            this.listeners = new Listener[oldListeners.length + listeners.length];
            System.arraycopy(oldListeners, 0, this.listeners, 0, oldListeners.length);
            System.arraycopy(listeners, 0, this.listeners, oldListeners.length, listeners.length);
        } else {
            this.listeners = listeners;
        }
    }

    @Override
    public void render(Context context) throws IOException, ParseException {
        if (listeners == null || listeners.length == 0) {
            return;
        }
        if (listeners.length == 1) {
            listeners[0].render(context);
            return;
        }
        for (Listener listener : listeners) {
            try {
                listener.render(context);
            } catch (Exception e) { // 确保第一个出错，不影响第二个执行
                if (log != null && log.isErrorEnabled()) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}