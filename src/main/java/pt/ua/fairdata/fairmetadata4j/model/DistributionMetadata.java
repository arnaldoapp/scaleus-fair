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

// Distribution metadata object
public class DistributionMetadata extends Metadata {

	private IRI accessURL;
	private IRI downloadURL;
	private Literal mediaType;
	private Literal format;
	private Literal byteSize;
	private Literal distributionIssued;
	private Literal distributionModified;

	/**
	 * @param accessURL the accessURL to set
	 */
	public void setAccessURL(IRI accessURL) {
		this.accessURL = accessURL;
	}

	/**
	 * @param downloadURL the downloadURL to set
	 */
	public void setDownloadURL(IRI downloadURL) {
		this.downloadURL = downloadURL;
	}

	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(Literal mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(Literal format) {
		this.format = format;
	}

	/**
	 * @param byteSize the byteSize to set
	 */
	public void setByteSize(Literal byteSize) {
		this.byteSize = byteSize;
	}

	/**
	 * @return the accessURL
	 */
	public IRI getAccessURL() {
		return accessURL;
	}

	/**
	 * @return the downloadURL
	 */
	public IRI getDownloadURL() {
		return downloadURL;
	}

	/**
	 * @return the mediaType
	 */
	public Literal getMediaType() {
		return mediaType;
	}

	/**
	 * @return the format
	 */
	public Literal getFormat() {
		return format;
	}

	/**
	 * @return the byteSize
	 */
	public Literal getByteSize() {
		return byteSize;
	}

	/**
	 * @return the distributionIssued
	 */
	public Literal getDistributionIssued() {
		return distributionIssued;
	}

	/**
	 * @param distributionIssued the distributionIssued to set
	 */
	public void setDistributionIssued(Literal distributionIssued) {
		this.distributionIssued = distributionIssued;
	}

	/**
	 * @return the distributionModified
	 */
	public Literal getDistributionModified() {
		return distributionModified;
	}

	/**
	 * @param distributionModified the distributionModified to set
	 */
	public void setDistributionModified(Literal distributionModified) {
		this.distributionModified = distributionModified;
	}
}
