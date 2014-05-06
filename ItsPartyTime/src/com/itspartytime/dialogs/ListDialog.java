/**
 * ListDialog.java
 *
 * This class is the dialog responsible for showing the list of available parties
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 * private ArrayList<String> displayList:                           The list of device names
 * private String title                                             Dialog Title
 * private ListView mListView                                       UI List View
 * private AdapterView.OnItemClickListener mOnItemClickListener     Listener for List Item Click
 *
 *
 *
 * Known Faults:
 *
 *
 *
 */
package com.itspartytime.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.itspartytime.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListDialog extends DialogFragment
{

    private ArrayList<String> displayList;
    private String title;
    private ListView mListView;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public ListDialog()
    {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout mLinearLayout = (LinearLayout)inflater.inflate(R.layout.list_dialog_layout, null);
        ListView mListView = (ListView) mLinearLayout.findViewById(R.id.display_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mLinearLayout);
        builder.setCancelable(true);
        builder.setTitle(title);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, displayList);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
        this.mListView = mListView;
        return builder.create();
    }

    /**
     * Creates a dialog based on parameters
     * @param displayList
     * @param title
     * @param onItemClickListener
     */
    public void createDialog(ArrayList<String> displayList, String title, AdapterView.OnItemClickListener onItemClickListener)
    {
        this.displayList = displayList;
        this.title = title;
        this.mOnItemClickListener = onItemClickListener;
    }
}
