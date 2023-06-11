package Project.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxHighlightingController {

    @FXML
    private CodeArea codeArea;

    SyntaxHighlightingController(CodeArea _codeArea, String text) {
        codeArea = _codeArea;
        initialize(text);
    }

    private static final Map<String, String> TAGS = parseTags();

    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(
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

    private static final Set<String> TYPES = new HashSet<String>(Arrays.asList(
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
            "volatile",
            "vector",
            "string",
            "array",
            "pair",
            "queue",
            "stack",
            "priority_queue",
            "tuple"
    ));

    private static Map<String, String> parseTags() {
        Map<String, String> tagMap = new HashMap<String, String>();
        String absolutePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        String dir = absolutePath + fileSeparator + "res" + fileSeparator + "lib";
        File dirFile = new File(dir);
        File[] directoryFiles = dirFile.listFiles();
        if (directoryFiles != null) {
            for (File file : directoryFiles) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    if (reader != null) {
                        StringBuilder fileCodeBuilder = new StringBuilder();
                        boolean tagsEnded = false;
                        String line = reader.readLine();
                        List<String> tags = new LinkedList<String>();
                        while (line != null) {
                            if (line.equals("END_OF_TAGS")) {
                                tagsEnded = true;
                            } else if (!tagsEnded) {
                                tags.add(line);
                            } else {
                                fileCodeBuilder.append(line);
                                fileCodeBuilder.append(System.lineSeparator());
                            }
                            line = reader.readLine();
                        }
                        reader.close();
                        String fileCode = fileCodeBuilder.toString();
                        for (String tag : tags)
                            tagMap.putIfAbsent(tag, fileCode);
                    }
                } catch (Exception ex) {
                    System.out.println("happiness not found exception");
                }
            }
        } else {
            System.out.println("libs doesn't exist sus sus amongas");
        }
        return tagMap;
    }

    @FXML
    private void initialize(String text) {
        codeArea.requestFollowCaret();
        codeArea.getStylesheets().add(Objects.requireNonNull(SyntaxHighlightingController.class.getResource("cpp-keywords.css")).toExternalForm());
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(text);
        refreshSyntaxHighlighting(false);
        Pattern indentPattern = Pattern.compile("^\\s+");
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, KE -> {
            if (KE.getCode() == KeyCode.TAB) {
                refreshSyntaxHighlighting(true);
            } else if (KE.getCode() == KeyCode.ENTER) {
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
                System.out.println("( pressed resulting in: " + codeArea.getText());
            } else if (KE.getText().equals("{")) {
                codeArea.insertText(codeArea.getCaretPosition(), "}");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            } else if (KE.getText().equals("'")) {
                codeArea.insertText(codeArea.getCaretPosition(), "'");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            } else if (KE.getText().equals("\"")) {
                codeArea.insertText(codeArea.getCaretPosition(), "\"");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }
            refreshSyntaxHighlighting(false);
        });
        codeArea.addEventFilter(KeyEvent.ANY, KE -> {
            refreshSyntaxHighlighting(false);
        });
    }

    private String getTypedText(boolean changeCommitted) {
        int caretPosition = codeArea.getCaretPosition();
        int removedBeforeCaretPosition = 0;
        StringBuilder textBuilder = new StringBuilder();
        String codeAreaText = codeArea.getText();
        for (int i = 0; i < codeAreaText.length(); ++i)
            if (changeCommitted || !codeArea.getStyleOfChar(i).contains("suggestion"))
                textBuilder.append(codeAreaText.charAt(i));
            else if (i <= caretPosition)
                ++removedBeforeCaretPosition;
        caretPosition -= removedBeforeCaretPosition;
        codeArea.moveTo(caretPosition);
        return textBuilder.toString();
    }

    int stringDistance(String a, String b) {
        ArrayList<Integer> c = new ArrayList<Integer>();
        c.add(0);
        for (int i = 1; i <= a.length(); ++i)
            c.add(a.length() + b.length());
        for (int i = 0; i < b.length(); ++i) {
            int old = c.get(0);
            c.set(0, old + 1);
            for (int j = 1; j <= a.length(); ++j) {
                int tmp = c.get(j);
                if (Character.toLowerCase(b.charAt(i)) == Character.toLowerCase(a.charAt(j - 1)))
                    c.set(j, old);
                else {
                    if (tmp < old)
                        c.set(j, tmp + 1);
                    else
                        c.set(j, old + 1);
                    if (c.get(j - 1) < c.get(j))
                        c.set(j, c.get(j - 1) + 1);
                }
                old = tmp;
            }
        }
        return c.get(a.length());
    }

    String getClosestSuggestionMatch(String suggestion) {
        if (suggestion.length() == 0)
            return "Search for an algorithm or data structure" + System.lineSeparator();
        String res = null;
        int distance = 0;
        for (String key : TAGS.keySet()) {
            int strDistance = stringDistance(suggestion, key);
            if (res == null || strDistance < distance) {
                res = TAGS.get(key);
                distance = strDistance;
            }
        }
        return res;
    }

    @FXML
    public void refreshSyntaxHighlighting(boolean changeCommitted) {
        String text = getTypedText(changeCommitted);
        int caretPosition = codeArea.getCaretPosition();
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int ptr = 0;
        boolean inGlobalComment = false;
        boolean inString = false;
        int lastEndl = 0;
        while (ptr < text.length()) {
            int lineStart = ptr;
            int lineEnd = ptr;
            while (lineEnd < text.length() && text.charAt(lineEnd) != '\n')
                ++lineEnd;
            if (lineEnd != text.length()) {
                lastEndl = lineEnd;
                ++lineEnd;
            }
            StringBuilder lastString = new StringBuilder();
            boolean inLocalComment = false;
            int lastIntervalEnd = lineStart;
            for (int i = lineStart; i < lineEnd; ++i)
                if (inGlobalComment) {
                    if (i + 1 < lineEnd && text.charAt(i) == '*' && text.charAt(i + 1) == '/') {
                        inGlobalComment = false;
                        spansBuilder.add(Collections.singleton("comment"), i + 2 - lastIntervalEnd);
                        lastIntervalEnd = i + 2;
                        ++i;
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
                            spansBuilder.add(Collections.singleton("comment"), lineEnd - lastIntervalEnd);
                            lastIntervalEnd = lineEnd;
                            int nextLineChars = 0;
                            int nextLineEnd = lineEnd;
                            while (nextLineEnd < text.length() && text.charAt(nextLineEnd) != '\n') {
                                if (!Character.isWhitespace(text.charAt(nextLineEnd)))
                                    ++nextLineChars;
                                ++nextLineEnd;
                            }
                            if (nextLineChars == 0 && caretPosition >= i + 3 && caretPosition < lineEnd && i + 2 < lineEnd && text.charAt(i + 2) == '/') {
                                StringBuilder suggestion = new StringBuilder();
                                for (int j = i + 3; j < lineEnd; ++j)
                                    if (!Character.isWhitespace(text.charAt(j)))
                                        suggestion.append(text.charAt(j));
                                String suggestedAlgorithm = getClosestSuggestionMatch(suggestion.toString());
                                if (suggestedAlgorithm != null) {
                                    StringBuilder newTextBuilder = new StringBuilder(text);
                                    newTextBuilder.insert(lineEnd, suggestedAlgorithm);
                                    text = newTextBuilder.toString();
                                    spansBuilder.add(Collections.singleton("suggestion"), suggestedAlgorithm.length());
                                    lineEnd += suggestedAlgorithm.length();
                                    lastIntervalEnd = lineEnd;
                                }
                            }
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
                if (inGlobalComment)
                    spansBuilder.add(Collections.singleton("comment"), lineEnd - lastIntervalEnd);
                else if (inString)
                    spansBuilder.add(Collections.singleton("string"), lineEnd - lastIntervalEnd);
                else
                    spansBuilder.add(Collections.singleton("normal"), lineEnd - lastIntervalEnd);
            }
            ptr = lineEnd;
        }
        if (caretPosition > lastEndl) {
            text += '\n';
            spansBuilder.add(Collections.singleton("normal"), 1);
        }
        codeArea.replaceText(text);
        codeArea.moveTo(caretPosition);
        if (text.length() != 0)
            codeArea.setStyleSpans(0, spansBuilder.create());
    }
}
