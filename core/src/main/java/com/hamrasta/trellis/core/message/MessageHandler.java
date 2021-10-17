package com.hamrasta.trellis.core.message;

import com.hamrasta.trellis.core.metadata.Language;
import org.apache.commons.lang3.StringUtils;

public interface MessageHandler {
    String name();

    default String getMessage() {
        return getMessage(Language.FA);
    }

    default Integer getCode() {
        return MessageHelper.getCode(name());
    }

    default String getMessage(Object... var1) {
        String result = MessageHelper.getMessage(Language.FA, name(), var1);
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

    default String getMessage(Language language) {
        String result = MessageHelper.getMessage(language, name());
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

    default String getMessage(Language language, Object... var1) {
        String result = MessageHelper.getMessage(language, name(), var1);
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

}
