package com.URBinLAB;

import com.URBinLAB.utils.AccessControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		AccessControl.innit();
		SpringApplication.run(BackendApplication.class, args);
	}

}
