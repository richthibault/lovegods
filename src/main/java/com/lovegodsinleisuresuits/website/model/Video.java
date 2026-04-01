package com.lovegodsinleisuresuits.website.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Video {

	private String title;
	private String url;

	/** Converts a YouTube watch/short URL to an embeddable src. */
	public String getEmbedUrl() {
		String id = null;
		if (url.contains("youtu.be/")) {
			id = url.substring(url.lastIndexOf('/') + 1);
		} else if (url.contains("v=")) {
			id = url.substring(url.indexOf("v=") + 2);
			if (id.contains("&")) id = id.substring(0, id.indexOf("&"));
		}
		return id != null ? "https://www.youtube.com/embed/" + id + "?rel=0" : url;
	}

}
