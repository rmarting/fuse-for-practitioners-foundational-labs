package org.fuse.usecase;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.annotations.ApplyLdifFiles;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.apache.directory.server.ldap.LdapServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jms.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(FrameworkRunner.class)
@CreateLdapServer(transports = { @CreateTransport(protocol = "LDAP", port = 1024) })
@ApplyLdifFiles("org/fuse/usecase/activemq.ldif")
public class LDAPActiveMQTest extends AbstractLdapTestUnit {
	
	// http://ibookmate.blogspot.be/2010/07/jaas-loginmodule-for-activemq.html

	public BrokerService broker;
	public static LdapServer ldapServer;

	@Before
	public void setup() throws Exception {
		System.setProperty("ldapPort",
				String.valueOf(getLdapServer().getPort()));
		broker = BrokerFactory
				.createBroker("xbean:org/fuse/usecase/activemq-broker.xml");
		broker.start();
		broker.waitUntilStarted();
	}

	@After
	public void shutdown() throws Exception {
		broker.stop();
		broker.waitUntilStopped();
	}

	@Test
	public void testFailCreateSessionNotEnoughRight() throws Exception {

	}

	@Test
	public void testCreateQueuePublishConsume() throws Exception {
		try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616"); // apparently the vm part is all i need
            Connection connection = connectionFactory.createConnection("jdoe", "sunflower"); 
            connection.start();
            
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TEST.FOO");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage message = session.createTextMessage(text);
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);
            session.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
	
	
	}

	@Test
	public void testFailCreateQueuePublishConsume() throws Exception {

	}

}