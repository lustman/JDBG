package org.jdbg.gui.tabs.classanalysis.codepanel.token;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;

import javax.swing.text.Segment;

public class JavaBytecodeTokenMaker extends AbstractTokenMaker {

    @Override
    public Token getTokenList(Segment text, int initialTokenType, int startOffset) {
        return null;
    }

    @Override
    public TokenMap getWordsToHighlight() {
        TokenMap tokenMap = new TokenMap();

        return tokenMap;
    }
}
