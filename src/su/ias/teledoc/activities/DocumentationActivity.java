package su.ias.teledoc.activities;

import android.os.Bundle;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.SendSmsFragment;
import su.ias.teledoc.fragments.ThnxFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_Documentation_LegalPersonalityFragment;
import su.ias.teledoc.fragments.fill_form.FillForm_Documentation_SelfEmployed_Fragment;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;
import su.ias.teledoc.fragments.select_form.ElectronicDocumentationSelectFormFragment;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.10.2014
 * Time: 11:51
 */

public class DocumentationActivity extends AbstractActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFormSelectFragment();
    }



    @Override
    public AbstractFormSelectFragment getFormSelectionFragment() {
        return ElectronicDocumentationSelectFormFragment.getInstance();
    }


    @Override
    public int getType() {
        return ELECTRONIC_DOCUMENTATION_TYPE;
    }


    @Override
    public void setGUI() {
        smoothProgressBar.applyStyle(R.style.GreenProgressBar);
        hintTV.setText(getString(R.string.desc_documentation));
        resizeSlidingHeader();
        bcg.setBackgroundColor(getResources().getColor(R.color.green));
        hintBcg.setBackgroundColor(getResources().getColor(R.color.green));
        titleTV.setText(getString(R.string.title_documents));
    }


    @Override
    public void setLegalPersonalityFragment() {
        FillForm_Documentation_LegalPersonalityFragment fr = new FillForm_Documentation_LegalPersonalityFragment();
        replaceFragment(fr,TAG_FORM,true);
    }


    @Override
    public void setSelfEmployedFragment() {
        FillForm_Documentation_SelfEmployed_Fragment fr = new FillForm_Documentation_SelfEmployed_Fragment();
        replaceFragment(fr,TAG_FORM,true);
    }

    @Override
    public void setNaturalPersonFragment() {

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