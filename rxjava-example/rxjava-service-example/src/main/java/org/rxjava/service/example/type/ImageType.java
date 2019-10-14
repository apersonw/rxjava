package org.rxjava.service.example.type;

import org.springframework.http.MediaType;

public enum ImageType {
    png, jpg;
    public MediaType getMediaType(String imageType){
        return MediaType.IMAGE_PNG;
    }
}
