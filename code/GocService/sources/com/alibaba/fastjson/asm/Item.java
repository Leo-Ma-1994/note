package com.alibaba.fastjson.asm;

import androidx.appcompat.widget.ActivityChooserView;

/* access modifiers changed from: package-private */
public final class Item {
    int hashCode;
    int index;
    int intVal;
    long longVal;
    Item next;
    String strVal1;
    String strVal2;
    String strVal3;
    int type;

    Item() {
    }

    Item(int index2, Item i) {
        this.index = index2;
        this.type = i.type;
        this.intVal = i.intVal;
        this.longVal = i.longVal;
        this.strVal1 = i.strVal1;
        this.strVal2 = i.strVal2;
        this.strVal3 = i.strVal3;
        this.hashCode = i.hashCode;
    }

    /* access modifiers changed from: package-private */
    public void set(int type2, String strVal12, String strVal22, String strVal32) {
        this.type = type2;
        this.strVal1 = strVal12;
        this.strVal2 = strVal22;
        this.strVal3 = strVal32;
        if (!(type2 == 1 || type2 == 7 || type2 == 8)) {
            if (type2 == 12) {
                this.hashCode = ((strVal12.hashCode() * strVal22.hashCode()) + type2) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                return;
            } else if (type2 != 13) {
                this.hashCode = ((strVal12.hashCode() * strVal22.hashCode() * strVal32.hashCode()) + type2) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                return;
            }
        }
        this.hashCode = (strVal12.hashCode() + type2) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    /* access modifiers changed from: package-private */
    public void set(int intVal2) {
        this.type = 3;
        this.intVal = intVal2;
        this.hashCode = (this.type + intVal2) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    /* access modifiers changed from: package-private */
    public boolean isEqualTo(Item i) {
        int i2 = this.type;
        if (i2 != 1) {
            if (i2 != 15) {
                if (i2 != 12) {
                    if (i2 != 13) {
                        switch (i2) {
                            case 3:
                            case 4:
                                if (i.intVal == this.intVal) {
                                    return true;
                                }
                                return false;
                            case 5:
                            case 6:
                                break;
                            case 7:
                            case 8:
                                break;
                            default:
                                if (!i.strVal1.equals(this.strVal1) || !i.strVal2.equals(this.strVal2) || !i.strVal3.equals(this.strVal3)) {
                                    return false;
                                }
                                return true;
                        }
                    }
                } else if (!i.strVal1.equals(this.strVal1) || !i.strVal2.equals(this.strVal2)) {
                    return false;
                } else {
                    return true;
                }
            }
            if (i.longVal == this.longVal) {
                return true;
            }
            return false;
        }
        return i.strVal1.equals(this.strVal1);
    }
}
