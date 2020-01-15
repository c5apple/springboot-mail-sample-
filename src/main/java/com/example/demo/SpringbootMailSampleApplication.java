package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class SpringbootMailSampleApplication {

	public static void main(String[] args) {
		//		SpringApplication.run(SpringbootMailSampleApplication.class, args);

		try (ConfigurableApplicationContext ctx = SpringApplication.run(SpringbootMailSampleApplication.class, args)) {
			SpringbootMailSampleApplication app = ctx.getBean(SpringbootMailSampleApplication.class);
			app.sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private MailSender mailSender;

	/**
	 * メールを送信する
	 */
	public void sendMail() {
		System.out.println("Start!!");

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom("送信元メールアドレス"); // FIXME
		msg.setTo("宛先メールアドレス"); // FIXME
		msg.setSubject("テストメール");
		msg.setText("Spring Boot より本文送信");

		mailSender.send(msg);

		System.out.println("End!!");
	}
}
