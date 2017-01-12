package cn.suxiangbao.ofo.adapter;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import cn.suxiangbao.ofo.R;
import cn.suxiangbao.ofo.entity.OFOBike;

/**
 * Created by Jne
 * Date: 2015/1/11.
 */
public class OFOListAdapter extends BaseAdapter{
    private Context context;
    private List<OFOBike> bikeList;

    public OFOListAdapter(Context context, List<OFOBike> bikeList) {
        this.context = context;
        this.bikeList = bikeList;
    }

    @Override
    public int getCount() {
        return bikeList.size();
    }

    @Override
    public Object getItem(int position) {
        return bikeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        OFOBike bike = bikeList.get(position);
        if (bike == null){
            return null;
        }

        ViewHolder holder = null;
        if (view != null){
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.show_sql_item, null);

            holder = new ViewHolder();
            holder.idTextView = (TextView) view.findViewById(R.id.idTextView);
            holder.numberTestView = (TextView) view.findViewById(R.id.numberTextView);
            holder.password = (TextView) view.findViewById(R.id.passwordTextView);
            view.setTag(holder);
        }

        holder.idTextView.setText( String.valueOf(bike.getId()));
        holder.numberTestView.setText(bike.getBikenumber());
        holder.password.setText(bike.getPassword());
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public static class ViewHolder{
        public TextView idTextView;
        public TextView numberTestView;
        public TextView password ;
    }
}
