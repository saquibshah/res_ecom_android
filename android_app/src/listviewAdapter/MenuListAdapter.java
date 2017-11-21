package listviewAdapter;

import java.util.ArrayList;

import com.example.res_e_com.R;
import com.example.res_e_com.R.id;
import com.example.res_e_com.R.layout;

import model.ModelMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter{
	ArrayList<ModelMenu>menuList;
	Context context;
	public MenuListAdapter(Context context, ArrayList<ModelMenu>menuList){
		this.context=context;
		this.menuList=menuList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ModelMenu res=menuList.get(position);
		
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.menu_list_item,parent,false);
		}
		TextView menuName=(TextView) convertView.findViewById(R.id.menu_name);
		menuName.setText(res.getName());
		TextView ingredient=(TextView) convertView.findViewById(R.id.ingredient);
		String tmpIngredient=res.getIngredient();
		if(tmpIngredient.length()>30){
			tmpIngredient=tmpIngredient.substring(0, 29)+"...";
		}
		ingredient.setText(tmpIngredient);
		TextView price=(TextView) convertView.findViewById(R.id.menu_price);
		price.setText(""+res.getPrice());
//		deliveryCharge.setText(toString(res.getDeliveryCharge()));		
		return convertView;
	}

}
