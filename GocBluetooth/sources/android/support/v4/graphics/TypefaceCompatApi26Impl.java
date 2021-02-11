package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class mFontFamily;
    protected final Constructor mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Method abortCreation;
        Method freeze;
        Method addFontFromBuffer;
        Method addFontFromAssetManager;
        Method addFontFromAssetManager2;
        Constructor fontFamilyCtor;
        Class fontFamily;
        try {
            fontFamily = obtainFontFamily();
            fontFamilyCtor = obtainFontFamilyCtor(fontFamily);
            addFontFromAssetManager2 = obtainAddFontFromAssetManagerMethod(fontFamily);
            addFontFromAssetManager = obtainAddFontFromBufferMethod(fontFamily);
            addFontFromBuffer = obtainFreezeMethod(fontFamily);
            freeze = obtainAbortCreationMethod(fontFamily);
            abortCreation = obtainCreateFromFamiliesWithDefaultMethod(fontFamily);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            abortCreation = null;
            fontFamily = null;
            fontFamilyCtor = null;
            addFontFromAssetManager2 = null;
            addFontFromAssetManager = null;
            addFontFromBuffer = null;
            freeze = null;
        }
        this.mFontFamily = fontFamily;
        this.mFontFamilyCtor = fontFamilyCtor;
        this.mAddFontFromAssetManager = addFontFromAssetManager2;
        this.mAddFontFromBuffer = addFontFromAssetManager;
        this.mFreeze = addFontFromBuffer;
        this.mAbortCreation = freeze;
        this.mCreateFromFamiliesWithDefault = abortCreation;
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style, FontVariationAxis[] axes) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(family, context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), axes)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(family, buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(this.mFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, familyArray, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean freeze(Object family) {
        try {
            return ((Boolean) this.mFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void abortCreation(Object family) {
        try {
            this.mAbortCreation.invoke(family, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        FontResourcesParserCompat.FontFileResourceEntry[] entries = entry.getEntries();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entries) {
            if (!addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), fontFile.getTtcIndex(), fontFile.getWeight(), fontFile.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFile.getVariationSettings()))) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (!freeze(fontFamily)) {
            return null;
        }
        return createFromFamiliesWithDefault(fontFamily);
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:52:0x00aa */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0058, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        if (r0 != null) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        throw r0;
     */
    @Override // android.support.v4.graphics.TypefaceCompatApi21Impl, android.support.v4.graphics.TypefaceCompatBaseImpl
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r20, android.os.CancellationSignal r21, android.support.v4.provider.FontsContractCompat.FontInfo[] r22, int r23) {
        /*
            r19 = this;
            r7 = r19
            r8 = r21
            r9 = r22
            r10 = r23
            int r0 = r9.length
            r1 = 1
            r11 = 0
            if (r0 >= r1) goto L_0x000e
            return r11
        L_0x000e:
            boolean r0 = r19.isFontFamilyPrivateAPIAvailable()
            if (r0 != 0) goto L_0x0068
            android.support.v4.provider.FontsContractCompat$FontInfo r1 = r7.findBestInfo(r9, r10)
            android.content.ContentResolver r2 = r20.getContentResolver()
            android.net.Uri r0 = r1.getUri()     // Catch:{ IOException -> 0x0066 }
            java.lang.String r3 = "r"
            android.os.ParcelFileDescriptor r0 = r2.openFileDescriptor(r0, r3, r8)     // Catch:{ IOException -> 0x0066 }
            r3 = r0
            if (r3 != 0) goto L_0x0032
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0031:
            return r11
        L_0x0032:
            android.graphics.Typeface$Builder r0 = new android.graphics.Typeface$Builder     // Catch:{ all -> 0x0055 }
            java.io.FileDescriptor r4 = r3.getFileDescriptor()     // Catch:{ all -> 0x0055 }
            r0.<init>(r4)     // Catch:{ all -> 0x0055 }
            int r4 = r1.getWeight()     // Catch:{ all -> 0x0055 }
            android.graphics.Typeface$Builder r0 = r0.setWeight(r4)     // Catch:{ all -> 0x0055 }
            boolean r4 = r1.isItalic()     // Catch:{ all -> 0x0055 }
            android.graphics.Typeface$Builder r0 = r0.setItalic(r4)     // Catch:{ all -> 0x0055 }
            android.graphics.Typeface r0 = r0.build()     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0054
            r3.close()
        L_0x0054:
            return r0
        L_0x0055:
            r0 = move-exception
            r4 = r0
            throw r4     // Catch:{ all -> 0x0058 }
        L_0x0058:
            r0 = move-exception
            r5 = r0
            if (r3 == 0) goto L_0x0065
            r3.close()     // Catch:{ all -> 0x0060 }
            goto L_0x0065
        L_0x0060:
            r0 = move-exception
            r6 = r0
            r4.addSuppressed(r6)
        L_0x0065:
            throw r5
        L_0x0066:
            r0 = move-exception
            return r11
        L_0x0068:
            r12 = r20
            java.util.Map r0 = android.support.v4.provider.FontsContractCompat.prepareFontData(r12, r9, r8)
            java.lang.Object r13 = r19.newFamily()
            r1 = 0
            int r14 = r9.length
            r2 = 0
            r16 = r1
            r15 = 0
        L_0x0078:
            if (r15 >= r14) goto L_0x00ad
            r17 = r9[r15]
            android.net.Uri r1 = r17.getUri()
            java.lang.Object r1 = r0.get(r1)
            r18 = r1
            java.nio.ByteBuffer r18 = (java.nio.ByteBuffer) r18
            if (r18 != 0) goto L_0x008b
            goto L_0x00aa
        L_0x008b:
            int r4 = r17.getTtcIndex()
            int r5 = r17.getWeight()
            boolean r6 = r17.isItalic()
            r1 = r19
            r2 = r13
            r3 = r18
            boolean r1 = r1.addFontFromBuffer(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x00a7
            r7.abortCreation(r13)
            return r11
        L_0x00a7:
            r2 = 1
            r16 = r2
        L_0x00aa:
            int r15 = r15 + 1
            goto L_0x0078
        L_0x00ad:
            if (r16 != 0) goto L_0x00b3
            r7.abortCreation(r13)
            return r11
        L_0x00b3:
            boolean r1 = r7.freeze(r13)
            if (r1 != 0) goto L_0x00ba
            return r11
        L_0x00ba:
            android.graphics.Typeface r1 = r7.createFromFamiliesWithDefault(r13)
            android.graphics.Typeface r2 = android.graphics.Typeface.create(r1, r10)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1, null)) {
            abortCreation(fontFamily);
            return null;
        } else if (!freeze(fontFamily)) {
            return null;
        } else {
            return createFromFamiliesWithDefault(fontFamily);
        }
    }

    /* access modifiers changed from: protected */
    public Class obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    /* access modifiers changed from: protected */
    public Constructor obtainFontFamilyCtor(Class fontFamily) throws NoSuchMethodException {
        return fontFamily.getConstructor(new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainAddFontFromAssetManagerMethod(Class fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
    }

    /* access modifiers changed from: protected */
    public Method obtainAddFontFromBufferMethod(Class fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
    }

    /* access modifiers changed from: protected */
    public Method obtainFreezeMethod(Class fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(FREEZE_METHOD, new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainAbortCreationMethod(Class fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainCreateFromFamiliesWithDefaultMethod(Class fontFamily) throws NoSuchMethodException {
        Method m = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(fontFamily, 1).getClass(), Integer.TYPE, Integer.TYPE);
        m.setAccessible(true);
        return m;
    }
}
