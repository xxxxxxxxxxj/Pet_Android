package pet.haotang.com.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pet.haotang.com.pet.base.AppActivity;
import pet.haotang.com.pet.base.BaseFragment;

public class MainToBeauList extends AppActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_beau_list);
    }

    @Override
    public void initParms(Intent intent) {

    }

    @Override
    protected void clickRightTextView() {

    }

    @Override
    protected void clickRightButton() {

    }

    @Override
    protected void clickLeftButton() {

    }

    @Override
    public View getContentView() {
        return null;
    }
}
