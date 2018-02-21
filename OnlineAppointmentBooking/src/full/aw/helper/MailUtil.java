package full.aw.helper;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	//code to send simple mail to the requried customer
	public void sendSimpleMail(String email, String name, String subject, String message) {
		Logger logger = Logger.getLogger(MailUtil.class.getName());
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("vikram.narasaiah@anywhere.co", "Appointment Booking Application"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, name));
			msg.setSubject(subject);
			msg.setText(message);
			Transport.send(msg);
		} catch (AddressException e) {
			logger.warning("AddressException");
		} catch (MessagingException e) {
			logger.warning("MessagingException");
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException");
		}
	}//method close
}//class close
