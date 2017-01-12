package cn.suxiangbao.ofo;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import cn.suxiangbao.ofo.adapter.OFOListAdapter;
import cn.suxiangbao.ofo.db.OFODao;
import cn.suxiangbao.ofo.entity.OFOBike;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private OFODao ofoDao;



    private ListView showDateListView;

    private List<OFOBike> bikeList;

    private OFOListAdapter adapter;
    private SearchView mSearchView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ofoDao = new OFODao(this);
        initComponent();

        bikeList = ofoDao.getAllData();
        if (bikeList != null){
            adapter = new OFOListAdapter(this, bikeList);
            showDateListView.setAdapter(adapter);
        }
    }

    private void initComponent(){

        showDateListView = (ListView)findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item, null), null, false);
    }

    private void refreshOrderList(){
        // 注意：千万不要直接赋值，如：orderList = ordersDao.getAllDate() 此时相当于重新分配了一个内存 原先的内存没改变 所以界面不会有变化
        // Java中的类是地址传递 基本数据才是值传递
        bikeList.clear();
        bikeList.addAll(ofoDao.getAllData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.search:
                Intent i = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(i);
                break;
            case R.id.add:
                Toast.makeText(MainActivity.this,"Add" , Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
