package com.aks.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author anuj.siddhu
 *
 */
public abstract class StringUtility {
	public static final Logger logger = LoggerFactory.getLogger(StringUtility.class);
	public static final String DOT = ".";
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String EQUAL = "=";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String PERCENTAGE = "%";
	public static final String FRONT_SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String PIPE_SEPARATOR = "\\|";
	public static final String CAMEL_CASE_REGEX = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])";
	public static final String URL_REGEX = "(?:http:\\/\\/|https:\\/\\/)?(?:www\\.|m\\.)?(?:[a-zA-Z0-9]*\\.)?[a-zA-Z0-9\\-]*\\.[a-z]{2,3}(\\.[a-z]{2})?";
	private static final Pattern urlPattern = Pattern.compile(URL_REGEX);

	public static boolean isUrl(String name) {
		return urlPattern.matcher(name).matches();
	}

	public static boolean isAllUpper(String input) {
		boolean result = true;
		for (int i = 0; i < input.length(); i++) {
			result = result && Character.isUpperCase(input.charAt(i));
		}
		return result;
	}

	public static String sanitize(String value) {
		// Convert to lower case.
		value = value.toLowerCase();
		// Vowels are now not getting removed
		value = value.trim().replaceAll("\\s+", " ");
		value = getHost(value);
		return value;
	}

	/**
	 * http://stackoverflow.com/questions/4826061/what-is-the-fastest-way-to-get
	 * -the-domain-host-name-from-a-url. Will take a url such as
	 * http://www.stackoverflow.com and return www.stackoverflow.com
	 * 
	 * @param url
	 * @return
	 */
	public static String getHost(String url) {
		if (url == null || url.length() == 0) {
			return url;
		}
		int doubleslash = url.indexOf("//");
		if (doubleslash == -1) {
			doubleslash = 0;
		} else {
			doubleslash += 2;
		}
		int end = url.indexOf('/', doubleslash);
		end = end >= 0 ? end : url.length();

		int port = url.indexOf(':', doubleslash);
		end = (port > 0 && port < end) ? port : end;
		return url.substring(doubleslash, end).replace("www.", "").replace("w.", "").replace("m.", "").replace("www ",
				"");
	}

	/**
	 * http://stackoverflow.com/questions/959731/how-to-replace-a-set-of-tokens-
	 * in-a-java-string
	 * 
	 * @param text
	 * @param replacements
	 * @return
	 */
	public static Optional<String> substituteValues(String text, Map<String, Object> replacements) {
		Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			Object replacement = replacements.get(matcher.group(1));
			if (replacement != null) {
				matcher.appendReplacement(buffer, "");
				buffer.append(replacement);
			} else {
				return Optional.empty();
			}
		}
		matcher.appendTail(buffer);
		return Optional.of(buffer.toString());
	}

	public static String substituteValues(String text, String placeholder, Object replacement) {
		Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();
		if (matcher.find()) {
			if (placeholder.equals(matcher.group(1)) && replacement != null) {
				matcher.appendReplacement(buffer, "");
				buffer.append(replacement);
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	public static String getJsonForUI(String jsonString) {
		return jsonString.replace("'", "\\\'").replace("\"", "\\\"");
	}

	public static String escapeHTML(String s) {
		StringBuilder sb = new StringBuilder();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\n':
				sb.append("&lt;br/&gt;");
			case '\'':
				sb.append("\\'");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static String escapeSQL(String s) {
		StringBuilder sb = new StringBuilder();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\'':
				sb.append("\'\'");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static String deescapeHTML(String s) {
		return s.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&amp;", "&");
	}

	public static String stripNonDecimal(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (char ch : str.toCharArray()) {
			if (Character.isDigit(ch)) {
				sb.append(ch);
			} else if (ch == '.' && first) {
				first = false;
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String getStringForThrowable(Throwable t) {
		StringBuilder body = new StringBuilder();
		body.append("\n\nException Stack trace:\n\n");

		body.append(t.getClass()).append(" - ");
		body.append(t.getCause()).append(" - ");
		body.append(t.getMessage()).append("\n");

		if (t != null) {
			StackTraceElement[] stElem = t.getStackTrace();
			for (int i = 0; i < stElem.length; i++) {
				body.append("\t").append(stElem[i].toString()).append("\n");
			}
		}
		return body.toString();
	}

	public static boolean isAlphabetical(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char ch = str.charAt(i);
			if (!Character.isUpperCase(ch) && !Character.isLowerCase(ch)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean containsNumber(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				return true;
			}
		}
		return false;
	}

	public static boolean containsOnlyDigits(String str) {
		if (isBlank(str)) {
			throw new IllegalArgumentException();
		}
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for a null, white space or empty string
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isBlacklistedCharPresent(String text) {
		if (text == null)
			return false;
		Pattern p = Pattern.compile("[\\<\\>\\=\\;\"]");
		Matcher m = p.matcher(text);
		return m.find();
	}

	public static boolean contains(String text, String pattern) {
		if (text == null || pattern == null)
			return false;
		return text.toLowerCase().contains(pattern.toLowerCase());
	}

	public static String cleanName(String name) {
		name = name.replaceAll("[\"`'=]", "").replaceAll("\\s+", " ").trim().toUpperCase();
		String nfdNormalizedString = Normalizer.normalize(name, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	public static String stringToHex(String arg) {
		return String.format("%040x", new BigInteger(1, arg.getBytes()));
	}

	public static String readAllContentFromFile(String fileName) {
		try {
			StringBuffer contents = new StringBuffer();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			try {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				bufferedReader.close();
			}
			return contents.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static int getCommonPrefixLength(String str1, String str2) {
		if (str1 == null || str2 == null)
			return 0;

		int i = 0;
		while (str1.charAt(i) == str2.charAt(i)) {
			i++;
			if (i >= str1.length() || i >= str2.length())
				break;
		}
		return i;
	}

	public static String getMaxPrefixMachingString(List<String> strBasket, String pattern) {
		if (strBasket == null || pattern == null)
			return null;

		String maxMatchedStr = null;
		int maxMatch = -1;
		for (String entry : strBasket) {
			int matchedLen = getCommonPrefixLength(entry, pattern);
			if (matchedLen > maxMatch) {
				maxMatchedStr = entry;
				maxMatch = matchedLen;
			}
		}
		return maxMatchedStr;
	}
	
	public static boolean areEqual(String str1, String str2) {
		if (str1 == str2) {
			return true;
		} else if (str1 != null && str2 != null) {
			return str1.equalsIgnoreCase(str2);
		} else {
			return false;
		}
	}
}
