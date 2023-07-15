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

import org.spdx.library.model.core.Element;
import org.spdx.library.model.core.ProfileIdentifierType;

/**
 * DO NOT EDIT - this file is generated by the Owl to Java Utility 
 * See: https://github.com/spdx/tools-java 
 * 
 * A LicenseAddition represents text which is intended to be added to a License as additional 
 * text, but which is not itself intended to be a standalone License. It may be an exception 
 * which is listed on the SPDX Exceptions List (ListedLicenseException), or may be 
 * any other additional text (as an exception or otherwise) which is defined by an SPDX 
 * data creator (CustomLicenseAddition). 
 */
public class LicenseAddition extends Element  {

	
	/**
	 * Create the LicenseAddition with default model store and generated anonymous ID
	 * @throws InvalidSPDXAnalysisException when unable to create the LicenseAddition
	 */
	public LicenseAddition() throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore().getNextId(IdType.Anonymous, null));
	}

	/**
	 * @param objectUri URI or anonymous ID for the LicenseAddition
	 * @throws InvalidSPDXAnalysisException when unable to create the LicenseAddition
	 */
	public LicenseAddition(String objectUri) throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore(), objectUri, DefaultModelStore.getDefaultCopyManager(), true);
	}

	/**
	 * @param modelStore Model store where the LicenseAddition is to be stored
	 * @param objectUri URI or anonymous ID for the LicenseAddition
	 * @param copyManager Copy manager for the LicenseAddition - can be null if copying is not required
	 * @param create true if LicenseAddition is to be created
	 * @throws InvalidSPDXAnalysisException when unable to create the LicenseAddition
	 */
	public LicenseAddition(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager,
			boolean create)	throws InvalidSPDXAnalysisException {
		super(modelStore, objectUri, copyManager, create);
	}

	/**
	 * Create the LicenseAddition from the builder - used in the builder class
	 * @param builder Builder to create the LicenseAddition from
	 * @throws InvalidSPDXAnalysisException when unable to create the LicenseAddition
	 */
	protected LicenseAddition(LicenseAdditionBuilder builder) throws InvalidSPDXAnalysisException {
		super(builder);
		setIsDeprecatedAdditionId(builder.isDeprecatedAdditionId);
		setStandardAdditionTemplate(builder.standardAdditionTemplate);
		setAdditionText(builder.additionText);
	}

	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#getType()
	 */
	@Override
	public String getType() {
		return "Licensing.LicenseAddition";
	}
	
	// Getters and Setters
	

		/**
	 * @return the isDeprecatedAdditionId
	 */
	public Optional<Boolean> getIsDeprecatedAdditionId() throws InvalidSPDXAnalysisException {
		return getBooleanPropertyValue(SpdxConstants.LICENSING_PROP_IS_DEPRECATED_ADDITION_ID);
	}
	
	/**
	 * @param isDeprecatedAdditionId the isDeprecatedAdditionId to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public LicenseAddition setIsDeprecatedAdditionId(@Nullable Boolean isDeprecatedAdditionId) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.LICENSING_PROP_IS_DEPRECATED_ADDITION_ID, isDeprecatedAdditionId);
		return this;
	}

		/**
	 * @return the standardAdditionTemplate
	 */
	public Optional<String> getStandardAdditionTemplate() throws InvalidSPDXAnalysisException {
		return getStringPropertyValue(SpdxConstants.LICENSING_PROP_STANDARD_ADDITION_TEMPLATE);
	}
	/**
	 * @param standardAdditionTemplate the standardAdditionTemplate to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public LicenseAddition setStandardAdditionTemplate(@Nullable String standardAdditionTemplate) throws InvalidSPDXAnalysisException {
		setPropertyValue(SpdxConstants.LICENSING_PROP_STANDARD_ADDITION_TEMPLATE, standardAdditionTemplate);
		return this;
	}

	/**
	 * @return the additionText
	 */
	public @Nullable String getAdditionText() throws InvalidSPDXAnalysisException {
		Optional<String> retval = getStringPropertyValue(SpdxConstants.LICENSING_PROP_ADDITION_TEXT);
		return retval.isPresent() ? retval.get() : null;
	}
		/**
	 * @param additionText the additionText to set
	 * @return this to chain setters
	 * @throws InvalidSPDXAnalysisException 
	 */
	public LicenseAddition setAdditionText(@Nullable String additionText) throws InvalidSPDXAnalysisException {
		if (isStrict() && Objects.isNull(additionText)) {
			throw new InvalidSPDXAnalysisException("additionText is a required property");
		}
		setPropertyValue(SpdxConstants.LICENSING_PROP_ADDITION_TEXT, additionText);
		return this;
	}
	
	
	@Override
	public String toString() {
		return "LicenseAddition: "+getObjectUri();
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#_verify(java.util.List)
	 */
	@Override
	protected List<String> _verify(Set<String> verifiedIds, String specVersion, List<ProfileIdentifierType> profiles) {
		List<String> retval = new ArrayList<>();
		retval.addAll(super._verify(verifiedIds, specVersion, profiles));
		try {
			Optional<Boolean> isDeprecatedAdditionId = getIsDeprecatedAdditionId();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting isDeprecatedAdditionId for LicenseAddition: "+e.getMessage());
		}
		try {
			Optional<String> standardAdditionTemplate = getStandardAdditionTemplate();
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting standardAdditionTemplate for LicenseAddition: "+e.getMessage());
		}
		try {
			String additionText = getAdditionText();
			if (Objects.isNull(additionText)) {
				retval.add("Missing additionText in LicenseAddition");
			}
		} catch (InvalidSPDXAnalysisException e) {
			retval.add("Error getting additionText for LicenseAddition: "+e.getMessage());
		}
		return retval;
	}
	
	public static class LicenseAdditionBuilder extends ElementBuilder {
	
		public LicenseAdditionBuilder(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) {
			super(modelStore, objectUri, copyManager);
		}
		
		Boolean isDeprecatedAdditionId = null;
		String standardAdditionTemplate = null;
		String additionText = null;
		
		
		/**
		 * Sets the initial value of isDeprecatedAdditionId
		 * @parameter isDeprecatedAdditionId value to set
		 * @return this for chaining
		**/
		LicenseAdditionBuilder setisDeprecatedAdditionId(Boolean isDeprecatedAdditionId) {
			this.isDeprecatedAdditionId = isDeprecatedAdditionId;
			return this;
		}
		
		/**
		 * Sets the initial value of standardAdditionTemplate
		 * @parameter standardAdditionTemplate value to set
		 * @return this for chaining
		**/
		LicenseAdditionBuilder setstandardAdditionTemplate(String standardAdditionTemplate) {
			this.standardAdditionTemplate = standardAdditionTemplate;
			return this;
		}
		
		/**
		 * Sets the initial value of additionText
		 * @parameter additionText value to set
		 * @return this for chaining
		**/
		LicenseAdditionBuilder setadditionText(String additionText) {
			this.additionText = additionText;
			return this;
		}
	
		
		/**
		 * @return the LicenseAddition
		 * @throws InvalidSPDXAnalysisException on any errors during build
		 */
		public LicenseAddition build() throws InvalidSPDXAnalysisException {
			IModelStoreLock lock = modelStore.enterCriticalSection(false);
			try {
				return new LicenseAddition(this);
			} finally {
				modelStore.leaveCriticalSection(lock);
			}
		}
	}
}
