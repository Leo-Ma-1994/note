package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ASMException;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

public class ASMDeserializerFactory implements Opcodes {
    private static final ASMDeserializerFactory instance = new ASMDeserializerFactory();
    private final ASMClassLoader classLoader;
    private final AtomicLong seed;

    public String getGenClassName(Class<?> clazz) {
        return "Fastjson_ASM_" + clazz.getSimpleName() + "_" + this.seed.incrementAndGet();
    }

    public String getGenFieldDeserializer(Class<?> clazz, FieldInfo fieldInfo) {
        return ("Fastjson_ASM__Field_" + clazz.getSimpleName()) + "_" + fieldInfo.getName() + "_" + this.seed.incrementAndGet();
    }

    public ASMDeserializerFactory() {
        this.seed = new AtomicLong();
        this.classLoader = new ASMClassLoader();
    }

    public ASMDeserializerFactory(ClassLoader parentClassLoader) {
        this.seed = new AtomicLong();
        this.classLoader = new ASMClassLoader(parentClassLoader);
    }

    public static final ASMDeserializerFactory getInstance() {
        return instance;
    }

    public boolean isExternalClass(Class<?> clazz) {
        return this.classLoader.isExternalClass(clazz);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0098, code lost:
        if (r3 == null) goto L_0x00a1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.fastjson.parser.deserializer.ObjectDeserializer createJavaBeanDeserializer(com.alibaba.fastjson.parser.ParserConfig r11, java.lang.Class<?> r12, java.lang.reflect.Type r13) throws java.lang.Exception {
        /*
            r10 = this;
            boolean r0 = r12.isPrimitive()
            if (r0 != 0) goto L_0x00c7
            java.lang.String r0 = r10.getGenClassName(r12)
            com.alibaba.fastjson.asm.ClassWriter r1 = new com.alibaba.fastjson.asm.ClassWriter
            r1.<init>()
            r7 = r1
            r2 = 49
            r3 = 33
            r6 = 0
            java.lang.String r5 = "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer"
            r4 = r0
            r1.visit(r2, r3, r4, r5, r6)
            com.alibaba.fastjson.util.DeserializeBeanInfo r1 = com.alibaba.fastjson.util.DeserializeBeanInfo.computeSetters(r12, r13)
            com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context r2 = new com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context
            r3 = 3
            r2.<init>(r0, r11, r1, r3)
            r10._init(r7, r2)
            com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context r2 = new com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context
            r2.<init>(r0, r11, r1, r3)
            r10._createInstance(r7, r2)
            com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context r2 = new com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context
            r3 = 4
            r2.<init>(r0, r11, r1, r3)
            r10._deserialze(r7, r2)
            com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context r2 = new com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context
            r2.<init>(r0, r11, r1, r3)
            r10._deserialzeArrayMapping(r7, r2)
            byte[] r2 = r7.toByteArray()
            java.lang.String r3 = com.alibaba.fastjson.JSON.DUMP_CLASS
            if (r3 == 0) goto L_0x00a1
            r3 = 0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0075 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0075 }
            r5.<init>()     // Catch:{ Exception -> 0x0075 }
            java.lang.String r6 = com.alibaba.fastjson.JSON.DUMP_CLASS     // Catch:{ Exception -> 0x0075 }
            r5.append(r6)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r6 = java.io.File.separator     // Catch:{ Exception -> 0x0075 }
            r5.append(r6)     // Catch:{ Exception -> 0x0075 }
            r5.append(r0)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r6 = ".class"
            r5.append(r6)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0075 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0075 }
            r3 = r4
            r3.write(r2)     // Catch:{ Exception -> 0x0075 }
        L_0x006f:
            r3.close()
            goto L_0x00a1
        L_0x0073:
            r4 = move-exception
            goto L_0x009b
        L_0x0075:
            r4 = move-exception
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r6.<init>()     // Catch:{ all -> 0x0073 }
            java.lang.String r8 = "FASTJSON dump class:"
            r6.append(r8)     // Catch:{ all -> 0x0073 }
            r6.append(r0)     // Catch:{ all -> 0x0073 }
            java.lang.String r8 = "失败:"
            r6.append(r8)     // Catch:{ all -> 0x0073 }
            java.lang.String r8 = r4.getMessage()     // Catch:{ all -> 0x0073 }
            r6.append(r8)     // Catch:{ all -> 0x0073 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0073 }
            r5.println(r6)     // Catch:{ all -> 0x0073 }
            if (r3 == 0) goto L_0x00a1
            goto L_0x006f
        L_0x009b:
            if (r3 == 0) goto L_0x00a0
            r3.close()
        L_0x00a0:
            throw r4
        L_0x00a1:
            com.alibaba.fastjson.util.ASMClassLoader r3 = r10.classLoader
            int r4 = r2.length
            r5 = 0
            java.lang.Class r3 = r3.defineClassPublic(r0, r2, r5, r4)
            r4 = 2
            java.lang.Class[] r6 = new java.lang.Class[r4]
            java.lang.Class<com.alibaba.fastjson.parser.ParserConfig> r8 = com.alibaba.fastjson.parser.ParserConfig.class
            r6[r5] = r8
            java.lang.Class<java.lang.Class> r8 = java.lang.Class.class
            r9 = 1
            r6[r9] = r8
            java.lang.reflect.Constructor r6 = r3.getConstructor(r6)
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r5] = r11
            r4[r9] = r12
            java.lang.Object r4 = r6.newInstance(r4)
            r5 = r4
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r5 = (com.alibaba.fastjson.parser.deserializer.ObjectDeserializer) r5
            return r5
        L_0x00c7:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "not support type :"
            r1.append(r2)
            java.lang.String r2 = r12.getName()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory.createJavaBeanDeserializer(com.alibaba.fastjson.parser.ParserConfig, java.lang.Class, java.lang.reflect.Type):com.alibaba.fastjson.parser.deserializer.ObjectDeserializer");
    }

    /* access modifiers changed from: package-private */
    public void _setFlag(MethodVisitor mw, Context context, int i) {
        String varName = "_asm_flag_" + (i / 32);
        mw.visitVarInsn(21, context.var(varName));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(128);
        mw.visitVarInsn(54, context.var(varName));
    }

    /* access modifiers changed from: package-private */
    public void _isFlag(MethodVisitor mw, Context context, int i, Label label) {
        mw.visitVarInsn(21, context.var("_asm_flag_" + (i / 32)));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(Opcodes.IAND);
        mw.visitJumpInsn(Opcodes.IFEQ, label);
    }

    /* access modifiers changed from: package-private */
    public void _deserialzeArrayMapping(ClassWriter cw, Context context) {
        int fieldListSize;
        char seperator;
        int i;
        int i2;
        MethodVisitor mw = cw.visitMethod(1, "deserialzeArrayMapping", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        defineVarLexer(context, mw);
        _createInstance(context, mw);
        List<FieldInfo> sortedFieldInfoList = context.getBeanInfo().getSortedFieldList();
        int fieldListSize2 = sortedFieldInfoList.size();
        int i3 = 0;
        while (i3 < fieldListSize2) {
            boolean last = i3 == fieldListSize2 + -1;
            char seperator2 = last ? ']' : ',';
            FieldInfo fieldInfo = sortedFieldInfoList.get(i3);
            Class<?> fieldClass = fieldInfo.getFieldClass();
            Type fieldType = fieldInfo.getFieldType();
            if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE) {
                seperator = seperator2;
                fieldListSize = fieldListSize2;
            } else if (fieldClass == Integer.TYPE) {
                seperator = seperator2;
                fieldListSize = fieldListSize2;
            } else {
                if (fieldClass == Long.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator2);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanLong", "(C)J");
                    mw.visitVarInsn(55, context.var(fieldInfo.getName() + "_asm", 2));
                    fieldListSize = fieldListSize2;
                } else {
                    fieldListSize = fieldListSize2;
                    if (fieldClass == Boolean.TYPE) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanBoolean", "(C)Z");
                        mw.visitVarInsn(54, context.var(fieldInfo.getName() + "_asm"));
                    } else if (fieldClass == Float.TYPE) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanFloat", "(C)F");
                        mw.visitVarInsn(56, context.var(fieldInfo.getName() + "_asm"));
                    } else if (fieldClass == Double.TYPE) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanDouble", "(C)D");
                        mw.visitVarInsn(57, context.var(fieldInfo.getName() + "_asm", 2));
                    } else if (fieldClass == Character.TYPE) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanString", "(C)Ljava/lang/String;");
                        mw.visitInsn(3);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C");
                        mw.visitVarInsn(54, context.var(fieldInfo.getName() + "_asm"));
                    } else if (fieldClass == String.class) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanString", "(C)Ljava/lang/String;");
                        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
                    } else if (fieldClass.isEnum()) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(fieldClass)));
                        mw.visitVarInsn(25, 1);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getSymbolTable", "()Lcom/alibaba/fastjson/parser/SymbolTable;");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanEnum", "(Ljava/lang/Class;Lcom/alibaba/fastjson/parser/SymbolTable;C)Ljava/lang/Enum;");
                        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
                        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
                    } else if (Collection.class.isAssignableFrom(fieldClass)) {
                        Class<?> itemClass = getCollectionItemClass(fieldType);
                        if (itemClass == String.class) {
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(fieldClass)));
                            mw.visitVarInsn(16, seperator2);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanStringArray", "(Ljava/lang/Class;C)Ljava/util/Collection;");
                            mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
                        } else {
                            mw.visitVarInsn(25, 1);
                            if (i3 == 0) {
                                i2 = Opcodes.GETSTATIC;
                                mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
                            } else {
                                i2 = Opcodes.GETSTATIC;
                                mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
                            }
                            mw.visitFieldInsn(i2, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "accept", "(II)V");
                            _newCollection(mw, fieldClass);
                            mw.visitInsn(89);
                            mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
                            _getCollectionFieldItemDeser(context, mw, fieldInfo, itemClass);
                            mw.visitVarInsn(25, 1);
                            mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(itemClass)));
                            mw.visitVarInsn(25, 3);
                            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "com/alibaba/fastjson/util/ASMUtils", "parseArray", "(Ljava/util/Collection;Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                        }
                    } else {
                        mw.visitVarInsn(25, 1);
                        if (i3 == 0) {
                            i = Opcodes.GETSTATIC;
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
                        } else {
                            i = Opcodes.GETSTATIC;
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
                        }
                        mw.visitFieldInsn(i, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "accept", "(II)V");
                        _deserObject(context, mw, fieldInfo, fieldClass);
                        mw.visitVarInsn(25, 1);
                        if (!last) {
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
                        } else {
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "RBRACKET", "I");
                            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "EOF", "I");
                        }
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "accept", "(II)V");
                    }
                }
                i3++;
                fieldListSize2 = fieldListSize;
                sortedFieldInfoList = sortedFieldInfoList;
            }
            mw.visitVarInsn(25, context.var("lexer"));
            mw.visitVarInsn(16, seperator);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "scanInt", "(C)I");
            mw.visitVarInsn(54, context.var(fieldInfo.getName() + "_asm"));
            i3++;
            fieldListSize2 = fieldListSize;
            sortedFieldInfoList = sortedFieldInfoList;
        }
        _batchSet(context, mw, false);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitMaxs(5, context.getVariantCount());
        mw.visitEnd();
    }

    /* JADX INFO: Multiple debug info for r7v6 'i'  int: [D('fieldClass' java.lang.Class<?>), D('i' int)] */
    /* JADX INFO: Multiple debug info for r7v7 'i'  int: [D('fieldClass' java.lang.Class<?>), D('i' int)] */
    /* access modifiers changed from: package-private */
    public void _deserialze(ClassWriter cw, Context context) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        Label reset_;
        Label end_;
        int i;
        String str7;
        int i2;
        Label end_2;
        Label return_;
        if (context.getFieldInfoList().size() != 0) {
            for (FieldInfo fieldInfo : context.getFieldInfoList()) {
                Class<?> fieldClass = fieldInfo.getFieldClass();
                Type fieldType = fieldInfo.getFieldType();
                if (fieldClass != Character.TYPE) {
                    if (Collection.class.isAssignableFrom(fieldClass) && !((fieldType instanceof ParameterizedType) && (((ParameterizedType) fieldType).getActualTypeArguments()[0] instanceof Class))) {
                        return;
                    }
                } else {
                    return;
                }
            }
            Collections.sort(context.getFieldInfoList());
            MethodVisitor mw = cw.visitMethod(1, "deserialze", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            Label reset_2 = new Label();
            Label super_ = new Label();
            Label return_2 = new Label();
            Label end_3 = new Label();
            defineVarLexer(context, mw);
            _isEnable(context, mw, Feature.SortFeidFastMatch);
            mw.visitJumpInsn(Opcodes.IFEQ, super_);
            Label next_ = new Label();
            mw.visitVarInsn(25, 0);
            String str8 = "lexer";
            mw.visitVarInsn(25, context.var(str8));
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "isSupportArrayToBean", "(Lcom/alibaba/fastjson/parser/JSONLexer;)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, next_);
            mw.visitVarInsn(25, context.var(str8));
            String str9 = "com/alibaba/fastjson/parser/JSONLexerBase";
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "token", "()I");
            String str10 = "I";
            mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", str10);
            mw.visitJumpInsn(160, next_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, context.getClassName(), "deserialzeArrayMapping", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
            mw.visitInsn(Opcodes.ARETURN);
            mw.visitLabel(next_);
            mw.visitVarInsn(25, context.var(str8));
            mw.visitLdcInsn(context.getClazz().getName());
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanType", "(Ljava/lang/String;)I");
            mw.visitFieldInsn(Opcodes.GETSTATIC, str9, "NOT_MATCH", str10);
            mw.visitJumpInsn(Opcodes.IF_ICMPEQ, super_);
            mw.visitVarInsn(25, 1);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getContext", "()Lcom/alibaba/fastjson/parser/ParseContext;");
            mw.visitVarInsn(58, context.var("mark_context"));
            mw.visitInsn(3);
            mw.visitVarInsn(54, context.var("matchedCount"));
            _createInstance(context, mw);
            mw.visitVarInsn(25, 1);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getContext", "()Lcom/alibaba/fastjson/parser/ParseContext;");
            mw.visitVarInsn(58, context.var("context"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("context"));
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, 3);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "setContext", "(Lcom/alibaba/fastjson/parser/ParseContext;Ljava/lang/Object;Ljava/lang/Object;)Lcom/alibaba/fastjson/parser/ParseContext;");
            mw.visitVarInsn(58, context.var("childContext"));
            mw.visitVarInsn(25, context.var(str8));
            String str11 = "matchStat";
            mw.visitFieldInsn(Opcodes.GETFIELD, str9, str11, str10);
            mw.visitFieldInsn(Opcodes.GETSTATIC, str9, "END", str10);
            mw.visitJumpInsn(Opcodes.IF_ICMPEQ, return_2);
            mw.visitInsn(3);
            mw.visitIntInsn(54, context.var(str11));
            int fieldListSize = context.getFieldInfoList().size();
            for (int i3 = 0; i3 < fieldListSize; i3 += 32) {
                mw.visitInsn(3);
                mw.visitVarInsn(54, context.var("_asm_flag_" + (i3 / 32)));
            }
            int i4 = 0;
            while (true) {
                str = "_asm";
                if (i4 >= fieldListSize) {
                    break;
                }
                FieldInfo fieldInfo2 = context.getFieldInfoList().get(i4);
                Class<?> fieldClass2 = fieldInfo2.getFieldClass();
                if (fieldClass2 == Boolean.TYPE || fieldClass2 == Byte.TYPE || fieldClass2 == Short.TYPE) {
                    return_ = return_2;
                    end_2 = end_3;
                } else if (fieldClass2 == Integer.TYPE) {
                    return_ = return_2;
                    end_2 = end_3;
                } else {
                    if (fieldClass2 == Long.TYPE) {
                        mw.visitInsn(9);
                        StringBuilder sb = new StringBuilder();
                        return_ = return_2;
                        sb.append(fieldInfo2.getName());
                        sb.append(str);
                        mw.visitVarInsn(55, context.var(sb.toString(), 2));
                        end_2 = end_3;
                    } else {
                        return_ = return_2;
                        if (fieldClass2 == Float.TYPE) {
                            mw.visitInsn(11);
                            mw.visitVarInsn(56, context.var(fieldInfo2.getName() + str));
                            end_2 = end_3;
                        } else if (fieldClass2 == Double.TYPE) {
                            mw.visitInsn(14);
                            mw.visitVarInsn(57, context.var(fieldInfo2.getName() + str, 2));
                            end_2 = end_3;
                        } else {
                            if (fieldClass2 == String.class) {
                                Label flagEnd_ = new Label();
                                _isEnable(context, mw, Feature.InitStringFieldAsEmpty);
                                mw.visitJumpInsn(Opcodes.IFEQ, flagEnd_);
                                _setFlag(mw, context, i4);
                                mw.visitLabel(flagEnd_);
                                mw.visitVarInsn(25, context.var(str8));
                                end_2 = end_3;
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "stringDefaultValue", "()Ljava/lang/String;");
                            } else {
                                end_2 = end_3;
                                mw.visitInsn(1);
                            }
                            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass2));
                            mw.visitVarInsn(58, context.var(fieldInfo2.getName() + str));
                        }
                    }
                    i4++;
                    super_ = super_;
                    return_2 = return_;
                    end_3 = end_2;
                }
                mw.visitInsn(3);
                mw.visitVarInsn(54, context.var(fieldInfo2.getName() + str));
                i4++;
                super_ = super_;
                return_2 = return_;
                end_3 = end_2;
            }
            Label end_4 = end_3;
            int i5 = 0;
            while (i5 < fieldListSize) {
                FieldInfo fieldInfo3 = context.getFieldInfoList().get(i5);
                Class<?> fieldClass3 = fieldInfo3.getFieldClass();
                Type fieldType2 = fieldInfo3.getFieldType();
                Label notMatch_ = new Label();
                if (fieldClass3 == Boolean.TYPE) {
                    i2 = i5;
                    mw.visitVarInsn(25, context.var(str8));
                    mw.visitVarInsn(25, 0);
                    String className = context.getClassName();
                    StringBuilder sb2 = new StringBuilder();
                    str3 = str10;
                    sb2.append(fieldInfo3.getName());
                    sb2.append("_asm_prefix__");
                    mw.visitFieldInsn(Opcodes.GETFIELD, className, sb2.toString(), "[C");
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldBoolean", "([C)Z");
                    mw.visitVarInsn(54, context.var(fieldInfo3.getName() + str));
                    str7 = str11;
                } else {
                    str3 = str10;
                    i2 = i5;
                    if (fieldClass3 == Byte.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldInt", "([C)I");
                        mw.visitVarInsn(54, context.var(fieldInfo3.getName() + str));
                        str7 = str11;
                    } else if (fieldClass3 == Short.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldInt", "([C)I");
                        mw.visitVarInsn(54, context.var(fieldInfo3.getName() + str));
                        str7 = str11;
                    } else if (fieldClass3 == Integer.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldInt", "([C)I");
                        mw.visitVarInsn(54, context.var(fieldInfo3.getName() + str));
                        str7 = str11;
                    } else if (fieldClass3 == Long.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldLong", "([C)J");
                        mw.visitVarInsn(55, context.var(fieldInfo3.getName() + str, 2));
                        str7 = str11;
                    } else if (fieldClass3 == Float.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldFloat", "([C)F");
                        mw.visitVarInsn(56, context.var(fieldInfo3.getName() + str));
                        str7 = str11;
                    } else if (fieldClass3 == Double.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldDouble", "([C)D");
                        mw.visitVarInsn(57, context.var(fieldInfo3.getName() + str, 2));
                        str7 = str11;
                    } else if (fieldClass3 == String.class) {
                        Label notEnd_ = new Label();
                        mw.visitIntInsn(21, context.var(str11));
                        mw.visitInsn(7);
                        mw.visitJumpInsn(160, notEnd_);
                        mw.visitVarInsn(25, context.var(str8));
                        str7 = str11;
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "stringDefaultValue", "()Ljava/lang/String;");
                        mw.visitVarInsn(58, context.var(fieldInfo3.getName() + str));
                        mw.visitJumpInsn(Opcodes.GOTO, notMatch_);
                        mw.visitLabel(notEnd_);
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldString", "([C)Ljava/lang/String;");
                        mw.visitVarInsn(58, context.var(fieldInfo3.getName() + str));
                    } else {
                        str7 = str11;
                        if (fieldClass3.isEnum()) {
                            mw.visitVarInsn(25, context.var(str8));
                            mw.visitVarInsn(25, 0);
                            mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                            Label enumNull_ = new Label();
                            mw.visitInsn(1);
                            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass3));
                            mw.visitVarInsn(58, context.var(fieldInfo3.getName() + str));
                            mw.visitVarInsn(25, 1);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getSymbolTable", "()Lcom/alibaba/fastjson/parser/SymbolTable;");
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldSymbol", "([CLcom/alibaba/fastjson/parser/SymbolTable;)Ljava/lang/String;");
                            mw.visitInsn(89);
                            mw.visitVarInsn(58, context.var(fieldInfo3.getName() + "_asm_enumName"));
                            mw.visitJumpInsn(Opcodes.IFNULL, enumNull_);
                            mw.visitVarInsn(25, context.var(fieldInfo3.getName() + "_asm_enumName"));
                            mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.getType(fieldClass3), "valueOf", "(Ljava/lang/String;)" + ASMUtils.getDesc(fieldClass3));
                            mw.visitVarInsn(58, context.var(fieldInfo3.getName() + str));
                            mw.visitLabel(enumNull_);
                        } else {
                            if (Collection.class.isAssignableFrom(fieldClass3)) {
                                mw.visitVarInsn(25, context.var(str8));
                                mw.visitVarInsn(25, 0);
                                mw.visitFieldInsn(Opcodes.GETFIELD, context.getClassName(), fieldInfo3.getName() + "_asm_prefix__", "[C");
                                Class<?> itemClass = getCollectionItemClass(fieldType2);
                                if (itemClass == String.class) {
                                    mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(fieldClass3)));
                                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.getDesc(Collection.class));
                                    mw.visitVarInsn(58, context.var(fieldInfo3.getName() + str));
                                } else {
                                    end_ = end_4;
                                    reset_ = reset_2;
                                    str5 = str;
                                    str3 = str3;
                                    str2 = str9;
                                    str6 = str7;
                                    str4 = str8;
                                    i = i2;
                                    _deserialze_list_obj(context, mw, reset_, fieldInfo3, fieldClass3, itemClass, i);
                                    if (i == fieldListSize - 1) {
                                        _deserialize_endCheck(context, mw, reset_);
                                    }
                                }
                            } else {
                                end_ = end_4;
                                reset_ = reset_2;
                                i = i2;
                                str6 = str7;
                                str2 = str9;
                                str4 = str8;
                                str5 = str;
                                _deserialze_obj(context, mw, reset_, fieldInfo3, fieldClass3, i);
                                if (i == fieldListSize - 1) {
                                    _deserialize_endCheck(context, mw, reset_);
                                }
                            }
                            reset_2 = reset_;
                            str11 = str6;
                            fieldListSize = fieldListSize;
                            str = str5;
                            str8 = str4;
                            str10 = str3;
                            str9 = str2;
                            i5 = i + 1;
                            end_4 = end_;
                        }
                    }
                }
                mw.visitVarInsn(25, context.var(str8));
                mw.visitFieldInsn(Opcodes.GETFIELD, str9, str7, str3);
                Label flag_ = new Label();
                mw.visitJumpInsn(Opcodes.IFLE, flag_);
                _setFlag(mw, context, i2);
                mw.visitLabel(flag_);
                mw.visitVarInsn(25, context.var(str8));
                mw.visitFieldInsn(Opcodes.GETFIELD, str9, str7, str3);
                mw.visitInsn(89);
                mw.visitVarInsn(54, context.var(str7));
                mw.visitFieldInsn(Opcodes.GETSTATIC, str9, "NOT_MATCH", str3);
                reset_ = reset_2;
                mw.visitJumpInsn(Opcodes.IF_ICMPEQ, reset_);
                mw.visitVarInsn(25, context.var(str8));
                mw.visitFieldInsn(Opcodes.GETFIELD, str9, str7, str3);
                mw.visitJumpInsn(Opcodes.IFLE, notMatch_);
                mw.visitVarInsn(21, context.var("matchedCount"));
                mw.visitInsn(4);
                mw.visitInsn(96);
                mw.visitVarInsn(54, context.var("matchedCount"));
                mw.visitVarInsn(25, context.var(str8));
                mw.visitFieldInsn(Opcodes.GETFIELD, str9, str7, str3);
                mw.visitFieldInsn(Opcodes.GETSTATIC, str9, "END", str3);
                end_ = end_4;
                mw.visitJumpInsn(Opcodes.IF_ICMPEQ, end_);
                mw.visitLabel(notMatch_);
                if (i2 == fieldListSize - 1) {
                    str5 = str;
                    mw.visitVarInsn(25, context.var(str8));
                    mw.visitFieldInsn(Opcodes.GETFIELD, str9, str7, str3);
                    mw.visitFieldInsn(Opcodes.GETSTATIC, str9, "END", str3);
                    mw.visitJumpInsn(160, reset_);
                    str3 = str3;
                    str6 = str7;
                    str2 = str9;
                    i = i2;
                    str4 = str8;
                } else {
                    str5 = str;
                    str3 = str3;
                    str6 = str7;
                    str2 = str9;
                    i = i2;
                    str4 = str8;
                }
                reset_2 = reset_;
                str11 = str6;
                fieldListSize = fieldListSize;
                str = str5;
                str8 = str4;
                str10 = str3;
                str9 = str2;
                i5 = i + 1;
                end_4 = end_;
            }
            mw.visitLabel(end_4);
            if (!context.getClazz().isInterface() && !Modifier.isAbstract(context.getClazz().getModifiers())) {
                _batchSet(context, mw);
            }
            mw.visitLabel(return_2);
            _setContext(context, mw);
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitInsn(Opcodes.ARETURN);
            mw.visitLabel(reset_2);
            _batchSet(context, mw);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "parseRest", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(context.getClazz()));
            mw.visitInsn(Opcodes.ARETURN);
            mw.visitLabel(super_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "deserialze", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
            mw.visitInsn(Opcodes.ARETURN);
            mw.visitMaxs(5, context.getVariantCount());
            mw.visitEnd();
        }
    }

    private Class<?> getCollectionItemClass(Type fieldType) {
        if (!(fieldType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
        if (actualTypeArgument instanceof Class) {
            Class<?> itemClass = (Class) actualTypeArgument;
            if (Modifier.isPublic(itemClass.getModifiers())) {
                return itemClass;
            }
            throw new ASMException("can not create ASMParser");
        }
        throw new ASMException("can not create ASMParser");
    }

    private void _isEnable(Context context, MethodVisitor mw, Feature feature) {
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/Feature", feature.name(), "Lcom/alibaba/fastjson/parser/Feature;");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "isEnabled", "(Lcom/alibaba/fastjson/parser/Feature;)Z");
    }

    private void defineVarLexer(Context context, MethodVisitor mw) {
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getLexer", "()Lcom/alibaba/fastjson/parser/JSONLexer;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, "com/alibaba/fastjson/parser/JSONLexerBase");
        mw.visitVarInsn(58, context.var("lexer"));
    }

    private void _createInstance(Context context, MethodVisitor mw) {
        if (Modifier.isPublic(context.getBeanInfo().getDefaultConstructor().getModifiers())) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(context.getClazz()));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(context.getClazz()), "<init>", "()V");
            mw.visitVarInsn(58, context.var("instance"));
            return;
        }
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "createInstance", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;)Ljava/lang/Object;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(context.getClazz()));
        mw.visitVarInsn(58, context.var("instance"));
    }

    private void _batchSet(Context context, MethodVisitor mw) {
        _batchSet(context, mw, true);
    }

    private void _batchSet(Context context, MethodVisitor mw, boolean flag) {
        int size = context.getFieldInfoList().size();
        for (int i = 0; i < size; i++) {
            Label notSet_ = new Label();
            if (flag) {
                _isFlag(mw, context, i, notSet_);
            }
            _loadAndSet(context, mw, context.getFieldInfoList().get(i));
            if (flag) {
                mw.visitLabel(notSet_);
            }
        }
    }

    private void _loadAndSet(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Class<?> fieldClass = fieldInfo.getFieldClass();
        Type fieldType = fieldInfo.getFieldType();
        if (fieldClass == Boolean.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE || fieldClass == Character.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Long.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(22, context.var(fieldInfo.getName() + "_asm", 2));
            if (fieldInfo.getMethod() != null) {
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.getType(context.getClazz()), fieldInfo.getMethod().getName(), ASMUtils.getDesc(fieldInfo.getMethod()));
                if (!fieldInfo.getMethod().getReturnType().equals(Void.TYPE)) {
                    mw.visitInsn(87);
                    return;
                }
                return;
            }
            mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.getType(fieldInfo.getDeclaringClass()), fieldInfo.getField().getName(), ASMUtils.getDesc(fieldInfo.getFieldClass()));
        } else if (fieldClass == Float.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(23, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == Double.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(24, context.var(fieldInfo.getName() + "_asm", 2));
            _set(context, mw, fieldInfo);
        } else if (fieldClass == String.class) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (fieldClass.isEnum()) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        } else if (Collection.class.isAssignableFrom(fieldClass)) {
            mw.visitVarInsn(25, context.var("instance"));
            if (getCollectionItemClass(fieldType) == String.class) {
                mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
                mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
            } else {
                mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
            }
            _set(context, mw, fieldInfo);
        } else {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
            _set(context, mw, fieldInfo);
        }
    }

    private void _set(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        if (fieldInfo.getMethod() != null) {
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.getType(fieldInfo.getDeclaringClass()), fieldInfo.getMethod().getName(), ASMUtils.getDesc(fieldInfo.getMethod()));
            if (!fieldInfo.getMethod().getReturnType().equals(Void.TYPE)) {
                mw.visitInsn(87);
                return;
            }
            return;
        }
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.getType(fieldInfo.getDeclaringClass()), fieldInfo.getField().getName(), ASMUtils.getDesc(fieldInfo.getFieldClass()));
    }

    private void _setContext(Context context, MethodVisitor mw) {
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("context"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "setContext", "(Lcom/alibaba/fastjson/parser/ParseContext;)V");
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitJumpInsn(Opcodes.IFNULL, endIf_);
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/ParseContext", "setObject", "(Ljava/lang/Object;)V");
        mw.visitLabel(endIf_);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor mw, Label reset_) {
        Label _end_if = new Label();
        mw.visitIntInsn(21, context.var("matchedCount"));
        mw.visitJumpInsn(Opcodes.IFLE, reset_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "RBRACE", "I");
        mw.visitJumpInsn(160, reset_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        mw.visitLabel(_end_if);
    }

    private void _deserialze_list_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, Class<?> itemType, int i) {
        Label matched_ = new Label();
        Label _end_if = new Label();
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "matchField", "([C)Z");
        mw.visitJumpInsn(Opcodes.IFNE, matched_);
        mw.visitInsn(1);
        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(matched_);
        _setFlag(mw, context, i);
        Label valueNotNull_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "NULL", "I");
        mw.visitJumpInsn(160, valueNotNull_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        mw.visitInsn(1);
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
        mw.visitLabel(valueNotNull_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "LBRACKET", "I");
        mw.visitJumpInsn(160, reset_);
        _getCollectionFieldItemDeser(context, mw, fieldInfo, itemType);
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/alibaba/fastjson/parser/deserializer/ObjectDeserializer", "getFastMatchToken", "()I");
        mw.visitVarInsn(54, context.var("fastMatchToken"));
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitVarInsn(21, context.var("fastMatchToken"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        _newCollection(mw, fieldClass);
        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getContext", "()Lcom/alibaba/fastjson/parser/ParseContext;");
        mw.visitVarInsn(58, context.var("listContext"));
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
        mw.visitLdcInsn(fieldInfo.getName());
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/alibaba/fastjson/parser/ParseContext;");
        mw.visitInsn(87);
        Label loop_ = new Label();
        Label loop_end_ = new Label();
        mw.visitInsn(3);
        mw.visitVarInsn(54, context.var("i"));
        mw.visitLabel(loop_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "RBRACKET", "I");
        mw.visitJumpInsn(Opcodes.IF_ICMPEQ, loop_end_);
        mw.visitVarInsn(25, 0);
        String className = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className, fieldInfo.getName() + "_asm_list_item_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(itemType)));
        mw.visitVarInsn(21, context.var("i"));
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/alibaba/fastjson/parser/deserializer/ObjectDeserializer", "deserialze", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitVarInsn(58, context.var("list_item_value"));
        mw.visitIincInsn(context.var("i"), 1);
        mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
        mw.visitVarInsn(25, context.var("list_item_value"));
        if (fieldClass.isInterface()) {
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.getType(fieldClass), "add", "(Ljava/lang/Object;)Z");
        } else {
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.getType(fieldClass), "add", "(Ljava/lang/Object;)Z");
        }
        mw.visitInsn(87);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var(fieldInfo.getName() + "_asm"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "checkListResolve", "(Ljava/util/Collection;)V");
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
        mw.visitJumpInsn(160, loop_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitVarInsn(21, context.var("fastMatchToken"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, loop_);
        mw.visitLabel(loop_end_);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("listContext"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "setContext", "(Lcom/alibaba/fastjson/parser/ParseContext;)V");
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "token", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "RBRACKET", "I");
        mw.visitJumpInsn(160, reset_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/JSONToken", "COMMA", "I");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "nextToken", "(I)V");
        mw.visitLabel(_end_if);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String className = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className, fieldInfo.getName() + "_asm_list_item_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getConfig", "()Lcom/alibaba/fastjson/parser/ParserConfig;");
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(itemType)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/ParserConfig", "getDeserializer", "(Ljava/lang/reflect/Type;)Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        String className2 = context.getClassName();
        mw.visitFieldInsn(Opcodes.PUTFIELD, className2, fieldInfo.getName() + "_asm_list_item_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        String className3 = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className3, fieldInfo.getName() + "_asm_list_item_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
    }

    private void _newCollection(MethodVisitor mw, Class<?> fieldClass) {
        if (fieldClass.isAssignableFrom(ArrayList.class)) {
            mw.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(LinkedList.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(LinkedList.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(LinkedList.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(HashSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(HashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(HashSet.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(TreeSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(TreeSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(TreeSet.class), "<init>", "()V");
        } else {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(fieldClass));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(fieldClass), "<init>", "()V");
        }
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
    }

    private void _deserialze_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, int i) {
        Label matched_ = new Label();
        Label _end_if = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitVarInsn(25, 0);
        String className = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className, fieldInfo.getName() + "_asm_prefix__", "[C");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/JSONLexerBase", "matchField", "([C)Z");
        mw.visitJumpInsn(Opcodes.IFNE, matched_);
        mw.visitInsn(1);
        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(matched_);
        _setFlag(mw, context, i);
        mw.visitVarInsn(21, context.var("matchedCount"));
        mw.visitInsn(4);
        mw.visitInsn(96);
        mw.visitVarInsn(54, context.var("matchedCount"));
        _deserObject(context, mw, fieldInfo, fieldClass);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getResolveStatus", "()I");
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/DefaultJSONParser", "NeedToResolve", "I");
        mw.visitJumpInsn(160, _end_if);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getLastResolveTask", "()Lcom/alibaba/fastjson/parser/DefaultJSONParser$ResolveTask;");
        mw.visitVarInsn(58, context.var("resolveTask"));
        mw.visitVarInsn(25, context.var("resolveTask"));
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getContext", "()Lcom/alibaba/fastjson/parser/ParseContext;");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser$ResolveTask", "setOwnerContext", "(Lcom/alibaba/fastjson/parser/ParseContext;)V");
        mw.visitVarInsn(25, context.var("resolveTask"));
        mw.visitVarInsn(25, 0);
        mw.visitLdcInsn(fieldInfo.getName());
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "getFieldDeserializer", "(Ljava/lang/String;)Lcom/alibaba/fastjson/parser/deserializer/FieldDeserializer;");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser$ResolveTask", "setFieldDeserializer", "(Lcom/alibaba/fastjson/parser/deserializer/FieldDeserializer;)V");
        mw.visitVarInsn(25, 1);
        mw.visitFieldInsn(Opcodes.GETSTATIC, "com/alibaba/fastjson/parser/DefaultJSONParser", "NONE", "I");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "setResolveStatus", "(I)V");
        mw.visitLabel(_end_if);
    }

    private void _deserObject(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> fieldClass) {
        _getFieldDeser(context, mw, fieldInfo);
        mw.visitVarInsn(25, 1);
        if (fieldInfo.getFieldType() instanceof Class) {
            mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(fieldInfo.getFieldClass())));
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn(fieldInfo.getName());
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "getFieldType", "(Ljava/lang/String;)Ljava/lang/reflect/Type;");
        }
        mw.visitLdcInsn(fieldInfo.getName());
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/alibaba/fastjson/parser/deserializer/ObjectDeserializer", "deserialze", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
        mw.visitVarInsn(58, context.var(fieldInfo.getName() + "_asm"));
    }

    private void _getFieldDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        String className = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className, fieldInfo.getName() + "_asm_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/DefaultJSONParser", "getConfig", "()Lcom/alibaba/fastjson/parser/ParserConfig;");
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.getDesc(fieldInfo.getFieldClass())));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/ParserConfig", "getDeserializer", "(Ljava/lang/reflect/Type;)Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        String className2 = context.getClassName();
        mw.visitFieldInsn(Opcodes.PUTFIELD, className2, fieldInfo.getName() + "_asm_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        String className3 = context.getClassName();
        mw.visitFieldInsn(Opcodes.GETFIELD, className3, fieldInfo.getName() + "_asm_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;");
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) throws Exception {
        Class<?> fieldClass = fieldInfo.getFieldClass();
        if (fieldClass == Integer.TYPE || fieldClass == Long.TYPE || fieldClass == String.class) {
            return createStringFieldDeserializer(mapping, clazz, fieldInfo);
        }
        return mapping.createFieldDeserializerWithoutASM(mapping, clazz, fieldInfo);
    }

    public FieldDeserializer createStringFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) throws Exception {
        Class<?> superClass;
        int INVAKE_TYPE;
        Class<?> fieldClass = fieldInfo.getFieldClass();
        Method method = fieldInfo.getMethod();
        String className = getGenFieldDeserializer(clazz, fieldInfo);
        ClassWriter cw = new ClassWriter();
        if (fieldClass == Integer.TYPE) {
            superClass = IntegerFieldDeserializer.class;
        } else if (fieldClass == Long.TYPE) {
            superClass = LongFieldDeserializer.class;
        } else {
            superClass = StringFieldDeserializer.class;
        }
        if (clazz.isInterface()) {
            INVAKE_TYPE = 185;
        } else {
            INVAKE_TYPE = 182;
        }
        cw.visit(49, 33, className, ASMUtils.getType(superClass), null);
        MethodVisitor mw = cw.visitMethod(1, "<init>", "(Lcom/alibaba/fastjson/parser/ParserConfig;Ljava/lang/Class;Lcom/alibaba/fastjson/util/FieldInfo;)V", null, null);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(superClass), "<init>", "(Lcom/alibaba/fastjson/parser/ParserConfig;Ljava/lang/Class;Lcom/alibaba/fastjson/util/FieldInfo;)V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(4, 6);
        mw.visitEnd();
        if (method != null) {
            if (fieldClass == Integer.TYPE) {
                MethodVisitor mw2 = cw.visitMethod(1, "setValue", "(Ljava/lang/Object;I)V", null, null);
                mw2.visitVarInsn(25, 1);
                mw2.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(method.getDeclaringClass()));
                mw2.visitVarInsn(21, 2);
                mw2.visitMethodInsn(INVAKE_TYPE, ASMUtils.getType(method.getDeclaringClass()), method.getName(), ASMUtils.getDesc(method));
                mw2.visitInsn(Opcodes.RETURN);
                mw2.visitMaxs(3, 3);
                mw2.visitEnd();
            } else if (fieldClass == Long.TYPE) {
                MethodVisitor mw3 = cw.visitMethod(1, "setValue", "(Ljava/lang/Object;J)V", null, null);
                mw3.visitVarInsn(25, 1);
                mw3.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(method.getDeclaringClass()));
                mw3.visitVarInsn(22, 2);
                mw3.visitMethodInsn(INVAKE_TYPE, ASMUtils.getType(method.getDeclaringClass()), method.getName(), ASMUtils.getDesc(method));
                mw3.visitInsn(Opcodes.RETURN);
                mw3.visitMaxs(3, 4);
                mw3.visitEnd();
            } else {
                MethodVisitor mw4 = cw.visitMethod(1, "setValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", null, null);
                mw4.visitVarInsn(25, 1);
                mw4.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(method.getDeclaringClass()));
                mw4.visitVarInsn(25, 2);
                mw4.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.getType(fieldClass));
                mw4.visitMethodInsn(INVAKE_TYPE, ASMUtils.getType(method.getDeclaringClass()), method.getName(), ASMUtils.getDesc(method));
                mw4.visitInsn(Opcodes.RETURN);
                mw4.visitMaxs(3, 3);
                mw4.visitEnd();
            }
        }
        byte[] code = cw.toByteArray();
        return (FieldDeserializer) this.classLoader.defineClassPublic(className, code, 0, code.length).getConstructor(ParserConfig.class, Class.class, FieldInfo.class).newInstance(mapping, clazz, fieldInfo);
    }

    /* access modifiers changed from: package-private */
    public static class Context {
        private final DeserializeBeanInfo beanInfo;
        private String className;
        private Class<?> clazz;
        private List<FieldInfo> fieldInfoList;
        private int variantIndex = 5;
        private Map<String, Integer> variants = new HashMap();

        public Context(String className2, ParserConfig config, DeserializeBeanInfo beanInfo2, int initVariantIndex) {
            this.className = className2;
            this.clazz = beanInfo2.getClazz();
            this.variantIndex = initVariantIndex;
            this.beanInfo = beanInfo2;
            this.fieldInfoList = new ArrayList(beanInfo2.getFieldList());
        }

        public String getClassName() {
            return this.className;
        }

        public List<FieldInfo> getFieldInfoList() {
            return this.fieldInfoList;
        }

        public DeserializeBeanInfo getBeanInfo() {
            return this.beanInfo;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }

        public int getVariantCount() {
            return this.variantIndex;
        }

        public int var(String name, int increment) {
            if (this.variants.get(name) == null) {
                this.variants.put(name, Integer.valueOf(this.variantIndex));
                this.variantIndex += increment;
            }
            return this.variants.get(name).intValue();
        }

        public int var(String name) {
            if (this.variants.get(name) == null) {
                Map<String, Integer> map = this.variants;
                int i = this.variantIndex;
                this.variantIndex = i + 1;
                map.put(name, Integer.valueOf(i));
            }
            return this.variants.get(name).intValue();
        }
    }

    private void _init(ClassWriter cw, Context context) {
        int size = context.getFieldInfoList().size();
        for (int i = 0; i < size; i++) {
            cw.visitField(1, context.getFieldInfoList().get(i).getName() + "_asm_prefix__", "[C").visitEnd();
        }
        int size2 = context.getFieldInfoList().size();
        for (int i2 = 0; i2 < size2; i2++) {
            FieldInfo fieldInfo = context.getFieldInfoList().get(i2);
            Class<?> fieldClass = fieldInfo.getFieldClass();
            if (!fieldClass.isPrimitive() && !fieldClass.isEnum()) {
                if (Collection.class.isAssignableFrom(fieldClass)) {
                    cw.visitField(1, fieldInfo.getName() + "_asm_list_item_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;").visitEnd();
                } else {
                    cw.visitField(1, fieldInfo.getName() + "_asm_deser__", "Lcom/alibaba/fastjson/parser/deserializer/ObjectDeserializer;").visitEnd();
                }
            }
        }
        MethodVisitor mw = cw.visitMethod(1, "<init>", "(Lcom/alibaba/fastjson/parser/ParserConfig;Ljava/lang/Class;)V", null, null);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "<init>", "(Lcom/alibaba/fastjson/parser/ParserConfig;Ljava/lang/Class;)V");
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, "com/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer", "serializer", "Lcom/alibaba/fastjson/parser/deserializer/ASMJavaBeanDeserializer$InnerJavaBeanDeserializer;");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/alibaba/fastjson/parser/deserializer/JavaBeanDeserializer", "getFieldDeserializerMap", "()Ljava/util/Map;");
        mw.visitInsn(87);
        int size3 = context.getFieldInfoList().size();
        for (int i3 = 0; i3 < size3; i3++) {
            FieldInfo fieldInfo2 = context.getFieldInfoList().get(i3);
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn("\"" + fieldInfo2.getName() + "\":");
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C");
            String className = context.getClassName();
            mw.visitFieldInsn(Opcodes.PUTFIELD, className, fieldInfo2.getName() + "_asm_prefix__", "[C");
        }
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(4, 4);
        mw.visitEnd();
    }

    private void _createInstance(ClassWriter cw, Context context) {
        MethodVisitor mw = cw.visitMethod(1, "createInstance", "(Lcom/alibaba/fastjson/parser/DefaultJSONParser;Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
        mw.visitTypeInsn(Opcodes.NEW, ASMUtils.getType(context.getClazz()));
        mw.visitInsn(89);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.getType(context.getClazz()), "<init>", "()V");
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitMaxs(3, 3);
        mw.visitEnd();
    }
}
