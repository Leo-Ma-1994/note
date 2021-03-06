package com.goodocom.gocsdk.vcard;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class VCardProperty {
    private static final String LOG_TAG = "vCard";
    private byte[] mByteValue;
    private List<String> mGroupList;
    private String mName;
    private Map<String, Collection<String>> mParameterMap = new HashMap();
    private String mRawValue;
    private List<String> mValueList;

    public void setName(String name) {
        String str = this.mName;
        if (str != null) {
            Log.w(LOG_TAG, String.format("Property name is re-defined (existing: %s, requested: %s", str, name));
        }
        this.mName = name;
    }

    public void addGroup(String group) {
        if (this.mGroupList == null) {
            this.mGroupList = new ArrayList();
        }
        this.mGroupList.add(group);
    }

    public void setParameter(String paramName, String paramValue) {
        this.mParameterMap.clear();
        addParameter(paramName, paramValue);
    }

    public void addParameter(String paramName, String paramValue) {
        Collection<String> values;
        if (!this.mParameterMap.containsKey(paramName)) {
            if (paramName.equals(VCardConstants.PARAM_TYPE)) {
                values = new HashSet<>();
            } else {
                values = new ArrayList<>();
            }
            this.mParameterMap.put(paramName, values);
        } else {
            values = this.mParameterMap.get(paramName);
        }
        values.add(paramValue);
    }

    public void setRawValue(String rawValue) {
        this.mRawValue = rawValue;
    }

    public void setValues(String... propertyValues) {
        this.mValueList = Arrays.asList(propertyValues);
    }

    public void setValues(List<String> propertyValueList) {
        this.mValueList = propertyValueList;
    }

    public void addValues(String... propertyValues) {
        List<String> list = this.mValueList;
        if (list == null) {
            this.mValueList = Arrays.asList(propertyValues);
        } else {
            list.addAll(Arrays.asList(propertyValues));
        }
    }

    public void addValues(List<String> propertyValueList) {
        List<String> list = this.mValueList;
        if (list == null) {
            this.mValueList = new ArrayList(propertyValueList);
        } else {
            list.addAll(propertyValueList);
        }
    }

    public void setByteValue(byte[] byteValue) {
        this.mByteValue = byteValue;
    }

    public String getName() {
        return this.mName;
    }

    public List<String> getGroupList() {
        return this.mGroupList;
    }

    public Map<String, Collection<String>> getParameterMap() {
        return this.mParameterMap;
    }

    public Collection<String> getParameters(String type) {
        return this.mParameterMap.get(type);
    }

    public String getRawValue() {
        return this.mRawValue;
    }

    public List<String> getValueList() {
        return this.mValueList;
    }

    public byte[] getByteValue() {
        return this.mByteValue;
    }
}
