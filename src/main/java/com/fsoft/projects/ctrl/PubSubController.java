package com.fsoft.projects.ctrl;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.projects.model.Body;
import com.fsoft.projects.model.Message;

@RestController
public class PubSubController {
	
	private static Logger LOG = LoggerFactory.getLogger(PubSubController.class);
	
	@GetMapping("/")
	public ResponseEntity<String> info() {
		return ResponseEntity.ok("PubSub Service Consumer Running");
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> receiveMessage(@RequestBody Body body) {
		// Get PubSub message from request body.
		Message message = body.getMessage();
		if (message == null) {
			String msg = "Bad Request: invalid Pub/Sub message format";
			System.out.println(msg);
			return ResponseEntity.badRequest().body(msg);
		}

		String data = message.getData();
		String target = !StringUtils.isEmpty(data) ? new String(Base64.getDecoder().decode(data)) : "Kein Data";
		String msg = "Recieved " + target + "!";

		LOG.info(msg);
		return ResponseEntity.accepted().build();
	}
}
