package hello.controller;

import java.util.List;

import hello.model.Message;
import hello.repository.MessageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	private static Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private MessageRepository messageRepository;

    @RequestMapping("/")
    public String index(ModelMap model) {
    	List<Message> messages = messageRepository.findBySubject("Greet");
    	logger.debug("Retrieved message list with {} elements", messages.size());
		model.addAttribute("messages", messages);
        return "index";
    }

    @RequestMapping("/secure/")
    public String secureIndex() {
        return "secure-index";
    }

}