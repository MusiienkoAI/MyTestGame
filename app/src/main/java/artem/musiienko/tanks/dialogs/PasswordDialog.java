package artem.musiienko.tanks.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import artem.musiienko.tanks.R;

/**
 * Created by artyom on 07.07.16.
 */
public class PasswordDialog extends DialogFragment {


    private static PasswordDialog dialogPassword;


    public static PasswordDialog getInstance() {

        if (dialogPassword == null) {
            dialogPassword = new PasswordDialog();
        }
        return dialogPassword;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public void setIcon(int icon) {
//        this.icon = icon;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
//            if (getArguments().containsKey(KEY_TITLE))
//                title = getArguments().getString(KEY_TITLE);
//            if (getArguments().containsKey(KEY_BODY))
//                body = getArguments().getString(KEY_BODY);
//            if (getArguments().containsKey(KEY_ICON))
//                icon = getArguments().getInt(KEY_ICON);
        }

        Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

//
//        RelativeLayout mainContent = (RelativeLayout) dialog.findViewById(R.id.main_content);
//        ModTextView tvTitle = (ModTextView) dialog.findViewById(R.id.tv_title);
//        ModTextView tvBody = (ModTextView) dialog.findViewById(R.id.tv_body);
//        ImageView ivIcon = (ImageView) dialog.findViewById(R.id.iv_icon);
//        ModTextView tvButton = (ModTextView) dialog.findViewById(R.id.tv_button);


        return dialog;
    }


}
