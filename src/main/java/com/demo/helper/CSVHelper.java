package com.demo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



@Component
public class CSVHelper {
    public static String TYPE = "text/csv";
//    static String[] HEADERs = { "Login email","Identifier","First name","Last name"};
//    static String[] HEADERs2 =	{"Username", "Identifier","One-time password","Recovery code","First name","Last name","Department","Location"};

    public static boolean hasCSVFormat(MultipartFile file) {
    	System.out.println(file.getContentType());
//        if (!TYPE.equals(file.getContentType())) { return false;}
        return true;
    }
    public static HashMap<Integer, String> csvToOrders(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
             CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
                    HashMap<Integer, String> map=new HashMap<>();
                    Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                    for (CSVRecord csvRecord : csvRecords) {
                    	map.put(Integer.parseInt(csvRecord.get(1)), csvRecord.get(0));
                    }
                    return map;
        }catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        }
    public static List< HashMap<String, Object>>  csvToObject(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         CSVParser csvParser = new CSVParser(fileReader,
         CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
    
    	List< HashMap<String, Object>> listMap = new ArrayList<>();
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                for (CSVRecord csvRecord : csvRecords) {
                	HashMap<String, Object> map=new HashMap<String, Object>();
                	map.put("Username", csvRecord.get(0));
                	map.put("UserId", Integer.parseInt(csvRecord.get(1)));
                	map.put("FirstName", csvRecord.get(4));
                	map.put("LastName", csvRecord.get(5));
                	map.put("Department", csvRecord.get(6));
                	map.put("Location", csvRecord.get(7));
                	map.put("password", csvRecord.get(2));
                	listMap.add(map);}
                return listMap;
    }catch (IOException e) {
        throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }}}
