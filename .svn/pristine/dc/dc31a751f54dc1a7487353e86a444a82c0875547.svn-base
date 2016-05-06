package co.playtech.otrosproductosrd.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.objects.Banca;
import co.playtech.otrosproductosrd.objects.Recarga;

/**
 * Created by Playtech2 on 04/03/2016.
 */
public class RecargasAdapter extends RecyclerView.Adapter<RecargasAdapter.ViewHolder> {


    private Context mContext;
    private List<Recarga> mRecarga;
    private static OnItemClickListener onItemClickListener;

    public RecargasAdapter(Context context, List<Recarga> recarga){
        this.mContext = context;
        this.mRecarga = recarga;
    }

    @Override
    public RecargasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recarga, parent, false), this);
    }

    @Override
    public void onBindViewHolder(RecargasAdapter.ViewHolder holder, int position) {
        holder.bindData(mRecarga.get(position));
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
    public int getItemCount() {
        return mRecarga.size();
    }

    public void remove(int position) {
        String title = mContext.getString(R.string.title_banca);
        mRecarga.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvFecha;
        public TextView tvNumero;
        public TextView tvValor;
        private String sbIdTransaccion;
        private RecargasAdapter parent;

        public ViewHolder(View v, RecargasAdapter parent) {
            super(v);
            tvFecha = (TextView)v.findViewById(R.id.tvFechaRecarga);
            tvNumero = (TextView)v.findViewById(R.id.tvNumeroRecarga);
            tvValor = (TextView)v.findViewById(R.id.tvValorRecarga);

            tvFecha.setOnClickListener(this);
            tvNumero.setOnClickListener(this);
            tvValor.setOnClickListener(this);
            v.setOnClickListener(this);
            this.parent = parent;
        }

        public CharSequence getIdTransaccion(){
            return sbIdTransaccion;
        }

        public void bindData(Recarga recarga){
            this.tvFecha.setText(recarga.getFecha());
            this.tvNumero.setText(recarga.getNumero());
            this.tvValor.setText(recarga.getValor());
            sbIdTransaccion = recarga.getIdTrasnaccion();
        }


        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                listener.onItemClick(this, getLayoutPosition());
            }
        }
    }
}
