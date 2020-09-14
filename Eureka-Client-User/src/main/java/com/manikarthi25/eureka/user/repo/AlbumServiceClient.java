package com.manikarthi25.eureka.user.repo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.manikarthi25.eureka.user.response.model.AlbumResponseModel;

@FeignClient(name = "album-ms")
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAblums(@PathVariable String id);

}
