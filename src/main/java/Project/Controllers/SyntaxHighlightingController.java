package Project.Controllers;

import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpansBuilder;

public class SyntaxHighlightingController {

    @FXML
    private CodeArea codeArea;

	private final String exampleString = "\nint main() {\n	float n = 1;\n	double m = 2;\n	cout << n * m;\n	return 0;\n}";

	SyntaxHighlightingController(CodeArea _codeArea, String text) {
		codeArea = _codeArea;
		initialize(text);
	}

    private static final Set<String> KEYWORDS = new HashSet<String> (Arrays.asList(
		"break",
		"case",
		"catch",
		"class",
		"continue",
		"delete",
		"do",
		"else",
		"enum",
		"explicit",
		"for",
		"friend",
		"goto",
		"if",
		"namespace",
		"new",
		"operator",
		"public",
		"private",
		"return",
		"struct",
		"switch",
		"template",
		"throw",
		"try",
		"union",
		"while"
	));

    private static final Set<String> TYPES = new HashSet<String> (Arrays.asList(
		"auto",
		"bool",
		"char",
		"double",
		"float",
		"int",
		"long",
		"short",
		"signed",
		"unsigned",
		"void",
		"const",
		"static",
		"inline",
		"typedef",
		"typename",
		"using",
		"volatile"
	));

	@FXML
	private void initialize(String text) {
		codeArea.getStylesheets().add(SyntaxHighlightingController.class.getResource("cpp-keywords.css").toExternalForm());
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.replaceText(text);
		refreshSyntaxHighlighting();
		Pattern indentPattern = Pattern.compile( "^\\s+" );
		codeArea.addEventFilter(KeyEvent.KEY_PRESSED, KE -> {
    		if (KE.getCode() == KeyCode.ENTER) {
				Matcher m = indentPattern.matcher(codeArea.getParagraph(codeArea.getCurrentParagraph()).getSegments().get(0));
        		if (m.find())
					Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), m.group()));
   			} else if (KE.getText().equals("[")) {
				System.out.println(KE.getText());
				codeArea.insertText(codeArea.getCaretPosition(), "]");
				codeArea.moveTo(codeArea.getCaretPosition() - 1);
			} else if (KE.getText().equals("(")) {
				codeArea.insertText(codeArea.getCaretPosition(), ")");
				codeArea.moveTo(codeArea.getCaretPosition() - 1);
			} else if (KE.getText().equals("{")) {
				codeArea.insertText(codeArea.getCaretPosition(), "}");
				codeArea.moveTo(codeArea.getCaretPosition() - 1);
			} else if (KE.getText().equals("'")) {
				codeArea.insertText(codeArea.getCaretPosition(), "'");
				codeArea.moveTo(codeArea.getCaretPosition() - 1);
			} else if (KE.getText().equals("\"")) {
				codeArea.insertText(codeArea.getCaretPosition(), "\"");
				codeArea.moveTo(codeArea.getCaretPosition() - 1);
			} else if (
				KE.getCode() == KeyCode.UP ||
				KE.getCode() == KeyCode.DOWN ||
				KE.getCode() == KeyCode.LEFT ||
				KE.getCode() == KeyCode.RIGHT
			)
				refreshSyntaxHighlighting();
		});
	}

	private String getTypedText() {
		int caretPosition = codeArea.getCaretPosition();
		Integer removedBeforeCaretPosition = 0;
		StringBuilder textBuilder = new StringBuilder();
		String codeAreaText = codeArea.getText();
		for (int i = 0; i < codeAreaText.length(); ++ i)
			if (!codeArea.getStyleOfChar(i).contains("suggestion"))
				textBuilder.append(codeAreaText.charAt(i));
			else if (i <= caretPosition)
				++	removedBeforeCaretPosition;
		caretPosition -= removedBeforeCaretPosition;
		codeArea.moveTo(caretPosition);
		return textBuilder.toString();
	}
    @FXML
	public void refreshSyntaxHighlighting() {
		String text = getTypedText();
		String newText = text;
		System.out.println(text);
		StyleSpansBuilder<Collection<String> > spansBuilder = new StyleSpansBuilder<>();
		int ptr = 0;
		boolean inGlobalComment = false;
		boolean inString = false;
		int lastEndl = 0;
		while (ptr < text.length()) {
			int	lineStart = ptr;
			int lineEnd = ptr;
			while (lineEnd < text.length() && text.charAt(lineEnd) != '\n')
				++ lineEnd;
			if (lineEnd != text.length()) {
				lastEndl = lineEnd;
				++ lineEnd;
			}
			StringBuilder lastString = new StringBuilder();
			boolean inLocalComment = false;
			int lastIntervalEnd = lineStart;
			for (int i = lineStart; i < lineEnd; ++ i)
				if (inGlobalComment) {
					if (i + 1 < lineEnd && text.charAt(i) == '*' && text.charAt(i + 1) == '/') {
						inGlobalComment = false;
						spansBuilder.add(Collections.singleton("comment"), i + 2 - lastIntervalEnd);
						lastIntervalEnd = i + 2;
						++ i;
					}
				} else if (inString) {
					if (text.charAt(i) == '"') {
						inString = false;
						spansBuilder.add(Collections.singleton("string"), i + 1 - lastIntervalEnd);
						lastIntervalEnd = i + 1;
					}
				} else if (!inLocalComment) {
					if (!(Character.isAlphabetic(text.charAt(i)) || Character.isDigit(text.charAt(i)))) {
						if (lastString.length() > 0) {
							if (KEYWORDS.contains(lastString.toString()))
								spansBuilder.add(Collections.singleton("keyword"), lastString.length());
							else if (TYPES.contains(lastString.toString()))
								spansBuilder.add(Collections.singleton("type"), lastString.length());
							else
								spansBuilder.add(Collections.singleton("normal"), lastString.length());
							lastString.delete(0, lastString.length());	
							lastIntervalEnd = i;
						}
					}
					if (text.charAt(i) == '"' || (i + 1 < lineEnd && text.charAt(i) == '/' && (text.charAt(i + 1) == '/' || text.charAt(i + 1) == '*'))) {
						if (lastIntervalEnd != i) {
							spansBuilder.add(Collections.singleton("normal"), i - lastIntervalEnd);
							lastIntervalEnd = i;
						}
						if (text.charAt(i) == '"') {
							inString = true;
						} else if (text.charAt(i + 1) == '*') {
							inGlobalComment = true;
						} else {
							inLocalComment = true;
							break;
						}
					} else {
						if (!(Character.isAlphabetic(text.charAt(i)) || Character.isDigit(text.charAt(i)))) {
							spansBuilder.add(Collections.singleton("normal"), 1);
							lastIntervalEnd = i + 1;
						} else
							lastString.append(text.charAt(i));
					}
				}
			if (lastIntervalEnd != lineEnd) {
				if (inGlobalComment || inLocalComment)
					spansBuilder.add(Collections.singleton("comment"), lineEnd - lastIntervalEnd);
				else if (inString)
					spansBuilder.add(Collections.singleton("string"), lineEnd - lastIntervalEnd);
				else
					spansBuilder.add(Collections.singleton("normal"), lineEnd - lastIntervalEnd);
			}
			ptr = lineEnd;
		}
		int caretPosition = codeArea.getCaretPosition();
		if (caretPosition > lastEndl) {
			newText += '\n';
			spansBuilder.add(Collections.singleton("normal"), 1);
		}
		codeArea.replaceText(newText);
		codeArea.moveTo(caretPosition);
        if (newText.length() != 0)
			codeArea.setStyleSpans(0, spansBuilder.create());
	}
}
