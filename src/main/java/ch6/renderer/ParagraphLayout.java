package ch6.renderer;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Map;

/**
 * Created by Artyom Zheltyshev on 06.05.2026
 */
public class ParagraphLayout implements ElementLayout {
    public static final float FONT_SIZE = 14.0f;
    private final String text;
    private final int width;

    public ParagraphLayout(String text, int width) {
        this.text = text;
        this.width = width;
    }

    @Override
    public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
        Map<TextAttribute, Object> attributeMap = Map.of(
                TextAttribute.FAMILY, Font.SERIF,
                TextAttribute.SIZE, FONT_SIZE
        );
        AttributedString attributedString = new AttributedString(text, attributeMap);
        AttributedCharacterIterator iterator = attributedString.getIterator();
        LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(iterator, g.getFontRenderContext());
        float drawPosY = y;
        while (lineBreakMeasurer.getPosition() < iterator.getEndIndex()) {
            TextLayout textLayout = lineBreakMeasurer.nextLayout(width);
            drawPosY += textLayout.getAscent();
            textLayout.draw(g, x, drawPosY);
            drawPosY += textLayout.getDescent() + textLayout.getLeading();
        }
        return new Rectangle(x, y, width, (int) (drawPosY - y));
    }
}
