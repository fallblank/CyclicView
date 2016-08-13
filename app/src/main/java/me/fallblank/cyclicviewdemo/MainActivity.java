package me.fallblank.cyclicviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import me.fallblank.cyclicview.CyclicData;
import me.fallblank.cyclicview.CyclicView;

public class MainActivity extends AppCompatActivity {

    private CyclicView cyclicView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cyclicView = (CyclicView) findViewById(R.id.cyclic_view);

        cyclicView.setScaleType(ImageView.ScaleType.FIT_XY);
        cyclicView.setIndicatorRadius(10);
        cyclicView.setIndicatorSeparation(16);

        List<CyclicData> list = new ArrayList<>();
        list.add(new CyclicData(this,R.drawable.image01,R.string.description_01));
        list.add(new CyclicData(this,R.drawable.image02,R.string.description_02));
        list.add(new CyclicData(this,R.drawable.image03,R.string.description_03));
        list.add(new CyclicData(this,R.drawable.image04,R.string.description_04));

        cyclicView.setData(list);
    }


    @Override protected void onStart() {
        super.onStart();
        cyclicView.startPlay();
    }


    @Override protected void onStop() {
        super.onStop();
        cyclicView.stopPlay();
    }
}
