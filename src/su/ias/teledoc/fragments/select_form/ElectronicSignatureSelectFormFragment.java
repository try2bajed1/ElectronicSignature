package su.ias.teledoc.fragments.select_form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import su.ias.teledoc.R;
import su.ias.teledoc.activities.AbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */

public class ElectronicSignatureSelectFormFragment extends AbstractFormSelectFragment {

    protected Button legalPersonalityBtn;
    protected Button selfEmployedBtn;
    protected Button naturalPersonBtn;

    public static ElectronicSignatureSelectFormFragment getInstance() {
        //add bundle if needed
        return new ElectronicSignatureSelectFormFragment();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_el_signature, container, false);

        legalPersonalityBtn = (Button) view.findViewById(R.id.legal_personality_btn);
        legalPersonalityBtn.setOnClickListener(legalPersonalityClickListener);


        selfEmployedBtn  = (Button) view.findViewById(R.id.self_employed_btn);
        selfEmployedBtn.setOnClickListener(selfEmpoyedClickListener);


        //тут обработчик класса не стал выносить в родительский класс,
        //тк он встречается только тут
        naturalPersonBtn = (Button) view.findViewById(R.id.natural_person_btn);
        naturalPersonBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AbstractActivity) getActivity()).setNaturalPersonFragment();
                }
        });


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

}