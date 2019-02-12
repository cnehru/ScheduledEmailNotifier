
import javax.mail.*
import javax.mail.internet.*
import javax.activation.*

def mail() {

	// Recipient's email ID needs to be mentioned.
	def receivers = "cnehru22@gmail.com"

	// Sender's email ID needs to be mentioned
	def sender = "web@gmail.com"

	// Assuming you are sending email from localhost
	def host = "smtp.gmail.com"

	def subject = "Test Email Subject"

	def text = "Test Email"

	def props = new Properties()
	props.put("mail.smtp.host", host)
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.host", "smtp.gmail.com");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");
	props.put("mail.debug", "true");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.socketFactory.fallback", "false");

	try{
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("cnehru22@gmail.com","lybuvqbldeeaswnf");
					}
				});

		//session.setDebug(true);
		Transport transport = session.getTransport();
		InternetAddress addressFrom = new InternetAddress(sender);

		MimeMessage message = new MimeMessage(session);
		message.setSender(addressFrom);
		message.setSubject(subject);
		message.setContent(text, "text/plain");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receivers));

		transport.connect();
		Transport.send(message);
		transport.close();
	}catch(Exception e){
		e.printStackTrace()
	}

}

return [
    mail: this.&mail
]

