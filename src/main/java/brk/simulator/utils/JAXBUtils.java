package brk.simulator.utils;

import brk.simulator.Main;
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
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

public class JAXBUtils {
    private JAXBContext jaxbContext;
    private Schema schema;

    /**
     * use this constructor to marshaller or unmarshaller, with validation messages.
     * @param schemaFileName the schemas directory's name
     * @param clazz class that you want to use marshaller or unmarshaller
     */
    public JAXBUtils(String schemaFileName, Class... clazz){
        initJAXBContext(clazz);
        initSchemaValidation(schemaFileName);
    }

    private void initJAXBContext(Class... clazz){
        try {
            this.jaxbContext = JAXBContext.newInstance(clazz);
        } catch (JAXBException e) {
            System.out.print(e.toString());
        }
    }

    private void initSchemaValidation(String schemaFileName){
        try {
            this.schema = createSchema(schemaFileName);
        } catch (SAXException | FileNotFoundException e) {
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

    private Schema createSchema(String schemaFileName) throws SAXException, FileNotFoundException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source[] sources = createSource(schemaFileName);

        return schemaFactory.newSchema(sources);

    }

    private Source[] createSource(String schemaFileName) throws FileNotFoundException {
        String path = getFullPath(schemaFileName);
        File filePath = new File(path);
        List<Source> sources = new ArrayList<>();

        if (filePath.isDirectory()){
            File[] listFiles = filePath.listFiles();

            for (File schemaFile : listFiles) {
                sources.add(new StreamSource(schemaFile));
            }
        }

        return sources.toArray(new Source[0]);
    }

    private String getFullPath(String schemaFileName) throws FileNotFoundException {
        URL url = getClass().getClassLoader().getResource(schemaFileName);

        if (url != null){
            return url.getPath();
        }

        throw new FileNotFoundException("error in putting schema to JAXBUtils. file name '" + schemaFileName + "' doesn't exist");
    }
}
