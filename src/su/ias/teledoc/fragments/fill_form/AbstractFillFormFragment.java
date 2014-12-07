package su.ias.teledoc.fragments.fill_form;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import su.ias.teledoc.Utils;
import su.ias.teledoc.activities.AbstractActivity;
import su.ias.teledoc.activities.TermsActivity;
import su.ias.teledoc.data.PropertyData;
import su.ias.teledoc.maskedEditText.MaskedEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 28.10.2014
 * Time: 10:32
 */


public abstract class AbstractFillFormFragment extends Fragment  {

    protected EditText surnameET;
    protected EditText nameET;
    protected EditText patronymicET;
    protected MaskedEditText phoneNumberET;
    protected EditText emailET;

    protected TextView rulesTV;
    protected CheckBox acceptChBox;
    protected Button sendFilledFormBtn;

    public static PropertyData[] propsTypesArr = new PropertyData[] {new PropertyData(1,"ООО (Общество с ограниченной ответственностью)"),
                                                                     new PropertyData(2,"ОАО (Открытое акционерное общество)"),
                                                                     new PropertyData(4,"ЗАО (Закрытое акционерное общество)")};

    public abstract boolean checkAnswers();



    protected boolean checkET(EditText editText) {
        if(editText == null) {
            Log.e("@", "null");
            return false;
        } else {
            if (editText.getText().toString().trim().isEmpty()) {
                editText.setError("Обязательное поле");
                return false;
            } else {
                return true;
            }
        }
    }


    protected boolean checkPhone() {
        if (phoneNumberET == null) {
            return false;
        } else{
            String trimedPhoneStr = phoneNumberET.getText().toString().replaceAll("[ -]", "");
            if (trimedPhoneStr.isEmpty()) {
                phoneNumberET.setError("Обязательное поле");
                return false;
            }  else {
                String expression = "[\\d]{10}";
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(trimedPhoneStr);
                if(!matcher.matches()){
                    phoneNumberET.setError("Неверный формат телефона");
                    return false;
                } else return true;
            }
        }
    }



    protected boolean checkMail() {
        if(emailET == null){
            return false;
        } else {
            String enteredEMailStr = emailET.getText().toString().trim();
            if (enteredEMailStr.isEmpty()) {
                emailET.setError("Обязательное поле");
                return false;
            } else if (!Utils.validEmail(enteredEMailStr)) {
                emailET.setError("Неверный формат адреса");
                return false;
            } else return true;
        }
    }




    protected void startTermsActivity() {
        startActivity(new Intent(getActivity(), TermsActivity.class).putExtra(AbstractActivity.TYPE,((AbstractActivity)getActivity()).getType()));
    }
}