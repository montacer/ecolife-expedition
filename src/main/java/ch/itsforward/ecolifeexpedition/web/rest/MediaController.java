package ch.itsforward.ecolifeexpedition.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import ch.itsforward.ecolifeexpedition.config.StaticResourcesWebConfiguration;

@RestController
@RequestMapping("/media")
public class MediaController {

	private final Logger LOG = LoggerFactory.getLogger(TourResource.class);

	@GetMapping("/video/{name}")
	public ResponseEntity<StreamingResponseBody> getVideoMedia(@PathVariable String name,HttpServletRequest request,
			HttpServletResponse response) {

		File file = new File(StaticResourcesWebConfiguration.RESOURCE_VIDEO_MEDIA_LOCATIONS+File.separatorChar+name);
		if (!file.isFile()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}
		StreamingResponseBody stream = out -> {
			try {
				final FileInputStream inputStream = new FileInputStream(file);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = inputStream.read(bytes)) >= 0) {
					out.write(bytes, 0, length);
				}
				inputStream.close();
				out.flush();

			} catch (final Exception e) {
				LOG.error("Exception while reading and streaming data {} ", e);
			}

		};

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "video/mp4");
		headers.add("Content-Length", Long.toString(file.length()));

		return ResponseEntity.ok().headers(headers).body(stream);
	}

	@GetMapping("/audio/{name}")
	public ResponseEntity<StreamingResponseBody> getAudioMedia(@PathVariable String name,HttpServletRequest request,
			HttpServletResponse response) {
		File file = new File(StaticResourcesWebConfiguration.RESOURCE_AUDIO_MEDIA_LOCATIONS+File.separatorChar);
		if (!file.isFile()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}
		StreamingResponseBody stream = out -> {
			try {
				final FileInputStream inputStream = new FileInputStream(file);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = inputStream.read(bytes)) >= 0) {
					out.write(bytes, 0, length);
				}
				inputStream.close();
				out.flush();

			} catch (final Exception e) {
				LOG.error("Exception while reading and streaming data {} ", e);
			}

		};

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "audio/mp3");
		headers.add("Content-Length", Long.toString(file.length()));

		return ResponseEntity.ok().headers(headers).body(stream);

	}

}
