package com.fsoft.projects.ctrl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.projects.model.Body;
import com.fsoft.projects.model.Message;
import com.fsoft.projects.model.TradeDaten;

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
			LOG.info(msg);
			return ResponseEntity.badRequest().body(msg);
		}

		String data = message.getData();
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		String strJson = new String(Base64.getDecoder().decode(data), Charset.forName("UTF-8"));
		LOG.info(strJson);
		
		TradeDaten trade = null;
		try {
			trade = mapper.readValue(Base64.getDecoder().decode(data), TradeDaten.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String msg = (trade != null) ? "Recieved " + trade.toString() + "!": "Problem Serial/Deserial";

		LOG.info(msg);
		return ResponseEntity.accepted().build();
	}
}
