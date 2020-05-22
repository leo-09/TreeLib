package com.hrg.treelib;

import android.app.Application;
import android.text.TextUtils;

import com.hrg.treelib.bean.EquipCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyApplication extends Application {
    private static List<EquipCategory> categories = new ArrayList<>();
    private static HashMap<String, EquipCategory> deptMap = new HashMap<>();

    public static List<EquipCategory> getCategories() {
        return categories;
    }

    public static void setCategories(final List<EquipCategory> categories) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                update(categories);
            }
        }).start();
    }

    private static void update(List<EquipCategory> list) {
        List<EquipCategory> allCategories = new ArrayList<>();

        for (int index = 0; index < 10; index++) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                EquipCategory category = (EquipCategory) it.next();
                if (index == 0) {
                    MyApplication.getDeptMap().put(category.getId(), category);
                }

                if (TextUtils.isEmpty(category.getParentId())) {
                    allCategories.add(category);
                    it.remove();
                } else {
                    boolean exist = false;
                    for (int i = 0; i < allCategories.size(); i++) {
                        EquipCategory item = allCategories.get(i);

                        if (category.getParentId().equals(item.getId())) {
                            allCategories.add(i + 1, category);
                            it.remove();
                            exist = true;
                            break;
                        }
                    }

                    if (!exist) {
                        list.set(list.size() - 1, category);
                    }
                }
            }
        }

        MyApplication.categories = allCategories;
    }

    public static HashMap<String, EquipCategory> getDeptMap() {
        return deptMap;
    }

    public static void setDeptMap(HashMap<String, EquipCategory> deptMap) {
        MyApplication.deptMap = deptMap;
    }
}
