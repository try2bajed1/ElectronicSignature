package su.ias.teledoc.fragments.fill_form;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;
import su.ias.teledoc.*;
import su.ias.teledoc.activities.AbstractActivity;
import su.ias.teledoc.maskedEditText.MaskedEditText;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */



public class FillForm_Agent_SelfEmpoyedFragment extends AbstractFillFormFragment implements IListener {


    private EditText innET;
    private EditText addressET;

//    private CheckBox acceptChBox;
//    private Button sendFilledFormBtn;
//    private TextView rulesTV;
    private TextView usuallyTV;


/*

    public static FillForm_ElectrSign_LegalPersonality getInstance(int formType){

        FillForm_ElectrSign_LegalPersonality formSelectionFragment = new FillForm_ElectrSign_LegalPersonality();
        Bundle args = new Bundle();
        args.putInt(SELECTED_FORM_TYPE, formType);
        formSelectionFragment.setArguments(args);

        return formSelectionFragment;
    }

*/

    private void saveToShared() {
        SharedPreferences prefs = AppSingleton.getInstance().prefs;
        prefs.edit()
                .putString(AbstractActivity.SHARED_USER_SURNAME, surnameET.getText().toString())
                .putString(AbstractActivity.SHARED_USER_NAME, nameET.getText().toString())
                .putString(AbstractActivity.SHARED_USER_PATRONYMIC,patronymicET.getText().toString())
                .putString(AbstractActivity.SHARED_INN,innET.getText().toString())
                .putString(AbstractActivity.SHARED_ADDRESS, addressET.getText().toString())
                .putString(AbstractActivity.SHARED_PHONE,phoneNumberET.getText().toString())
                .putString(AbstractActivity.SHARED_EMAIL,emailET.getText().toString()).apply();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int resId = R.layout.fragment_agent_fill_self_employed;
        View view = inflater.inflate(resId, container, false);


        surnameET = (EditText) view.findViewById(R.id.surname);
        nameET = (EditText) view.findViewById(R.id.name);
        patronymicET = (EditText) view.findViewById(R.id.patronymic);
        phoneNumberET = (MaskedEditText) view.findViewById(R.id.phone_number);
        emailET = (EditText) view.findViewById(R.id.email);
        innET = (EditText) view.findViewById(R.id.inn);
        addressET = (EditText) view.findViewById(R.id.address);


        SharedPreferences prefs = AppSingleton.getInstance().prefs;

        String inn = prefs.getString(AbstractActivity.SHARED_INN, "");
        innET.setText(inn);

        String surnameShared = prefs.getString(AbstractActivity.SHARED_USER_SURNAME, "");
        surnameET.setText(surnameShared);

        String nameSharedStr = prefs.getString(AbstractActivity.SHARED_USER_NAME, "");
        nameET.setText(nameSharedStr);

        String patrShared = prefs.getString(AbstractActivity.SHARED_USER_PATRONYMIC, "");
        patronymicET.setText(patrShared);

        String adressShared = prefs.getString(AbstractActivity.SHARED_ADDRESS, "");
        addressET.setText(adressShared);

        String phoneSharedStr = prefs.getString(AbstractActivity.SHARED_PHONE, "");
        phoneNumberET.setText(phoneSharedStr);


        String emailShared = prefs.getString(AbstractActivity.SHARED_EMAIL, "");
        emailET.setText(emailShared);


        rulesTV = (TextView) view.findViewById(R.id.terms_clickable);
        rulesTV.setText(Html.fromHtml( "Ознакомлен и согласен с <u><b><font color='#2D6BD8'>Условиями</font></b></u>"));
        rulesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTermsActivity();
                if (!acceptChBox.isChecked()) {
                    acceptChBox.setChecked(true);
                }
            }
        });

        usuallyTV = (TextView) view.findViewById(R.id.usually);
        usuallyTV.setText(Html.fromHtml("Внимание!<br/>Для того, чтобы стать агентом, Вам необходимо оформить <u><b><font color='#2D6BD8'>Электронную подпись</font></b></u> и <u><b><font color='#2D6BD8'>Электронный документооборот</font></b></u>, а также оплатить Личный кабинет"));

        acceptChBox = (CheckBox) view.findViewById(R.id.terms_chbox);
        acceptChBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendFilledFormBtn.setEnabled(isChecked);
                sendFilledFormBtn.setAlpha(isChecked ? 1 : 0.40f);
            }
        });


        sendFilledFormBtn = (Button) view.findViewById(R.id.send_btn);
        sendFilledFormBtn.setEnabled(false);
        sendFilledFormBtn.setAlpha(0.40f);
        sendFilledFormBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                saveToShared();

                if (checkAnswers()) {

                    AbstractActivity activity = (AbstractActivity) getActivity();
                    String phoneStr = phoneNumberET.getText().toString();
                    String formattedPhoneStr = Utils.getCorrectPhoneStr(phoneStr);

                    activity.setUserPhoneNum(formattedPhoneStr);
                    activity.smoothProgressBar.setVisibility(View.VISIBLE);
                    activity.setResultJson(getFilledDataAsJson());

                    new PostDataTask(FillForm_Agent_SelfEmpoyedFragment.this).execute(activity.getType(),
                            PostDataTask.STEP_1,
                            activity.getJsonForGetSmsCode(formattedPhoneStr).toString());
                } else {
                    Toast.makeText(getActivity(),"Пожалуйста, заполните обязательные поля",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }





    @Override
    public void onResume() {
        super.onResume();

    }




    private JSONObject getJsonForBusinessUnit() {

        JSONObject bUnit = new JSONObject();
        try {

            bUnit.put("BuFace",2);
            bUnit.put("BuForm",0);
            bUnit.put("BuName", "");
            bUnit.put("FactAddress", addressET.getText().toString());
            bUnit.put("Inn", innET.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bUnit;
    }



    public JSONObject getFilledDataAsJson() {

        JSONObject jsonToPost = new JSONObject();
        try {

            jsonToPost.put("appTicket", AbstractActivity.APP_KEY);

            jsonToPost.put("businessUnit", getJsonForBusinessUnit());

            JSONObject person = new JSONObject();
            person.put("FirstName", surnameET.getText().toString());
            person.put("SecondName", nameET.getText().toString());
            person.put("LastName", patronymicET.getText().toString());

            String phoneStr = phoneNumberET.getText().toString();
            String formattedPhoneStr = Utils.getCorrectPhoneStr(phoneStr);
            person.put("Mobile", formattedPhoneStr);
            person.put("Email", emailET.getText().toString());

            jsonToPost.put("person", person);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonToPost;
    }



    @Override
    public boolean checkAnswers() {


        boolean isSurnameCorrect = checkET(surnameET);
        boolean isNameCorrect = checkET(nameET);
        boolean isAddressCorrect = checkET(addressET);
        boolean isPhoneCorrect = checkPhone();
        boolean isMailCorrect = checkMail();
        boolean isINNCorrect = checkINN();

        return isSurnameCorrect
            && isNameCorrect
            && isAddressCorrect
            && isPhoneCorrect
            && isMailCorrect
            && isINNCorrect;


        /*boolean result = true;

        if (surnameET.getText().toString().trim().isEmpty()) {
            surnameET.setError("Обязательное поле");
            result = false;
        }

        if (nameET.getText().toString().trim().isEmpty()) {
            nameET.setError("Обязательное поле");
            result = false;
        }

        if (addressET.getText().toString().trim().isEmpty()) {
            addressET.setError("Обязательное поле");
            result = false;
        }


        if (innET.getText().toString().trim().isEmpty()) {
            innET.setError("Обязательное поле");
            result = false;
        } else if (innET.getText().toString().trim().length()!=12) {
            innET.setError("В значении ИНН должно быть 12 символов");
            result = false;
        }


        String trimedPhoneStr = phoneNumberET.getText().toString().replaceAll("[ -]", "");
        if (trimedPhoneStr.isEmpty()) {
            phoneNumberET.setError("Обязательное поле");
            result = false;
        }  else {
            String expression = "[\\d]{10}";
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(trimedPhoneStr);
            if(!matcher.matches()){
                phoneNumberET.setError("Неверный формат телефона");
                result = false;
            }
        }


        String enteredEMailStr = emailET.getText().toString().trim();
        if (enteredEMailStr.isEmpty()) {
            emailET.setError("Обязательное поле");
            result = false;
        } else {
            if(!Utils.validEmail(enteredEMailStr)) {
                emailET.setError("Неверный формат адреса");
                result = Utils.validEmail(enteredEMailStr);
            }
        }

        //return именно в конце, т.к надо проверить все поля
        return result;*/

    }


    // отличается от checkINN в FillForm_Agent_LegalPersonalityFragment
    private boolean checkINN() {
        if (innET.getText().toString().trim().isEmpty()) {
            innET.setError("Обязательное поле");
            return false;
        } else if (innET.getText().toString().trim().length()!=12) {
            innET.setError("В значении ИНН должно быть 12 символов");
            return false;
        } else
            return true;
    }

    @Override
    public void responseCompleteHandler(Integer serviceType, Integer currStep, String jsonFromApi) {
        ((AbstractActivity)getActivity()).setSendSmsCodeFragment();
        ((AbstractActivity) getActivity()).smoothProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void responseErrorHandler(Integer serviceType, Integer currStep, String errorStr) {
        ((AbstractActivity) getActivity()).smoothProgressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(),"Ошибка при передаче данных", Toast.LENGTH_SHORT).show();
    }





}