package su.ias.teledoc.activities;

import android.app.*;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.view.Display;
import android.view.View;
import android.widget.*;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import org.json.JSONException;
import org.json.JSONObject;
import su.ias.teledoc.R;
import su.ias.teledoc.fragments.select_form.AbstractFormSelectFragment;



@SuppressWarnings("ALL")
public abstract class AbstractActivity extends Activity {

    public final static String APP_KEY = "XDD";

    public static final String TYPE = "type";

    public static final int ELECTRONIC_SIGNATURE_TYPE = 0;
    public static final int BANK_GUARANTEE_TYPE = 1;
    public static final int ELECTRONIC_DOCUMENTATION_TYPE = 2;
    public static final int PAYMENT_SERVICE_TYPE = 3;
    public static final int AGENT_TYPE = 4;


    public final static String TAG_SELECT_FORM_TYPE = "TAG_SELECT_FORM_TYPE";
    public final static String TAG_FORM = "TAG_FORM";
    public final static String TAG_CHECK_SMS = "TAG_CHECK_SMS";
    public final static String TAG_THNX = "TAG_THNX";



    public final static String SHARED_SELECTED_PROP_INDEX = "SHARED_SELECTED_PROP_INDEX";
    public final static String SHARED_COMPANY_NAME = "SHARED_COMPANY_NAME";
    public final static String SHARED_USER_NAME = "SHARED_USER_NAME";
    public final static String SHARED_USER_SURNAME = "SHARED_USER_SURNAME";
    public final static String SHARED_USER_PATRONYMIC = "SHARED_USER_PATRONYMIC";
    public final static String SHARED_PHONE = "SHARED_PHONE";
    public final static String SHARED_EMAIL = "SHARED_EMAIL";
    public final static String SHARED_CITY = "SHARED_CITY";
    public final static String SHARED_ADDRESS = "SHARED_ADDRESS";
    public final static String SHARED_GUARANTEE_PERIOD = "SHARED_GUARANTEE_PERIOD";
    public final static String SHARED_GUARANTEE_SUMM = "SHARED_GUARANTEE_SUMM";
    public final static String SHARED_INN = "SHARED_INN";



    protected MenuDrawer mMenuDrawer;
    public FragmentManager fragmentManager;
    protected RelativeLayout framesContainer;
    protected int framesContainerId;

    protected ActionBar actionBar;
    protected TextView titleTV;
    protected ImageView dropDownIcon;
    protected View actionBarView;

    protected RelativeLayout bcg;
    protected ImageButton backBtn;
    protected ImageButton phoneBtn;

    protected TextView hintTV;
    protected LinearLayout hintBcg;

    public SmoothProgressBar smoothProgressBar;

    private String userPhoneNum;

    private JSONObject resultJson;
    private JSONObject personInfoJson;
    private JSONObject buFaceJson;

    private String recievedSmsCode;

    public abstract void setGUI();

    public abstract void setLegalPersonalityFragment();
    public abstract void setSelfEmployedFragment();
    public abstract void setNaturalPersonFragment();

    public abstract void setSendSmsCodeFragment();
    public abstract void setThnxFragment();

    public abstract void saveAnswToDB();

    public abstract AbstractFormSelectFragment getFormSelectionFragment();
    public abstract int getType();



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_back_custom_view);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBarView = actionBar.getCustomView();

        dropDownIcon = (ImageView) findViewById(R.id.switcher_icon);
        bcg  =  (RelativeLayout) actionBarView.findViewById(R.id.bcg);
        backBtn  = (ImageButton) actionBarView.findViewById(R.id.back_btn);
        phoneBtn = (ImageButton) actionBarView.findViewById(R.id.phone_btn);
        titleTV  = (TextView)    actionBarView.findViewById(R.id.title);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                onBackPressed();
            }
        });


        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:88007003922")));
            }
        });


        actionBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMenuDrawer.isMenuVisible())
                     mMenuDrawer.openMenu(true);
                else mMenuDrawer.closeMenu();
            }
        });


        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT, Position.TOP);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_BEZEL);
        mMenuDrawer.setTouchBezelSize(100);

        mMenuDrawer.setContentView(R.layout.layout_body);
        mMenuDrawer.setMenuView(R.layout.layout_header);

        mMenuDrawer.findViewById(R.id.hint_bcg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuDrawer.closeMenu();
            }
        });



        mMenuDrawer.setOffsetMenuEnabled(false);
        mMenuDrawer.setDropShadowEnabled(false);

        mMenuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {

                // Log.i("@","old state "+oldState +" new state "+ newState);
                if (oldState == MenuDrawer.STATE_CLOSED ) {
                    dropDownIcon.setVisibility(View.GONE);
                }

                if (newState == MenuDrawer.STATE_CLOSED) {
                    dropDownIcon.setVisibility(View.VISIBLE);
                }
            }
        });


        hintTV = (TextView) mMenuDrawer.findViewById(R.id.hint_text);
        hintBcg = (LinearLayout) mMenuDrawer.findViewById(R.id.hint_bcg);

        fragmentManager = getFragmentManager();
        framesContainer = (RelativeLayout) mMenuDrawer.findViewById(R.id.fragment_container);
        framesContainerId = framesContainer.getId();

        smoothProgressBar = (SmoothProgressBar) findViewById(R.id.smooth_pb);

