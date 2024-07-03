package com.server.booyoungee.domain.tourInfo.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourInfoOpenApiService {
	private final ObjectMapper objectMapper;

	public Object getTourInfo(String url) {
		HttpURLConnection urlConnection = null;
		Object jsonResult = null;
		InputStream stream;
		String result;

		try {
			URL requestUrl = new URL(url);

			urlConnection = (HttpURLConnection)requestUrl.openConnection();
			stream = getNetworkConnection(urlConnection);
			result = readStreamToString(stream);

			jsonResult = objectMapper.readValue(result, Object.class);

			if (stream != null)
				stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return jsonResult;
	}

	private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
		urlConnection.setConnectTimeout(3000);
		urlConnection.setReadTimeout(3000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoInput(true);

		if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
		}

		return urlConnection.getInputStream();
	}

	private String readStreamToString(InputStream stream) throws IOException {
		StringBuilder result = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

		String readLine;
		while ((readLine = br.readLine()) != null) {
			result.append(readLine + "\n\r");
		}

		br.close();

		return result.toString();
	}
}
