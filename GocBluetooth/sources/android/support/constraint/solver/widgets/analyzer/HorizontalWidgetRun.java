package android.support.constraint.solver.widgets.analyzer;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.Helper;
import android.support.constraint.solver.widgets.analyzer.DependencyNode;
import android.support.constraint.solver.widgets.analyzer.WidgetRun;

public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget widget) {
        super(widget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getWidth());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent2 = this.widget.getParent()) != null && parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int resolvedDimension = (parent2.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(resolvedDimension);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent = this.widget.getParent()) != null && parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
        }
        if (!this.dimension.resolved || !this.widget.measured) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                int i = this.widget.mMatchConstraintDefaultWidth;
                if (i == 2) {
                    ConstraintWidget parent3 = this.widget.getParent();
                    if (parent3 != null) {
                        DimensionDependency dimensionDependency = parent3.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency);
                        dimensionDependency.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                    }
                } else if (i == 3) {
                    if (this.widget.mMatchConstraintDefaultHeight == 3) {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        this.widget.verticalRun.start.updateDelegate = this;
                        this.widget.verticalRun.end.updateDelegate = this;
                        this.dimension.updateDelegate = this;
                        if (this.widget.isInVerticalChain()) {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            this.widget.verticalRun.dimension.updateDelegate = this;
                            this.dimension.targets.add(this.widget.verticalRun.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                        } else if (this.widget.isInHorizontalChain()) {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                            this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                        } else {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                        }
                    } else {
                        DimensionDependency dimensionDependency2 = this.widget.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency2);
                        dimensionDependency2.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        this.end.targets.add(this.dimension);
                    }
                }
            }
            if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
                if (this.widget.mListAnchors[0].mTarget != null) {
                    DependencyNode target = getTarget(this.widget.mListAnchors[0]);
                    if (target != null) {
                        addTarget(this.start, target, this.widget.mListAnchors[0].getMargin());
                        addTarget(this.end, this.start, 1, this.dimension);
                    }
                } else if (this.widget.mListAnchors[1].mTarget != null) {
                    DependencyNode target2 = getTarget(this.widget.mListAnchors[1]);
                    if (target2 != null) {
                        addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                        addTarget(this.start, this.end, -1, this.dimension);
                    }
                } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
                    addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                    addTarget(this.end, this.start, 1, this.dimension);
                }
            } else if (this.widget.isInHorizontalChain()) {
                this.start.margin = this.widget.mListAnchors[0].getMargin();
                this.end.margin = -this.widget.mListAnchors[1].getMargin();
            } else {
                DependencyNode startTarget = getTarget(this.widget.mListAnchors[0]);
                DependencyNode endTarget = getTarget(this.widget.mListAnchors[1]);
                startTarget.addDependency(this);
                endTarget.addDependency(this);
                this.mRunType = WidgetRun.RunType.CENTER;
            }
        } else if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
            if (this.widget.mListAnchors[0].mTarget != null) {
                DependencyNode target3 = getTarget(this.widget.mListAnchors[0]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.widget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.dimension.value);
                }
            } else if (this.widget.mListAnchors[1].mTarget != null) {
                DependencyNode target4 = getTarget(this.widget.mListAnchors[1]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.dimension.value);
                }
            } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                addTarget(this.end, this.start, this.dimension.value);
            }
        } else if (this.widget.isInHorizontalChain()) {
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
            DependencyNode startTarget2 = getTarget(this.widget.mListAnchors[0]);
            if (startTarget2 != null) {
                addTarget(this.start, startTarget2, this.widget.mListAnchors[0].getMargin());
            }
            DependencyNode endTarget2 = getTarget(this.widget.mListAnchors[1]);
            if (endTarget2 != null) {
                addTarget(this.end, endTarget2, -this.widget.mListAnchors[1].getMargin());
            }
            this.start.delegateToWidgetRun = true;
            this.end.delegateToWidgetRun = true;
        }
    }

    private void computeInsetRatio(int[] dimensions, int x1, int x2, int y1, int y2, float ratio, int side) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        if (side == -1) {
            int candidateX1 = (int) ((((float) dy) * ratio) + 0.5f);
            int candidateY2 = (int) ((((float) dx) / ratio) + 0.5f);
            if (candidateX1 <= dx && dy <= dy) {
                dimensions[0] = candidateX1;
                dimensions[1] = dy;
            } else if (dx <= dx && candidateY2 <= dy) {
                dimensions[0] = dx;
                dimensions[1] = candidateY2;
            }
        } else if (side == 0) {
            dimensions[0] = (int) ((((float) dy) * ratio) + 0.5f);
            dimensions[1] = dy;
        } else if (side == 1) {
            dimensions[0] = dx;
            dimensions[1] = (int) ((((float) dx) * ratio) + 0.5f);
        }
    }

    /* renamed from: android.support.constraint.solver.widgets.analyzer.HorizontalWidgetRun$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$support$constraint$solver$widgets$analyzer$WidgetRun$RunType = new int[WidgetRun.RunType.values().length];

        static {
            try {
                $SwitchMap$android$support$constraint$solver$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Multiple debug info for r1v24 int: [D('dy' int), D('dx' int)] */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x032c, code lost:
        if (r7 != 1) goto L_0x03a0;
     */
    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun, android.support.constraint.solver.widgets.analyzer.Dependency
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(android.support.constraint.solver.widgets.analyzer.Dependency r26) {
        /*
        // Method dump skipped, instructions count: 1233
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.analyzer.HorizontalWidgetRun.update(android.support.constraint.solver.widgets.analyzer.Dependency):void");
    }

    @Override // android.support.constraint.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.start.resolved) {
            this.widget.setX(this.start.value);
        }
    }
}