//        smoothProgressBar.setProgressiveStartActivated();
        smoothProgressBar.progressiveStart();

        setGUI();

    }



    // т.к wrap content у этой библиотеки работает странно, вычисляем высоту текстового поля и
    // выставляем для меню высоту вручную (с учетом паддингов)
    protected void resizeSlidingHeader() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - hintTV.getPaddingLeft()-hintTV.getPaddingRight();

        StaticLayout measure = new StaticLayout(hintTV.getText(), hintTV.getPaint(), width, Layout.Alignment.ALIGN_NORMAL, 2f, 1.0f, false);
        mMenuDrawer.setMenuSize(measure.getHeight() + hintTV.getPaddingTop() + hintTV.getPaddingBottom());

    }



    @Override
    public void onBackPressed() {

        if (fragmentManager.findFragmentByTag(TAG_THNX) != null) {
            //super.onBackPressed();
            finish();
        } else {
             super.onBackPressed();
        }
    }


    public void setFormSelectFragment() {
        replaceFragment(getFormSelectionFragment(),TAG_SELECT_FORM_TYPE, false);
    }



    protected void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(framesContainerId, fragment, tag);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commit();
    }



    //format  +7 (926) 379-05-60
    public JSONObject getJsonForCheckSmsCode(String formattedPhone, String smsCode) {

        JSONObject jsonToPost = new JSONObject();
        try {
            jsonToPost.put("appTicket", APP_KEY);
            jsonToPost.put("phoneNumber", formattedPhone);
            jsonToPost.put("code", smsCode);
            jsonToPost.put("type", "18");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonToPost;
    }


    public JSONObject getJsonForGetSmsCode(String formattedPhone) {

        JSONObject jsonToPost = new JSONObject();
        try {
            jsonToPost.put("phoneNumber", formattedPhone);
            jsonToPost.put("type", "18");
            jsonToPost.put("appTicket", APP_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonToPost;
    }










    public JSONObject getJsonForUserData() {

        JSONObject jsonToPost = new JSONObject();
        try {

            jsonToPost.put("appTicket", APP_KEY);

            JSONObject bUnit = new JSONObject();
            bUnit.put("BuFace",1);
            bUnit.put("BuForm",1);
            bUnit.put("BuName","ll");
            jsonToPost.put("businessUnit", bUnit);

            JSONObject person = new JSONObject();
            person.put("FirstName", "ss");
            person.put("SecondName", "ss");
            person.put("LastName", "ss");
            person.put("Mobile", "+7 (926) 379-05-60");
            person.put("Email","test@test.ru");
            jsonToPost.put("person", person);

            jsonToPost.put("city", "Zamkad");
            jsonToPost.put("smsCode", "1942");
            jsonToPost.put("sum", 100000);
            jsonToPost.put("month", 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonToPost;
    }




  /*  //mb use StringFormatter
    public String getCorrectPhoneStr(String oldStr) {

        String[] splittedArr = oldStr.split("-");
        StringBuilder builder = new StringBuilder();

        builder.append("+7 (");
        builder.append(splittedArr[0]);
        builder.append(") ");
        builder.append(splittedArr[1]);
        builder.append("-");
        builder.append(splittedArr[2]);
        builder.append("-");
        builder.append(splittedArr[3]);

        return builder.toString();
    }
*/





    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }



    public JSONObject getPersonInfoJson() {
        return personInfoJson;
    }

    public void setPersonInfoJson(JSONObject personInfoJson) {
        this.personInfoJson = personInfoJson;
    }


    public void setPersonInfoJson(String surname, String name, String lastName, String formattedPhone, String email) {

        personInfoJson = new JSONObject();
        try {
            personInfoJson.put("FirstName", surname);
            personInfoJson.put("SecondName", name);
            personInfoJson.put("LastName", lastName);
            personInfoJson.put("Mobile", formattedPhone);
            personInfoJson.put("Email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public JSONObject getBuFaceJson() {
        return buFaceJson;
    }

    public void setBuFaceInfoJson(JSONObject buFaceJson) {
        this.buFaceJson = buFaceJson;
    }



    public String getRecievedSmsCode() {
        return recievedSmsCode;
    }

    public void setRecievedSmsCode(String recievedSmsCode) {
        this.recievedSmsCode = recievedSmsCode;
    }



    public JSONObject getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSONObject resultJson) {
        this.resultJson = resultJson;
    }



}

