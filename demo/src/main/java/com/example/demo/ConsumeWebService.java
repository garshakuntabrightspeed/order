package com.example.demo;

import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class ConsumeWebService {
	
	@Autowired
	RestTemplate restTemplate;	

	@RequestMapping(value = "/template", method = RequestMethod.POST)
	public Object createProducts() {
		JSONObject jObj = new JSONObject("{\"statements\": [{\"statement\": \"MATCH (n:FTTH_CIRCUIT_6089) RETURN n LIMIT 25\",\"parameters\": {\"nodeId\": 3}}]}");
		
		String plainCreds = "neo4j:2314";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		
		HttpEntity<Object> entity = new HttpEntity<Object>(jObj, headers);
		ResponseEntity<JSONObject> response = restTemplate.exchange("http://localhost:7474/db/neo4j/tx", HttpMethod.POST, entity, JSONObject.class);
		System.out.println("response ____________" + response);

		return response;
	}
}