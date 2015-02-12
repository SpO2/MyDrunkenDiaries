/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  DialogFragment                             *   
 *                                                      *   
 * Usage: Show a Dialog Fragment to add title to the    * 
 * new party.                                           *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.externalfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * @author spo2
 *
 */
public class NewPartyDialogFragment extends DialogFragment 
{
	private EditText activityName;
	
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
                	   Party party = new Party();
                	   party.setName(activityName.getText().toString());
                	   party.setCreatedAt(DateTimeTools.getDateTime());
                	   PartySqliteAdapter partySqliteAdapter = 
                			   new PartySqliteAdapter(getActivity());
                	   partySqliteAdapter.open();
                	   partySqliteAdapter.create(party);
                	   partySqliteAdapter.close();
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id) 
                   {
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
