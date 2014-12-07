package su.ias.teledoc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import su.ias.teledoc.IListener;
import su.ias.teledoc.PostDataTask;
import su.ias.teledoc.R;
import su.ias.teledoc.activities.AbstractActivity;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */


public class SendSmsFragment extends Fragment implements IListener {

    private static final String SERVICE_TYPE = "service_type";
    private static final String PHONE_NUM = "phone_num";


    private EditText mobileET;
    private EditText smsCodeET;
    private Button sendSmsCodeBtn;
    private TextView getSmsCodeAgainTV;


    private int serviceType;
    private String userPhoneNumber;
    private String smsCode;


    public static SendSmsFragment getInstance(int serviceType, String userPhoneNumber){

        SendSmsFragment formSelectionFragment = new SendSmsFragment();
        Bundle args = new Bundle();
        args.putInt(SERVICE_TYPE, serviceType);
        args.putString(PHONE_NUM, userPhoneNumber);
        formSelectionFragment.setArguments(args);

        return formSelectionFragment;
    }



    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        serviceType = args.getInt(SERVICE_TYPE, 0);
        userPhoneNumber = args.getString(PHONE_NUM, "");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int resId = R.layout.fragment_check_sms;
        View view = inflater.inflate(resId, container, false);

        mobileET = (EditText) view.findViewById(R.id.phone_number);
        mobileET.setText(userPhoneNumber);

        smsCodeET = (EditText) view.findViewById(R.id.sms_code);
        smsCodeET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendData();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(smsCodeET.getWindowToken(), 0);
                }
                return true;
            }
        });


        getSmsCodeAgainTV = (TextView) view.findViewById(R.id.send_again);
        getSmsCodeAgainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbstractActivity activity = (AbstractActivity) getActivity();

                new PostDataTask(SendSmsFragment.this).execute(activity.getType(),
                                                               PostDataTask.STEP_1,
                                                               activity.getJsonForGetSmsCode(activity.getUserPhoneNum()).toString());
                activity.smoothProgressBar.setVisibility(View.VISIBLE);
            }
        });


        sendSmsCodeBtn = (Button) view.findViewById(R.id.send_btn);
        int pL = sendSmsCodeBtn.getPaddingLeft();
        int pT = sendSmsCodeBtn.getPaddingTop();
        int pR = sendSmsCodeBtn.getPaddingRight();
        int pB = sendSmsCodeBtn.getPaddingBottom();


        sendSmsCodeBtn.setBackgroundDrawable(getResources().getDrawable(getBcgResByType()));
        if (serviceType == AbstractActivity.AGENT_TYPE) {
            sendSmsCodeBtn.setTextColor(getResources().getColor(R.color.text_color));
        }
        sendSmsCodeBtn.setPadding(pL, pT, pR, pB);

        sendSmsCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        return view;
    }



    private int getBcgResByType() {
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




    private void sendData() {
        AbstractActivity activity = (AbstractActivity) getActivity();
        smsCode = smsCodeET.getText().toString();
        activity.smoothProgressBar.setVisibility(View.VISIBLE);

        new PostDataTask(SendSmsFragment.this).execute(activity.getType(),
                PostDataTask.STEP_2,
                activity.getJsonForCheckSmsCode(activity.getUserPhoneNum(), smsCode).toString());
    }



    @Override
    public void onResume() {
        super.onResume();

    }



    @Override
    public void responseCompleteHandler(Integer serviceType, Integer currStep, String jsonFromApi) {

        AbstractActivity activity = (AbstractActivity) getActivity();
        activity.smoothProgressBar.setVisibility(View.GONE);

        if (currStep == PostDataTask.STEP_2) {

//            jsonToPost.put("smsCode", "1942");
            try {
                activity.getResultJson().put("smsCode", smsCode);
                new PostDataTask(SendSmsFragment.this).execute(activity.getType(), PostDataTask.STEP_3, activity.getResultJson().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (currStep == PostDataTask.STEP_3) {
            ((AbstractActivity)getActivity()).setThnxFragment();

            activity.smoothProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void responseErrorHandler(Integer serviceType, Integer currStep, String errorStr) {
        ((AbstractActivity) getActivity()).smoothProgressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Ошибка при передаче данных", Toast.LENGTH_SHORT).show();
    }




}