package co.playtech.otrosproductosrd.help;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.adapters.BancaAdapter;

/**
 * Created by adammcneilly on 9/8/15.
 * Egonzalias
 */
public class BancaTouchHelper extends ItemTouchHelper.SimpleCallback {
    private static BancaAdapter mBancaAdapter;

    public BancaTouchHelper(BancaAdapter bancaAdapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mBancaAdapter = bancaAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mBancaAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mBancaAdapter.remove(viewHolder.getAdapterPosition());
    }

    public static void removeItemManually(int pos){
        mBancaAdapter.remove(pos);
    }
}
