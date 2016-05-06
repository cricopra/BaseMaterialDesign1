package co.playtech.otrosproductosrd.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.objects.Loteria;

/**
 * Created by Egonzalias on 29/01/2016.
 */
public class LoteriaAdapter extends RecyclerView.Adapter<LoteriaAdapter.ViewHolder> {

    private Context mContext;
    private List<Loteria> mLoteria;
    private static OnItemClickListener onItemClickListener;
    private List<Loteria> lstLoteriasStatus;//Para guardar el estado de cada loteria seleccionada

    public LoteriaAdapter(Context context, List<Loteria> loteria){
        this.mContext = context;
        this.mLoteria = loteria;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loteria, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mLoteria.get(position));
    }

    public List<Loteria> getAllLoterias(){
        return mLoteria;
    }

    @Override
    public int getItemCount() {
        return mLoteria.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNombre;
        private CheckBox chkItem;
        private LoteriaAdapter parent;

        public ViewHolder(View v, LoteriaAdapter parent) {
            super(v);
            tvNombre = (TextView)v.findViewById(R.id.tvNombreLoteria);
            chkItem = (CheckBox)v.findViewById(R.id.chkLoteria);

            chkItem.setOnClickListener(this);
            //v.setOnClickListener(this);
            this.parent = parent;
        }

        public boolean isChecked(){
            return chkItem.isChecked();
        }

        public void bindData(Loteria loteria){
            this.tvNombre.setText(loteria.getNombre());
            this.chkItem.setChecked(loteria.isSelected());
        }


        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                mLoteria.get(getPosition()).setSelected(chkItem.isChecked());
                //listener.onItemClick(this, getPosition());
            }
        }
    }
}
