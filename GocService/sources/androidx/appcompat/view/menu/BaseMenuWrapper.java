package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.ArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;
import java.util.Iterator;
import java.util.Map;

/* access modifiers changed from: package-private */
public abstract class BaseMenuWrapper {
    final Context mContext;
    private Map<SupportMenuItem, MenuItem> mMenuItems;
    private Map<SupportSubMenu, SubMenu> mSubMenus;

    BaseMenuWrapper(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: package-private */
    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.mMenuItems == null) {
            this.mMenuItems = new ArrayMap();
        }
        MenuItem wrappedItem = this.mMenuItems.get(menuItem);
        if (wrappedItem != null) {
            return wrappedItem;
        }
        MenuItem wrappedItem2 = new MenuItemWrapperICS(this.mContext, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, wrappedItem2);
        return wrappedItem2;
    }

    /* access modifiers changed from: package-private */
    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.mSubMenus == null) {
            this.mSubMenus = new ArrayMap();
        }
        SubMenu wrappedMenu = this.mSubMenus.get(supportSubMenu);
        if (wrappedMenu != null) {
            return wrappedMenu;
        }
        SubMenu wrappedMenu2 = new SubMenuWrapperICS(this.mContext, supportSubMenu);
        this.mSubMenus.put(supportSubMenu, wrappedMenu2);
        return wrappedMenu2;
    }

    /* access modifiers changed from: package-private */
    public final void internalClear() {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map != null) {
            map.clear();
        }
        Map<SupportSubMenu, SubMenu> map2 = this.mSubMenus;
        if (map2 != null) {
            map2.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public final void internalRemoveGroup(int groupId) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map != null) {
            Iterator<SupportMenuItem> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                if (groupId == iterator.next().getGroupId()) {
                    iterator.remove();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void internalRemoveItem(int id) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map != null) {
            Iterator<SupportMenuItem> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                if (id == iterator.next().getItemId()) {
                    iterator.remove();
                    return;
                }
            }
        }
    }
}
