package su.ias.teledoc.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.SendSmsFragment;
import su.ias.teledoc.fragments.ThnxFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_Agent_LegalPersonalityFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_Agent_SelfEmpoyedFragment;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;
import su.ias.teledoc.fragments.select_form.AgentSelectFormFragment;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.10.2014
 * Time: 11:51
 */

public class AgentActivity extends AbstractActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setGUI();
        setFormSelectFragment();
    }


    @Override
    public int getType() {
        return AGENT_TYPE;
    }


    @Override
    public void setLegalPersonalityFragment() {
        FillForm_Agent_LegalPersonalityFragment fr = new FillForm_Agent_LegalPersonalityFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setSelfEmployedFragment() {
        FillForm_Agent_SelfEmpoyedFragment fr = new FillForm_Agent_SelfEmpoyedFragment();
        replaceFragment(fr,TAG_FORM,true);
    }



    @Override
    public void setSendSmsCodeFragment() {
        SendSmsFragment fr = SendSmsFragment.getInstance(getType(), getUserPhoneNum());
        replaceFragment(fr,TAG_CHECK_SMS,true);
    }


    @Override
    public void setThnxFragment() {
        ThnxFragment fr = ThnxFragment.getInstance(getType());
        replaceFragment(fr,TAG_THNX,true);
    }


    @Override
    public void setNaturalPersonFragment() {
        //nop
    }



    @Override
    public AbstractFormSelectFragment getFormSelectionFragment() {
        return AgentSelectFormFragment.getInstance();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void setGUI() {

        smoothProgressBar.applyStyle(R.style.GreyProgressBar);

        hintTV.setText(getString(R.string.desc_agent));
        //после выставления текста ресайз меню
        resizeSlidingHeader();

        bcg.setBackgroundColor(getResources().getColor(R.color.grey));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.grey));
        titleTV.setText(getString(R.string.title_agent));

        titleTV.setTextColor(R.color.text_color);
        hintTV.setTextColor(R.color.text_color);

        phoneBtn.setImageDrawable(getResources().getDrawable(R.drawable.icon_phone_gray));
        backBtn.setImageDrawable(getResources().getDrawable(R.drawable.icon_back_gray));

        ((ImageView) actionBarView.findViewById(R.id.switcher_icon)).setImageDrawable(getResources().getDrawable(R.drawable.arrow_dropdown));
        ((ImageView) mMenuDrawer.findViewById(R.id.close_arrow)).setImageDrawable(getResources().getDrawable(R.drawable.arrow_topbar_gray));
    }



    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }


    @Override
    public void saveAnswToDB() {

    }
}