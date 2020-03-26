package brk.simulator.interfaces.receiver;

import brk.simulator.interfaces.sender.ShkResponseSender;
import brk.simulator.logic.MessageHandler;
import generated.LiveRequest;
import generated.LiveResponse;
import generated.ObjectFactory;
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
    private MessageHandler messageHandler;



    @Autowired
    public MitugRequestReceiver(MessageHandler messageHandler,
                                MqQueueManager mqQueueManager,
                                @Value("${mq.mitug.request.receive.queue.name}") String queueName){
        initMQReader(mqQueueManager, queueName);
        this.messageHandler = messageHandler;
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

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            messageHandler.handle((TextMessage) message);
        }
    }
}
