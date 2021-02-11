package androidx.core.content.pm;

import java.util.ArrayList;
import java.util.List;

public abstract class ShortcutInfoCompatSaver<T> {
    public abstract T addShortcuts(List<ShortcutInfoCompat> list);

    public abstract T removeAllShortcuts();

    public abstract T removeShortcuts(List<String> list);

    public List<ShortcutInfoCompat> getShortcuts() throws Exception {
        return new ArrayList();
    }

    public static class NoopImpl extends ShortcutInfoCompatSaver<Void> {
        @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
        public Void addShortcuts(List<ShortcutInfoCompat> list) {
            return null;
        }

        @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
        public Void removeShortcuts(List<String> list) {
            return null;
        }

        @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
        public Void removeAllShortcuts() {
            return null;
        }
    }
}
