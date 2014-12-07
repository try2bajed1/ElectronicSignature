package su.ias.teledoc.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import su.ias.teledoc.R;
import su.ias.teledoc.activities.AbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */



public class ThnxFragment extends Fragment  {

    private static final String PHONE_NUM = "phone_num";

    private Button closeBtn;
    private TextView moreTV;




    public static ThnxFragment getInstance(int serviceType){

        ThnxFragment formSelectionFragment = new ThnxFragment();
        Bundle args = new Bundle();
        args.putInt(PHONE_NUM, serviceType);
        formSelectionFragment.setArguments(args);

        return formSelectionFragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thnx, container, false);

        moreTV = (TextView) view.findViewById(R.id.more);
        moreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.teledoc.ru"));
                startActivity(browserIntent);
            }
        });

        //todo: customize btn through bundle
        closeBtn = (Button) view.findViewById(R.id.close_btn);
        int pL = closeBtn.getPaddingLeft();
        int pT = closeBtn.getPaddingTop();
        int pR = closeBtn.getPaddingRight();
        int pB = closeBtn.getPaddingBottom();


        closeBtn.setBackgroundDrawable(getResources().getDrawable(getBcgResByType()));

        if(((AbstractActivity) getActivity()).getType() == AbstractActivity.AGENT_TYPE){
            closeBtn.setTextColor(getResources().getColor(R.color.text_color));
        }
        closeBtn.setPadding(pL, pT, pR, pB);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return view;
    }



    private int getBcgResByType() {

        int serviceType = ((AbstractActivity) getActivity()).getType();

        if(serviceType == AbstractActivity.ELECTRONIC_SIGNATURE_TYPE)
            return R.drawable.btn_purple;

        if(serviceType == AbstractActivity.BANK_GUARANTEE_TYPE)
            return R.drawable.btn_blue;

        if(serviceType == AbstractActivity.ELECTRONIC_DOCUMENTATION_TYPE)
            return R.drawable.btn_green;

        if(serviceType == AbstractActivity.PAYMENT_SERVICE_TYPE)
            return R.drawable.btn_yellow;

        if(serviceType == AbstractActivity.AGENT_TYPE)
            return R.drawable.btn_white;

        return R.drawable.btn_purple;
    }


    @Override
    public void onResume() {
        super.onResume();

    }



}