package com.alibaba.fastjson.asm;

public class Label {
    static final int RESOLVED = 2;
    public Object info;
    int inputStackTop;
    int line;
    Label next;
    int outputStackMax;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int status;
    Label successor;

    /* access modifiers changed from: package-private */
    public void put(MethodWriter owner, ByteVector out, int source) {
        if ((this.status & 2) == 0) {
            addReference(source, out.length);
            out.putShort(-1);
            return;
        }
        out.putShort(this.position - source);
    }

    private void addReference(int sourcePosition, int referencePosition) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        int i = this.referenceCount;
        int[] iArr = this.srcAndRefPositions;
        if (i >= iArr.length) {
            int[] a = new int[(iArr.length + 6)];
            System.arraycopy(iArr, 0, a, 0, iArr.length);
            this.srcAndRefPositions = a;
        }
        int[] a2 = this.srcAndRefPositions;
        int i2 = this.referenceCount;
        this.referenceCount = i2 + 1;
        a2[i2] = sourcePosition;
        int i3 = this.referenceCount;
        this.referenceCount = i3 + 1;
        a2[i3] = referencePosition;
    }

    /* JADX INFO: Multiple debug info for r0v4 int: [D('i' int), D('source' int)] */
    /* access modifiers changed from: package-private */
    public void resolve(MethodWriter owner, int position2, byte[] data) {
        this.status |= 2;
        this.position = position2;
        int source = 0;
        while (source < this.referenceCount) {
            int[] iArr = this.srcAndRefPositions;
            int i = source + 1;
            int source2 = iArr[source];
            int i2 = i + 1;
            int reference = iArr[i];
            int offset = position2 - source2;
            data[reference] = (byte) (offset >>> 8);
            data[reference + 1] = (byte) offset;
            source = i2;
        }
    }
}
