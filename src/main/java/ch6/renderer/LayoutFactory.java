package ch6.renderer;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Artyom Zheltyshev on 17.05.2026
 */
public class LayoutFactory {

    public static final int WIDTH = 800;

    public ElementLayout createDocumentLayout(DocumentSource documentSource, List<ImgLayout> imgLayouts) {
        Document document = documentSource.loadDocument();
        return createElementLayout(document.body(), imgLayouts);
    }

    private ElementLayout createElementLayout(Element element, List<ImgLayout> imgLayouts) {
        return switch (element.tagName()) {
            case "body" -> createBodyLayout(element, imgLayouts);
            case "p" -> createParagraphLayout(element);
            case "img" -> {
                ImgLayout imgLayout = createImgLayout(element);
                imgLayouts.add(imgLayout);
                yield imgLayout;
            }
            case "h1" -> createH1Layout(element);
            default -> new EmptyLayout();
        };
    }

    private BodyLayout createBodyLayout(Element element, List<ImgLayout> imgLayouts) {
        List<ElementLayout> layouts = element.children()
                .stream()
                .map(e -> createElementLayout(e, imgLayouts))
                .collect(Collectors.toList());
        return new BodyLayout(layouts, WIDTH);
    }

    private ParagraphLayout createParagraphLayout(Element element) {
        return new ParagraphLayout(element.text(), WIDTH);
    }

    private ImgLayout createImgLayout(Element element) {
        Attribute src = Objects.requireNonNull(element.attribute("src"));
        Attribute width = Objects.requireNonNull(element.attribute("width"));
        Attribute height = Objects.requireNonNull(element.attribute("height"));
        return new ImgLayout(
                src.getValue(),
                Integer.parseInt(width.getValue()),
                Integer.parseInt(height.getValue())
        );
    }

    private H1Layout createH1Layout(Element element) {
        return new H1Layout(element.text(), WIDTH);
    }

}
