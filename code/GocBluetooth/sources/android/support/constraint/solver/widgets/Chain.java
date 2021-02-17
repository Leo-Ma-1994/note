package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import java.util.ArrayList;

public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> widgets, int orientation) {
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
            if (widgets == null || (widgets != null && widgets.contains(first.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:299:0x066e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0682  */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x0687  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x068e  */
    /* JADX WARNING: Removed duplicated region for block: B:309:0x0693  */
    /* JADX WARNING: Removed duplicated region for block: B:311:0x0696  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x06ae  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x06b2  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x06be  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x06c1 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(android.support.constraint.solver.widgets.ConstraintWidgetContainer r45, android.support.constraint.solver.LinearSystem r46, int r47, int r48, android.support.constraint.solver.widgets.ChainHead r49) {
        /*
        // Method dump skipped, instructions count: 1780
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Chain.applyChainConstraints(android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.LinearSystem, int, int, android.support.constraint.solver.widgets.ChainHead):void");
    }
}
