package com.recommendation.update_service.dto;

public class UpdateMessage {

    private String target;
    private int updateValue;
    private String updateUnit;
    private String rowId;
    private String columnId;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(int updateValue) {
        this.updateValue = updateValue;
    }

    public String getUpdateUnit() {
        return updateUnit;
    }

    public void setUpdateUnit(String updateUnit) {
        this.updateUnit = updateUnit;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Override
    public String toString() {
        return "UpdateMessage{" +
                "target='" + target + '\'' +
                ", updateValue=" + updateValue +
                ", updateUnit='" + updateUnit + '\'' +
                ", rowId='" + rowId + '\'' +
                ", columnId='" + columnId + '\'' +
                '}';
    }
}
