package cmpe202.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser implements Parser{

    private final FileWriter writer;
    private final DocumentBuilderFactory dbFactory;
    private final DocumentBuilder dBuilder;
    private final Document doc;
    private int currentIdx;

    public XMLParser(String input, String output){
        this.dbFactory = DocumentBuilderFactory.newInstance();
        try {
            this.dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(new InputSource(new FileReader(input)));
            this.writer = new FileWriter(output, false);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        this.currentIdx = 0;
    }

    @Override
    public Map<String, String> readRecord() {
        NodeList recordList = doc.getElementsByTagName("CARD");
        if (currentIdx < recordList.getLength()) {
            Node recordNode = recordList.item(currentIdx);
            if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                Element recordElement = (Element) recordNode;
                Map<String, String> recordMap = new HashMap<>();
                recordMap.put("cardHolderName", recordElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent());
                recordMap.put("cardNumber", recordElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent());
                recordMap.put("expirationDate", recordElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent());
                currentIdx++;
                return recordMap;
            }
        }
        return null;
    }

    @Override
    public void write(List<Map<String, String>> records){
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("CARDS");
            doc.appendChild(rootElement);

            for (Map<String, String> record : records) {
                Element recordElement = doc.createElement("CARD");
                rootElement.appendChild(recordElement);

                Element cardNumber = doc.createElement("CARD_NUMBER");
                cardNumber.appendChild(doc.createTextNode(record.get("cardNumber")));
                recordElement.appendChild(cardNumber);

                Element cardType = doc.createElement("CARD_TYPE");
                cardType.appendChild(doc.createTextNode(record.get("cardType")));
                recordElement.appendChild(cardType);
            }
            writeToFile(doc);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(Document doc) throws TransformerException {
        // Writing to the XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
    }


}
