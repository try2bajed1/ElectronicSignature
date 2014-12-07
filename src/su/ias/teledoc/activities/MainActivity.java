package su.ias.teledoc.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import su.ias.teledoc.MainMenuListAdapter;
import su.ias.teledoc.R;
import su.ias.teledoc.data.MainMenuItemData;

import java.util.ArrayList;


public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        initMenu();

        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.teledoc.ru"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:88007003922")));
            }
        });
    }



    private void initMenu() {

        ArrayList<MainMenuItemData> arrayList = new ArrayList<MainMenuItemData>();

        arrayList.add(new MainMenuItemData("Заказать электронную подпись (ЭП)",getString(R.string.el_sign_hint), Color.parseColor("#b289ff"), R.drawable.menu_purple));
        arrayList.add(new MainMenuItemData("Оформить банковскую гарантию по ФЗ№44 Ф3",getString(R.string.desc_bank_guarantee), Color.parseColor("#00d8d5"), R.drawable.menu_blue));
        arrayList.add(new MainMenuItemData("Подключиться к электронному документообороту (ЭДО)",getString(R.string.desc_documentation), Color.parseColor("#94e858"), R.drawable.menu_green));
        arrayList.add(new MainMenuItemData("Подключиться к платежному сервису",getString(R.string.desc_payment), Color.parseColor("#ffb94a"), R.drawable.menu_yellow));
        arrayList.add(new MainMenuItemData("Стать агентом Теледок",getString(R.string.desc_agent), Color.parseColor("#c1ccd1"), R.drawable.menu_white));

        final ActionSlideExpandableListView list = (ActionSlideExpandableListView) findViewById(R.id.main_menu_list);
        list.setAdapter(new MainMenuListAdapter(this, arrayList));
        list.setDivider(null);

        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

            @Override
            public void onClick(View listView, View buttonview, int position) {

//                Log.i("@", String.valueOf(((AbstractSlideExpandableListAdapter) list.getAdapter()).getExpandToggleButton(listView)));

                if (buttonview.getId() == R.id.item) {
                    if(position == 0) startActivity(new Intent(MainActivity.this, ElectronicSignatureActivity.class));
                    if(position == 1) startActivity(new Intent(MainActivity.this, BankGuaranteeActivity.class));
                    if(position == 2) startActivity(new Intent(MainActivity.this, DocumentationActivity.class));
                    if(position == 3) startActivity(new Intent(MainActivity.this, PaymentServiceActivity.class));;
                    if(position == 4) startActivity(new Intent(MainActivity.this, AgentActivity.class));

                } else if (buttonview.getId() == R.id.expandable_text) {

                    list.collapse();
                } /*else if (buttonview.getId() == R.id.expandable_toggle_button) {
                    Log.i("@", "toggle");
                }*/
            }

        }, R.id.expandable_text, R.id.item, R.id.expandable_toggle_button);
    }




}

