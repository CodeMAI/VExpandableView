package mirror.k.vexpandableview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView(){
        VExpandableView expandView = (VExpandableView)findViewById(R.id.expendView);

        final String[] items = new String[]{"J", "底片", "浮雕", "雕刻", "美白", "木刻"};
        expandView.setTextList(items);

        expandView.setOnItemClickListener(new VExpandableView.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(MainActivity.this, items[pos], Toast.LENGTH_SHORT).show();
            }
        });

    }




}
