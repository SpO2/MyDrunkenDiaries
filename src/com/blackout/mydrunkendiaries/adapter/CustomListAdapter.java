/**
 * 
 */
package com.blackout.mydrunkendiaries.adapter;

import java.util.List;
import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.entites.Party;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author spo2
 *
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Party> partyItems;
 
    public CustomListAdapter(Activity activity, List<Party> partyItems) 
    {
        this.activity = activity;
        this.partyItems = partyItems;
    }
 
    @Override
    public int getCount() 
    {
        return partyItems.size();
    }
 
    @Override
    public Object getItem(int location) 
    {
        return partyItems.get(location);
    }
 
    @Override
    public long getItemId(int position) 
    {
        return position;
    }
 
    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
 
//        if (inflater == null)
//            inflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (convertView == null)
//            convertView = inflater.inflate(R.layout.list_row, null);
// 
//        TextView title = (TextView) convertView.findViewById(R.id.title);
//        TextView createdAt = (TextView) convertView.findViewById(R.id.createdAt);
//        TextView endedAt = (TextView) convertView.findViewById(R.id.endedAt);
//        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
// 
//        // getting movie data for the row
//        Party party = partyItems.get(position);
//        
//        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnail);
//        // thumbnail image
//        //thumbNail.setImageUrl('@/');
//         
//        // title
//        title.setText(party.getName());
//        createdAt.setText(R.string.party_begin + party.getCreatedAt().toString());
//        endedAt.setText(R.string.party_end + party.getEndedAt().toString());
        // rating
        //rating.setText("Rating: " + String.valueOf(party.getRating()));
         
        // genre
//        String genreStr = "";
//        for (String str : m.getGenre()) {
//            genreStr += str + ", ";
//        }
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
//                genreStr.length() - 2) : genreStr;
//        genre.setText(genreStr);
         
        // release year
        //year.setText(String.valueOf(m.getYear()));
 
        return convertView;
    }
 
}
