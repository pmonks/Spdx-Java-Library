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

import java.util.Collection;

/**
 * DO NOT EDIT - this file is generated by the Owl to Java Utility 
 * See: https://github.com/spdx/tools-java 
 * 
 * An Element is a representation of a fundamental concept either directly inherent 
 * to the Bill of Materials (BOM) domain or indirectly related to the BOM domain and necessary 
 * for contextually characterizing BOM concepts and relationships. Within SPDX-3.0 
 * structure this is the base class acting as a consistent, unifying, and interoperable 
 * foundation for all explicit and inter-relatable content objects. 
 */
public class Element extends Payload  {

	Collection<ExternalReference> externalReferences;
	Collection<ExternalIdentifier> externalIdentifiers;
	
	/**
	 * Create the Element with default model store and generated anonymous ID
	 * @throws InvalidSPDXAnalysisException when unable to create the Element
	 */
	public Element() throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore().getNextId(IdType.Anonymous, null));
	}

	/**
	 * @param objectUri URI or anonymous ID for the Element
	 * @throws InvalidSPDXAnalysisException when unable to create the Element
	 */
	public Element(String objectUri) throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore(), objectUri, DefaultModelStore.getDefaultCopyManager(), true);
	}

	/**
	 * @param modelStore Model store where the Element is to be stored
	 * @param objectUri URI or anonymous ID for the Element
	 * @param copyManager Copy manager for the Element - can be null if copying is not required
	 * @param create true if Element is to be created
	 * @throws InvalidSPDXAnalysisException when unable to create the Element
	 */
	 @SuppressWarnings("unchecked")
	public Element(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager,
			boolean create)	throws InvalidSPDXAnalysisException {
		super(modelStore, objectUri, copyManager, create);
		externalReferences = (Collection<ExternalReference>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_EXTERNAL_REFERENCE, ExternalReference.class);
		externalIdentifiers = (Collection<ExternalIdentifier>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_EXTERNAL_IDENTIFIER, ExternalIdentifier.class);
	}

	/**
	 * Create the Element from the builder - used in the builder class
	 * @param builder Builder to create the Element from
	 * @throws InvalidSPDXAnalysisException when unable to create the Element
	 */
	protected Element(ElementBuilder builder) throws InvalidSPDXAnalysisException {
		super(builder);
		externalReferences = (Collection<ExternalReference>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_EXTERNAL_REFERENCE, ExternalReference.class);
		externalIdentifiers = (Collection<ExternalIdentifier>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_EXTERNAL_IDENTIFIER, ExternalIdentifier.class);
		getExternalReferences().addAll(builder.externalReferences);
		getExternalIdentifiers().addAll(builder.externalIdentifiers);
		setDescription(builder.description);
		setSummary(builder.summary);
	}

	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#getType()
	 */
	@Override
	public String getType() {
		return "Core.Element";
	}
	
	// Getters and Setters
	public Collection<ExternalReference> getExternalReferences() {
		return externalReferences;
	}
	public Collection<ExternalIdentifier> getExternalIdentifiers() {
		return externalIdentifiers;
	}
	

		/**
	 * @return the description
	 */
	public Optional<String> getDescription() throws InvalidSPDXAnalysisException {
		return getStringPropertyValue(SpdxConstants.CORE_PROP_DESCRIPTION);
	}
	/**
	 * @param description the description to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public Element setDescription(@Nullable String description) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.CORE_PROP_DESCRIPTION, description);
		return this;
	}

		/**
	 * @return the summary
	 */
	public Optional<String> getSummary() throws InvalidSPDXAnalysisException {
		return getStringPropertyValue(SpdxConstants.CORE_PROP_SUMMARY);
	}
	/**
	 * @param summary the summary to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public Element setSummary(@Nullable String summary) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.CORE_PROP_SUMMARY, summary);
		return this;
	}
	
	
	@Override
	public String toString() {
		return "Element: "+getObjectUri();
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#_verify(java.util.List)
	 */
	@Override
	protected List<String> _verify(Set<String> verifiedIds, String specVersion, List<ProfileIdentifierType> profiles) {
		List<String> retval = new ArrayList<>();
		retval.addAll(super._verify(verifiedIds, specVersion, profiles));
		try {
			Optional<String> description = getDescription();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting description for Element: "+e.getMessage());
		}
		try {
			Optional<String> summary = getSummary();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting summary for Element: "+e.getMessage());
		}
		for (ExternalReference externalReference:externalReferences) {
			retval.addAll(externalReference.verify(verifiedIds, specVersion, profiles));
		}
		for (ExternalIdentifier externalIdentifier:externalIdentifiers) {
			retval.addAll(externalIdentifier.verify(verifiedIds, specVersion, profiles));
		}
		return retval;
	}
	
	public static class ElementBuilder extends PayloadBuilder {
	
		public ElementBuilder(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) {
			super(modelStore, objectUri, copyManager);
		}
		
		Collection<ExternalReference> externalReferences = new ArrayList<>();
		Collection<ExternalIdentifier> externalIdentifiers = new ArrayList<>();
		String description = null;
		String summary = null;
		
		
		/**
		 * Adds a externalReference to the initial collection
		 * @parameter externalReference externalReference to add
		 * @return this for chaining
		**/
		ElementBuilder addExternalReference(ExternalReference externalReference) {
			if (Objects.nonNull(externalReference)) {
				externalReferences.add(externalReference);
			}
			return this;
		}
		
		/**
		 * Adds all elements from a collection to the initial externalReference collection
		 * @parameter externalReferenceCollection collection to initialize the externalReference
		 * @return this for chaining
		**/
		ElementBuilder addAllExternalReference(Collection<ExternalReference> externalReferenceCollection) {
			if (Objects.nonNull(externalReferenceCollection)) {
				externalReferences.addAll(externalReferenceCollection);
			}
			return this;
		}
		
		/**
		 * Adds a externalIdentifier to the initial collection
		 * @parameter externalIdentifier externalIdentifier to add
		 * @return this for chaining
		**/
		ElementBuilder addExternalIdentifier(ExternalIdentifier externalIdentifier) {
			if (Objects.nonNull(externalIdentifier)) {
				externalIdentifiers.add(externalIdentifier);
			}
			return this;
		}
		
		/**
		 * Adds all elements from a collection to the initial externalIdentifier collection
		 * @parameter externalIdentifierCollection collection to initialize the externalIdentifier
		 * @return this for chaining
		**/
		ElementBuilder addAllExternalIdentifier(Collection<ExternalIdentifier> externalIdentifierCollection) {
			if (Objects.nonNull(externalIdentifierCollection)) {
				externalIdentifiers.addAll(externalIdentifierCollection);
			}
			return this;
		}
		
		/**
		 * Sets the initial value of description
		 * @parameter description value to set
		 * @return this for chaining
		**/
		ElementBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		/**
		 * Sets the initial value of summary
		 * @parameter summary value to set
		 * @return this for chaining
		**/
		ElementBuilder setSummary(String summary) {
			this.summary = summary;
			return this;
		}
	
		
		/**
		 * @return the Element
		 * @throws InvalidSPDXAnalysisException on any errors during build
		 */
		public Element build() throws InvalidSPDXAnalysisException {
			IModelStoreLock lock = modelStore.enterCriticalSection(false);
			try {
				return new Element(this);
			} finally {
				modelStore.leaveCriticalSection(lock);
			}
		}
	}
}
