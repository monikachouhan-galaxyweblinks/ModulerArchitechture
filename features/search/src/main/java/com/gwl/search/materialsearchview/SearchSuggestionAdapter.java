package com.gwl.search.materialsearchview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwl.search.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Suggestions Adapter.
 *
 * @author Miguel Catalan Bañuls
 */
public class SearchSuggestionAdapter extends BaseAdapter /*implements Filterable*/ {

    // private ArrayList<String> data;
    private List<String> suggestions;
    private Drawable suggestionIcon;
    private LayoutInflater inflater;
    private boolean ellipsize;

    public SearchSuggestionAdapter(Context context, List<String> suggestions) {
        inflater = LayoutInflater.from(context);
        // data = new ArrayList<>();
        this.suggestions = suggestions;
    }

    public SearchSuggestionAdapter(Context context, List<String> suggestions, Drawable suggestionIcon, boolean ellipsize) {
        inflater = LayoutInflater.from(context);
        //   data = new ArrayList<>();
        this.suggestions = suggestions;
        this.suggestionIcon = suggestionIcon;
        this.ellipsize = ellipsize;
        Log.d("setHistoryORSuggestions", "setHistoryORSuggestions 1 " + getCount());
        Log.d("setHistoryORSuggestions", "setHistoryORSuggestions 2 " + this.suggestions.size());
    }

   /*    @Override
       public Filter getFilter() {
           Filter filter = new Filter() {
               @Override
               protected FilterResults performFiltering(CharSequence constraint) {
                   FilterResults filterResults = new FilterResults();
                   if (!TextUtils.isEmpty(constraint)) {

                       // Retrieve the autocomplete results.
                       List<String> searchData = new ArrayList<>();

                       for (String string : suggestions) {
                           if (string.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                               searchData.add(string);
                           }
                       }

                       // Assign the data to the FilterResults
                       filterResults.values = searchData;
                       filterResults.count = searchData.size();
                   }
                   return filterResults;
               }

               @Override
               protected void publishResults(CharSequence constraint, FilterResults results) {
                   if (results.values != null) {
                      // data = (ArrayList<String>) results.values;
                       notifyDataSetChanged();
                   }
               }
           };
           return filter;
       }*/

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SuggestionsViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.suggest_item, parent, false);
            viewHolder = new SuggestionsViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuggestionsViewHolder) convertView.getTag();
        }

        String currentListData = (String) getItem(position);

        viewHolder.textView.setText(currentListData);
        if (ellipsize) {
            viewHolder.textView.setSingleLine();
            viewHolder.textView.setEllipsize(TextUtils.TruncateAt.END);
        }

        return convertView;
    }

    private class SuggestionsViewHolder {

        TextView textView;
        ImageView imageView;

        public SuggestionsViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.suggestion_text);
            if (suggestionIcon != null) {
                imageView = (ImageView) convertView.findViewById(R.id.suggestion_icon);
                imageView.setImageDrawable(suggestionIcon);
            }
        }
    }
}