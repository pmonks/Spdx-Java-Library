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
 
package org.spdx.library.model.dataset;

import org.spdx.library.IndividualUriValue;

/**
 * DO NOT EDIT - this file is generated by the Owl to Java Utility 
 * See: https://github.com/spdx/tools-java 
 * 
 * Describes the different structures of data within a given dataset. A dataset can 
 * have multiple types of data, or even a single type of data but still match multiple 
 * types, for example sensor data could also be timeseries or labeled image data could 
 * also be considered categorical. 
 */
public enum DatasetType implements IndividualUriValue {

	SYNTACTIC("syntactic"),
	GRAPH("graph"),
	NUMERIC("numeric"),
	TIMESERIES("timeseries"),
	NO_ASSERTION("noAssertion"),
	IMAGE("image"),
	CATEGORICAL("categorical"),
	AUDIO("audio"),
	SENSOR("sensor"),
	OTHER("other"),
	TIMESTAMP("timestamp"),
	VIDEO("video"),
	STRUCTURED("structured"),
	TEXT("text");
	
	private String longName;
	
	private DatasetType(String longName) {
		this.longName = longName;
	}
	
	@Override
	public String getIndividualURI() {
		return getNameSpace() + "/" + getLongName();
	}
	
	public String getLongName() {
		return longName;
	}
	
	public String getNameSpace() {
		return "https://spdx.org/rdf/Dataset/DatasetType";
	}
}

