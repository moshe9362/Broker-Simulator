package iaf.ofek.ron.utils;

import iaf.ofek.ron.Main;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBUtils {
    private JAXBContext jaxbContext;

    public JAXBUtils(Class... clazz){
        initJAXBContext(clazz);
    }

    private void initJAXBContext(Class... clazz){
        try {
            this.jaxbContext = JAXBContext.newInstance(clazz);
        } catch (JAXBException e) {
            System.out.print(e.toString());
        }
    }

    public Object xmlToObject(String xmlMessage) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new StringReader(xmlMessage));

        return object;
    }

    public String objectToXML(Object object) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, stringWriter);

        return stringWriter.toString();
    }
}
