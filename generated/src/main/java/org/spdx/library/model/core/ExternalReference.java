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
 * An External Reference points to a resource outside the scope of the SPDX-3.0 content 
 * that provides additional characteristics of an Element. 
 */
public class ExternalReference extends ModelObject  {

	Collection<String> locators;
	
	/**
	 * Create the ExternalReference with default model store and generated anonymous ID
	 * @throws InvalidSPDXAnalysisException when unable to create the ExternalReference
	 */
	public ExternalReference() throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore().getNextId(IdType.Anonymous, null));
	}

	/**
	 * @param objectUri URI or anonymous ID for the ExternalReference
	 * @throws InvalidSPDXAnalysisException when unable to create the ExternalReference
	 */
	public ExternalReference(String objectUri) throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore(), objectUri, DefaultModelStore.getDefaultCopyManager(), true);
	}

	/**
	 * @param modelStore Model store where the ExternalReference is to be stored
	 * @param objectUri URI or anonymous ID for the ExternalReference
	 * @param copyManager Copy manager for the ExternalReference - can be null if copying is not required
	 * @param create true if ExternalReference is to be created
	 * @throws InvalidSPDXAnalysisException when unable to create the ExternalReference
	 */
	 @SuppressWarnings("unchecked")
	public ExternalReference(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager,
			boolean create)	throws InvalidSPDXAnalysisException {
		super(modelStore, objectUri, copyManager, create);
		locators = (Collection<String>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_LOCATOR, String.class);
	}

	/**
	 * Create the ExternalReference from the builder - used in the builder class
	 * @param builder Builder to create the ExternalReference from
	 * @throws InvalidSPDXAnalysisException when unable to create the ExternalReference
	 */
	protected ExternalReference(ExternalReferenceBuilder builder) throws InvalidSPDXAnalysisException {
		super(builder);
		locators = (Collection<String>)(Collection<?>)this.getObjectPropertyValueCollection(SpdxConstants.CORE_PROP_LOCATOR, String.class);
		getLocators().addAll(builder.locators);
		setExternalReferenceType(builder.externalReferenceType);
		setContentType(builder.contentType);
		setComment(builder.comment);
	}

	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#getType()
	 */
	@Override
	public String getType() {
		return "Core.ExternalReference";
	}
	
	// Getters and Setters
	public Collection<String> getLocators() {
		return locators;
	}
	
	
	/**
	 * @return the externalReferenceType
	 */
	 @SuppressWarnings("unchecked")
	public Optional<ExternalReferenceType> getExternalReferenceType() throws InvalidSPDXAnalysisException {
		Optional<Enum<?>> retval = getEnumPropertyValue(SpdxConstants.CORE_PROP_EXTERNAL_REFERENCE_TYPE);
		if (retval.isPresent()) {
			if (!(retval.get() instanceof ExternalReferenceType)) {
				throw new InvalidSPDXAnalysisException("Incorrect type stored for ");
			}
			return (Optional<ExternalReferenceType>)(Optional<?>)(retval);
		} else {
			return Optional.empty();
		}
	}
	/**
	 * @param externalReferenceType the externalReferenceType to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public ExternalReference setExternalReferenceType(@Nullable ExternalReferenceType externalReferenceType) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.CORE_PROP_EXTERNAL_REFERENCE_TYPE, externalReferenceType);
		return this;
	}

		/**
	 * @return the contentType
	 */
	public Optional<String> getContentType() throws InvalidSPDXAnalysisException {
		return getStringPropertyValue(SpdxConstants.CORE_PROP_CONTENT_TYPE);
	}
	/**
	 * @param contentType the contentType to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public ExternalReference setContentType(@Nullable String contentType) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.CORE_PROP_CONTENT_TYPE, contentType);
		return this;
	}

		/**
	 * @return the comment
	 */
	public Optional<String> getComment() throws InvalidSPDXAnalysisException {
		return getStringPropertyValue(SpdxConstants.CORE_PROP_COMMENT);
	}
	/**
	 * @param comment the comment to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public ExternalReference setComment(@Nullable String comment) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.CORE_PROP_COMMENT, comment);
		return this;
	}
	
	
	@Override
	public String toString() {
		return "ExternalReference: "+getObjectUri();
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#_verify(java.util.List)
	 */
	@Override
	protected List<String> _verify(Set<String> verifiedIds, String specVersion, List<ProfileIdentifierType> profiles) {
		List<String> retval = new ArrayList<>();
		try {
			Optional<ExternalReferenceType> externalReferenceType = getExternalReferenceType();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting externalReferenceType for ExternalReference: "+e.getMessage());
		}
		try {
			Optional<String> contentType = getContentType();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting contentType for ExternalReference: "+e.getMessage());
		}
		try {
			Optional<String> comment = getComment();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting comment for ExternalReference: "+e.getMessage());
		}
		return retval;
	}
	
	public static class ExternalReferenceBuilder extends ModelObjectBuilder {
	
		public ExternalReferenceBuilder(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) {
			super(modelStore, objectUri, copyManager);
		}
		
		Collection<String> locators = new ArrayList<>();
		ExternalReferenceType externalReferenceType = null;
		String contentType = null;
		String comment = null;
		
		
		/**
		 * Adds a locator to the initial collection
		 * @parameter locator locator to add
		 * @return this for chaining
		**/
		ExternalReferenceBuilder addlocator(String locator) {
			if (Objects.nonNull(locator)) {
				locators.add(locator);
			}
			return this;
		}
		
		/**
		 * Adds all elements from a collection to the initial locator collection
		 * @parameter locatorCollection collection to initialize the locator
		 * @return this for chaining
		**/
		ExternalReferenceBuilder addAlllocator(Collection<String> locatorCollection) {
			if (Objects.nonNull(locatorCollection)) {
				locators.addAll(locatorCollection);
			}
			return this;
		}
		
		/**
		 * Sets the initial value of externalReferenceType
		 * @parameter externalReferenceType value to set
		 * @return this for chaining
		**/
		ExternalReferenceBuilder setexternalReferenceType(ExternalReferenceType externalReferenceType) {
			this.externalReferenceType = externalReferenceType;
			return this;
		}
		
		/**
		 * Sets the initial value of contentType
		 * @parameter contentType value to set
		 * @return this for chaining
		**/
		ExternalReferenceBuilder setcontentType(String contentType) {
			this.contentType = contentType;
			return this;
		}
		
		/**
		 * Sets the initial value of comment
		 * @parameter comment value to set
		 * @return this for chaining
		**/
		ExternalReferenceBuilder setcomment(String comment) {
			this.comment = comment;
			return this;
		}
	
		
		/**
		 * @return the ExternalReference
		 * @throws InvalidSPDXAnalysisException on any errors during build
		 */
		public ExternalReference build() throws InvalidSPDXAnalysisException {
			IModelStoreLock lock = modelStore.enterCriticalSection(false);
			try {
				return new ExternalReference(this);
			} finally {
				modelStore.leaveCriticalSection(lock);
			}
		}
	}
}
