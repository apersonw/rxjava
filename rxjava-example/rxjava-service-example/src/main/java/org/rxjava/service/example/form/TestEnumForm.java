package org.rxjava.service.example.form;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.service.example.type.ImageType;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class TestEnumForm {
    private ImageType imageType;
}
