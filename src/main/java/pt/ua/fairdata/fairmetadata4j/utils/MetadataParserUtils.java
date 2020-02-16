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

package pt.ua.fairdata.fairmetadata4j.utils;

import pt.ua.fairdata.fairmetadata4j.io.CatalogMetadataParser;
import pt.ua.fairdata.fairmetadata4j.io.DataRecordMetadataParser;
import pt.ua.fairdata.fairmetadata4j.io.DatasetMetadataParser;
import pt.ua.fairdata.fairmetadata4j.io.DistributionMetadataParser;
import pt.ua.fairdata.fairmetadata4j.io.FDPMetadataParser;

// Provides metadata parser instances
public class MetadataParserUtils {
	private static final FDPMetadataParser fdpParser = new FDPMetadataParser();
	private static final CatalogMetadataParser catalogParser = new CatalogMetadataParser();
	private static final DatasetMetadataParser datasetParser = new DatasetMetadataParser();
	private static final DistributionMetadataParser distributionParser = new DistributionMetadataParser();
	private static final DataRecordMetadataParser dataRecordParser = new DataRecordMetadataParser();

	private MetadataParserUtils() {
	}

	/**
	 * @return the fdpParser
	 */
	public static FDPMetadataParser getFdpParser() {
		return fdpParser;
	}

	/**
	 * @return the catalogParser
	 */
	public static CatalogMetadataParser getCatalogParser() {
		return catalogParser;
	}

	/**
	 * @return the datasetParser
	 */
	public static DatasetMetadataParser getDatasetParser() {
		return datasetParser;
	}

	/**
	 * @return the distributionParser
	 */
	public static DistributionMetadataParser getDistributionParser() {
		return distributionParser;
	}

	/**
	 * @return the dataRecordParser
	 */
	public static DataRecordMetadataParser getDataRecordParser() {
		return dataRecordParser;
	}
}
