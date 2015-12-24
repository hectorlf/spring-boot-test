package hello.test;

import hello.Application;
import hello.model.Message;
import hello.repository.MessageRepository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class JpaRepositoryTests {

	@Autowired
	private MessageRepository messageRepository;

	@Test
	public void testInsertion() {
		Message m = new Message();
		m.setSubject("Hi!");
		m.setText("I'm not spam!");
		messageRepository.saveAndFlush(m);
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void testConstraintViolation1() {
		Message m = new Message();
		messageRepository.saveAndFlush(m);
	}

	@Test
	public void testFindBySubjectOk() {
		List<Message> messages = messageRepository.findBySubject("Greet");
		Assert.assertEquals(1, messages.size());
	}

}