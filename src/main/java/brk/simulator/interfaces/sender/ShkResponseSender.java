package brk.simulator.interfaces.sender;

import iaf.ron.common.tools.mq.MqQueueManager;
import iaf.ron.common.tools.mq.MqQueueWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class ShkResponseSender implements GroundInterfaceSender{
    private MqQueueWriter MqQueueWriter;

    @Autowired
    public ShkResponseSender(MqQueueManager mqQueueManager,
                             @Value("${mq.shk.response.sender.queue.name}") String queueName){
        initMqWriter(mqQueueManager, queueName);
    }

    private void initMqWriter(MqQueueManager mqQueueManager, String queueName) {
        try {
            MqQueueWriter = mqQueueManager.createWriter(queueName);
        } catch (JMSException e) {
            // TODO: add log
            System.out.print(e.toString());
        }
    }

    @Override
    public void send(Object message) {
        MqQueueWriter.putMessage(message);
    }
}
