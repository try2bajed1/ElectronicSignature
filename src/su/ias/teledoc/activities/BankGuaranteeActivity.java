package su.ias.teledoc.activities;

import android.os.Bundle;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.SendSmsFragment;
import su.ias.teledoc.fragments.ThnxFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_BankGuarantee_LegalPersonalityFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_BankGuarantee_SelfEmployed_Fragment;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;
import su.ias.teledoc.fragments.select_form.BankGuaranteeSelectFormFragment;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.10.2014
 * Time: 11:51
 */

public class BankGuaranteeActivity extends AbstractActivity {


    /*@Override
    public void setGUI() {

        hintTV.setText(getString(R.string.el_sign_hint));
        bcg.setBackgroundColor(getResources().getColor(R.color.blue));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.blue));
        titleTV.setText(getString(R.string.title_bank_guar));
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setGUI();
        setFormSelectFragment();
    }




    @Override
    public int getType() {
        return BANK_GUARANTEE_TYPE;
    }


    @Override
    public AbstractFormSelectFragment getFormSelectionFragment() {
        return BankGuaranteeSelectFormFragment.getInstance();
    }


/*
    @Override
    public AbstractFillFormFragment getFillFormFragment() {
        return new FillForm_ElectrSign_LegalPersonalityFragment();
    }
*/


    @Override
    public void setLegalPersonalityFragment() {
        FillForm_BankGuarantee_LegalPersonalityFragment fr = new FillForm_BankGuarantee_LegalPersonalityFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setSelfEmployedFragment() {
        FillForm_BankGuarantee_SelfEmployed_Fragment fr = new FillForm_BankGuarantee_SelfEmployed_Fragment();
        replaceFragment(fr,TAG_FORM,true);
    }



    @Override
    public void setNaturalPersonFragment() {
        //nop here
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
    public void setGUI() {
        smoothProgressBar.applyStyle(R.style.BlueProgressBar);

        hintTV.setText(getString(R.string.desc_bank_guarantee));
        resizeSlidingHeader();

        bcg.setBackgroundColor(getResources().getColor(R.color.blue));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.blue));
        titleTV.setText(getString(R.string.title_bank_guar));
    }





    @Override
    public void saveAnswToDB() {

    }


}