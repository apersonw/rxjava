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
package top.rxjava.apikit.httl.spi.codecs;

import top.rxjava.apikit.httl.util.StringUtils;

import java.util.Arrays;

/**
 * Json Codec. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractJsonCodec extends AbstractCodec {

    private static final char[] NULL_CHARS = "null".toCharArray();

    private static final char[] TRUE_CHARS = "true".toCharArray();

    private static final char[] FALSE_CHARS = "false".toCharArray();

    private static final byte[] NULL_BYTES = "null".getBytes();

    private static final byte[] TRUE_BYTES = "true".getBytes();

    private static final byte[] FALSE_BYTES = "false".getBytes();

    private boolean jsonWithClass;

    protected boolean isJsonWithClass() {
        return jsonWithClass;
    }

    /**
     * httl.properties: json.with.class=true
     */
    public void setJsonWithClass(boolean jsonWithClass) {
        this.jsonWithClass = jsonWithClass;
    }

    public String getFormat() {
        return "json";
    }

    public boolean isValueOf(String str) {
        return StringUtils.isNotEmpty(str)
                && (str.startsWith("{") || str.startsWith("[")
                || (str.startsWith("\"") && str.endsWith("\""))
                || (str.startsWith("\'") && str.endsWith("\'"))
                || StringUtils.isNumber(str) || "null".equals(str)
                || "true".equals(str) || "false".equals(str));
    }

    public boolean isValueOf(char[] str) {
        return StringUtils.isNotEmpty(str)
                && (str[0] == '{' || str[0] == '['
                || (str.length > 1 && str[0] == '\"' && str[str.length - 1] == '\"')
                || (str.length > 1 && str[0] == '\'' && str[str.length - 1] == '\'')
                || StringUtils.isNumber(str) || Arrays.equals(NULL_CHARS, str)
                || Arrays.equals(TRUE_CHARS, str) || Arrays.equals(FALSE_CHARS, str));
    }

    public boolean isValueOf(byte[] str) {
        return StringUtils.isNotEmpty(str)
                && (str[0] == '{' || str[0] == '['
                || (str.length > 1 && str[0] == '\"' && str[str.length - 1] == '\"')
                || (str.length > 1 && str[0] == '\'' && str[str.length - 1] == '\'')
                || StringUtils.isNumber(str) || Arrays.equals(NULL_BYTES, str)
                || Arrays.equals(TRUE_BYTES, str) || Arrays.equals(FALSE_BYTES, str));
    }

}