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

import java.util.List;
import javax.annotation.Nonnull;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;
import com.google.common.base.Preconditions;

public class DataRecordParser extends MetadataParser<DataRecord> {

	@Override
	protected DataRecord createMetadata() {
		return new DataRecord();
	}

	// Create DataRecord from RDF statements
	@Override
	public DataRecord parse(@Nonnull List<Statement> statements, @Nonnull IRI dataRecordURI) {
		Preconditions.checkNotNull(dataRecordURI, "dataRecordURI = null.");
		Preconditions.checkNotNull(statements, "Datarecord statements = null.");
		DataRecord metadata = super.parse(statements, dataRecordURI);
		ValueFactory f = SimpleValueFactory.getInstance();
		for (Statement st : statements) {
			Resource subject = st.getSubject();
			IRI predicate = st.getPredicate();
			Value object = st.getObject();
			if (subject.equals(dataRecordURI)) {
				if (predicate.equals(FDP.RML_MAPPING)) {
					metadata.setRmlURI((IRI) object);
				} else if (predicate.equals(FDP.REFERS_TO)) {
					metadata.setDistributionURI((IRI) object);
				} else if (predicate.equals(DCTERMS.ISSUED)) {
					metadata.setDataRecordIssued(f.createLiteral(object.stringValue(), XMLSchema.DATETIME));
				} else if (predicate.equals(DCTERMS.MODIFIED)) {
					metadata.setDataRecordModified(f.createLiteral(object.stringValue(), XMLSchema.DATETIME));
				}
			}
		}
		return metadata;
	}

	// Create DataRecord from RDF string
	public DataRecord parse(@Nonnull String dataRecordMetadata, @Nonnull IRI dataRecordURI, IRI datasetURI,
			@Nonnull RDFFormat format) throws Exception {
		Preconditions.checkNotNull(dataRecordMetadata, "DataRecordMetadata string = null.");
		Preconditions.checkNotNull(dataRecordURI, "Datarecord URI = null.");
		Preconditions.checkNotNull(format, "RDF format = null.");
		Preconditions.checkArgument(!dataRecordMetadata.isEmpty(), "DataRecordMetadata content isEmpty");
		List<Statement> statements = Utils.getStatements(dataRecordMetadata, dataRecordURI, format);
		DataRecord metadata = this.parse(statements, dataRecordURI);
		metadata.setParentURI(datasetURI);
		return metadata;
	}

	// Create DataRecord from RDF string
	public DataRecord parse(@Nonnull String dataRecordMetadata, IRI baseURI, @Nonnull RDFFormat format)
			throws Exception {
		Preconditions.checkNotNull(dataRecordMetadata, "DataRecordMetadata = null.");
		Preconditions.checkNotNull(format, "RDFFormat = null.");
		Preconditions.checkArgument(!dataRecordMetadata.isEmpty(), "DataRecordMetadata content isEmpty.");
		List<Statement> statements = Utils.getStatements(dataRecordMetadata, baseURI, format);
		IRI catalogURI = (IRI) statements.get(0).getSubject();
		DataRecord metadata = this.parse(statements, catalogURI);
		metadata.setUri(null);
		return metadata;
	}
}
