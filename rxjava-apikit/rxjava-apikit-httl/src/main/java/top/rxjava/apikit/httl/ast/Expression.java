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
package top.rxjava.apikit.httl.ast;

import top.rxjava.apikit.httl.Node;
import top.rxjava.apikit.httl.Visitor;
import top.rxjava.apikit.httl.spi.parsers.ExpressionParser;

import java.io.IOException;
import java.text.ParseException;

/**
 * Expression. (API, Prototype, Immutable, ThreadSafe)
 * 
 * @see ExpressionParser#parse(String, int)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class Expression implements Node {

	private final int offset;
	
	private Expression parent;

	public Expression(int offset) {
		this.offset = offset;
	}

	public void accept(Visitor visitor) throws IOException, ParseException {
		visitor.visit(this);
	}

	public int getOffset() {
		return offset;
	}

	public Expression getParent() {
		return parent;
	}

	public void setParent(Expression parent) {
		if (this.parent != null)
			throw new IllegalStateException("Can not modify parent.");
		this.parent = parent;
	}

}