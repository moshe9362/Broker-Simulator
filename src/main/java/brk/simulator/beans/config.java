package brk.simulator.beans;

import brk.simulator.utils.JAXBUtils;
import iaf.ron.common.tools.mq.MqQueueManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;

@Configuration
public class config {

    @Bean
    public MqQueueManager mqQueueManager(@Value("${mq.queueManager}") String queueManager,
                                         @Value("${mq.server.and.port}") String server,
                                         @Value("${mq.channel}") String channel){
        MqQueueManager mqQueueManager = new MqQueueManager(queueManager, server, channel, logger());
//        try {
//            mqQueueManager.connect();
//        } catch (JMSException e) {
//            System.out.println(e.toString());
//        }

        return mqQueueManager;
    }

    private Logger logger(){
        return null;
    }

    @Bean
    public JAXBUtils jaxbUtils(@Value("${schemas.file.name}") String schemaFileName){
        return new JAXBUtils(schemaFileName, ObjectFactory.class);
    }
}
