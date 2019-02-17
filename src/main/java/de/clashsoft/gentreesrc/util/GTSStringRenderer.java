package de.clashsoft.gentreesrc.util;

import org.stringtemplate.v4.StringRenderer;

import java.util.Locale;

public class GTSStringRenderer extends StringRenderer
{
	@Override
	public String toString(Object o, String formatString, Locale locale)
	{
		if (formatString != null && formatString.length() >= 4 && formatString.charAt(0) == 's')
		{
			final int delimiter = formatString.codePointAt(1);
			final int charCount = Character.charCount(delimiter);
			final int indexAfterDelimiter = 1 + charCount;
			final int indexOfSecond = formatString.indexOf(delimiter, indexAfterDelimiter);

			if (indexOfSecond >= 0)
			{
				final int indexOfThird = formatString.indexOf(delimiter, indexOfSecond + charCount);
				final int endOfReplacement = indexOfThird >= 0 ? indexOfThird : formatString.length();

				final String regex = formatString.substring(indexAfterDelimiter, indexOfSecond);
				final String replacement = formatString.substring(indexOfSecond + charCount, endOfReplacement);
				// final String options = formatString.substring(indexOfThird + charCount); // unused
				return o.toString().replaceAll(regex, replacement);
			}
		}

		return super.toString(o, formatString, locale);
	}
}
