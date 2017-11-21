package listviewAdapter;

import java.util.ArrayList;

import com.example.res_e_com.R;
import com.example.res_e_com.R.id;
import com.example.res_e_com.R.layout;

import model.ModelRestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RestaurantListAdapter extends BaseAdapter{
	ArrayList<ModelRestaurant>restaurantList;
	Context context;
	public RestaurantListAdapter(Context context, ArrayList<ModelRestaurant>restaurantList){
		this.context=context;
		this.restaurantList=restaurantList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return restaurantList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return restaurantList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ModelRestaurant res=restaurantList.get(position);
		
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.res_list_item,parent,false);
		}
		TextView resName=(TextView) convertView.findViewById(R.id.menu_name);
		resName.setText(res.getResName());
		TextView resTitle=(TextView) convertView.findViewById(R.id.ingredient);
		resTitle.setText(res.getTitle());
		TextView deliveryCharge=(TextView) convertView.findViewById(R.id.menu_price);
		deliveryCharge.setText("Delivery Charge"+res.getDeliveryCharge());
//		deliveryCharge.setText(toString(res.getDeliveryCharge()));		
		return convertView;
	}

}
