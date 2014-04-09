package com.itspartytime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matt on 4/8/14.
 */
public class ListDialog extends DialogFragment {

    private ArrayList<String> displayList;

    public ListDialog(ArrayList<String> displayList)
    {
        this.displayList = displayList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }
}
