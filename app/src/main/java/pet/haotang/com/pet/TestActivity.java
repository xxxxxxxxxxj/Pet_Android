package pet.haotang.com.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pet.haotang.com.pet.base.AppActivity;
import pet.haotang.com.pet.base.BaseFragment;

public class TestActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我是测试页TestActivity");
        openDialog();
        setRight(View.VISIBLE);
        setRightTextView(View.GONE);
        setRightButton(View.VISIBLE);
        closeDialog();
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    public void initParms(Intent intent) {

    }

    @Override
    protected void clickRightTextView() {

    }

    @Override
    protected void clickRightButton() {
        startActivity(TestOneActivity.class);
    }

    @Override
    protected void clickLeftButton() {

    }

    @Override
    public View getContentView() {
        View view = View.inflate(this, R.layout.activity_test, null);
        return view;
    }
}
