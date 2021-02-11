package com.goodocom.gocsdk.originbt;

import android.database.AbstractCursor;
import android.database.CursorIndexOutOfBoundsException;

public final class EmptyCursor extends AbstractCursor {
    private String[] mColumns;

    public EmptyCursor(String[] columns) {
        this.mColumns = columns;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        return 0;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        return this.mColumns;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String getString(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int column) {
        throw cursorException();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int column) {
        throw cursorException();
    }

    private CursorIndexOutOfBoundsException cursorException() {
        return new CursorIndexOutOfBoundsException("Operation not permitted on an empty cursor.");
    }
}
