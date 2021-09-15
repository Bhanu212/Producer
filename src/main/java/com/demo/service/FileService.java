package com.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.demo.helper.CSVHelper;

@Service
public class FileService {
	@Autowired
	ProducerService producerService;
	
	public void save(MultipartFile file, MultipartFile file2) {
		// TODO Auto-generated method stub


		try {
			List< HashMap<String, Object>> maps=CSVHelper.csvToObject(file2.getInputStream());
			HashMap<Integer, String> emails = CSVHelper.csvToOrders(file.getInputStream());
			for(HashMap<String, Object> map :maps) {
				int id=(int) map.get("UserId");
				map.put("Email", emails.containsKey(id)?emails.get(id):"");
				producerService.Send( "userInfo",new JSONObject(map));
				HashMap<String, Object> objHashMap=new HashMap<>();
				if(emails.containsKey(id)) {
				objHashMap.put("email", emails.get(id));
				objHashMap.put("password", map.get("password"));
				producerService.Send( "email",new JSONObject(objHashMap));
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
