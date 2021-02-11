package androidx.constraintlayout.solver.widgets;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.constraintlayout.solver.Cache;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> mChildren = new ArrayList<>();

    public WidgetContainer() {
    }

    public WidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public WidgetContainer(int width, int height) {
        super(width, height);
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void reset() {
        this.mChildren.clear();
        super.reset();
    }

    public void add(ConstraintWidget widget) {
        this.mChildren.add(widget);
        if (widget.getParent() != null) {
            ((WidgetContainer) widget.getParent()).remove(widget);
        }
        widget.setParent(this);
    }

    public void add(ConstraintWidget... widgets) {
        for (ConstraintWidget constraintWidget : widgets) {
            add(constraintWidget);
        }
    }

    public void remove(ConstraintWidget widget) {
        this.mChildren.remove(widget);
        widget.setParent(null);
    }

    public ArrayList<ConstraintWidget> getChildren() {
        return this.mChildren;
    }

    /* JADX INFO: Multiple debug info for r4v0 'this'  androidx.constraintlayout.solver.widgets.WidgetContainer: [D('container' androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer), D('item' androidx.constraintlayout.solver.widgets.ConstraintWidget)] */
    public ConstraintWidgetContainer getRootConstraintContainer() {
        ConstraintWidget parent = getParent();
        ConstraintWidgetContainer container = null;
        if (this instanceof ConstraintWidgetContainer) {
            container = (ConstraintWidgetContainer) this;
        }
        while (parent != null) {
            parent = parent.getParent();
            if (parent instanceof ConstraintWidgetContainer) {
                container = (ConstraintWidgetContainer) parent;
            }
        }
        return container;
    }

    public ConstraintWidget findWidget(float x, float y) {
        ConstraintWidget found = null;
        int l = getDrawX();
        int t = getDrawY();
        int r = getWidth() + l;
        int b = getHeight() + t;
        if (x >= ((float) l) && x <= ((float) r) && y >= ((float) t) && y <= ((float) b)) {
            found = this;
        }
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ConstraintWidget f = ((WidgetContainer) widget).findWidget(x, y);
                if (f != null) {
                    found = f;
                }
            } else {
                int l2 = widget.getDrawX();
                int t2 = widget.getDrawY();
                int r2 = widget.getWidth() + l2;
                int b2 = widget.getHeight() + t2;
                if (x >= ((float) l2) && x <= ((float) r2) && y >= ((float) t2) && y <= ((float) b2)) {
                    found = widget;
                }
            }
        }
        return found;
    }

    public ArrayList<ConstraintWidget> findWidgets(int x, int y, int width, int height) {
        ArrayList<ConstraintWidget> found = new ArrayList<>();
        Rectangle area = new Rectangle();
        area.setBounds(x, y, width, height);
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            Rectangle bounds = new Rectangle();
            bounds.setBounds(widget.getDrawX(), widget.getDrawY(), widget.getWidth(), widget.getHeight());
            if (area.intersects(bounds)) {
                found.add(widget);
            }
        }
        return found;
    }

    public static Rectangle getBounds(ArrayList<ConstraintWidget> widgets) {
        Rectangle bounds = new Rectangle();
        if (widgets.size() == 0) {
            return bounds;
        }
        int minX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int maxX = 0;
        int minY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int maxY = 0;
        int widgetsSize = widgets.size();
        for (int i = 0; i < widgetsSize; i++) {
            ConstraintWidget widget = widgets.get(i);
            if (widget.getX() < minX) {
                minX = widget.getX();
            }
            if (widget.getY() < minY) {
                minY = widget.getY();
            }
            if (widget.getRight() > maxX) {
                maxX = widget.getRight();
            }
            if (widget.getBottom() > maxY) {
                maxY = widget.getBottom();
            }
        }
        bounds.setBounds(minX, minY, maxX - minX, maxY - minY);
        return bounds;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void setOffset(int x, int y) {
        super.setOffset(x, y);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            this.mChildren.get(i).setOffset(getRootX(), getRootY());
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void updateDrawPosition() {
        super.updateDrawPosition();
        ArrayList<ConstraintWidget> arrayList = this.mChildren;
        if (arrayList != null) {
            int count = arrayList.size();
            for (int i = 0; i < count; i++) {
                ConstraintWidget widget = this.mChildren.get(i);
                widget.setOffset(getDrawX(), getDrawY());
                if (!(widget instanceof ConstraintWidgetContainer)) {
                    widget.updateDrawPosition();
                }
            }
        }
    }

    public void layout() {
        updateDrawPosition();
        ArrayList<ConstraintWidget> arrayList = this.mChildren;
        if (arrayList != null) {
            int count = arrayList.size();
            for (int i = 0; i < count; i++) {
                ConstraintWidget widget = this.mChildren.get(i);
                if (widget instanceof WidgetContainer) {
                    ((WidgetContainer) widget).layout();
                }
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void resetSolverVariables(Cache cache) {
        super.resetSolverVariables(cache);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            this.mChildren.get(i).resetSolverVariables(cache);
        }
    }

    public void removeAllChildren() {
        this.mChildren.clear();
    }
}
