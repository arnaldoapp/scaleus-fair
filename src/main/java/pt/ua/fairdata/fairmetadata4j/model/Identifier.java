/**
 * The MIT License
 * Copyright © 2017 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package pt.ua.fairdata.fairmetadata4j.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

import pt.ua.fairdata.fairmetadata4j.utils.vocabulary.DataCite;

// Identifier object
public class Identifier {
	private IRI uri;
	private IRI type = DataCite.IDENTIFIER;
	private Literal identifier;

	/**
	 * @return the type
	 */
	public IRI getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(IRI type) {
		this.type = type;
	}

	/**
	 * @return the identifier
	 */
	public Literal getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Literal identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the uri
	 */
	public IRI getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(IRI uri) {
		this.uri = uri;
	}
}
