package com.weisheng.chartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.weisheng.chartdemo.widget.MySumAllLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MySumAllLayout sumLLLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sumLLLayout = findViewById(R.id.ll_sum);
        sumLLLayout.setListData(getShowBeans());
    }

    private List<MySumAllLayout.ShowBean> getShowBeans() {
        List<MySumAllLayout.ShowBean> showBeans = new ArrayList<>();
        MySumAllLayout.ShowBean showBean = new MySumAllLayout.ShowBean();
        showBean.resId = R.id.tv_1;
        showBean.amount = 1.00f;
        showBean.isDouble = true;
        showBean.title = "销售总额";
        ((TextView) sumLLLayout.findViewById(R.id.tv_1)).setText(String.format("%.2f元", showBean
                .amount));

        MySumAllLayout.ShowBean showBean2 = new MySumAllLayout.ShowBean();
        showBean2.resId = R.id.tv_2;
        showBean2.qty = 111;
        showBean2.isDouble = false;
        showBean2.title = "销售数量";
        ((TextView) sumLLLayout.findViewById(R.id.tv_2)).setText(showBean2.qty + "个");

        MySumAllLayout.ShowBean showBean3 = new MySumAllLayout.ShowBean();
        showBean3.resId = R.id.tv_3;
        showBean3.amount =1.00f;
        showBean3.isDouble = true;
        showBean3.title = "退货总额";
        ((TextView) sumLLLayout.findViewById(R.id.tv_3)).setText(String.format("%.2f元",
                showBean3.amount));

        MySumAllLayout.ShowBean showBean4 = new MySumAllLayout.ShowBean();
        showBean4.resId = R.id.tv_4;
        showBean4.qty = 111;
        showBean4.isDouble = false;
        showBean4.title = "退货数量";
        ((TextView) sumLLLayout.findViewById(R.id.tv_4)).setText(showBean4.qty + "个");

        showBeans.add(showBean);
        showBeans.add(showBean2);
        showBeans.add(showBean3);
        showBeans.add(showBean4);

        return showBeans;
    }
}
