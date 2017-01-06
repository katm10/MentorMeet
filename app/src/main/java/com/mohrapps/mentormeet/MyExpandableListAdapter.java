package com.mohrapps.mentormeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Katherine on 1/5/2017.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> genAreas;
    HashMap<String, List<String>> specificAreas;
    List<String> selectedInterests = new ArrayList<String>();
    TextView selectedText;

    public MyExpandableListAdapter(Context context, List<String> genAreas, HashMap<String, List<String>> specificAreas, TextView textView){
        this.context = context;
        this.genAreas = genAreas;
        this.specificAreas = specificAreas;
        this.selectedText = textView;
    }

    @Override
    public int getGroupCount() {
        if (genAreas!=null) {
            return genAreas.size();
        }else{
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int i) {
        if(specificAreas!=null){
                return specificAreas.get(genAreas.get(i)).size();

        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return genAreas.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return specificAreas.get(genAreas.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String title = (String)getGroup(i);
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_parent, null);
        }
        TextView textView = (TextView)view.findViewById(R.id.parent_txt);
        textView.setText(title);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String topic = (String)getChild(i, i1);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            view = inflater.inflate(R.layout.list_child, null);
        }
        TextView textView = (TextView)view.findViewById(R.id.child_txt);
        textView.setText(topic);
        CheckBox check = (CheckBox)view.findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    addInterest(topic);
                }else{
                    removeInterest(topic);
                }
                String display = "You have selected ";
               for(String interest : selectedInterests) {
                   display += (interest + ", ");
               }
                selectedText.setText(display);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void addInterest(String string){
        selectedInterests.add(string);
    }

    public void removeInterest(String topic){
        for(String str : selectedInterests){
            if(topic.equals(str)){
                selectedInterests.remove(selectedInterests.indexOf(str));
            }
        }
    }

    public List<String> getSelectedInterests() {
        return selectedInterests;
    }
}
