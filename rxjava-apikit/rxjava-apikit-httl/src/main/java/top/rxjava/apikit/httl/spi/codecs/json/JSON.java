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
package top.rxjava.apikit.httl.spi.codecs.json;

import top.rxjava.apikit.httl.spi.Converter;
import top.rxjava.apikit.httl.util.Stack;

import java.io.*;
import java.text.ParseException;
import java.util.Map;

/**
 * JSON.
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class JSON {

    public static final char LBRACE = '{', RBRACE = '}';

    public static final char LSQUARE = '[', RSQUARE = ']';

    public static final char COMMA = ',', COLON = ':', QUOTE = '"';

    public static final String NULL = "null";

    // state.
    public static final byte END = 0, START = 1, OBJECT_ITEM = 2,
            OBJECT_VALUE = 3, ARRAY_ITEM = 4;

    private JSON() {
    }

    /**
     * json string.
     *
     * @param obj object.
     * @return json string.
     * @throws IOException.
     */
    public static String json(Object obj, boolean writeClass,
                              Converter<Object, Map<String, Object>> mc) throws IOException {
        if (obj == null)
            return NULL;
        StringWriter sw = new StringWriter();
        try {
            json(obj, sw, writeClass, mc);
            return sw.getBuffer().toString();
        } finally {
            sw.close();
        }
    }

    /**
     * write json.
     *
     * @param obj    object.
     * @param writer writer.
     * @throws IOException.
     */
    public static void json(Object obj, Writer writer,
                            Converter<Object, Map<String, Object>> mc) throws IOException {
        json(obj, writer, false, mc);
    }

    public static void json(Object obj, Writer writer, boolean writeClass,
                            Converter<Object, Map<String, Object>> mc) throws IOException {
        if (obj == null)
            writer.write(NULL);
        else
            json(obj, new JSONWriter(writer), writeClass, mc);
    }

    /**
     * json string.
     *
     * @param obj        object.
     * @param properties property name array.
     * @return json string.
     * @throws IOException.
     */
    public static String json(Object obj, String[] properties,
                              Converter<Object, Map<String, Object>> mc) throws IOException {
        if (obj == null)
            return NULL;
        StringWriter sw = new StringWriter();
        try {
            json(obj, properties, sw, mc);
            return sw.getBuffer().toString();
        } finally {
            sw.close();
        }
    }

    public static void json(Object obj, final String[] properties,
                            Writer writer, Converter<Object, Map<String, Object>> mc)
            throws IOException {
        json(obj, properties, writer, false, mc);
    }

    /**
     * write json.
     *
     * @param obj        object.
     * @param properties property name array.
     * @param writer     writer.
     * @throws IOException.
     */
    public static void json(Object obj, final String[] properties,
                            Writer writer, boolean writeClass,
                            Converter<Object, Map<String, Object>> mc) throws IOException {
        if (obj == null)
            writer.write(NULL);
        else
            json(obj, properties, new JSONWriter(writer), writeClass, mc);
    }

    private static void json(Object obj, JSONWriter jb, boolean writeClass,
                             Converter<Object, Map<String, Object>> mc) throws IOException {
        if (obj == null)
            jb.valueNull();
        else
            new JSONValue(mc).writeValue(obj, jb, writeClass);
    }

    private static void json(Object obj, String[] properties, JSONWriter jb,
                             boolean writeClass, Converter<Object, Map<String, Object>> mc)
            throws IOException {
        if (obj == null) {
            jb.valueNull();
        } else {
            Map<String, Object> wrapper;
            try {
                wrapper = mc.convert(obj, null);
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            JSONValue jw = new JSONValue(mc);

            Object value;
            jb.objectBegin();
            for (String prop : properties) {
                jb.objectItem(prop);
                value = wrapper.get(prop);
                if (value == null)
                    jb.valueNull();
                else
                    jw.writeValue(value, jb, writeClass);
            }
            jb.objectEnd();
        }
    }

    /**
     * parse json.
     *
     * @param json json source.
     * @return JSONObject or JSONArray or Boolean or Long or Double or String or
     * null
     * @throws ParseException
     */
    public static Object parse(String json) throws ParseException {
        StringReader reader = new StringReader(json);
        try {
            return parse(reader);
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), 0);
        } finally {
            reader.close();
        }
    }

    /**
     * parse json.
     *
     * @param reader reader.
     * @return JSONObject or JSONArray or Boolean or Long or Double or String or
     * null
     * @throws IOException
     * @throws ParseException
     */
    public static Object parse(Reader reader) throws IOException,
            ParseException {
        return parse(reader, JSONToken.ANY);
    }

    /**
     * parse json.
     *
     * @param json json string.
     * @param type target type.
     * @return result.
     * @throws ParseException
     */
    public static <T> T parse(String json, Class<T> type,
                              Converter<Object, Map<String, Object>> mc) throws ParseException {
        StringReader reader = new StringReader(json);
        try {
            return parse(reader, type, mc);
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), 0);
        } finally {
            reader.close();
        }
    }

    /**
     * parse json
     *
     * @param reader json source.
     * @param type   target type.
     * @return result.
     * @throws IOException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static <T> T parse(Reader reader, Class<T> type,
                              Converter<Object, Map<String, Object>> mc) throws IOException,
            ParseException {
        return (T) parse(reader, new JSONVisitor(type, new JSONValue(mc), mc),
                JSONToken.ANY);
    }

    /**
     * parse json.
     *
     * @param json  json string.
     * @param types target type array.
     * @return result.
     * @throws ParseException
     */
    public static Object[] parse(String json, Class<?>[] types,
                                 Converter<Object, Map<String, Object>> mc) throws ParseException {
        StringReader reader = new StringReader(json);
        try {
            return (Object[]) parse(reader, types, mc);
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), 0);
        } finally {
            reader.close();
        }
    }

    /**
     * parse json.
     *
     * @param reader json source.
     * @param types  target type array.
     * @return result.
     * @throws IOException
     * @throws ParseException
     */
    public static Object[] parse(Reader reader, Class<?>[] types,
                                 Converter<Object, Map<String, Object>> mc) throws IOException,
            ParseException {
        return (Object[]) parse(reader, new JSONVisitor(types,
                new JSONValue(mc)), JSONToken.LSQUARE);
    }

    /**
     * parse json.
     *
     * @param json    json string.
     * @param handler handler.
     * @return result.
     * @throws ParseException
     */
    public static Object parse(String json, JSONVisitor handler)
            throws ParseException {
        StringReader reader = new StringReader(json);
        try {
            return parse(reader, handler);
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), 0);
        } finally {
            reader.close();
        }
    }

    /**
     * parse json.
     *
     * @param reader  json source.
     * @param handler handler.
     * @return resule.
     * @throws IOException
     * @throws ParseException
     */
    public static Object parse(Reader reader, JSONVisitor handler)
            throws IOException, ParseException {
        return parse(reader, handler, JSONToken.ANY);
    }

    private static Object parse(Reader reader, int expect) throws IOException,
            ParseException {
        JSONReader jr = new JSONReader(reader);
        JSONToken token = jr.nextToken(expect);

        byte state = START;
        Object value = null, tmp;
        Stack<Entry> stack = new Stack<Entry>();

        do {
            switch (state) {
                case END:
                    throw new ParseException("JSON source format error.", 0);
                case START: {
                    switch (token.type) {
                        case JSONToken.NULL:
                        case JSONToken.BOOL:
                        case JSONToken.INT:
                        case JSONToken.FLOAT:
                        case JSONToken.STRING: {
                            state = END;
                            value = token.value;
                            break;
                        }
                        case JSONToken.LSQUARE: {
                            state = ARRAY_ITEM;
                            value = new JSONArray();
                            break;
                        }
                        case JSONToken.LBRACE: {
                            state = OBJECT_ITEM;
                            value = new JSONObject();
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case ARRAY_ITEM: {
                    switch (token.type) {
                        case JSONToken.COMMA:
                            break;
                        case JSONToken.NULL:
                        case JSONToken.BOOL:
                        case JSONToken.INT:
                        case JSONToken.FLOAT:
                        case JSONToken.STRING: {
                            ((JSONArray) value).add(token.value);
                            break;
                        }
                        case JSONToken.RSQUARE: // end of array.
                        {
                            if (stack.isEmpty()) {
                                state = END;
                            } else {
                                Entry entry = stack.pop();
                                state = entry.state;
                                value = entry.value;
                            }
                            break;
                        }
                        case JSONToken.LSQUARE: // array begin.
                        {
                            tmp = new JSONArray();
                            ((JSONArray) value).add(tmp);
                            stack.push(new Entry(state, value));

                            state = ARRAY_ITEM;
                            value = tmp;
                            break;
                        }
                        case JSONToken.LBRACE: // object begin.
                        {
                            tmp = new JSONObject();
                            ((JSONArray) value).add(tmp);
                            stack.push(new Entry(state, value));

                            state = OBJECT_ITEM;
                            value = tmp;
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or ',' or ']' or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case OBJECT_ITEM: {
                    switch (token.type) {
                        case JSONToken.COMMA:
                            break;
                        case JSONToken.IDENT: // item name.
                        {
                            stack.push(new Entry(OBJECT_ITEM, (String) token.value));
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.NULL: {
                            stack.push(new Entry(OBJECT_ITEM, "null"));
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.BOOL:
                        case JSONToken.INT:
                        case JSONToken.FLOAT:
                        case JSONToken.STRING: {
                            stack.push(new Entry(OBJECT_ITEM, token.value.toString()));
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.RBRACE: // end of object.
                        {
                            if (stack.isEmpty()) {
                                state = END;
                            } else {
                                Entry entry = stack.pop();
                                state = entry.state;
                                value = entry.value;
                            }
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ IDENT or VALUE or ',' or '}' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case OBJECT_VALUE: {
                    switch (token.type) {
                        case JSONToken.COLON:
                            break;
                        case JSONToken.NULL:
                        case JSONToken.BOOL:
                        case JSONToken.INT:
                        case JSONToken.FLOAT:
                        case JSONToken.STRING: {
                            ((JSONObject) value).put((String) stack.pop().value,
                                    token.value);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.LSQUARE: // array begin.
                        {
                            tmp = new JSONArray();
                            ((JSONObject) value).put((String) stack.pop().value, tmp);
                            stack.push(new Entry(OBJECT_ITEM, value));

                            state = ARRAY_ITEM;
                            value = tmp;
                            break;
                        }
                        case JSONToken.LBRACE: // object begin.
                        {
                            tmp = new JSONObject();
                            ((JSONObject) value).put((String) stack.pop().value, tmp);
                            stack.push(new Entry(OBJECT_ITEM, value));

                            state = OBJECT_ITEM;
                            value = tmp;
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                default:
                    throw new ParseException("Unexcepted state.", 0);
            }
        } while ((token = jr.nextToken()) != null);
        stack.clear();
        return value;
    }

    private static Object parse(Reader reader, JSONVisitor handler, int expect)
            throws IOException, ParseException {
        JSONReader jr = new JSONReader(reader);
        JSONToken token = jr.nextToken(expect);

        Object value = null;
        int state = START, index = 0;
        Stack<int[]> states = new Stack<int[]>();
        boolean pv = false;

        handler.begin();
        do {
            switch (state) {
                case END:
                    throw new ParseException("JSON source format error.", 0);
                case START: {
                    switch (token.type) {
                        case JSONToken.NULL: {
                            value = token.value;
                            state = END;
                            pv = true;
                            break;
                        }
                        case JSONToken.BOOL: {
                            value = token.value;
                            state = END;
                            pv = true;
                            break;
                        }
                        case JSONToken.INT: {
                            value = token.value;
                            state = END;
                            pv = true;
                            break;
                        }
                        case JSONToken.FLOAT: {
                            value = token.value;
                            state = END;
                            pv = true;
                            break;
                        }
                        case JSONToken.STRING: {
                            value = token.value;
                            state = END;
                            pv = true;
                            break;
                        }
                        case JSONToken.LSQUARE: {
                            handler.arrayBegin();
                            state = ARRAY_ITEM;
                            break;
                        }
                        case JSONToken.LBRACE: {
                            handler.objectBegin();
                            state = OBJECT_ITEM;
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case ARRAY_ITEM: {
                    switch (token.type) {
                        case JSONToken.COMMA:
                            break;
                        case JSONToken.NULL: {
                            handler.arrayItem(index++);
                            handler.arrayItemValue(index, token.value, true);
                            break;
                        }
                        case JSONToken.BOOL: {
                            handler.arrayItem(index++);
                            handler.arrayItemValue(index, token.value, true);
                            break;
                        }
                        case JSONToken.INT: {
                            handler.arrayItem(index++);
                            handler.arrayItemValue(index, token.value, true);
                            break;
                        }
                        case JSONToken.FLOAT: {
                            handler.arrayItem(index++);
                            handler.arrayItemValue(index, token.value, true);
                            break;
                        }
                        case JSONToken.STRING: {
                            handler.arrayItem(index++);
                            handler.arrayItemValue(index, token.value, true);
                            break;
                        }
                        case JSONToken.LSQUARE: {
                            handler.arrayItem(index++);
                            states.push(new int[]{state, index});

                            index = 0;
                            state = ARRAY_ITEM;
                            handler.arrayBegin();
                            break;
                        }
                        case JSONToken.LBRACE: {
                            handler.arrayItem(index++);
                            states.push(new int[]{state, index});

                            index = 0;
                            state = OBJECT_ITEM;
                            handler.objectBegin();
                            break;
                        }
                        case JSONToken.RSQUARE: {
                            if (states.isEmpty()) {
                                value = handler.arrayEnd(index);
                                state = END;
                            } else {
                                value = handler.arrayEnd(index);
                                int[] tmp = states.pop();
                                state = tmp[0];
                                index = tmp[1];

                                switch (state) {
                                    case ARRAY_ITEM: {
                                        handler.arrayItemValue(index, value, false);
                                        break;
                                    }
                                    case OBJECT_ITEM: {
                                        handler.objectItemValue(value, false);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or ',' or ']' or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case OBJECT_ITEM: {
                    switch (token.type) {
                        case JSONToken.COMMA:
                            break;
                        case JSONToken.IDENT: {
                            handler.objectItem((String) token.value);
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.NULL: {
                            handler.objectItem("null");
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.BOOL:
                        case JSONToken.INT:
                        case JSONToken.FLOAT:
                        case JSONToken.STRING: {
                            handler.objectItem(token.value.toString());
                            state = OBJECT_VALUE;
                            break;
                        }
                        case JSONToken.RBRACE: {
                            if (states.isEmpty()) {
                                value = handler.objectEnd(index);
                                state = END;
                            } else {
                                value = handler.objectEnd(index);
                                int[] tmp = states.pop();
                                state = tmp[0];
                                index = tmp[1];

                                switch (state) {
                                    case ARRAY_ITEM: {
                                        handler.arrayItemValue(index, value, false);
                                        break;
                                    }
                                    case OBJECT_ITEM: {
                                        handler.objectItemValue(value, false);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ IDENT or VALUE or ',' or '}' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                case OBJECT_VALUE: {
                    switch (token.type) {
                        case JSONToken.COLON:
                            break;
                        case JSONToken.NULL: {
                            handler.objectItemValue(token.value, true);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.BOOL: {
                            handler.objectItemValue(token.value, true);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.INT: {
                            handler.objectItemValue(token.value, true);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.FLOAT: {
                            handler.objectItemValue(token.value, true);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.STRING: {
                            handler.objectItemValue(token.value, true);
                            state = OBJECT_ITEM;
                            break;
                        }
                        case JSONToken.LSQUARE: {
                            states.push(new int[]{OBJECT_ITEM, index});

                            index = 0;
                            state = ARRAY_ITEM;
                            handler.arrayBegin();
                            break;
                        }
                        case JSONToken.LBRACE: {
                            states.push(new int[]{OBJECT_ITEM, index});

                            index = 0;
                            state = OBJECT_ITEM;
                            handler.objectBegin();
                            break;
                        }
                        default:
                            throw new ParseException(
                                    "Unexcepted token expect [ VALUE or '[' or '{' ] get '"
                                            + JSONToken.token2string(token.type) + "'",
                                    0);
                    }
                    break;
                }
                default:
                    throw new ParseException("Unexcepted state.", 0);
            }
        } while ((token = jr.nextToken()) != null);
        states.clear();
        return handler.end(value, pv);
    }

    private static class Entry {
        byte state;
        Object value;

        Entry(byte s, Object v) {
            state = s;
            value = v;
        }
    }
}