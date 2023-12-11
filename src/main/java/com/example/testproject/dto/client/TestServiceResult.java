package com.example.testproject.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestServiceResult<T> {
	@JsonProperty("succeed")
	private boolean succeed;

	@JsonProperty("content")
	private T content;

	@JsonProperty("error")
	private T error;


	public static <T> TestServiceResult<T> succeed(T content){
		return TestServiceResult.<T>builder()
				.succeed(true)
				.content(content)
				.error(null)
				.build();
	}

	public static <T> TestServiceResult<T> fail(T error){
		return TestServiceResult.<T>builder()
				.succeed(false)
				.content(null)
				.error(error)
				.build();
	}
}
