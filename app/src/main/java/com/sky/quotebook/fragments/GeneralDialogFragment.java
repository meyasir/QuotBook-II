package com.sky.quotebook.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * created am app level dialog ('ll work in Fragment as well in activities)to share quotes and app
 * General Dialog have two buttons, ok & cancel
 */

public class GeneralDialogFragment extends BaseDialogFragment<GeneralDialogFragment.OnDialogFragmentClickListener> {

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        void onOkClicked(GeneralDialogFragment dialog);

        //public void onCancelClicked(GeneralDialogFragment dialog);

    }

    // Create an instance of the Dialog with the input
    public static GeneralDialogFragment newInstance(String title, String message) {
        GeneralDialogFragment dialogFragment = new GeneralDialogFragment();
        Bundle args = new Bundle();
        args.putString("share ", title);
        args.putString("msg", message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    // Create a Dialog using default AlertDialog builder , if not inflate custom view in onCreateView
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("share "))
                .setMessage(getArguments().getString("message"))
                .setCancelable(false)
                .setPositiveButton("share",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                                getActivityInstance().onOkClicked(GeneralDialogFragment.this);
                            }
                        }
                )
                /*.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                                getActivityInstance().onCancelClicked(GeneralDialogFragment.this);
                            }
                        }
                )*/
                .create();
    }

}