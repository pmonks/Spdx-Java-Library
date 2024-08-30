/**
 * Copyright (c) 2019 Source Auditor Inc.
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
package org.spdx.utility.compare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spdx.core.InvalidSPDXAnalysisException;
import org.spdx.library.ListedLicenses;
import org.spdx.library.model.v2.SpdxConstantsCompatV2;
import org.spdx.library.model.v3_0_1.expandedlicensing.ConjunctiveLicenseSet;
import org.spdx.library.model.v3_0_1.expandedlicensing.CustomLicense;
import org.spdx.library.model.v3_0_1.expandedlicensing.DisjunctiveLicenseSet;
import org.spdx.library.model.v3_0_1.expandedlicensing.License;
import org.spdx.library.model.v3_0_1.expandedlicensing.ListedLicense;
import org.spdx.library.model.v3_0_1.expandedlicensing.ListedLicenseException;
import org.spdx.library.model.v3_0_1.simplelicensing.AnyLicenseInfo;
import org.spdx.licenseTemplate.LicenseParserException;
import org.spdx.licenseTemplate.LicenseTemplateRuleException;
import org.spdx.licenseTemplate.LicenseTextHelper;
import org.spdx.licenseTemplate.LineColumn;
import org.spdx.licenseTemplate.SpdxLicenseTemplateHelper;
import org.spdx.utility.compare.CompareTemplateOutputHandler.DifferenceDescription;
import org.spdx.utility.compare.FilterTemplateOutputHandler.OptionalTextHandling;
import org.spdx.utility.compare.FilterTemplateOutputHandler.VarTextHandling;

/**
 * Primarily a static class of helper functions for comparing two SPDX licenses
 * @author Gary O'Neall
 *
 */
@SuppressWarnings("deprecation")
public class LicenseCompareHelper {
	
	static final Logger logger = LoggerFactory.getLogger(LicenseCompareHelper.class);

	protected static final Integer CROSS_REF_NUM_WORDS_MATCH = 80;
	
	protected static final Pattern REGEX_QUANTIFIER_PATTERN = Pattern.compile(".*\\.\\{(\\d+),(\\d+)\\}$");
	static final String START_COMMENT_CHAR_PATTERN = "(//|/\\*|\\*|#|' |REM |<!--|--|;|\\(\\*|\\{-)|\\.\\\\\"";
	
	public static String licenseUriToLicenseId(String objectUri) {
		if (objectUri.startsWith(SpdxConstantsCompatV2.LISTED_LICENSE_NAMESPACE_PREFIX)) {
			return objectUri.substring(SpdxConstantsCompatV2.LISTED_LICENSE_NAMESPACE_PREFIX.length());
		} else if (objectUri.startsWith(SpdxConstantsCompatV2.LISTED_LICENSE_URL)) {
			return objectUri.substring(SpdxConstantsCompatV2.LISTED_LICENSE_URL.length());
		} else {
			return objectUri; // no match - should we throw an exception?
		}
	}
	
