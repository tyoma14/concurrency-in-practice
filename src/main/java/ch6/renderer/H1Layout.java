package ch6.renderer;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Map;

/**
 * Created by Artyom Zheltyshev on 05.05.2026
 */
public class H1Layout implements ElementLayout {

    public static final int FONT_SIZE = 20;
    private final String text;
    private final int width;

    public H1Layout(String text, int width) {
        this.text = text;
        this.width = width;
    }

    @Override
    public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
        Map<TextAttribute, Object> attributeMap = Map.of(
                TextAttribute.FONT, new Font(Font.SERIF, Font.BOLD, FONT_SIZE)
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
