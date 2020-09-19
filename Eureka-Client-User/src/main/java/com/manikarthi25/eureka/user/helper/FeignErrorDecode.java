package com.manikarthi25.eureka.user.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecode implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 400: {
			break;
		}

		case 404: {
			// getAblums - method name
			if (methodKey.contains("getAlbums")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Ablbums is not present");
			}
			break;
		}
		default:
			return new Exception(response.reason());
		}
		return null;
	}

}
