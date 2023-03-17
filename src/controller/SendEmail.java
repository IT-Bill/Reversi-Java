package controller;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail {
	public static void send(String msg) {
		Transport transport = null;
		try {
			//创建一个配置文件并保存
			Properties properties = new Properties();

			properties.setProperty("mail.host", "smtp.163.com");

			properties.setProperty("mail.transport.protocol", "smtp");

			properties.setProperty("mail.smtp.auth", "true");


			//QQ存在一个特性设置SSL加密
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);

			//创建一个session对象
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("ef51211@163.com", "MVWOPINVYOADXXZG");
				}
			});

			//开启debug模式
			session.setDebug(true);

			//获取连接对象
			transport = session.getTransport();

			//连接服务器
			transport.connect("smtp.163.com", "ef51211@163.com", "MVWOPINVYOADXXZG");

			//创建邮件对象
			MimeMessage mimeMessage = new MimeMessage(session);

			//邮件发送人
			mimeMessage.setFrom(new InternetAddress("ef51211@163.com"));

			//邮件接收人
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("1746428920@qq.com"));

			//邮件标题
			mimeMessage.setSubject("来自玩家的反馈");

			//邮件内容
			mimeMessage.setContent(msg, "text/html;charset=UTF-8");

			//发送邮件
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (transport != null) {
					transport.close();
				}

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		//关闭连接
	}

}
