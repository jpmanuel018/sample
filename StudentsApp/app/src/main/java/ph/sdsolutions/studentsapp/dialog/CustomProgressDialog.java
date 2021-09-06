package ph.sdsolutions.studentsapp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import ph.sdsolutions.studentsapp.R;

public class CustomProgressDialog {

    private ProgressDialog progressDialog;

    public CustomProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void show(){
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void dismiss(){
        progressDialog.dismiss();
    }

    public boolean isShowing(){
        if (progressDialog.isShowing()) {
            return true;
        }else{
            return false;
        }
    }
}
