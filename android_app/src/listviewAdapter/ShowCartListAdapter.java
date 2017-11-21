package listviewAdapter;

import java.util.ArrayList;

import com.example.res_e_com.R;
import com.example.res_e_com.R.id;
import com.example.res_e_com.R.layout;

import model.ModelCart;
import model.ModelMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShowCartListAdapter extends BaseAdapter{
	ArrayList<ModelMenu>menuList;
	ArrayList<ModelCart>cartList;
	Context context;
	public ShowCartListAdapter(Context context, ArrayList<ModelMenu>menuList,ArrayList<ModelCart>cartList){
		this.context=context;
		this.menuList=menuList;
		this.cartList=cartList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cartList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cartList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ModelMenu res=menuList.get(position);
		ModelCart cart=cartList.get(position);
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.show_cart_list_item,parent,false);
		}
		TextView menuName=(TextView) convertView.findViewById(R.id.menu_name_on_cart);
		menuName.setText(res.getName());
		TextView priceTxt=(TextView) convertView.findViewById(R.id.menu_price_on_cart);
		double price=cart.getQuantity()*cart.getPrice();
		priceTxt.setText(cart.getQuantity()+"x"+cart.getPrice()+"="+price +" Tk");
		return convertView;
	}

}
