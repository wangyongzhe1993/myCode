package dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by wyz on 2018/1/8.
 */
public class TestDom {
    public static void main(String[] args) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("conf/test.xml"));
        Element rootElement = document.getRootElement();

        Iterator<Attribute> attributeIterator = rootElement.attributeIterator();
        while (attributeIterator.hasNext()) {
            Attribute attribute = attributeIterator.next();
            System.out.println("name:" + attribute.getName() + " value:" + attribute.getValue() + " text:" + attribute.getText());
        }

        Iterator<Element> elementIterator = rootElement.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            System.out.println(element.getName());
        }

        elementIterator = rootElement.elementIterator("type");
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            System.out.println(element.getName());
        }

    }
}
