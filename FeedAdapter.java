package com.agustinbergomi.lasmejores10apps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    //se necesita inflar el archivo xml
    //inflar un xml significa crear efectivamente el componente (widget) utilizando la representacion del mismo en xml
    //importante: se va a poner el inflator como una variable de clase para uso mas eficiente.
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;

    public FeedAdapter(Context context, int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.applications = applications;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutResource, parent, false);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvArtist = (TextView) view.findViewById(R.id.tvArtist);
        TextView tvSummary = (TextView) view.findViewById(R.id.tvSummary);

        FeedEntry currentApp = applications.get(position);

        tvName.setText(currentApp.getName());
        tvArtist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return view;
    }

}
