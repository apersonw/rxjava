package org.rxjava.service.example.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.rxjava.service.example.type.ImageType;

@Getter
@Setter
public class TestEnumForm {
    private ImageType imageType;
    private String id;
    private ObjectId objectId;
}
