package com.alibaba.fastjson.asm;

final class FieldWriter implements FieldVisitor {
    private final int access;
    private final int desc;
    private final int name;
    FieldWriter next;

    FieldWriter(ClassWriter cw, int access2, String name2, String desc2) {
        if (cw.firstField == null) {
            cw.firstField = this;
        } else {
            cw.lastField.next = this;
        }
        cw.lastField = this;
        this.access = access2;
        this.name = cw.newUTF8(name2);
        this.desc = cw.newUTF8(desc2);
    }

    @Override // com.alibaba.fastjson.asm.FieldVisitor
    public void visitEnd() {
    }

    /* access modifiers changed from: package-private */
    public int getSize() {
        return 8;
    }

    /* access modifiers changed from: package-private */
    public void put(ByteVector out) {
        int i = this.access;
        out.putShort(i & (~(((262144 & i) / 64) | 393216))).putShort(this.name).putShort(this.desc);
        out.putShort(0);
    }
}
