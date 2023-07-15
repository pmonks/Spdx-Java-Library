/**
 * Copyright (c) 2023 Source Auditor Inc.
 *
 * SPDX-License-Identifier: Apache-2.0
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
 
package org.spdx.library.model.core;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.spdx.library.InvalidSPDXAnalysisException;
import org.spdx.library.ModelCopyManager;
import org.spdx.library.SpdxConstants.SpdxMajorVersion;
import org.spdx.library.model.core.SpdxDocument.SpdxDocumentBuilder;
import org.spdx.storage.IModelStore;
import org.spdx.storage.simple.InMemSpdxStore;
import org.spdx.utility.compare.UnitTestHelper;

import junit.framework.TestCase;

public class SpdxDocumentTest extends TestCase {

	static final String TEST_OBJECT_URI = "https://test.uri/testuri";

	IModelStore modelStore;
	ModelCopyManager copyManager;

	protected void setUp() throws Exception {
		super.setUp();
		modelStore = new InMemSpdxStore();
		copyManager = new ModelCopyManager();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public static SpdxDocumentBuilder builderForSpdxDocumentTests(
					IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) throws InvalidSPDXAnalysisException {
		SpdxDocumentBuilder retval = new SpdxDocumentBuilder(modelStore, objectUri, copyManager);
		//TODO: Add in test values
		/********************
		***************/
		return retval;
	}
	
	/**
	 * Test method for {@link org.spdx.library.model.core.SpdxDocument#verify()}.
	 * @throws InvalidSPDXAnalysisException on errors
	 */
	public void testVerify() throws InvalidSPDXAnalysisException {
		SpdxDocument testSpdxDocument = builderForSpdxDocumentTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		List<String> result = testSpdxDocument.verify();
		assertTrue(result.isEmpty());
		// TODO - add negative tests
	}

	/**
	 * Test method for {@link org.spdx.library.model.core.SpdxDocument#getType()}.
	 */
	public void testGetType() throws InvalidSPDXAnalysisException {
		SpdxDocument testSpdxDocument = builderForSpdxDocumentTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("Core.SpdxDocument", testSpdxDocument.getType());
	}

	/**
	 * Test method for {@link org.spdx.library.model.core.SpdxDocument#toString()}.
	 */
	public void testToString() throws InvalidSPDXAnalysisException {
		SpdxDocument testSpdxDocument = builderForSpdxDocumentTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("SpdxDocument: "+TEST_OBJECT_URI, testSpdxDocument.toString());
	}

	/**
	 * Test method for {@link org.spdx.library.model.core.SpdxDocument#Element(org.spdx.library.model.core.SpdxDocument.SpdxDocumentBuilder)}.
	 */
	public void testSpdxDocumentSpdxDocumentBuilder() throws InvalidSPDXAnalysisException {
		SpdxDocument testSpdxDocument = builderForSpdxDocumentTests(modelStore, TEST_OBJECT_URI, copyManager).build();
	}
	
	public void testEquivalent() throws InvalidSPDXAnalysisException {
		SpdxDocument testSpdxDocument = builderForSpdxDocumentTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		SpdxDocument test2SpdxDocument = builderForSpdxDocumentTests(new InMemSpdxStore(), "https://testObject2", copyManager).build();
		assertTrue(testSpdxDocument.equivalent(test2SpdxDocument));
		assertTrue(test2SpdxDocument.equivalent(testSpdxDocument));
		// TODO change some parameters for negative tests
	}

/*
*/

}