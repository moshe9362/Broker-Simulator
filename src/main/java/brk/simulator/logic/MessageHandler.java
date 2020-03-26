package brk.simulator.logic;

import brk.simulator.interfaces.sender.ShkResponseSender;
import generated.LiveRequest;
import generated.LiveResponse;
import generated.ObjectFactory;
import generated.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class MessageHandler {
    private ObjectFactory objectFactory;
    private ShkResponseSender shkResponseSender;

    @Autowired
    public MessageHandler(ObjectFactory objectFactory,
                          ShkResponseSender shkResponseSender) {
        this.objectFactory = objectFactory;
        this.shkResponseSender = shkResponseSender;
    }

    public void handle(TextMessage message) {
        if (message instanceof LiveRequest) {
            handleLiveRequest();
        }
    }

    private void handleLiveRequest() {
        LiveResponse liveResponse = objectFactory.createLiveResponse();
        liveResponse.setStatus(RequestStatus.OK);
        shkResponseSender.send(liveResponse);
    }
}
