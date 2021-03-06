/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  DialogFragment                             *   
 *                                                      *   
 * Usage: Show a Dialog Fragment to add name to the     * 
 * new place.                                           *   
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
 * Dialog for the new place to add.
 * @author romain
 *
 */
@SuppressLint("InflateParams")
public class NewPlaceDialogFragment extends DialogFragment 
{
	/**
	 * Listener for dialog button click.
	 */
	private DialogButtonClick mListener;
	/**
	 * Hold the new place name.
	 */
	private EditText placeName;
	
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
					" must implement NewPlaceDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceStace)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_new_place, null);
		placeName = (EditText) view.findViewById(R.id.place_name);
		builder.setView(view)
			.setPositiveButton(R.string.add_new, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onDialogPositiveClick(NewPlaceDialogFragment.this);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onDialogNegativeClick(NewPlaceDialogFragment.this);
				}	
			});
		return builder.create();
	}
	
	/**
	 * Get the new place name.
	 * @return the new place name.
	 */
	public EditText getPlaceName()
	{
		return this.placeName;
	}
}
