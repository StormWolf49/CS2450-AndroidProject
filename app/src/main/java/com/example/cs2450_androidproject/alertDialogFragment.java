package com.example.cs2450_androidproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class alertDialogFragment extends DialogFragment {
    String name = "";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //need view and layout inflater to access textfield
        LayoutInflater layoutInf = getLayoutInflater();
        View view = layoutInf.inflate(R.layout.name_input,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.game_over);
        builder.setView(view);
        EditText input = (EditText) view.findViewById(R.id.nameInputField);
        builder.setMessage(R.string.alertText)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //save score
                        //(not sure how to send/store this info)
                        //(so for now it's just saved to this "name" string here)
                        name = input.getText().toString();
                        System.out.println(name);
                        //return to menu after choosing to save score
                        Intent newGameIntent = new Intent(getActivity(), MainActivity.class);
                        requireActivity().finish();
                        startActivity(newGameIntent);
                    }
                })
                .setNegativeButton(R.string.skip_saving, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //return to menu
                        Intent newGameIntent = new Intent(getActivity(), MainActivity.class);
                        requireActivity().finish();
                        startActivity(newGameIntent);
                    }
                });
        return builder.create();
    }
}
