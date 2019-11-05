package org.rxjava.service.example.type;

import org.springframework.http.MediaType;

public enum ImageType {
    /**
     * Png格式
     */
    png,
    /**
     * Jpg格式
     */
    jpg;
    public MediaType getMediaType(String imageType){
        return MediaType.IMAGE_PNG;
    }
}
