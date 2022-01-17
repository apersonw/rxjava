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
package top.rxjava.apikit.httl.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ByteCache {
	
	private static final AtomicLong IDS = new AtomicLong();
	
	private static final ConcurrentMap<String, byte[]> cache = new ConcurrentHashMap<String, byte[]>();

	public static String put(byte[] source) {
		String id = String.valueOf(IDS.incrementAndGet());
		cache.putIfAbsent(id, source);
		return id;
	}

	public static byte[] getAndRemove(String id) {
		return cache.remove(id);
	}

}