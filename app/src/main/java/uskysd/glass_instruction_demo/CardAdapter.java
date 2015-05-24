package uskysd.glass_instruction_demo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

/**
 * Created by Yusuke Yoshida on 2015/05/22.
 */
public class CardAdapter extends CardScrollAdapter {

    final List<CardBuilder> mCards;


    public CardAdapter(List<CardBuilder> cards) {
        mCards = cards;
    }
    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        return mCards.get(position).getView(convertView, viewGroup);
    }

    @Override
    public int getViewTypeCount() {
        return CardBuilder.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mCards.get(position).getItemViewType();
    }

    @Override
    public int getPosition(Object item) {
        for (int i=0; i<mCards.size(); i++) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }
        return AdapterView.INVALID_POSITION;
    }

    public void addCard(CardBuilder card) {
        mCards.add(card);
        notifyDataSetChanged();
    }

    public void addCards(List<CardBuilder> cards) {
        mCards.addAll(cards);
        notifyDataSetChanged();
    }

    public void removeCard(int position) {
        mCards.remove(position);
        notifyDataSetChanged();
    }

}
