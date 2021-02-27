// ICanService.aidl
package com.yadea.yadeaserver;

import com.yadea.yadeaserver.ICanServiceListener;
// Declare any non-default types here with import statements

interface ICanService {

    void registerListener(ICanServiceListener listener);
    void unregisterListener(ICanServiceListener listener);

    int getProp(int propId, int defaultValue);
    int getPropForce(int propId, int defaultValue);
}