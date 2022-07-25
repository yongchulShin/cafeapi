package com.medicalip.cafeapi.domains.auth.service;

import com.medicalip.cafeapi.domains.commons.response.SingleResult;

public interface TokenService {

	SingleResult generateJwtToken(String email);

}
