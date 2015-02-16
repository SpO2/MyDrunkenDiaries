/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  DialogFragment                             *   
 *                                                      *   
 * Usage: Show a Dialog Fragment to add name to the     * 
 * new party.                                           *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.externalfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.blackout.mydrunkendiaries.R;

/**
 * @author spo2
 *
 */
public class NewPartyDialogFragment extends DialogFragment 
{
	
	private EditText activityName;
	private DialogButtonClick mListener;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			mListener = (DialogButtonClick) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + 
					" must implement NewPartyDialogListener");
		}
	}
	
	@SuppressLint("InflateParams")
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_party, null);
		activityName = (EditText) view.findViewById(R.id.party_name);
        builder.setView(view)
        	.setPositiveButton(R.string.add_new, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                	   mListener.onDialogPositiveClick(NewPartyDialogFragment.this);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                	   mListener.onDialogNegativeClick(NewPartyDialogFragment.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	/**
	 * 
	 * @return the EditText that contains the new activity name
	 */
	public EditText getActivityName()
	{
		return this.activityName;
	}
}
