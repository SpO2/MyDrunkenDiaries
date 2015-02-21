/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Interface                                  *   
 *                                                      *   
 * Usage: Interface for the dialog buttons click.       *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.externalfragment;

import android.app.DialogFragment;

/**
 * Interface for the clic event listener.
 * @author romain
 *
 */
public interface DialogButtonClick 
{
	public void onDialogPositiveClick(DialogFragment dialog);
	public void onDialogNegativeClick(DialogFragment dialog);
}
