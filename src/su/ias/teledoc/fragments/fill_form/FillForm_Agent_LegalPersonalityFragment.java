package su.ias.teledoc.fragments.fill_form;

import android.annotation.SuppressLint;
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


public class FillForm_Agent_LegalPersonalityFragment extends AbstractFillFormFragment implements IListener {

    private Spinner  selectPropertyTypeSpinner;
    private EditText companyNameET;

    private EditText addressET;
    private EditText innET;



    private AbstractActivity activity;
    private TextView usuallyTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void saveToShared() {
        SharedPreferences prefs = AppSingleton.getInstance().prefs;
        prefs.edit().putInt(AbstractActivity.SHARED_SELECTED_PROP_INDEX, selectPropertyTypeSpinner.getSelectedItemPosition())
                .putString(AbstractActivity.SHARED_COMPANY_NAME, companyNameET.getText().toString())
                .putString(AbstractActivity.SHARED_INN,innET.getText().toString())
                .putString(AbstractActivity.SHARED_USER_SURNAME, surnameET.getText().toString())
                .putString(AbstractActivity.SHARED_USER_NAME, nameET.getText().toString())
                .putString(AbstractActivity.SHARED_USER_PATRONYMIC,patronymicET.getText().toString())
                .putString(AbstractActivity.SHARED_PHONE,phoneNumberET.getText().toString())
                .putString(AbstractActivity.SHARED_ADDRESS, addressET.getText().toString())
                .putString(AbstractActivity.SHARED_EMAIL,emailET.getText().toString()).apply();
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int resId = R.layout.fragment_agent_fill_legal_personality;
        View view = inflater.inflate(resId, container, false);

        activity = (AbstractActivity) getActivity();

        selectPropertyTypeSpinner = (Spinner) view.findViewById(R.id.property_type_spinner);
        CardsSpinnerAdapter adapter = new CardsSpinnerAdapter(activity, propsTypesArr);
        selectPropertyTypeSpinner.setAdapter(adapter);

        companyNameET = (EditText) view.findViewById(R.id.company_name);
        surnameET = (EditText) view.findViewById(R.id.surname);
        nameET = (EditText) view.findViewById(R.id.name);
        patronymicET = (EditText) view.findViewById(R.id.patronymic);
        phoneNumberET = (MaskedEditText) view.findViewById(R.id.phone_number);
        emailET = (EditText) view.findViewById(R.id.email);
        addressET = (EditText) view.findViewById(R.id.address);
        innET = (EditText) view.findViewById(R.id.inn);


        SharedPreferences prefs = AppSingleton.getInstance().prefs;

        selectPropertyTypeSpinner.setSelection(prefs.getInt(AbstractActivity.SHARED_SELECTED_PROP_INDEX,0));


        String companySharedStr = prefs.getString(AbstractActivity.SHARED_COMPANY_NAME, "");
        companyNameET.setText(companySharedStr);

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

                    new PostDataTask(FillForm_Agent_LegalPersonalityFragment.this).execute(activity.getType(), PostDataTask.STEP_1, activity.getJsonForGetSmsCode(formattedPhoneStr).toString());
                } else {
                    Toast.makeText(getActivity(), "Пожалуйста, заполните обязательные поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    @Override
    public boolean checkAnswers() {

         boolean isCompanyCorrect = checkET(companyNameET);
         boolean isSurnameCorrect = checkET(surnameET);
         boolean isNameCorrect = checkET(nameET);
         boolean isAddressCorrect = checkET(addressET);
         boolean isPhoneCorrect = checkPhone();
         boolean isMailCorrect = checkMail();
         boolean isINNCorrect = checkINN();

        return isCompanyCorrect
            && isSurnameCorrect
            && isNameCorrect
            && isAddressCorrect
            && isPhoneCorrect
            && isMailCorrect
            && isINNCorrect;


/*        boolean result = true;

        if (companyNameET.getText().toString().trim().isEmpty()) {
            companyNameET.setError("Обязательное поле");
            result = false;
        }

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
        } else if (innET.getText().toString().trim().length()!=10) {
            innET.setError("В значении ИНН должно быть 10 символов");
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




    private boolean checkINN() {
        if (innET.getText().toString().trim().isEmpty()) {
            innET.setError("Обязательное поле");
            return false;
        } else if (innET.getText().toString().trim().length()!=10) {
            innET.setError("В значении ИНН должно быть 10 символов");
            return false;
        } else
            return true;
    }


    /*private void checkTexts(ViewGroup container) {

        Class<?> maskedEditTextClass = MaskedEditText.class;

        for (int i = container.getChildCount() - 1; i >= 0; i--) {
            final View child = container.getChildAt(i);
            if (child instanceof ViewGroup) {
                checkTexts((ViewGroup) child);
            } else {
                if (child instanceof EditText) {
                    String enteredTextStr = "";

                    if (maskedEditTextClass.isInstance(child)) {
                        enteredTextStr = ((EditText) child).getText().toString().replaceAll("[ -]", "");
                    } else {
                        enteredTextStr = ((EditText) child).getText().toString().trim();
                    }

                    if (enteredTextStr.isEmpty()) {
                        ((EditText) child).setError("Нет данных");
                    }
                }
            }
        }
    }*/



    private JSONObject getJsonForBusinessUnit() {

        JSONObject bUnit = new JSONObject();
        try {

            bUnit.put("BuFace",1);
            bUnit.put("BuForm", propsTypesArr[selectPropertyTypeSpinner.getSelectedItemPosition()].getValue());
            bUnit.put("BuName", companyNameET.getText().toString());
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
    public void onResume() {
        super.onResume();

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