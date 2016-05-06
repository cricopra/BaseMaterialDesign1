package co.playtech.otrosproductosrd.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.objects.Loteria;

/**
 * Created by Egonzalias on 29/01/2016.
 */
public class DevicesBluetoothAdapter extends RecyclerView.Adapter<DevicesBluetoothAdapter.ViewHolder> {

    private Context mContext;
    private List<BluetoothDevice> mDevices;
    private static OnItemClickListener onItemClickListener;

    public DevicesBluetoothAdapter(Context context, List<BluetoothDevice> mDevices){
        this.mContext = context;
        this.mDevices = mDevices;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(ViewHolder item, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mDevices.get(position));
    }

    public List<BluetoothDevice> getAllDevices(){
        return mDevices;
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNombre;
        //private CheckBox chkItem;
        public String sbMac;
        private DevicesBluetoothAdapter parent;

        public ViewHolder(View v, DevicesBluetoothAdapter parent) {
            super(v);
            tvNombre = (TextView)v.findViewById(R.id.tvNombreDispositivo);
            //chkItem = (CheckBox)v.findViewById(R.id.chkLoteria);

            //chkItem.setOnClickListener(this);
            v.setOnClickListener(this);
            this.parent = parent;
        }

        /*public boolean isChecked(){
            return chkItem.isChecked();
        }*/

        public void bindData(BluetoothDevice device){
            this.tvNombre.setText(device.getName());
            this.sbMac = device.getAddress();
            //this.chkItem.setChecked(loteria.isSelected());
        }


        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                //mDevices.get(getPosition()).setSelected(chkItem.isChecked());
                listener.onItemClick(this, getPosition());
            }
        }
    }
}
