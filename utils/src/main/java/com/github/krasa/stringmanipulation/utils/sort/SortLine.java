package com.github.krasa.stringmanipulation.utils.sort;

import com.github.krasa.stringmanipulation.utils.common.IntellijStringUtil;
import com.github.krasa.stringmanipulation.utils.common.StringUtil;

import static org.apache.commons.lang3.StringUtils.isEmpty;


public class SortLine {

	private final String text;
	private final SortSettings sortSettings;
	private final String textForComparison;

	public SortLine(String text, SortSettings sortSettings) {
		this.text = text;
		this.sortSettings = sortSettings;

		this.textForComparison = makeTextForComparison(text, sortSettings);
	}

	public SortLine(String selection) {
		text = selection;
		sortSettings = SortSettings.allFeaturesDisabled(null);

		this.textForComparison = makeTextForComparison(text, sortSettings);
	}

	protected String makeTextForComparison(String text, SortSettings sortSettings) {
		String textForComparison = text;
		if (sortSettings.isIgnoreLeadingSpaces()) {
			textForComparison = text.substring(StringUtil.indexOfAnyButWhitespace(text), text.length());
		}
		if (sortSettings.isPreserveTrailingSpecialCharacters()) {
			int textWithoutTrailingCharsEndIndex = lastIndexOfAnyBut(textForComparison, sortSettings.getTrailingChars());
			textForComparison = textForComparison.substring(0, textWithoutTrailingCharsEndIndex);
		}
		return textForComparison;
	}

	public String getTextForComparison() {
		return textForComparison;
	}

	public String transformTo(SortLine line) {
		String result = line.text;
		String fromText = text;
		if (sortSettings.isPreserveLeadingSpaces()) {
			int oldContentStartIndex = StringUtil.indexOfAnyButWhitespace(fromText);
			int newContentStartIndex = StringUtil.indexOfAnyButWhitespace(result);

			String oldContentLeadingSpaces = fromText.substring(0, oldContentStartIndex);
			String newActualContent = result.substring(newContentStartIndex, result.length());

			result = oldContentLeadingSpaces + newActualContent;
		}

		if (sortSettings.isPreserveTrailingSpecialCharacters()) {
			int newContentEndIndex = lastIndexOfAnyBut(result, sortSettings.getTrailingChars());
			int oldContentEndIndex = lastIndexOfAnyBut(fromText, sortSettings.getTrailingChars());

			String newContentWithoutTrailingCharacters = result.substring(0, newContentEndIndex);
			String oldTrailingCharacters = fromText.substring(oldContentEndIndex);

			result = newContentWithoutTrailingCharacters + oldTrailingCharacters;
		}


		return result;
	}

	protected int lastIndexOfAnyBut(String str, String searchChars) {
		if (!isEmpty(str) && !isEmpty(searchChars)) {
			int lastSearchCharFoundIndex = str.length();
			for (int i = str.length() - 1; i >= 0; i--) {
				boolean isSearchChar = searchChars.indexOf(str.charAt(i)) >= 0;

				if (isSearchChar) {
					lastSearchCharFoundIndex = i;
				} else if (IntellijStringUtil.isWhiteSpace(str.charAt(i))) {
					continue;
				} else {
					return lastSearchCharFoundIndex;
				}
			}

			return str.length();
		} else {
			return str.length();
		}
	}


}
							