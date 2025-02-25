package com.rajuProject.olaKrutrimAssignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.rajuProject.olaKrutrimAssignment",
		"com.rajuProject.olaKrutrimAssignment.grpc"
})
public class OlaKrutrimAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlaKrutrimAssignmentApplication.class, args);
	}

}
