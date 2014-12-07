package su.ias.teledoc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import su.ias.teledoc.data.MainMenuItemData;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 29.08.13
 * Time: 18:18
 */


public class MainMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    public ArrayList<MainMenuItemData> menuItems;


    public MainMenuListAdapter(Context c, ArrayList<MainMenuItemData> menuItems) {
        mContext = c;
        layoutInflater = LayoutInflater.from(mContext);
        this.menuItems = menuItems;
    }



    public int getCount() {
        return menuItems.size();
    }


    public Object getItem(int position) {
        return null;
    }


    public long getItemId(int position) {
        return 0;
    }



    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.titleTV = ((TextView) convertView.findViewById((R.id.text)));
            viewHolder.descrTV = ((TextView) convertView.findViewById((R.id.expandable_text)));
            viewHolder.stripe  = convertView.findViewById((R.id.stripe_description));
            viewHolder.bcg  = (RelativeLayout) convertView.findViewById(R.id.item);
            viewHolder.expandableBtn  = (ImageView) convertView.findViewById(R.id.expandable_toggle_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        MainMenuItemData menuItem = menuItems.get(position);

        viewHolder.titleTV.setText(menuItem.getTitle());
        viewHolder.descrTV.setText(menuItem.getDescription());
        viewHolder.stripe.setBackgroundColor(menuItem.getColor());
        viewHolder.bcg.setBackgroundDrawable(mContext.getResources().getDrawable(menuItem.getBcgDrawableId()));

        if (position == 4) {
            viewHolder.titleTV.setTextColor(Color.DKGRAY);
            viewHolder.expandableBtn.setImageDrawable(mContext.getResources().getDrawable(R.drawable.menu_arrow_gray));

        }

        return convertView;
    }




    static class ViewHolder {
        TextView titleTV;
        TextView descrTV;
        RelativeLayout bcg;
        View stripe;
        ImageView expandableBtn;

    }

}

















