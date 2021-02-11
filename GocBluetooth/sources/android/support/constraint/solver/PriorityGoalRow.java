package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;

public class PriorityGoalRow extends ArrayRow {
    private static final boolean DEBUG = false;
    static final int NOT_FOUND = -1;
    private static final float epsilon = 1.0E-4f;
    private int TABLE_SIZE = 128;
    GoalVariableAccessor accessor;
    private SolverVariable[] arrayGoals;
    Cache mCache;
    private int numGoals;
    private SolverVariable[] sortArray;

    class GoalVariableAccessor implements Comparable {
        PriorityGoalRow row;
        SolverVariable variable;

        public GoalVariableAccessor(PriorityGoalRow row2) {
            this.row = row2;
        }

        public void init(SolverVariable variable2) {
            this.variable = variable2;
        }

        public boolean addToGoal(SolverVariable other, float value) {
            if (this.variable.inGoal) {
                boolean empty = true;
                for (int i = 0; i < 9; i++) {
                    float[] fArr = this.variable.goalStrengthVector;
                    fArr[i] = fArr[i] + (other.goalStrengthVector[i] * value);
                    if (Math.abs(this.variable.goalStrengthVector[i]) < PriorityGoalRow.epsilon) {
                        this.variable.goalStrengthVector[i] = 0.0f;
                    } else {
                        empty = false;
                    }
                }
                if (!empty) {
                    return false;
                }
                PriorityGoalRow.this.removeGoal(this.variable);
                return false;
            }
            for (int i2 = 0; i2 < 9; i2++) {
                float strength = other.goalStrengthVector[i2];
                if (strength != 0.0f) {
                    float v = value * strength;
                    if (Math.abs(v) < PriorityGoalRow.epsilon) {
                        v = 0.0f;
                    }
                    this.variable.goalStrengthVector[i2] = v;
                } else {
                    this.variable.goalStrengthVector[i2] = 0.0f;
                }
            }
            return true;
        }

        public void add(SolverVariable other) {
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.variable.goalStrengthVector;
                fArr[i] = fArr[i] + other.goalStrengthVector[i];
                if (Math.abs(this.variable.goalStrengthVector[i]) < PriorityGoalRow.epsilon) {
                    this.variable.goalStrengthVector[i] = 0.0f;
                }
            }
        }

