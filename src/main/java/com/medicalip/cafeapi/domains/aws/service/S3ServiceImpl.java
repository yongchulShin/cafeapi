package com.medicalip.cafeapi.domains.aws.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{

//	private final AmazonS3 amazonS3;
//	
//	@Value("${aws.s3.bucket}")
//	private String bucket;
//	
//	/**
//	 * S3 bucket 파일 다운로드
//	 */
//	
//	@Override
//	public ResponseEntity<byte[]> getObject(String storedFileName) throws IOException {
//		// TODO Auto-generated method stub
//		S3Object obj = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
//		S3ObjectInputStream objInputStream = obj.getObjectContent();
//		byte[] bytes = IOUtils.toByteArray(objInputStream);
//		
//		String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		httpHeaders.setContentLength(bytes.length);
//		httpHeaders.setContentDispositionFormData("attachment", fileName);
//		
//		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.SC_OK);
//	}

}
