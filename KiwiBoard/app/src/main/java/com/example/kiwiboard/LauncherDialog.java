package com.example.kiwiboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class LauncherDialog extends DialogFragment {
    private EditText editTextMinutes, editTextSeconds;
    private Button buttonEditQuestion, buttonDeleteQuestion;
    private LauncherDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_launcher_dialog, null);

        builder.setView(view)
                .setTitle("Launch Question")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Launch", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mins = editTextMinutes.getText().toString();
                        String secs = editTextSeconds.getText().toString();

                        int minutes = 0, seconds = 0;
                        if (!mins.equals("") || !secs.equals("")){
                            if (!mins.equals(""))
                                 minutes = Integer.parseInt(mins);
                            if (!secs.equals(""))
                                seconds = Integer.parseInt(secs);
                            listener.launchQuestion(minutes, seconds);
                        } else {
                            Toast.makeText(getActivity(), "Launch Failed: You must set a time limit", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        editTextMinutes = view.findViewById(R.id.txtMinutes);
        editTextSeconds = view.findViewById(R.id.txtSeconds);
        buttonEditQuestion = view.findViewById(R.id.btnEditQuestion);
        buttonDeleteQuestion = view.findViewById(R.id.btnDeleteQuestion);

        buttonEditQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfMain profmain = (ProfMain) getActivity();
                assert profmain != null;
                profmain.editQueueQuestion();
                profmain.closeLanucherDialog();
            }
        });

        buttonDeleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfMain profmain = (ProfMain) getActivity();
                assert profmain != null;
                profmain.deleteQueueQuestion();
                profmain.closeLanucherDialog();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (LauncherDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LauncherDialogListener");
        }

    }

    public interface LauncherDialogListener{
        void launchQuestion(int minutes, int seconds);
    }
}
