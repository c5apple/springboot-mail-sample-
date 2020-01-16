package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
public class SpringbootMailSampleApplication {

	public static void main(String[] args) {
		//		SpringApplication.run(SpringbootMailSampleApplication.class, args);

		try (ConfigurableApplicationContext ctx = SpringApplication.run(SpringbootMailSampleApplication.class, args)) {
			SpringbootMailSampleApplication app = ctx.getBean(SpringbootMailSampleApplication.class);
			app.sendMail2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

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

	/**
	 * メールを送信する
	 */
	public void sendMail2() {
		System.out.println("Start!!");

		// テストデータ
		Context context = new Context();
		context.setVariable("name", "ユーザー名");
		List<String> itemList = Arrays.asList("1", "2");
		context.setVariable("itemList", itemList);

		MimeMessagePreparator message = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
						StandardCharsets.UTF_8.name());
				helper.setFrom("送信元メールアドレス"); // FIXME
				helper.setTo("宛先メールアドレス"); // FIXME
				helper.setSubject("テストメール");

				String templateName = "samplemail";
				SpringTemplateEngine templateEngine = new SpringTemplateEngine();
				ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
				templateResolver.setTemplateMode(TemplateMode.HTML);
				templateResolver.setPrefix("mailtemplate/");
				templateResolver.setSuffix(".html");
				templateResolver.setCharacterEncoding("UTF-8");
				templateResolver.setCacheable(true);
				templateEngine.setTemplateResolver(templateResolver);
				String text = templateEngine.process(templateName, context);
				helper.setText(text, true);
			}
		};

		javaMailSender.send(message);

		System.out.println("End!!");
	}
}
