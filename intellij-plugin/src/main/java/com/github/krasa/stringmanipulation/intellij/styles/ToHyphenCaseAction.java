package com.github.krasa.stringmanipulation.intellij.styles;

import com.github.krasa.stringmanipulation.commons.style.Style;

public class ToHyphenCaseAction extends AbstractCaseConvertingAction {

	public ToHyphenCaseAction() {
	}

	public ToHyphenCaseAction(boolean b) {
		super(b);
	}

	@Override
	public String transformByLine(String s) {
		Style from = Style.from(s);
		if (from == Style.KEBAB_LOWERCASE) {
			return Style.SNAKE_CASE.transform(from, s);
		}
		return Style.KEBAB_LOWERCASE.transform(from, s);
	}

}