        public final boolean isNegative() {
            for (int i = 8; i >= 0; i--) {
                float value = this.variable.goalStrengthVector[i];
                if (value > 0.0f) {
                    return false;
                }
                if (value < 0.0f) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isSmallerThan(SolverVariable other) {
            for (int i = 8; i >= 0; i--) {
                float comparedValue = other.goalStrengthVector[i];
                float value = this.variable.goalStrengthVector[i];
                if (value != comparedValue) {
                    if (value < comparedValue) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        public final boolean isNull() {
            for (int i = 0; i < 9; i++) {
                if (this.variable.goalStrengthVector[i] != 0.0f) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object o) {
            return this.variable.id - ((SolverVariable) o).id;
        }

        public void reset() {
            Arrays.fill(this.variable.goalStrengthVector, 0.0f);
        }

        @Override // java.lang.Object
        public String toString() {
            String result = "[ ";
            if (this.variable != null) {
                for (int i = 0; i < 9; i++) {
                    result = result + this.variable.goalStrengthVector[i] + " ";
                }
            }
            return result + "] " + this.variable;
        }
    }

    @Override // android.support.constraint.solver.ArrayRow, android.support.constraint.solver.LinearSystem.Row
    public void clear() {
        this.numGoals = 0;
        this.constantValue = 0.0f;
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        int i = this.TABLE_SIZE;
        this.arrayGoals = new SolverVariable[i];
        this.sortArray = new SolverVariable[i];
        this.numGoals = 0;
        this.accessor = new GoalVariableAccessor(this);
        this.mCache = cache;
    }

    @Override // android.support.constraint.solver.ArrayRow, android.support.constraint.solver.LinearSystem.Row
    public boolean isEmpty() {
        return this.numGoals == 0;
    }

    @Override // android.support.constraint.solver.ArrayRow, android.support.constraint.solver.LinearSystem.Row
    public SolverVariable getPivotCandidate(LinearSystem system, boolean[] avoid) {
        int pivot = -1;
        for (int i = 0; i < this.numGoals; i++) {
            SolverVariable variable = this.arrayGoals[i];
            if (!avoid[variable.id]) {
                this.accessor.init(variable);
                if (pivot == -1) {
                    if (this.accessor.isNegative()) {
                        pivot = i;
                    }
                } else if (this.accessor.isSmallerThan(this.arrayGoals[pivot])) {
                    pivot = i;
                }
            }
        }
        if (pivot == -1) {
            return null;
        }
        return this.arrayGoals[pivot];
    }

    @Override // android.support.constraint.solver.ArrayRow, android.support.constraint.solver.LinearSystem.Row
    public void addError(SolverVariable error) {
        this.accessor.init(error);
        this.accessor.reset();
        error.goalStrengthVector[error.strength] = 1.0f;
        addToGoal(error);
    }

    private final void addToGoal(SolverVariable variable) {
        int i;
        int i2 = this.numGoals + 1;
        SolverVariable[] solverVariableArr = this.arrayGoals;
        if (i2 > solverVariableArr.length) {
            this.arrayGoals = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            SolverVariable[] solverVariableArr2 = this.arrayGoals;
            this.sortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.arrayGoals;
        int i3 = this.numGoals;
        solverVariableArr3[i3] = variable;
        this.numGoals = i3 + 1;
        int i4 = this.numGoals;
        if (i4 > 1 && solverVariableArr3[i4 - 1].id > variable.id) {
            int i5 = 0;
            while (true) {
                i = this.numGoals;
                if (i5 >= i) {
                    break;
                }
                this.sortArray[i5] = this.arrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.sortArray, 0, i, new Comparator<SolverVariable>() {
                /* class android.support.constraint.solver.PriorityGoalRow.AnonymousClass1 */

                public int compare(SolverVariable variable1, SolverVariable variable2) {
                    return variable1.id - variable2.id;
                }
            });
            for (int i6 = 0; i6 < this.numGoals; i6++) {
                this.arrayGoals[i6] = this.sortArray[i6];
            }
        }
        variable.inGoal = true;
        variable.addToRow(this);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void removeGoal(SolverVariable variable) {
        for (int i = 0; i < this.numGoals; i++) {
            if (this.arrayGoals[i] == variable) {
                int j = i;
                while (true) {
                    int i2 = this.numGoals;
                    if (j < i2 - 1) {
                        SolverVariable[] solverVariableArr = this.arrayGoals;
                        solverVariableArr[j] = solverVariableArr[j + 1];
                        j++;
                    } else {
                        this.numGoals = i2 - 1;
                        variable.inGoal = false;
                        return;
                    }
                }
            }
        }
    }

    @Override // android.support.constraint.solver.ArrayRow, android.support.constraint.solver.LinearSystem.Row
    public void updateFromRow(LinearSystem system, ArrayRow definition, boolean removeFromDefinition) {
        SolverVariable goalVariable = definition.variable;
        if (goalVariable != null) {
            ArrayRow.ArrayRowVariables rowVariables = definition.variables;
            int currentSize = rowVariables.getCurrentSize();
            for (int i = 0; i < currentSize; i++) {
                SolverVariable solverVariable = rowVariables.getVariable(i);
                float value = rowVariables.getVariableValue(i);
                this.accessor.init(solverVariable);
                if (this.accessor.addToGoal(goalVariable, value)) {
                    addToGoal(solverVariable);
                }
                this.constantValue += definition.constantValue * value;
            }
            removeGoal(goalVariable);
        }
    }

    @Override // android.support.constraint.solver.ArrayRow
    public String toString() {
        String result = " goal -> (" + this.constantValue + ") : ";
        for (int i = 0; i < this.numGoals; i++) {
            this.accessor.init(this.arrayGoals[i]);
            result = result + this.accessor + " ";
        }
        return result;
    }
}
