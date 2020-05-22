package com.hrg.treelib;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hrg.treelib.adapter.TreeAdapter;
import com.hrg.treelib.adapter.TreeUtils;
import com.hrg.treelib.bean.EquipCategory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EquipCategoryTreeActivity extends AppCompatActivity {

    private ListView listView;
    private EditText et_filter;
    private TextView et_filter_tv;

    private String keyword;
    private TreeAdapter adapter;

    private List<EquipCategory> allCategories = new ArrayList<>();
    private List<EquipCategory> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_category_tree);

        listView();
        initData();
    }

    protected void initData() {
        for (EquipCategory category : MyApplication.getCategories()) {
            category.setExpand(false);
            category.setShow(false);
            allCategories.add(category);

            if (categories.size() == 0) {
                category.setShow(true);
                categories.add(category);
            }
        }

        adapter = new TreeAdapter(this, categories);
        listView.setAdapter(adapter);
    }

    private void listView() {
        listView = findViewById(R.id.device_listview_device);
        et_filter = findViewById(R.id.et_filter);
        et_filter_tv = findViewById(R.id.et_filter_tv);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EquipCategory category = (EquipCategory) categories.get(position);

                if (category.isLeaf()) {
                    // 点击叶子节点
                    Intent data = new Intent();
                    data.putExtra("category", category);
                    setResult(0x1111, data);
                    EquipCategoryTreeActivity.this.finish();
                } else {
                    // 如果点击的是父类
                    category.setExpand(!category.isExpand());

                    expandCategoryData();
                }
            }
        });

        et_filter_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        et_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search();

                return true;
            }
        });
    }

    /**
     * 展开数据
     */
    private void expandCategoryData() {
        categories.clear();

        for (int i = 0; i < allCategories.size(); i++) {
            EquipCategory category = allCategories.get(i);

            EquipCategory parent = TreeUtils.getDeptCar(category.getParentId(), MyApplication.getDeptMap());
            if (parent != null) {
                category.setShow(parent.isExpand());
                if (!category.isShow() && !category.isLeaf()) {
                    category.setExpand(false);
                }
            }

            if (category.isShow()) {
                categories.add(category);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void search() {
        keyword = et_filter.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            setKeyword();
        }
    }

    /**
     * 搜索的时候，先关闭所有的条目，然后，按照条件，找到含有关键字的数据
     * 如果是叶子节点，
     */
    public void setKeyword() {
        allCategories.clear();
        categories.clear();
        for (EquipCategory category : MyApplication.getCategories()) {
            category.setExpand(false);
            category.setShow(false);
            allCategories.add(category);
        }

        Iterator it = allCategories.iterator();
        while (it.hasNext()) {
            EquipCategory category = (EquipCategory) it.next();

            if (category.isLeaf() && category.getName().contains(keyword)) {
                category.setShow(true);
                // 展开从最顶层到该点的所有节点
                openExpand(category);
            }
        }

        for (EquipCategory category : allCategories) {
            if (category.isShow()) {
                categories.add(category);
            }
        }

        adapter.setKeyword(keyword);
    }

    /**
     * 从DepartmentCar开始一直展开到顶部
     *
     * @param cate
     */
    private void openExpand(EquipCategory cate) {
        if (cate.getParentId().isEmpty()) {
            cate.setExpand(true);
            cate.setShow(true);
        } else {
            EquipCategory item = MyApplication.getDeptMap().get(cate.getParentId());
            item.setExpand(true);
            item.setShow(true);
            openExpand(item);
        }
    }
}