	/**
	 * Remove common comment characters from either a template or license text strings
	 * @param s
	 * @return
	 */
	public static String removeCommentChars(String s) {
	       StringBuilder sb = new StringBuilder();
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new StringReader(s));
	            String line = reader.readLine();
	            while (line != null) {
	            	line = line.replaceAll("(\\*/|-->|-\\}|\\*\\)|\\s\\*)\\s*$", "");  // remove end of line comments
	                line = line.replaceAll("^\\s*" + START_COMMENT_CHAR_PATTERN, "");  // remove start of line comments
                    line = line.replaceAll("^\\s*<<beginOptional>>\\s*" + START_COMMENT_CHAR_PATTERN, "<<beginOptional>>");
                    sb.append(line);
	                sb.append("\n");
	                line = reader.readLine();
	            }
	            return sb.toString();
	        } catch (IOException e) {
	            logger.warn("IO error reading strings?!?", e);
	            return s;
	        } finally {
                if (Objects.nonNull(reader)) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        logger.warn("IO error closing a string reader?!?", e);
                    }
                }
	        }
	}
	
	/**
	 * Locate the original text starting with the start token and ending with the end token
	 * @param fullLicenseText
	 * @param startToken
	 * @param endToken
	 * @param tokenToLocation
	 * @return
	 */
	public static String locateOriginalText(String fullLicenseText, int startToken, int endToken,  
			Map<Integer, LineColumn> tokenToLocation, String[] tokens) {
		if (startToken > endToken) {
			return "";
		}
		LineColumn start = tokenToLocation.get(startToken);
		if (start == null) {
			return "";
		}
		LineColumn end = tokenToLocation.get(endToken);
		// If end == null, then we read to the end
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new StringReader(fullLicenseText));
			int currentLine = 1;
			String line = reader.readLine();
			while (line != null && currentLine < start.getLine()) {
				currentLine++;
				line = reader.readLine();
			}
			if (line == null) {
				return "";
			}
			if (end == null) {
				// read until the end of the stream
				StringBuilder sb = new StringBuilder(line.substring(start.getColumn(), line.length()));
				currentLine++;
				line = reader.readLine();
				while (line != null) {
					sb.append(line);
					currentLine++;
					line = reader.readLine();
				}
				return sb.toString();
			} else if (end.getLine() == currentLine) {
				return line.substring(start.getColumn(), end.getColumn()+end.getLen());
			} else {
				StringBuilder sb = new StringBuilder(line.substring(start.getColumn(), line.length()));
				currentLine++;
				line = reader.readLine();
				while (line != null && currentLine < end.getLine()) {
					sb.append("\n");
					sb.append(line);
					currentLine++;
					line = reader.readLine();
				}
				if (line != null && end.getColumn()+end.getLen() > 0) {
					sb.append("\n");
					sb.append(line.substring(0, end.getColumn()+end.getLen()));
				}
				return sb.toString();
			}			
		} catch (IOException e) {
			// just build with spaces - not ideal, but close enough most of the time
			StringBuilder sb = new StringBuilder(tokens[startToken]);
			for (int i = startToken+1; i <= endToken; i++) {
				sb.append(' ');
				sb.append(tokens[i]);
			}
			return sb.toString();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	/*
	 * @param text
	 * @return the first token in the license text
	 */
	public static String getFirstLicenseToken(String text) {
		String textToTokenize = LicenseTextHelper.normalizeText(LicenseTextHelper.replaceMultWord(LicenseTextHelper.replaceSpaceComma(
				LicenseTextHelper.removeLineSeparators(removeCommentChars(text))))).toLowerCase();
		Matcher m = LicenseTextHelper.TOKEN_SPLIT_PATTERN.matcher(textToTokenize);
		while (m.find()) {
			if (!m.group(1).trim().isEmpty()) {
				return m.group(1).trim();
			}
		}
		return null;
	}
	
	/**
	 * @param text
	 * @return true if the text contains a single token
	 */
	public static boolean isSingleTokenString(String text) {
		if (text.contains("\n")) {
			return false;
		}
		Matcher m = LicenseTextHelper.TOKEN_SPLIT_PATTERN.matcher(text);
		boolean found = false;
		while (m.find()) {
			if (!m.group(1).trim().isEmpty()) {
				if (found) {
					return false;
				} else {
					found = true;
				}
			}
		}
		return true;
	}

	/**
	 * Compares two licenses from potentially two different documents which may have
	 * different license ID's for the same license
	 * @param license1
	 * @param license2
	 * @param xlationMap Mapping the license ID's from license 1 to license 2
	 * @return
	 * @throws SpdxCompareException 
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static boolean isLicenseEqual(AnyLicenseInfo license1,
			AnyLicenseInfo license2, Map<String, String> xlationMap) throws SpdxCompareException, InvalidSPDXAnalysisException {
		if (license1 instanceof ConjunctiveLicenseSet) {
			if (!(license2 instanceof ConjunctiveLicenseSet)) {
				return false;
			} else {
				return isLicenseSetsEqual(((ConjunctiveLicenseSet)license1).getMembers(),
						((ConjunctiveLicenseSet)license2).getMembers(), xlationMap);
			}
		} else if (license1 instanceof DisjunctiveLicenseSet) {
			if (!(license2 instanceof DisjunctiveLicenseSet)) {
				return false;
			} else {
				return isLicenseSetsEqual(((DisjunctiveLicenseSet)license1).getMembers(),
						((DisjunctiveLicenseSet)license2).getMembers(), xlationMap);
			}
		} else if (license1 instanceof CustomLicense) {
			if (!(license2 instanceof CustomLicense)) {
				return false;
			} else {
				String licenseid1 = ((CustomLicense)license1).getObjectUri();
				String licenseid2 = ((CustomLicense)license2).getObjectUri();
				String xlatedLicenseId = xlationMap.get(licenseid1);
				if (xlatedLicenseId == null) {
					return false;	// no equivalent license was found
				}
				return xlatedLicenseId.equals(licenseid2);
			}
		} else {
            return license1.equals(license2);
        }
	}

	/**
	 * Compares two license sets using the xlationMap for the non-standard license IDs
	 * @param license1
	 * @param license2
	 * @return
	 * @throws SpdxCompareException 
	 * @throws InvalidSPDXAnalysisException 
	 */
	private static boolean isLicenseSetsEqual(Collection<AnyLicenseInfo> licenseInfos1, 
			Collection<AnyLicenseInfo> licenseInfos2, Map<String, String> xlationMap) throws SpdxCompareException, InvalidSPDXAnalysisException {
		// note - order does not matter
		if (licenseInfos1 == null) {
			return licenseInfos2 == null;
		}
		if (licenseInfos2 == null) {
			return false;
		}
		if (licenseInfos1.size() != licenseInfos2.size()) {
			return false;
		}
		for (AnyLicenseInfo ali1:licenseInfos1) {
			boolean found = false;
			for (AnyLicenseInfo ali2:licenseInfos2) {
				if (isLicenseEqual(ali1, ali2, xlationMap)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the text of a license minus any optional text - note: this include the default variable text
	 * @param licenseTemplate license template containing optional and var tags
	 * @param varTextHandling include original, exclude, or include the regex (enclosed with "~~~") for "var" text
	 * @return list of strings for all non-optional license text.  
	 * @throws SpdxCompareException
	 */
	public static List<String> getNonOptionalLicenseText(String licenseTemplate, 
			VarTextHandling varTextHandling) throws SpdxCompareException {
		return getNonOptionalLicenseText(licenseTemplate, varTextHandling, OptionalTextHandling.OMIT);
	}
	
	/**
	 * Get the text of a license converting variable and optional text according to the options
	 * @param licenseTemplate license template containing optional and var tags
	 * @param varTextHandling include original, exclude, or include the regex (enclosed with "~~~") for "var" text
	 * @param optionalTextHandling include optional text, exclude, or include a regex for the optional text
	 * @return list of strings for all non-optional license text.  
	 * @throws SpdxCompareException
	 */
	public static List<String> getNonOptionalLicenseText(String licenseTemplate, 
			VarTextHandling varTextHandling, OptionalTextHandling optionalTextHandling) throws SpdxCompareException {
		FilterTemplateOutputHandler filteredOutput = new FilterTemplateOutputHandler(varTextHandling, optionalTextHandling);
		try {
			SpdxLicenseTemplateHelper.parseTemplate(licenseTemplate, filteredOutput);
		} catch (LicenseTemplateRuleException e) {
			throw new SpdxCompareException("Invalid template rule found during filter: "+e.getMessage(),e);
		} catch (LicenseParserException e) {
			throw new SpdxCompareException("Invalid template found during filter: "+e.getMessage(),e);
		}
		return filteredOutput.getFilteredText();
	}
	
	/**
	 * @param template Template in the standard template format used for comparison
	 * @param compareText Text to compare using the template
	 * @return any differences found
	 * @throws SpdxCompareException
	 * @throws InvalidSPDXAnalysisException
	 */
	public static DifferenceDescription isTextMatchingTemplate(String template, String compareText) throws SpdxCompareException, InvalidSPDXAnalysisException {
		CompareTemplateOutputHandler compareTemplateOutputHandler = null;
		try {
			compareTemplateOutputHandler = new CompareTemplateOutputHandler(LicenseTextHelper.removeLineSeparators(removeCommentChars(compareText)));
		} catch (IOException e1) {
			throw new SpdxCompareException("IO Error reading the compare text: "+e1.getMessage(),e1);
		}
		try {
		    //TODO: The remove comment chars will not be removed for lines beginning with a template << or ending with >>
			SpdxLicenseTemplateHelper.parseTemplate(removeCommentChars(template), compareTemplateOutputHandler);
		} catch (LicenseTemplateRuleException e) {
			throw new SpdxCompareException("Invalid template rule found during compare: "+e.getMessage(),e);
		} catch (LicenseParserException e) {
			throw new SpdxCompareException("Invalid template found during compare: "+e.getMessage(),e);
		}
		return compareTemplateOutputHandler.getDifferences();
	}

	/**
	 * Compares license text to the license text of an SPDX Standard License
	 * @param license SPDX Standard License to compare
	 * @param compareText Text to compare to the standard license
	 * @return any differences found
	 * @throws SpdxCompareException
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static DifferenceDescription isTextStandardLicense(License license, String compareText) throws SpdxCompareException, InvalidSPDXAnalysisException {
		String licenseTemplate = license.getStandardLicenseTemplate().orElse("");
		if (licenseTemplate == null || licenseTemplate.trim().isEmpty()) {
			licenseTemplate = license.getLicenseText();
		}
		return isTextMatchingTemplate(licenseTemplate, compareText);
	}
	
	/**
	 * Compares exception text to the exception text of an SPDX Standard exception
	 * @param exception SPDX Standard exception to compare
	 * @param compareText Text to compare to the standard exceptions
	 * @return any differences found
	 * @throws SpdxCompareException
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static DifferenceDescription isTextStandardException(ListedLicenseException exception, String compareText) throws SpdxCompareException, InvalidSPDXAnalysisException {
		String exceptionTemplate = exception.getStandardAdditionTemplate().orElse("");
		if (exceptionTemplate == null || exceptionTemplate.trim().isEmpty()) {
			exceptionTemplate = exception.getAdditionText();
		}
		return isTextMatchingTemplate(exceptionTemplate, compareText);
	}

	/**
	 * Detect if a text contains the standard license (perhaps along with other text before and/or after)
	 * @param text    The text to search within (should not be null)
	 * @param license The standard SPDX license to search for (should not be null)
	 * @return True if the license is found within the text, false otherwise (or if either argument is null)
	 */
	public static boolean isStandardLicenseWithinText(String text, ListedLicense license) {
		try {
			return new TemplateRegexMatcher(license.getStandardLicenseTemplate().orElse(license.getLicenseText())).isTemplateMatchWithinText(text);
		} catch (SpdxCompareException e) {
			logger.warn("Error getting optional text for license " + license.getObjectUri(), e);
			return false;
		} catch (InvalidSPDXAnalysisException e) {
			logger.warn("Error getting optional text for license " + license.getObjectUri(), e);
			return false;
		}
	}


	/**
	 * Detect if a text contains the standard license exception (perhaps along with other text before and/or after)
	 * @param text    The text to search within (should not be null)
	 * @param exception The standard SPDX license exception to search for (should not be null)
	 * @return True if the license exception is found within the text, false otherwise (or if either argument is null)
	 */
	public static boolean isStandardLicenseExceptionWithinText(String text, ListedLicenseException exception) {
		boolean result = false;
		if (text == null || text.isEmpty() || exception == null) {
			return false;
		}
		try {
			return new TemplateRegexMatcher(exception.getStandardAdditionTemplate().orElse(exception.getAdditionText())).isTemplateMatchWithinText(text);
		} catch (SpdxCompareException e) {
			logger.warn("Error getting optional text for license exception ID " + exception.getObjectUri(), e);
		} catch (InvalidSPDXAnalysisException e) {
			logger.warn("Error getting optional text for license exception ID " + exception.getObjectUri(), e);
		}
		return result;
	}


	/**
	 * Returns a list of SPDX Standard License ID's that match the text provided using
	 * the SPDX matching guidelines.
	 * @param licenseText Text to compare to the standard license texts
	 * @return Array of SPDX standard license IDs that match
	 * @throws InvalidSPDXAnalysisException If an error occurs accessing the standard licenses
	 * @throws SpdxCompareException If an error occurs in the comparison
	 */
	public static String[] matchingStandardLicenseIds(String licenseText) throws InvalidSPDXAnalysisException, SpdxCompareException {
		List<String> stdLicenseIds = ListedLicenses.getListedLicenses().getSpdxListedLicenseIds();
		List<String> matchingIds  = new ArrayList<>();
		for (String stdLicId : stdLicenseIds) {
			ListedLicense license = ListedLicenses.getListedLicenses().getListedLicenseById(stdLicId);
			if (!isTextStandardLicense(license, licenseText).isDifferenceFound()) {
				matchingIds.add(licenseUriToLicenseId(license.getObjectUri()));
			}
		}
		return matchingIds.toArray(new String[matchingIds.size()]);
	}


	/**
	 * Returns a list of SPDX Standard License ID's from the provided list that were found within the text, using
	 * the SPDX matching guidelines.
	 * @param text Text to compare to
	 * @param licenseIds License ids to compare against
	 * @return List of SPDX standard license IDs from licenseIds that match
	 * @throws InvalidSPDXAnalysisException If an error occurs accessing the standard licenses
	 * @throws SpdxCompareException If an error occurs in the comparison
	 */
	public static List<String> matchingStandardLicenseIdsWithinText(String text, List<String> licenseIds) throws InvalidSPDXAnalysisException, SpdxCompareException {
		List<String> result = new ArrayList<>();

		if (text != null && !text.isEmpty() && licenseIds != null && !licenseIds.isEmpty()) {
			for (String stdLicId : licenseIds) {
				ListedLicense license = ListedLicenses.getListedLicenses().getListedLicenseById(stdLicId);
				if (isStandardLicenseWithinText(text, license)) {
					result.add(licenseUriToLicenseId(license.getObjectUri()));
				}
			}
		}

		return result;
	}


	/**
	 * Returns a list of SPDX Standard License ID's that were found within the text, using
	 * the SPDX matching guidelines.
	 * @param text Text to compare to all of the standard licenses
	 * @return List of SPDX standard license IDs that match
	 * @throws InvalidSPDXAnalysisException If an error occurs accessing the standard licenses
	 * @throws SpdxCompareException If an error occurs in the comparison
	 */
	public static List<String> matchingStandardLicenseIdsWithinText(String text) throws InvalidSPDXAnalysisException, SpdxCompareException {
		return matchingStandardLicenseIdsWithinText(text, ListedLicenses.getListedLicenses().getSpdxListedLicenseIds());
	}


	/**
	 * Returns a list of SPDX Standard License Exception ID's from the provided list that were found within the text, using
	 * the SPDX matching guidelines.
	 * @param text Text to compare to
	 * @param licenseExceptionIds License Exceptions Ids to compare against
	 * @return Array of SPDX standard license exception IDs from licenseExceptionIds that match
	 * @throws InvalidSPDXAnalysisException If an error occurs accessing the standard license exceptions
	 * @throws SpdxCompareException If an error occurs in the comparison
	 */
	public static List<String> matchingStandardLicenseExceptionIdsWithinText(String text, List<String> licenseExceptionIds) throws InvalidSPDXAnalysisException, SpdxCompareException {
		List<String> result = new ArrayList<>();

		if (text != null && !text.isEmpty() && licenseExceptionIds != null && !licenseExceptionIds.isEmpty()) {
			for (String stdLicExcId : licenseExceptionIds) {
				ListedLicenseException licenseException = ListedLicenses.getListedLicenses().getListedExceptionById(stdLicExcId);
				if (isStandardLicenseExceptionWithinText(text, licenseException)) {
					result.add(licenseUriToLicenseId(licenseException.getObjectUri()));
				}
			}
		}

		return result;
	}


	/**
	 * Returns a list of SPDX Standard License Exception ID's that were found within the text, using
	 * the SPDX matching guidelines.
	 * @param text Text to compare to all of the standard license exceptions
	 * @return Array of SPDX standard license exception IDs that match
	 * @throws InvalidSPDXAnalysisException If an error occurs accessing the standard license exceptions
	 * @throws SpdxCompareException If an error occurs in the comparison
	 */
	public static List<String> matchingStandardLicenseExceptionIdsWithinText(String text) throws InvalidSPDXAnalysisException, SpdxCompareException {
		return matchingStandardLicenseExceptionIdsWithinText(text, ListedLicenses.getListedLicenses().getSpdxListedExceptionIds());
	}


	private static <T> boolean contains(
			T[] array,
			T value
	) {
		for (T t : array) {
			if (Objects.equals(t, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Detect if a license pass black lists
	 * @param license license
	 * @param blackList license black list
	 * @return if the license pass black lists
	 * @throws InvalidSPDXAnalysisException
	 */
	public static boolean isLicensePassBlackList(
			AnyLicenseInfo license,
			String... blackList
	) throws InvalidSPDXAnalysisException {
		if (license == null) {
			return true;
		}
		if (blackList == null || blackList.length == 0) {
			return true;
		}
		if (license instanceof ConjunctiveLicenseSet) {
			for (AnyLicenseInfo member : ((ConjunctiveLicenseSet) license).getMembers()) {
				if (!isLicensePassBlackList(member, blackList)) {
					return false;
				}
			}
			return true;
		} else if (license instanceof DisjunctiveLicenseSet) {
			for (AnyLicenseInfo member : ((DisjunctiveLicenseSet) license).getMembers()) {
				if (isLicensePassBlackList(member, blackList)) {
					return true;
				}
			}
			return false;
		} else {
			return !contains(blackList, license.toString());
		}
	}

	/**
	 * Detect if a license pass white lists
	 * @param license license
	 * @param whiteList license white list
	 * @return if the license pass white lists
	 * @throws InvalidSPDXAnalysisException
	 */
	public static boolean isLicensePassWhiteList(
			AnyLicenseInfo license,
			String... whiteList
	) throws InvalidSPDXAnalysisException {
		if (license == null) {
			return false;
		}
		if (whiteList == null || whiteList.length == 0) {
			return false;
		}
		if (license instanceof ConjunctiveLicenseSet) {
			for (AnyLicenseInfo member : ((ConjunctiveLicenseSet) license).getMembers()) {
				if (!isLicensePassWhiteList(member, whiteList)) {
					return false;
				}
			}
			return true;
		} else if (license instanceof DisjunctiveLicenseSet) {
			for (AnyLicenseInfo member : ((DisjunctiveLicenseSet) license).getMembers()) {
				if (isLicensePassWhiteList(member, whiteList)) {
					return true;
				}
			}
			return false;
		} else {
			return contains(whiteList, license.toString());
		}
	}
	
	/**
	 * The following methods are provided for compatibility with the SPDX 2.X versions of the 
	 * library
	 */
	
	/**
	 * Compares two licenses from potentially two different documents which may have
	 * different license ID's for the same license
	 * @param license1
	 * @param license2
	 * @param xlationMap Mapping the license URIs from license 1 to license 2
	 * @return
	 * @throws SpdxCompareException 
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static boolean isLicenseEqual(org.spdx.library.model.v2.license.AnyLicenseInfo license1,
			org.spdx.library.model.v2.license.AnyLicenseInfo license2, Map<String, String> xlationMap) throws SpdxCompareException, InvalidSPDXAnalysisException {
		if (license1 instanceof org.spdx.library.model.v2.license.ConjunctiveLicenseSet) {
			if (!(license2 instanceof org.spdx.library.model.v2.license.ConjunctiveLicenseSet)) {
				return false;
			} else {
				return isLicenseSetsEqual((org.spdx.library.model.v2.license.ConjunctiveLicenseSet)license1,
						(org.spdx.library.model.v2.license.ConjunctiveLicenseSet)license2, xlationMap);
			}
		} else if (license1 instanceof org.spdx.library.model.v2.license.DisjunctiveLicenseSet) {
			if (!(license2 instanceof org.spdx.library.model.v2.license.DisjunctiveLicenseSet)) {
				return false;
			} else {
				return isLicenseSetsEqual((org.spdx.library.model.v2.license.DisjunctiveLicenseSet)license1,
						(org.spdx.library.model.v2.license.DisjunctiveLicenseSet)license2, xlationMap);
			}
		} else if (license1 instanceof org.spdx.library.model.v2.license.ExtractedLicenseInfo) {
			if (!(license2 instanceof org.spdx.library.model.v2.license.ExtractedLicenseInfo)) {
				return false;
			} else {
				String licenseUri1 = ((org.spdx.library.model.v2.license.ExtractedLicenseInfo)license1).getObjectUri();
				String licenseUri2 = ((org.spdx.library.model.v2.license.ExtractedLicenseInfo)license2).getObjectUri();
				String xlatedLicenseId = xlationMap.get(licenseUri1);
				if (xlatedLicenseId == null) {
					return false;	// no equivalent license was found
				}
				return xlatedLicenseId.equals(licenseUri2);
			}
		} else {
            return license1.equals(license2);
        }
	}

	/**
	 * Compares two license sets using the xlationMap for the non-standard license IDs
	 * @param license1
	 * @param license2
	 * @return
	 * @throws SpdxCompareException 
	 * @throws InvalidSPDXAnalysisException 
	 */
	private static boolean isLicenseSetsEqual(org.spdx.library.model.v2.license.LicenseSet license1, 
			org.spdx.library.model.v2.license.LicenseSet license2, Map<String, String> xlationMap) throws SpdxCompareException, InvalidSPDXAnalysisException {
		// note - order does not matter
		Collection<org.spdx.library.model.v2.license.AnyLicenseInfo> licenseInfos1 = license1.getMembers();
		Collection<org.spdx.library.model.v2.license.AnyLicenseInfo> licenseInfos2 = license2.getMembers();
		if (licenseInfos1 == null) {
			return licenseInfos2 == null;
		}
		if (licenseInfos2 == null) {
			return false;
		}
		if (licenseInfos1.size() != licenseInfos2.size()) {
			return false;
		}
		for (org.spdx.library.model.v2.license.AnyLicenseInfo ali1:licenseInfos1) {
			boolean found = false;
			for (org.spdx.library.model.v2.license.AnyLicenseInfo ali2:licenseInfos2) {
				if (isLicenseEqual(ali1, ali2, xlationMap)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Compares license text to the license text of an SPDX Standard License
	 * @param license SPDX Standard License to compare
	 * @param compareText Text to compare to the standard license
	 * @return any differences found
	 * @throws SpdxCompareException
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static DifferenceDescription isTextStandardLicense(org.spdx.library.model.v2.license.License license, String compareText) throws SpdxCompareException, InvalidSPDXAnalysisException {
		String licenseTemplate = license.getStandardLicenseTemplate();
		if (licenseTemplate == null || licenseTemplate.trim().isEmpty()) {
			licenseTemplate = license.getLicenseText();
		}
		CompareTemplateOutputHandler compareTemplateOutputHandler = null;
		try {
			compareTemplateOutputHandler = new CompareTemplateOutputHandler(LicenseTextHelper.removeLineSeparators(removeCommentChars(compareText)));
		} catch (IOException e1) {
			throw new SpdxCompareException("IO Error reading the compare text: "+e1.getMessage(),e1);
		}
		try {
		    //TODO: The remove comment chars will not be removed for lines beginning with a template << or ending with >>
			SpdxLicenseTemplateHelper.parseTemplate(removeCommentChars(licenseTemplate), compareTemplateOutputHandler);
		} catch (LicenseTemplateRuleException e) {
			throw new SpdxCompareException("Invalid template rule found during compare: "+e.getMessage(),e);
		} catch (LicenseParserException e) {
			throw new SpdxCompareException("Invalid template found during compare: "+e.getMessage(),e);
		}
		return compareTemplateOutputHandler.getDifferences();
	}
	
	/**
	 * Compares exception text to the exception text of an SPDX Standard exception
	 * @param exception SPDX Standard exception to compare
	 * @param compareText Text to compare to the standard exceptions
	 * @return any differences found
	 * @throws SpdxCompareException
	 * @throws InvalidSPDXAnalysisException 
	 */
	public static DifferenceDescription isTextStandardException(org.spdx.library.model.v2.license.LicenseException exception, String compareText) throws SpdxCompareException, InvalidSPDXAnalysisException {
		String exceptionTemplate = exception.getLicenseExceptionTemplate();
		if (exceptionTemplate == null || exceptionTemplate.trim().isEmpty()) {
			exceptionTemplate = exception.getLicenseExceptionText();
		}
		CompareTemplateOutputHandler compareTemplateOutputHandler = null;
		try {
			compareTemplateOutputHandler = new CompareTemplateOutputHandler(LicenseTextHelper.removeLineSeparators(removeCommentChars(compareText)));
		} catch (IOException e1) {
			throw new SpdxCompareException("IO Error reading the compare text: "+e1.getMessage(),e1);
		}
		try {
		    //TODO: The remove comment chars will not be removed for lines beginning with a template << or ending with >>
			SpdxLicenseTemplateHelper.parseTemplate(removeCommentChars(exceptionTemplate), compareTemplateOutputHandler);
		} catch (LicenseTemplateRuleException e) {
			throw new SpdxCompareException("Invalid template rule found during compare: "+e.getMessage(),e);
		} catch (LicenseParserException e) {
			throw new SpdxCompareException("Invalid template found during compare: "+e.getMessage(),e);
		}
		return compareTemplateOutputHandler.getDifferences();
	}
	
	/**
	 * Detect if a text contains the standard license (perhaps along with other text before and/or after)
	 * @param text    The text to search within (should not be null)
	 * @param license The standard SPDX license to search for (should not be null)
	 * @return True if the license is found within the text, false otherwise (or if either argument is null)
	 */
	public static boolean isStandardLicenseWithinText(String text, org.spdx.library.model.v2.license.SpdxListedLicense license) {
		
		try {
			return new TemplateRegexMatcher(license.getStandardLicenseTemplate()).isTemplateMatchWithinText(text);
		} catch (SpdxCompareException e) {
			logger.warn("Error getting optional text for license " + license.getObjectUri(), e);
			return false;
		} catch (InvalidSPDXAnalysisException e) {
			logger.warn("Error getting optional text for license " + license.getObjectUri(), e);
			return false;
		}
	}


	/**
	 * Detect if a text contains the standard license exception (perhaps along with other text before and/or after)
	 * @param text    The text to search within (should not be null)
	 * @param exception The standard SPDX license exception to search for (should not be null)
	 * @return True if the license exception is found within the text, false otherwise (or if either argument is null)
	 */
	public static boolean isStandardLicenseExceptionWithinText(String text, org.spdx.library.model.v2.license.ListedLicenseException exception) {
		boolean result = false;
		if (text == null || text.isEmpty() || exception == null) {
			return false;
		}
		try {
			return new TemplateRegexMatcher(exception.getLicenseExceptionTemplate()).isTemplateMatchWithinText(text);
		} catch (SpdxCompareException e) {
			logger.warn("Error getting optional text for license exception ID " + exception.getObjectUri(), e);
		} catch (InvalidSPDXAnalysisException e) {
			logger.warn("Error getting optional text for license exception ID " + exception.getObjectUri(), e);
		}
		return result;
	}
	
	/**
	 * Detect if a license pass black lists
	 * @param license license
	 * @param blackList license black list
	 * @return if the license pass black lists
	 * @throws InvalidSPDXAnalysisException
	 */
	public static boolean isLicensePassBlackList(
			org.spdx.library.model.v2.license.AnyLicenseInfo license,
			String... blackList
	) throws InvalidSPDXAnalysisException {
		if (license == null) {
			return true;
		}
		if (blackList == null || blackList.length == 0) {
			return true;
		}
		if (license instanceof org.spdx.library.model.v2.license.ConjunctiveLicenseSet) {
			for (org.spdx.library.model.v2.license.AnyLicenseInfo member : ((org.spdx.library.model.v2.license.ConjunctiveLicenseSet) license).getMembers()) {
				if (!isLicensePassBlackList(member, blackList)) {
					return false;
				}
			}
			return true;
		} else if (license instanceof org.spdx.library.model.v2.license. DisjunctiveLicenseSet) {
			for (org.spdx.library.model.v2.license.AnyLicenseInfo member : ((org.spdx.library.model.v2.license.DisjunctiveLicenseSet) license).getMembers()) {
				if (isLicensePassBlackList(member, blackList)) {
					return true;
				}
			}
			return false;
		} else {
			return !contains(blackList, license.toString());
		}
	}

	/**
	 * Detect if a license pass white lists
	 * @param license license
	 * @param whiteList license white list
	 * @return if the license pass white lists
	 * @throws InvalidSPDXAnalysisException
	 */
	public static boolean isLicensePassWhiteList(
			org.spdx.library.model.v2.license.AnyLicenseInfo license,
			String... whiteList
	) throws InvalidSPDXAnalysisException {
		if (license == null) {
			return false;
		}
		if (whiteList == null || whiteList.length == 0) {
			return false;
		}
		if (license instanceof org.spdx.library.model.v2.license.ConjunctiveLicenseSet) {
			for (org.spdx.library.model.v2.license.AnyLicenseInfo member : ((org.spdx.library.model.v2.license.ConjunctiveLicenseSet) license).getMembers()) {
				if (!isLicensePassWhiteList(member, whiteList)) {
					return false;
				}
			}
			return true;
		} else if (license instanceof org.spdx.library.model.v2.license.DisjunctiveLicenseSet) {
			for (org.spdx.library.model.v2.license.AnyLicenseInfo member : ((org.spdx.library.model.v2.license.DisjunctiveLicenseSet) license).getMembers()) {
				if (isLicensePassWhiteList(member, whiteList)) {
					return true;
				}
			}
			return false;
		} else {
			return contains(whiteList, license.toString());
		}
	}

}