package su.ias.teledoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import su.ias.teledoc.data.PropertyData;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 17.11.2014
 * Time: 10:07
 */
public class CardsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private  Context context;
    PropertyData[] data;

    public CardsSpinnerAdapter(Context context, PropertyData[] data){
        super();
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
//            convertView = context.getLayoutInflater().inflate(R.layout.layout_spinner_item, null);

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_spinner_item, null);
//            convertView = vi.inflate(R.layout.spinner_item, null);
        }


//        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
//        textView.setText(arrayList.get(position).toString());//after changing from ArrayList<su.ias.teledoc.data.PropertyData> to ArrayList<Object>

        TextView text = (TextView) convertView.findViewById(R.id.list_item_text);
        text.setText(data[position].getTitle());
        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return data[position];
    }


    @Override
    public int getCount() {
        return data.length;
    }


    public View getDropDownView(int position, View convertView,ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_spinner_item, null);
        }


        TextView text = (TextView) convertView.findViewById(R.id.list_item_text);
        text.setText(data[position].getTitle());

        return convertView;
    }

}
