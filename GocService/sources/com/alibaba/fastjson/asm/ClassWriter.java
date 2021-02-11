package com.alibaba.fastjson.asm;

public class ClassWriter {
    static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
    static final int CLASS = 7;
    public static final int COMPUTE_FRAMES = 2;
    public static final int COMPUTE_MAXS = 1;
    static final int DOUBLE = 6;
    static final int FIELD = 9;
    static final int FIELDORMETH_INSN = 6;
    static final int FLOAT = 4;
    static final int IINC_INSN = 12;
    static final int IMETH = 11;
    static final int IMPLVAR_INSN = 4;
    static final int INT = 3;
    static final int ITFDYNMETH_INSN = 7;
    static final int LABELW_INSN = 9;
    static final int LABEL_INSN = 8;
    static final int LDCW_INSN = 11;
    static final int LDC_INSN = 10;
    static final int LONG = 5;
    static final int LOOK_INSN = 14;
    static final int MANA_INSN = 15;
    static final int METH = 10;
    static final int NAME_TYPE = 12;
    static final int NOARG_INSN = 0;
    static final int SBYTE_INSN = 1;
    static final int SHORT_INSN = 2;
    static final int STR = 8;
    static final int TABL_INSN = 13;
    static final byte[] TYPE;
    static final int TYPE_INSN = 5;
    static final int TYPE_MERGED = 15;
    static final int TYPE_NORMAL = 13;
    static final int TYPE_UNINIT = 14;
    static final int UTF8 = 1;
    static final int VAR_INSN = 3;
    static final int WIDE_INSN = 16;
    private int access;
    FieldWriter firstField;
    MethodWriter firstMethod;
    int index;
    private int interfaceCount;
    private int[] interfaces;
    Item[] items;
    final Item key;
    final Item key2;
    final Item key3;
    FieldWriter lastField;
    MethodWriter lastMethod;
    private int name;
    final ByteVector pool;
    private int superName;
    String thisName;
    int threshold;
    Item[] typeTable;
    int version;

