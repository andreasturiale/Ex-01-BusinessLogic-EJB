package it.distributedsystems.model.ejb;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * JmsLogProducer
 */
@Stateless
@Local(JmsLogProducerLocal.class)
public class JmsLogProducer implements JmsLogProducerLocal {
    @Resource(mappedName="java:/ConnectionFactory")    // inject ConnectionFactory (more)
    private ConnectionFactory factory;
  
    @Resource(mappedName="java:/jms/queue/LogQueue")  // inject Queue (more)
    private Queue target;
   
    @AroundInvoke
    public Object mdbInterceptor(InvocationContext ctx) throws Exception {
        Connection con = factory.createConnection();
        try {
          Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
          MessageProducer producer= session.createProducer(target);
          producer.send(session.createTextMessage(ctx.getMethod().getName()+" con i seguenti parametri: "+ctx.getMethod().getParameters().toString()));
        }
        finally {
          con.close();
        }
        return ctx.proceed();
    }

}