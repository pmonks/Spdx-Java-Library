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
 
package org.spdx.library.model.licensing;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.spdx.library.InvalidSPDXAnalysisException;
import org.spdx.library.ModelCopyManager;
import org.spdx.library.SpdxConstants.SpdxMajorVersion;
import org.spdx.library.model.licensing.OrLaterOperator.OrLaterOperatorBuilder;
import org.spdx.storage.IModelStore;
import org.spdx.storage.simple.InMemSpdxStore;
import org.spdx.utility.compare.UnitTestHelper;

import junit.framework.TestCase;

public class OrLaterOperatorTest extends TestCase {

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
	
	public static OrLaterOperatorBuilder builderForOrLaterOperatorTests(
					IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) throws InvalidSPDXAnalysisException {
		OrLaterOperatorBuilder retval = new OrLaterOperatorBuilder(modelStore, objectUri, copyManager);
		//TODO: Add in test values
		/********************
		***************/
		return retval;
	}
	
	/**
	 * Test method for {@link org.spdx.library.model.licensing.OrLaterOperator#verify()}.
	 * @throws InvalidSPDXAnalysisException on errors
	 */
	public void testVerify() throws InvalidSPDXAnalysisException {
		OrLaterOperator testOrLaterOperator = builderForOrLaterOperatorTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		List<String> result = testOrLaterOperator.verify();
		assertTrue(result.isEmpty());
		// TODO - add negative tests
	}

	/**
	 * Test method for {@link org.spdx.library.model.licensing.OrLaterOperator#getType()}.
	 */
	public void testGetType() throws InvalidSPDXAnalysisException {
		OrLaterOperator testOrLaterOperator = builderForOrLaterOperatorTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("Licensing.OrLaterOperator", testOrLaterOperator.getType());
	}

	/**
	 * Test method for {@link org.spdx.library.model.licensing.OrLaterOperator#toString()}.
	 */
	public void testToString() throws InvalidSPDXAnalysisException {
		OrLaterOperator testOrLaterOperator = builderForOrLaterOperatorTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("OrLaterOperator: "+TEST_OBJECT_URI, testOrLaterOperator.toString());
	}

	/**
	 * Test method for {@link org.spdx.library.model.licensing.OrLaterOperator#Element(org.spdx.library.model.licensing.OrLaterOperator.OrLaterOperatorBuilder)}.
	 */
	public void testOrLaterOperatorOrLaterOperatorBuilder() throws InvalidSPDXAnalysisException {
		OrLaterOperator testOrLaterOperator = builderForOrLaterOperatorTests(modelStore, TEST_OBJECT_URI, copyManager).build();
	}
	
	public void testEquivalent() throws InvalidSPDXAnalysisException {
		OrLaterOperator testOrLaterOperator = builderForOrLaterOperatorTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		OrLaterOperator test2OrLaterOperator = builderForOrLaterOperatorTests(new InMemSpdxStore(), "https://testObject2", copyManager).build();
		assertTrue(testOrLaterOperator.equivalent(test2OrLaterOperator));
		assertTrue(test2OrLaterOperator.equivalent(testOrLaterOperator));
		// TODO change some parameters for negative tests
	}

/*
*/

}