package iaf.ofek.ron.interfaces.receiver;

import iaf.ron.common.tools.mq.MqQueueManager;
import iaf.ron.common.tools.mq.MqQueueReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
public class MitugRequestReceiver implements MessageListener {
    private MqQueueReader mqQueueReader;

    @Autowired
    public MitugRequestReceiver(MqQueueManager mqQueueManager,
                                @Value("${mq.mitug.request.receive.queue.name}") String queueName){
        initMQReader(mqQueueManager, queueName);
    }

    private void initMQReader(MqQueueManager mqQueueManager, String queueName){
        try {
            mqQueueReader = mqQueueManager.createReader(queueName);
            mqQueueReader.setListener(this);
        } catch (JMSException e) {
            // TODO: add log
            System.out.print(e.toString());
        }
    }

    private void massageHandle(Message message) {

    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            massageHandle(message);
        }
    }
}
