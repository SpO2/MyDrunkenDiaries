/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  DialogFragment                             *   
 *                                                      *   
 * Usage: Show a Dialog Fragment to confirm action.     *   
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.R;

/**
 * Dialog for confirmation.
 * @author romain
 *
 */
@SuppressLint("InflateParams")
public class ConfirmDialog extends DialogFragment 
{
	/**
	 * Listener for the dialog button click.
	 */
	private DialogButtonClick mListener;
	/**
	 * Message of the dialog.
	 */
	private String message;
	/**
	 * Indicate if dialog should show or hide a rating.
	 */
	private Boolean withRating = false;
	/**
	 * RatingBar of the dialog.
	 */
	private RatingBar beerBar;
	
	/**
	 * Constructor
	 * @param message
	 */
	public ConfirmDialog(String message) 
	{
		this.message = message;
		this.withRating = false;
	}
	
	/**
	 * Constructor
	 * @param message
	 * @param withRating
	 */
	public ConfirmDialog(String message, Boolean withRating)
	{
		this.message = message;
		this.withRating = withRating;
	}
	
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
		View view = inflater.inflate(R.layout.dialog_confirm, null);
		TextView tv = (TextView) view.findViewById(R.id.message);
		beerBar = (RatingBar) view.findViewById(R.id.beerbar);
		if (!this.withRating)
		{
			beerBar.setVisibility(8);			
		}else{
			RatingBar currentBeerBar = (RatingBar) getActivity()
					.findViewById(R.id.beerbar_current);
			if (currentBeerBar != null){
				beerBar.setRating(currentBeerBar.getRating());
			}
		}
		tv.setText(this.message);
		builder.setView(view)
			.setPositiveButton(R.string.add_new, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onDialogPositiveClick(ConfirmDialog.this);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					mListener.onDialogNegativeClick(ConfirmDialog.this);
				}	
			});
		return builder.create();
	}
	
	/**
	 * Give the rating set for the trip that just ended.
	 * @return the rating for the trip
	 */
	public Float getRating(){
		return this.beerBar.getRating();
	}
}
