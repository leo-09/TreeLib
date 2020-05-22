package com.hrg.treelib.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * EquipCategory
 *
 * @author liyy
 * @date 2020/04/07
 */
public class EquipCategory implements Serializable {
    private String id;
    private String parentId;
    private String name;
    private String category;
    private String unit;
    private boolean isLeaf;

    /**
     * 是否展开了 true代表展开，false代表未展开
     */
    private boolean isExpand;
    private boolean isShow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        if (TextUtils.isEmpty(parentId)) {
            return "";
        }

        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isShow() {
        return isShow;
    }
}
