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

package pt.ua.scaleus.metadata;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

public class DataRecord extends Metadata {

	private IRI rmlURI;
	private IRI distributionURI;
	private Literal dataRecordIssued;
	private Literal dataRecordModified;

	public IRI getRmlURI() {
		return rmlURI;
	}

	public void setRmlURI(IRI rmlURI) {
		this.rmlURI = rmlURI;
	}

	public IRI getDistributionURI() {
		return distributionURI;
	}

	public void setDistributionURI(IRI distributionURI) {
		this.distributionURI = distributionURI;
	}

	public Literal getDataRecordIssued() {
		return dataRecordIssued;
	}

	public void setDataRecordIssued(Literal dataRecordIssued) {
		this.dataRecordIssued = dataRecordIssued;
	}

	public Literal getDataRecordModified() {
		return dataRecordModified;
	}

	public void setDataRecordModified(Literal dataRecordModified) {
		this.dataRecordModified = dataRecordModified;
	}
}
