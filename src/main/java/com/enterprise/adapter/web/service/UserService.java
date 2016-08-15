package com.enterprise.adapter.web.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Service
@EnableScheduling
public class UserService {

	public String CurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		cal.getTime();
		String currentTime = sdf.format(cal.getTime());
		return currentTime;
	}

	/*
	 * Timer to control old entry in user map
	 */
	@Scheduled(fixedDelay = (1000 * 60) * 2)
	public void ControlUserTimmer() {

	}
}