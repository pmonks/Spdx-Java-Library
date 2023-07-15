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
 
package org.spdx.library.model.security;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.spdx.library.InvalidSPDXAnalysisException;
import org.spdx.library.ModelCopyManager;
import org.spdx.library.SpdxConstants.SpdxMajorVersion;
import org.spdx.library.model.security.CvssV2VulnAssessmentRelationship.CvssV2VulnAssessmentRelationshipBuilder;
import org.spdx.storage.IModelStore;
import org.spdx.storage.simple.InMemSpdxStore;
import org.spdx.utility.compare.UnitTestHelper;

import junit.framework.TestCase;

public class CvssV2VulnAssessmentRelationshipTest extends TestCase {

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
	
	public static CvssV2VulnAssessmentRelationshipBuilder builderForCvssV2VulnAssessmentRelationshipTests(
					IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationshipBuilder retval = new CvssV2VulnAssessmentRelationshipBuilder(modelStore, objectUri, copyManager);
		//TODO: Add in test values
		/********************
		***************/
		return retval;
	}
	
	/**
	 * Test method for {@link org.spdx.library.model.security.CvssV2VulnAssessmentRelationship#verify()}.
	 * @throws InvalidSPDXAnalysisException on errors
	 */
	public void testVerify() throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationship testCvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		List<String> result = testCvssV2VulnAssessmentRelationship.verify();
		assertTrue(result.isEmpty());
		// TODO - add negative tests
	}

	/**
	 * Test method for {@link org.spdx.library.model.security.CvssV2VulnAssessmentRelationship#getType()}.
	 */
	public void testGetType() throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationship testCvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("Security.CvssV2VulnAssessmentRelationship", testCvssV2VulnAssessmentRelationship.getType());
	}

	/**
	 * Test method for {@link org.spdx.library.model.security.CvssV2VulnAssessmentRelationship#toString()}.
	 */
	public void testToString() throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationship testCvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		assertEquals("CvssV2VulnAssessmentRelationship: "+TEST_OBJECT_URI, testCvssV2VulnAssessmentRelationship.toString());
	}

	/**
	 * Test method for {@link org.spdx.library.model.security.CvssV2VulnAssessmentRelationship#Element(org.spdx.library.model.security.CvssV2VulnAssessmentRelationship.CvssV2VulnAssessmentRelationshipBuilder)}.
	 */
	public void testCvssV2VulnAssessmentRelationshipCvssV2VulnAssessmentRelationshipBuilder() throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationship testCvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(modelStore, TEST_OBJECT_URI, copyManager).build();
	}
	
	public void testEquivalent() throws InvalidSPDXAnalysisException {
		CvssV2VulnAssessmentRelationship testCvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(modelStore, TEST_OBJECT_URI, copyManager).build();
		CvssV2VulnAssessmentRelationship test2CvssV2VulnAssessmentRelationship = builderForCvssV2VulnAssessmentRelationshipTests(new InMemSpdxStore(), "https://testObject2", copyManager).build();
		assertTrue(testCvssV2VulnAssessmentRelationship.equivalent(test2CvssV2VulnAssessmentRelationship));
		assertTrue(test2CvssV2VulnAssessmentRelationship.equivalent(testCvssV2VulnAssessmentRelationship));
		// TODO change some parameters for negative tests
	}

/*
*/

}