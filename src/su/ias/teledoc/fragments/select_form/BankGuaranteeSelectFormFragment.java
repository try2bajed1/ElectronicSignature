package su.ias.teledoc.fragments.select_form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import su.ias.teledoc.R;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */



public class BankGuaranteeSelectFormFragment extends AbstractFormSelectFragment {


    protected Button legalPersonalityBtn;
    protected Button selfEmployedBtn;


    public static BankGuaranteeSelectFormFragment getInstance(){
        //add bundle if needed
        return new BankGuaranteeSelectFormFragment();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_bank_guarantee, container, false);

        legalPersonalityBtn = (Button) view.findViewById(R.id.legal_personality_btn);
        legalPersonalityBtn.setOnClickListener(legalPersonalityClickListener);

        selfEmployedBtn  = (Button) view.findViewById(R.id.self_employed_btn);
        selfEmployedBtn.setOnClickListener(selfEmpoyedClickListener);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

}