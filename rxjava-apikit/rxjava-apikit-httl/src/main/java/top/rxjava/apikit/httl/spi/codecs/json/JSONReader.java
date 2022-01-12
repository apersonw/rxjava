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

import java.io.*;
import java.text.ParseException;

/**
 * JSON reader.
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */

public class JSONReader {

    private static ThreadLocal<Yylex> LOCAL_LEXER = new ThreadLocal<Yylex>();

    private Yylex yylex;

    public JSONReader(InputStream is, String charset)
            throws UnsupportedEncodingException {
        this(new InputStreamReader(is, charset));
    }

    public JSONReader(Reader reader) {
        yylex = getLexer(reader);
    }

    private static Yylex getLexer(Reader reader) {
        Yylex ret = LOCAL_LEXER.get();
        if (ret == null) {
            ret = new Yylex(reader);
            LOCAL_LEXER.set(ret);
        } else {
            ret.yyreset(reader);
        }
        return ret;
    }

    public JSONToken nextToken() throws IOException, ParseException {
        return yylex.yylex();
    }

    public JSONToken nextToken(int expect) throws IOException, ParseException {
        JSONToken ret = yylex.yylex();
        if (ret == null)
            throw new ParseException("EOF error.", 0);
        if (expect != JSONToken.ANY && expect != ret.type)
            throw new ParseException("Unexcepted token.", 0);
        return ret;
    }

}