package co.playtech.otrosproductosrd.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.fragments.BancaFragment;
import co.playtech.otrosproductosrd.objects.Banca;

/**
 * Created by Egonzalias on 27/01/2016.
 */
public class BancaAdapter extends RecyclerView.Adapter<BancaAdapter.ViewHolder> {

    //private SparseArray<Banca> myDataset;
    private static OnItemClickListener onItemClickListener;

    //new
    private Context mContext;
    private List<Banca> mBanca;
    private Activity mActivity;
    private BancaFragment mFragment;

    public BancaAdapter(Context context, List<Banca> banca, Activity activity, BancaFragment fragment){
        this.mContext = context;
        this.mBanca = banca;
        this.mActivity = activity;
        this.mFragment = fragment;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(ViewHolder item, int position, int id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banca, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v, this);
        return vh;*/
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banca, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mBanca.get(position));
    }

    @Override
    public int getItemCount() {
        return mBanca.size();
    }

    public void remove(int position) {
        String title = mContext.getString(R.string.title_banca);
        mBanca.remove(position);
        if(mBanca!= null && mBanca.size() > 0) {
            title = mContext.getString(R.string.title_banca)+ " ("+mBanca.size()+")";
        }

        if(mFragment.llEmpty !=null && mBanca.size() == 0)
            mFragment.llEmpty.setVisibility(View.VISIBLE);
        else
            mFragment.llEmpty.setVisibility(View.GONE);

        ((MainActivity) mActivity).setActionBarTitle(title);
        notifyItemRemoved(position);
    }

    public void removeAll(){
        mBanca.removeAll(mBanca);
        mFragment.llEmpty.setVisibility(View.GONE);
        ((MainActivity) mActivity).setActionBarTitle(mContext.getString(R.string.title_banca));
        notifyDataSetChanged();
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(mBanca, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    public List<Banca> getAllData(){
        return mBanca;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        public TextView tvNumero;
        public TextView tvValor;
        public TextView tvProducto;
        private BancaAdapter parent;

        public ViewHolder(View v, BancaAdapter parent) {
            super(v);
            tvNumero = (TextView)v.findViewById(R.id.tvNumeroApuesta);
            tvValor = (TextView)v.findViewById(R.id.tvValorApuesta);
            tvProducto = (TextView)v.findViewById(R.id.tvProductoApuesta);

            tvNumero.setOnClickListener(this);
            tvValor.setOnClickListener(this);
            tvProducto.setOnClickListener(this);
            v.setOnClickListener(this);
            this.parent = parent;
        }

        public CharSequence getItemName(){
            return tvNumero.getText();
        }

        public void bindData(Banca banca){
            this.tvNumero.setText(banca.getNumero());
            this.tvValor.setText(banca.getValor());
            this.tvProducto.setText(banca.getProducto());
        }


        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                listener.onItemClick(this, getPosition(), v.getId());
            }
        }
    }
}
