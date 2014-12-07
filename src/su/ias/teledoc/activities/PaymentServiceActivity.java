package su.ias.teledoc.activities;

import android.os.Bundle;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.SendSmsFragment;
import su.ias.teledoc.fragments.ThnxFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_PaymentService_LegalPersonalityFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_PaymentService_SelfEmpoyedFragment;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;
import su.ias.teledoc.fragments.select_form.PaymentServiceSelectFormFragment;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.10.2014
 * Time: 11:51
 */

public class PaymentServiceActivity extends AbstractActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setGUI();
        setFormSelectFragment();
    }


    @Override
    public AbstractFormSelectFragment getFormSelectionFragment() {
        return PaymentServiceSelectFormFragment.getInstance();
    }


    @Override
    public int getType() {
        return PAYMENT_SERVICE_TYPE;
    }


    @Override
    public void setGUI() {
        smoothProgressBar.applyStyle(R.style.YellowProgressBar);
        hintTV.setText(getString(R.string.desc_payment));
        resizeSlidingHeader();
        bcg.setBackgroundColor(getResources().getColor(R.color.yellow));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.yellow));
        titleTV.setText(getString(R.string.title_payment));
    }


    @Override
    public void setLegalPersonalityFragment() {
        FillForm_PaymentService_LegalPersonalityFragment fr = new FillForm_PaymentService_LegalPersonalityFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setSelfEmployedFragment() {
        FillForm_PaymentService_SelfEmpoyedFragment fr = new FillForm_PaymentService_SelfEmpoyedFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setNaturalPersonFragment() {
        //nop
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
    public void saveAnswToDB() {

    }
}