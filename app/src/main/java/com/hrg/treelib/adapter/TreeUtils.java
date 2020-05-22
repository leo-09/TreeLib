package com.hrg.treelib.adapter;

import android.util.Log;

import com.hrg.treelib.bean.EquipCategory;

import java.util.HashMap;

/**
 *
 */
public class TreeUtils {

    // 第一级别为0
    public static int getLevel(EquipCategory departmentCar, HashMap<String, EquipCategory> map) {
        if (!map.containsKey(departmentCar.getParentId())) {
            return 0;
        } else {
            return 1 + getLevel(getDeptCar(departmentCar.getParentId(), map), map);
        }
    }

    public static EquipCategory getDeptCar(String ID, HashMap<String, EquipCategory> map) {
        if (map.containsKey(ID)) {
            return map.get(ID);
        }

        Log.e("xlc", "ID:" + ID);

        return null;
    }
}
