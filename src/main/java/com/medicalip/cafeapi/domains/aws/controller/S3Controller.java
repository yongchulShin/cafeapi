package com.medicalip.cafeapi.domains.aws.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medicalip.cafeapi.domains.aws.service.S3Service;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class S3Controller {
	
//	private final S3Service s3Service;
//	
//	@GetMapping("/filedown/{fileName}")
//	public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException{
//		return s3Service.getObject(fileName);
//	}
	

}
