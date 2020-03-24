package brk.simulator.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JAXBUtils {
    private JAXBContext jaxbContext;
    private Schema schema;

    /**
     * use this constructor use marshaller or unmarshaller, without validation messages!
     * @param clazz class that you want to use marshaller or unmarshaller
     */
    public JAXBUtils(Class... clazz){
        initJAXBContext(clazz);
    }

    /**
     * use this constructor for check validation messages
     * @param schemasFileLocation the schemas directory's path
     * @param clazz class that you want to use marshaller or unmarshaller
     */
    public JAXBUtils(String schemasFileLocation, Class... clazz){
        this(clazz);
        initSchemaValidation(schemasFileLocation);
    }

    private void initJAXBContext(Class... clazz){
        try {
            this.jaxbContext = JAXBContext.newInstance(clazz);
        } catch (JAXBException e) {
            System.out.print(e.toString());
        }
    }

    private void initSchemaValidation(String schemasFileLocation){
        try {
            this.schema = createSchema(schemasFileLocation);
        } catch (SAXException e) {
            System.out.println(e.toString());
        }
    }

    public Object xmlToObject(String xmlMessage) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        Object object = unmarshaller.unmarshal(new StringReader(xmlMessage));

        return object;
    }

    public String objectToXML(Object object) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, stringWriter);

        return stringWriter.toString();
    }

    private Schema createSchema(String schemasFileLocation) throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source[] sources = createSource(schemasFileLocation);

        return schemaFactory.newSchema(sources);

    }

    private Source[] createSource(String schemasFileLocation) {
        File file = new File(schemasFileLocation);
        File[] listFiles = new File[]{};
        List<Source> sources = new ArrayList<>();

        if (file.isDirectory()){
            listFiles = file.listFiles();

            for (File schemaFile : listFiles) {
                sources.add(new StreamSource(schemaFile));
            }
        } else {
            sources.add(new StreamSource(schemasFileLocation));
        }

        return (Source[]) sources.toArray();
    }
}
