package com.ecommerce.product.model;

import com.ecommerce.product.model.Image.ImageBuilder;

import java.nio.charset.StandardCharsets;

public class ImageTestBuilder {

    private static final Long ID = 1L;

    private static final String DEFAULT_DESCRIPTION = "red sock";
    private static final byte[] DEFAULT_IMAGE = "default b64 image".getBytes(StandardCharsets.UTF_8);

    private static final String NEW_DESCRIPTION = "brown sock";
    private static final byte[] NEW_IMAGE =  "new b64 image".getBytes(StandardCharsets.UTF_8);

    public static ImageTestBuilder init() {
        return new ImageTestBuilder();
    }

    public ImageBuilder withDefaultValues() {
        return Image.builder()
                .id(ID)
                .image(DEFAULT_IMAGE)
                .description(DEFAULT_DESCRIPTION);
    }

    public ImageBuilder withDefaultValuesNew() {
        return Image.builder()
                .id(ID)
                .image(NEW_IMAGE)
                .description(NEW_DESCRIPTION);
    }
}
