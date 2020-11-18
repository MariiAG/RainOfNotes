package com.example.rainofnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rainofnotes.R;
import com.example.rainofnotes.models.NoteModel;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NoteModel> modelArrayList;

    public NoteAdapter(Context context, ArrayList<NoteModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public NoteModel getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewItemNota = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            viewItemNota = layoutInflater.inflate(R.layout.list_item_note,parent,false);

        }
        TextView tv_note_list_item_title, tv_note_list_item_content;

        tv_note_list_item_title = viewItemNota.findViewById(R.id.tv_note_list_item_title);
        tv_note_list_item_content = viewItemNota.findViewById(R.id.tv_note_list_item_content);

        tv_note_list_item_title.setText(getItem(position).getTitle());
        tv_note_list_item_content.setText(getItem(position).getContent());

        return viewItemNota;
    }
}
