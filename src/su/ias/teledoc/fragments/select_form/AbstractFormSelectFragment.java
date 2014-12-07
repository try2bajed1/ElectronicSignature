package su.ias.teledoc.fragments.select_form;

import android.app.Fragment;
import android.view.View;
import su.ias.teledoc.activities.AbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */


public abstract class AbstractFormSelectFragment extends Fragment {


    public static final int FORM_TYPE_LEGAL_PERSONALITY = 1;
    public static final int FORM_TYPE_SELF_EMPLOYED = 2;
    //public static final int FORM_TYPE_NATURAL_PERSON = 2;





    protected View.OnClickListener legalPersonalityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((AbstractActivity) getActivity()).setLegalPersonalityFragment();
        }
    };


    protected View.OnClickListener selfEmpoyedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((AbstractActivity) getActivity()).setSelfEmployedFragment();
        }
    };

}