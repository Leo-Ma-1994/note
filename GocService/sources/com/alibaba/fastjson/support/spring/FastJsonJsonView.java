package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class FastJsonJsonView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/json";
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset = UTF8;
    private boolean disableCaching = true;
    private boolean extractValueFromSingleKeyModel = false;
    private Set<String> renderedAttributes;
    private SerializerFeature[] serializerFeatures = new SerializerFeature[0];
    private boolean updateContentLength = false;

    public FastJsonJsonView() {
        setContentType(DEFAULT_CONTENT_TYPE);
        setExposePathVariables(false);
    }

    public void setRenderedAttributes(Set<String> renderedAttributes2) {
        this.renderedAttributes = renderedAttributes2;
    }

    @Deprecated
    public void setSerializerFeature(SerializerFeature... features) {
        setFeatures(features);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset2) {
        this.charset = charset2;
    }

    public SerializerFeature[] getFeatures() {
        return this.serializerFeatures;
    }

    public void setFeatures(SerializerFeature... features) {
        this.serializerFeatures = features;
    }

    public boolean isExtractValueFromSingleKeyModel() {
        return this.extractValueFromSingleKeyModel;
    }

    public void setExtractValueFromSingleKeyModel(boolean extractValueFromSingleKeyModel2) {
        this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel2;
    }

    /* access modifiers changed from: protected */
    public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] bytes = JSON.toJSONString(filterModel(model), this.serializerFeatures).getBytes(this.charset);
        OutputStream stream = this.updateContentLength ? createTemporaryOutputStream() : response.getOutputStream();
        stream.write(bytes);
        if (this.updateContentLength) {
            writeToResponse(response, (ByteArrayOutputStream) stream);
        }
    }

    /* access modifiers changed from: protected */
    public void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        setResponseContentType(request, response);
        response.setCharacterEncoding(UTF8.name());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1);
        }
    }

    public void setDisableCaching(boolean disableCaching2) {
        this.disableCaching = disableCaching2;
    }

    public void setUpdateContentLength(boolean updateContentLength2) {
        this.updateContentLength = updateContentLength2;
    }

    /* access modifiers changed from: protected */
    public Object filterModel(Map<String, Object> model) {
        Map<String, Object> result = new HashMap<>(model.size());
        Set<String> renderedAttributes2 = !CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (!(entry.getValue() instanceof BindingResult) && renderedAttributes2.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        if (this.extractValueFromSingleKeyModel && result.size() == 1) {
            Iterator<Map.Entry<String, Object>> it = result.entrySet().iterator();
            if (it.hasNext()) {
                return it.next().getValue();
            }
        }
        return result;
    }
}
