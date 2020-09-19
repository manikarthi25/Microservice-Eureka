package com.manikarthi25.eureka.user.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.manikarthi25.eureka.user.response.model.AlbumResponseModel;

@FeignClient(name = "album-ms", fallback = AlbumsServiceClientFallback.class)
public interface AlbumServiceClient {

	@GetMapping("/user/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}

@Component
class AlbumsServiceClientFallback implements AlbumServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		return new ArrayList<>();
	}

}














/*package com.manikarthi25.eureka.user.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.manikarthi25.eureka.user.response.model.AlbumResponseModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "album-ms", fallback = AlbumsFallbackFactory.class)
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {

	@Override
	public AlbumServiceClient create(Throwable cause) {
		return new AlbumsServiceClientFallback(cause);
	}

}

class AlbumsServiceClientFallback implements AlbumServiceClient {
	
	@Autowired
	private AlbumServiceClient albumServiceClient;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public AlbumsServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {

		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("Album service is not up and running and Response Code : {} Exception : {}" + "404" + cause.getLocalizedMessage());
		} else {
			logger.error(cause.getLocalizedMessage());
		}

		return new ArrayList<>();
	}

}
*/