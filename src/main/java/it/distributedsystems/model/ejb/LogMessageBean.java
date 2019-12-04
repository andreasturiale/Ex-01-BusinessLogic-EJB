package it.distributedsystems.model.ejb;

import java.util.logging.Logger;

import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * LogMessageBean
 */
@MessageDriven(name = "LogMessageHandler", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/LogQueue") })
public class LogMessageBean implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(LogMessageBean.class.getName());

    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        try {
            LOGGER.info("E' stato invocato il metodo " + textMsg.getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}