package space.kuz.notesapp.ui;

import android.app.Application;
import android.widget.Button;
import android.widget.Toast;

public class MyApplication extends Application {
    @Override
    public void onTerminate() {
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show();
        super.onTerminate();
    }

}
