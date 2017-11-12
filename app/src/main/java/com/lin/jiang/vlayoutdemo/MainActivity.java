package com.lin.jiang.vlayoutdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jianglin
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.main_view);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(virtualLayoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });


        List<LayoutHelper> helpers = new LinkedList<>();

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setItemCount(25);

        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT,
                100, 500);

        helpers.add(DefaultLayoutHelper.newHelper(7));
        helpers.add(scrollFixLayoutHelper);
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);

        virtualLayoutManager.setLayoutHelpers(helpers);

        recyclerView.setAdapter(new VirtualLayoutAdapter(virtualLayoutManager) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(new TextView(MainActivity.this));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                VirtualLayoutManager.LayoutParams params = new VirtualLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 300);
                holder.itemView.setLayoutParams(params);
                ((TextView) holder.itemView).setText(Integer.toString(position));

                if(position == 7) {
                    params.height = 100;
                    params.width = 100;
                } else if(position > 35) {
                    params.height = 200 + (position - 30) * 100;
                }

                if (position > 35) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 30) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa00ff00);
                } else {
                    holder.itemView.setBackgroundColor(0xccff00ff);
                }
            }

            @Override
            public int getItemCount() {
                List<LayoutHelper> helpers = getLayoutHelpers();
                if(helpers == null) {
                    return 0;
                }
                int count = 0;
                for (int i = 0, size = helpers.size(); i < size; i++) {
                    count += helpers.get(i).getItemCount();
                }
                return count;
            }
        });

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(7);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 2000);
    }

    private static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
