package net.sourceforge.pinyin4j;

import java.io.BufferedInputStream;

class ResourceHelper {
    ResourceHelper() {
    }

    static BufferedInputStream getResourceInputStream(String resourceName) {
        return new BufferedInputStream(ResourceHelper.class.getResourceAsStream(resourceName));
    }
}
