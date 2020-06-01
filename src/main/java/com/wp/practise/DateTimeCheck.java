package com.wp.practise;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCheck {

	public static void main(String args[]) throws ParseException {
		//Date date = new Date();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			    .parse("2017-11-15 15:30:14"));
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		System.out.println(ldt.format(dtf));
		
		String dateInString = "2017-11-15 15:30";
		LocalDateTime ldt2 = LocalDateTime.parse(dateInString, dtf);
		Timestamp timestamp = Timestamp.valueOf(ldt2);
		System.out.println(timestamp);
		
	}
}
