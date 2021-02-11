package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

/* access modifiers changed from: package-private */
public class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (!constraintWidgetContainer.optimizeFor(4)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            } else if (!Optimizer.applyChainOptimized(constraintWidgetContainer, system, orientation, offset, first)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:286:0x063a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:290:0x064c  */
    /* JADX WARNING: Removed duplicated region for block: B:291:0x0651  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x0658  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x065d  */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x0660  */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x0674  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0678  */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x0684  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x0687 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r44, androidx.constraintlayout.solver.LinearSystem r45, int r46, int r47, androidx.constraintlayout.solver.widgets.ChainHead r48) {
        /*
        // Method dump skipped, instructions count: 1722
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
