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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.spdx.library.DefaultModelStore;
import org.spdx.library.InvalidSPDXAnalysisException;
import org.spdx.library.ModelCopyManager;
import org.spdx.library.SpdxConstants;
import org.spdx.library.model.ModelObject;
import org.spdx.storage.IModelStore;
import org.spdx.storage.IModelStore.IdType;
import org.spdx.storage.IModelStore.IModelStoreLock;

import org.spdx.library.model.core.Agent;
import org.spdx.library.model.core.Element;
import org.spdx.library.model.core.ProfileIdentifierType;
import org.spdx.library.model.core.Relationship;

/**
 * DO NOT EDIT - this file is generated by the Owl to Java Utility 
 * See: https://github.com/spdx/tools-java 
 * 
 * VulnAssessmentRelationship is the ancestor class common to all vulnerability 
 * assessment relationships. It factors out the common properties shared by them. 
 * External property restriction on /Core/Relationship/to: minCount: 1 
 */
public class VulnAssessmentRelationship extends Relationship  {

	
	/**
	 * Create the VulnAssessmentRelationship with default model store and generated anonymous ID
	 * @throws InvalidSPDXAnalysisException when unable to create the VulnAssessmentRelationship
	 */
	public VulnAssessmentRelationship() throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore().getNextId(IdType.Anonymous, null));
	}

	/**
	 * @param objectUri URI or anonymous ID for the VulnAssessmentRelationship
	 * @throws InvalidSPDXAnalysisException when unable to create the VulnAssessmentRelationship
	 */
	public VulnAssessmentRelationship(String objectUri) throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore(), objectUri, DefaultModelStore.getDefaultCopyManager(), true);
	}

	/**
	 * @param modelStore Model store where the VulnAssessmentRelationship is to be stored
	 * @param objectUri URI or anonymous ID for the VulnAssessmentRelationship
	 * @param copyManager Copy manager for the VulnAssessmentRelationship - can be null if copying is not required
	 * @param create true if VulnAssessmentRelationship is to be created
	 * @throws InvalidSPDXAnalysisException when unable to create the VulnAssessmentRelationship
	 */
	public VulnAssessmentRelationship(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager,
			boolean create)	throws InvalidSPDXAnalysisException {
		super(modelStore, objectUri, copyManager, create);
	}

	/**
	 * Create the VulnAssessmentRelationship from the builder - used in the builder class
	 * @param builder Builder to create the VulnAssessmentRelationship from
	 * @throws InvalidSPDXAnalysisException when unable to create the VulnAssessmentRelationship
	 */
	protected VulnAssessmentRelationship(VulnAssessmentRelationshipBuilder builder) throws InvalidSPDXAnalysisException {
		super(builder);
		setAssessedElement(builder.assessedElement);
		setSuppliedBy(builder.suppliedBy);
	}

	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#getType()
	 */
	@Override
	public String getType() {
		return "Security.VulnAssessmentRelationship";
	}
	
	// Getters and Setters
	
	/**
	 * @return the assessedElement
	 */
	public Optional<Element> getAssessedElement() throws InvalidSPDXAnalysisException {
		return getElementPropertyValue(SpdxConstants.SECURITY_PROP_ASSESSED_ELEMENT);
	}
	
	/**
	 * @param assessedElement the assessedElement to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public VulnAssessmentRelationship setAssessedElement(@Nullable Element assessedElement) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.SECURITY_PROP_ASSESSED_ELEMENT, assessedElement);
		return this;
	}
	
	/**
	 * @return the suppliedBy
	 */
	public Optional<Element> getSuppliedBy() throws InvalidSPDXAnalysisException {
		return getElementPropertyValue(SpdxConstants.SECURITY_PROP_SUPPLIED_BY);
	}
	
	/**
	 * @param suppliedBy the suppliedBy to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public VulnAssessmentRelationship setSuppliedBy(@Nullable Element suppliedBy) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.SECURITY_PROP_SUPPLIED_BY, suppliedBy);
		return this;
	}
	
	
	
	@Override
	public String toString() {
		return "VulnAssessmentRelationship: "+getObjectUri();
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#_verify(java.util.List)
	 */
	@Override
	protected List<String> _verify(Set<String> verifiedIds, String specVersion, List<ProfileIdentifierType> profiles) {
		List<String> retval = new ArrayList<>();
		retval.addAll(super._verify(verifiedIds, specVersion, profiles));
		Optional<Element> assessedElement;
		try {
			assessedElement = getAssessedElement();
			if (assessedElement.isPresent()) {
				retval.addAll(assessedElement.get().verify(verifiedIds, specVersion, profiles));
			}
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting assessedElement for VulnAssessmentRelationship: "+e.getMessage());
		}
		Optional<Element> suppliedBy;
		try {
			suppliedBy = getSuppliedBy();
			if (suppliedBy.isPresent()) {
				retval.addAll(suppliedBy.get().verify(verifiedIds, specVersion, profiles));
			}
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting suppliedBy for VulnAssessmentRelationship: "+e.getMessage());
		}
		return retval;
	}
	
	public static class VulnAssessmentRelationshipBuilder extends RelationshipBuilder {
	
		public VulnAssessmentRelationshipBuilder(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) {
			super(modelStore, objectUri, copyManager);
		}
		
		Element assessedElement = null;
		Agent suppliedBy = null;
		
		
		/**
		 * Sets the initial value of assessedElement
		 * @parameter assessedElement value to set
		 * @return this for chaining
		**/
		VulnAssessmentRelationshipBuilder setassessedElement(Element assessedElement) {
			this.assessedElement = assessedElement;
			return this;
		}
		
		/**
		 * Sets the initial value of suppliedBy
		 * @parameter suppliedBy value to set
		 * @return this for chaining
		**/
		VulnAssessmentRelationshipBuilder setsuppliedBy(Agent suppliedBy) {
			this.suppliedBy = suppliedBy;
			return this;
		}
	
		
		/**
		 * @return the VulnAssessmentRelationship
		 * @throws InvalidSPDXAnalysisException on any errors during build
		 */
		public VulnAssessmentRelationship build() throws InvalidSPDXAnalysisException {
			IModelStoreLock lock = modelStore.enterCriticalSection(false);
			try {
				return new VulnAssessmentRelationship(this);
			} finally {
				modelStore.leaveCriticalSection(lock);
			}
		}
	}
}
