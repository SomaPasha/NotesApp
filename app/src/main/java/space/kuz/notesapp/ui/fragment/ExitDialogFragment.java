package space.kuz.notesapp.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import space.kuz.notesapp.R;

import static space.kuz.notesapp.R.string.appClose;

public class ExitDialogFragment  extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.exit)
                .setIcon(R.drawable.ic_exit_dialog)
                .setCancelable(false)
                .setMessage(R.string.questionExit)
                .setPositiveButton(R.string.yes,((dialog, id) -> {
                    Toast.makeText(getContext(), appClose, Toast.LENGTH_SHORT).show();
                     requireActivity().finish();
                }))
                .setNegativeButton(R.string.no,((dialog, id) ->{
                        Toast.makeText(getContext(), R.string.no, Toast.LENGTH_SHORT).show();
                }
                ))
                .setNeutralButton(R.string.cancel,((dialog, id) -> Toast.makeText(getContext(), R.string.cancel, Toast.LENGTH_SHORT).show()))
                .create();
    }
}