    static {
        byte[] b = new byte[220];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) ("AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHHFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII".charAt(i) - 'A');
        }
        TYPE = b;
    }

    public ClassWriter() {
        this(0);
    }

    private ClassWriter(int flags) {
        this.index = 1;
        this.pool = new ByteVector();
        this.items = new Item[256];
        this.threshold = (int) (((double) this.items.length) * 0.75d);
        this.key = new Item();
        this.key2 = new Item();
        this.key3 = new Item();
    }

    public void visit(int version2, int access2, String name2, String superName2, String[] interfaces2) {
        this.version = version2;
        this.access = access2;
        this.name = newClass(name2);
        this.thisName = name2;
        this.superName = superName2 == null ? 0 : newClass(superName2);
        if (interfaces2 != null && interfaces2.length > 0) {
            this.interfaceCount = interfaces2.length;
            this.interfaces = new int[this.interfaceCount];
            for (int i = 0; i < this.interfaceCount; i++) {
                this.interfaces[i] = newClass(interfaces2[i]);
            }
        }
    }

    public FieldVisitor visitField(int access2, String name2, String desc) {
        return new FieldWriter(this, access2, name2, desc);
    }

    public MethodVisitor visitMethod(int access2, String name2, String desc, String signature, String[] exceptions) {
        return new MethodWriter(this, access2, name2, desc, signature, exceptions);
    }

    public byte[] toByteArray() {
        int size = (this.interfaceCount * 2) + 24;
        int nbFields = 0;
        for (FieldWriter fb = this.firstField; fb != null; fb = fb.next) {
            nbFields++;
            size += fb.getSize();
        }
        int nbMethods = 0;
        for (MethodWriter mb = this.firstMethod; mb != null; mb = mb.next) {
            nbMethods++;
            size += mb.getSize();
        }
        ByteVector out = new ByteVector(size + this.pool.length);
        out.putInt(-889275714).putInt(this.version);
        out.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
        int i = this.access;
        out.putShort(i & (~(393216 | ((262144 & i) / 64)))).putShort(this.name).putShort(this.superName);
        out.putShort(this.interfaceCount);
        for (int i2 = 0; i2 < this.interfaceCount; i2++) {
            out.putShort(this.interfaces[i2]);
        }
        out.putShort(nbFields);
        for (FieldWriter fb2 = this.firstField; fb2 != null; fb2 = fb2.next) {
            fb2.put(out);
        }
        out.putShort(nbMethods);
        for (MethodWriter mb2 = this.firstMethod; mb2 != null; mb2 = mb2.next) {
            mb2.put(out);
        }
        out.putShort(0);
        return out.data;
    }

    /* access modifiers changed from: package-private */
    public Item newConstItem(Object cst) {
        if (cst instanceof Integer) {
            return newInteger(((Integer) cst).intValue());
        }
        if (cst instanceof String) {
            return newString((String) cst);
        }
        if (cst instanceof Type) {
            Type t = (Type) cst;
            return newClassItem(t.getSort() == 10 ? t.getInternalName() : t.getDescriptor());
        }
        throw new IllegalArgumentException("value " + cst);
    }

    /* access modifiers changed from: package-private */
    public Item newInteger(int value) {
        this.key.set(value);
        Item result = get(this.key);
        if (result != null) {
            return result;
        }
        this.pool.putByte(3).putInt(value);
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key);
        put(result2);
        return result2;
    }

    public int newUTF8(String value) {
        this.key.set(1, value, null, null);
        Item result = get(this.key);
        if (result == null) {
            this.pool.putByte(1).putUTF8(value);
            int i = this.index;
            this.index = i + 1;
            result = new Item(i, this.key);
            put(result);
        }
        return result.index;
    }

    /* access modifiers changed from: package-private */
    public Item newClassItem(String value) {
        this.key2.set(7, value, null, null);
        Item result = get(this.key2);
        if (result != null) {
            return result;
        }
        this.pool.put12(7, newUTF8(value));
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key2);
        put(result2);
        return result2;
    }

    public int newClass(String value) {
        return newClassItem(value).index;
    }

    /* access modifiers changed from: package-private */
    public Item newFieldItem(String owner, String name2, String desc) {
        this.key3.set(9, owner, name2, desc);
        Item result = get(this.key3);
        if (result != null) {
            return result;
        }
        put122(9, newClass(owner), newNameType(name2, desc));
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key3);
        put(result2);
        return result2;
    }

    /* access modifiers changed from: package-private */
    public Item newMethodItem(String owner, String name2, String desc, boolean itf) {
        int type = itf ? 11 : 10;
        this.key3.set(type, owner, name2, desc);
        Item result = get(this.key3);
        if (result != null) {
            return result;
        }
        put122(type, newClass(owner), newNameType(name2, desc));
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key3);
        put(result2);
        return result2;
    }

    private Item newString(String value) {
        this.key2.set(8, value, null, null);
        Item result = get(this.key2);
        if (result != null) {
            return result;
        }
        this.pool.put12(8, newUTF8(value));
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key2);
        put(result2);
        return result2;
    }

    public int newNameType(String name2, String desc) {
        return newNameTypeItem(name2, desc).index;
    }

    /* access modifiers changed from: package-private */
    public Item newNameTypeItem(String name2, String desc) {
        this.key2.set(12, name2, desc, null);
        Item result = get(this.key2);
        if (result != null) {
            return result;
        }
        put122(12, newUTF8(name2), newUTF8(desc));
        int i = this.index;
        this.index = i + 1;
        Item result2 = new Item(i, this.key2);
        put(result2);
        return result2;
    }

    private Item get(Item key4) {
        Item i = this.items[key4.hashCode % this.items.length];
        while (i != null && (i.type != key4.type || !key4.isEqualTo(i))) {
            i = i.next;
        }
        return i;
    }

    private void put(Item i) {
        if (this.index > this.threshold) {
            int ll = this.items.length;
            int nl = (ll * 2) + 1;
            Item[] newItems = new Item[nl];
            for (int l = ll - 1; l >= 0; l--) {
                Item j = this.items[l];
                while (j != null) {
                    int index2 = j.hashCode % newItems.length;
                    Item k = j.next;
                    j.next = newItems[index2];
                    newItems[index2] = j;
                    j = k;
                }
            }
            this.items = newItems;
            this.threshold = (int) (((double) nl) * 0.75d);
        }
        int ll2 = i.hashCode;
        Item[] itemArr = this.items;
        int index3 = ll2 % itemArr.length;
        i.next = itemArr[index3];
        itemArr[index3] = i;
    }

    private void put122(int b, int s1, int s2) {
        this.pool.put12(b, s1).putShort(s2);
    }
}
