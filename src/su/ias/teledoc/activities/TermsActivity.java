package su.ias.teledoc.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import su.ias.teledoc.R;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 18.11.2014
 * Time: 13:55
 */


@SuppressWarnings("ALL")
public class TermsActivity extends Activity {

    private ActionBar actionBar;
    private View actionBarView;

    private TextView termsTV;
    private ImageButton backBtn;
    private ImageButton phoneBtn;
    private TextView titleTV;

    private int typeExtra;
    private RelativeLayout bcg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms);

        actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_back_custom_view);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBarView = actionBar.getCustomView();
        actionBarView.findViewById(R.id.switcher_icon).setVisibility(View.GONE);

        titleTV  = (TextView)    actionBarView.findViewById(R.id.title);
        titleTV.setText("Согласие");
        backBtn  = (ImageButton) actionBarView.findViewById(R.id.back_btn);
        phoneBtn = (ImageButton) actionBarView.findViewById(R.id.phone_btn);
        bcg  =  (RelativeLayout) actionBarView.findViewById(R.id.bcg);


        typeExtra = getIntent().getIntExtra(AbstractActivity.TYPE, 0);
        if(typeExtra == AbstractActivity.ELECTRONIC_SIGNATURE_TYPE){
            bcg.setBackgroundColor(getResources().getColor(R.color.purple));
        }
        if(typeExtra == AbstractActivity.BANK_GUARANTEE_TYPE){
            bcg.setBackgroundColor(getResources().getColor(R.color.blue));
        }
        if(typeExtra == AbstractActivity.ELECTRONIC_DOCUMENTATION_TYPE){
            bcg.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if(typeExtra == AbstractActivity.PAYMENT_SERVICE_TYPE){
            bcg.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        if(typeExtra == AbstractActivity.AGENT_TYPE){
            bcg.setBackgroundColor(getResources().getColor(R.color.grey));
            titleTV.setTextColor(R.color.text_color);
            phoneBtn.setImageDrawable(getResources().getDrawable(R.drawable.icon_phone_gray));
            backBtn.setImageDrawable(getResources().getDrawable(R.drawable.icon_back_gray));
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:88007003922")));
            }
        });

        termsTV = (TextView) findViewById(R.id.terms_text);
        termsTV.setMovementMethod(LinkMovementMethod.getInstance());

        termsTV.setText(Html.fromHtml("Юридическая информация "));

    }



}
