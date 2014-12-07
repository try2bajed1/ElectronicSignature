package su.ias.teledoc.activities;

import android.os.Bundle;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.SendSmsFragment;
import su.ias.teledoc.fragments.ThnxFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_ElectrSign_LegalPersonalityFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_ElectrSign_NaturalPerson_Fragment;
import su.ias.teledoc.fragments.fill_form.FillForm_ElectrSign_SelfEmpoyedFragment;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;
import su.ias.teledoc.fragments.select_form.ElectronicSignatureSelectFormFragment;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.10.2014
 * Time: 11:51
 */

public class ElectronicSignatureActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFormSelectFragment();
    }


    @Override
    public int getType() {
        return ELECTRONIC_SIGNATURE_TYPE;
    }


    @Override
    public AbstractFormSelectFragment getFormSelectionFragment() {
        return ElectronicSignatureSelectFormFragment.getInstance();
    }


/*
    @Override
    public AbstractFillFormFragment getFillFormFragment() {
        return new FillForm_ElectrSign_LegalPersonalityFragment();
    }*/


    @Override
    public void setLegalPersonalityFragment() {
        FillForm_ElectrSign_LegalPersonalityFragment fr = new FillForm_ElectrSign_LegalPersonalityFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setSelfEmployedFragment() {
        FillForm_ElectrSign_SelfEmpoyedFragment fr = new FillForm_ElectrSign_SelfEmpoyedFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setNaturalPersonFragment() {
        FillForm_ElectrSign_NaturalPerson_Fragment fr = new FillForm_ElectrSign_NaturalPerson_Fragment();
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
    public void setGUI() {
        smoothProgressBar.applyStyle(R.style.PurpleProgressBar);
        hintTV.setText(getString(R.string.el_sign_hint));
        resizeSlidingHeader();
        bcg.setBackgroundColor(getResources().getColor(R.color.purple));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.purple));
        titleTV.setText(getString(R.string.title_el_sign));
    }





    @Override
    public void saveAnswToDB() {

    }
}