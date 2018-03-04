package com.yuxie.myapp.video;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.yuxie.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoListActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_video)
    ListView lvVideo;
    private List<String> alist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("Vip视频");
        init();
    }

    private void init() {
        final List<Map<String, Object>> data1 = getMovieList();
        SimpleAdapter adp2 = new SimpleAdapter(this, data1, R.layout.tab2, new String[]{"img", "name", "content"},
                new int[]{R.id.imageview2, R.id.laji_a2, R.id.tab_showContent2});
        lvVideo.setAdapter(adp2);

        lvVideo.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intenl = new Intent();
            // 设定组件
            ComponentName componentName = new ComponentName(this, PlayActivity.class);
            intenl.setComponent(componentName);
            String url = alist.get(i);
            // 设定参数
            intenl.putExtra("what_web", url);
            startActivity(intenl);
        });

    }

    // 返回电影列表信息
    private List<Map<String, Object>> getMovieList() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        alist = new ArrayList<String>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("img", R.drawable.tenxun);
        map1.put("name", "腾讯视频");
        map1.put("content", "马化腾产业....使用王卡的可以享福啦!!");
        alist.add("http://3g.v.qq.com");

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("img", R.drawable.youku);
        map2.put("name", "优酷视频");
        map2.put("content", "第这世界很酷!!!");
        alist.add("http://m.youku.com");

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("img", R.drawable.iqiyi);
        map3.put("name", "爱奇艺");
        map3.put("content", "爱奇艺,悦享品质");
        alist.add("http://m.iqiyi.com");

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("img", R.drawable.leshi);
        map4.put("name", "乐视视频");
        map4.put("content", "准备关门,又没关门的公司!!!");
        alist.add("http://le.com");

        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("img", R.drawable.manguo);
        map5.put("name", "芒果TV");
        map5.put("content", "喜欢湖南卫视的一定知道这个的呢!!!");
        alist.add("http://m.mgtv.com");

        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("img", R.drawable.souhu);
        map6.put("name", "搜狐视频");
        map6.put("content", "听说很多新闻都在这上面哦!!!");
        alist.add("http://m.tv.sohu.com");

        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);
        data.add(map6);
        return data;
    }


    @OnClick(R.id.rl_left)
    public void onViewClicked() {
        finish();
    }
}
