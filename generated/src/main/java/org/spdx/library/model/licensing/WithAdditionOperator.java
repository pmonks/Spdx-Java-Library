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

import org.spdx.library.model.core.ProfileIdentifierType;

/**
 * DO NOT EDIT - this file is generated by the Owl to Java Utility 
 * See: https://github.com/spdx/tools-java 
 * 
 * A WithAdditionOperator indicates that the designated License is subject to the 
 * designated LicenseAddition, which might be a license exception on the SPDX Exceptions 
 * List (ListedLicenseException) or may be other additional text (CustomLicenseAddition). 
 * It is represented in the SPDX License Expression Syntax by the `WITH` operator. 
 */
public class WithAdditionOperator extends AnyLicenseInfo  {

	
	/**
	 * Create the WithAdditionOperator with default model store and generated anonymous ID
	 * @throws InvalidSPDXAnalysisException when unable to create the WithAdditionOperator
	 */
	public WithAdditionOperator() throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore().getNextId(IdType.Anonymous, null));
	}

	/**
	 * @param objectUri URI or anonymous ID for the WithAdditionOperator
	 * @throws InvalidSPDXAnalysisException when unable to create the WithAdditionOperator
	 */
	public WithAdditionOperator(String objectUri) throws InvalidSPDXAnalysisException {
		this(DefaultModelStore.getDefaultModelStore(), objectUri, DefaultModelStore.getDefaultCopyManager(), true);
	}

	/**
	 * @param modelStore Model store where the WithAdditionOperator is to be stored
	 * @param objectUri URI or anonymous ID for the WithAdditionOperator
	 * @param copyManager Copy manager for the WithAdditionOperator - can be null if copying is not required
	 * @param create true if WithAdditionOperator is to be created
	 * @throws InvalidSPDXAnalysisException when unable to create the WithAdditionOperator
	 */
	public WithAdditionOperator(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager,
			boolean create)	throws InvalidSPDXAnalysisException {
		super(modelStore, objectUri, copyManager, create);
	}

	/**
	 * Create the WithAdditionOperator from the builder - used in the builder class
	 * @param builder Builder to create the WithAdditionOperator from
	 * @throws InvalidSPDXAnalysisException when unable to create the WithAdditionOperator
	 */
	protected WithAdditionOperator(WithAdditionOperatorBuilder builder) throws InvalidSPDXAnalysisException {
		super(builder);
	}

	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#getType()
	 */
	@Override
	public String getType() {
		return "Licensing.WithAdditionOperator";
	}
	
	// Getters and Setters
	
	
	
	@Override
	public String toString() {
		return "WithAdditionOperator: "+getObjectUri();
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.library.model.ModelObject#_verify(java.util.List)
	 */
	@Override
	protected List<String> _verify(Set<String> verifiedIds, String specVersion, List<ProfileIdentifierType> profiles) {
		List<String> retval = new ArrayList<>();
		retval.addAll(super._verify(verifiedIds, specVersion, profiles));
		return retval;
	}
	
	public static class WithAdditionOperatorBuilder extends AnyLicenseInfoBuilder {
	
		public WithAdditionOperatorBuilder(IModelStore modelStore, String objectUri, @Nullable ModelCopyManager copyManager) {
			super(modelStore, objectUri, copyManager);
		}
		
		
	
		
		/**
		 * @return the WithAdditionOperator
		 * @throws InvalidSPDXAnalysisException on any errors during build
		 */
		public WithAdditionOperator build() throws InvalidSPDXAnalysisException {
			IModelStoreLock lock = modelStore.enterCriticalSection(false);
			try {
				return new WithAdditionOperator(this);
			} finally {
				modelStore.leaveCriticalSection(lock);
			}
		}
	}
}
