package com.smartdraw.dijay.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.smartdraw.R;

/**
 * Author：DJ
 * Time：2016/6/23 11:16
 * Name：Src
 * Description：
 */
public class BaseDialog extends Dialog
{
    private Context context;
    private String title;
    private String content;
    private String confirmButtonText;
    private String cancelButtonText;
    private DialogClickListener dialogClickListener;

    public BaseDialog(Context context, String title,String content, String confirmButtonText, String cancelButtonText)
    {
        super (context,R.style.MyDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);

        init ();
    }

    public void init()
    {
        LayoutInflater layoutInflater = LayoutInflater.from (context);
        final View view = layoutInflater.inflate (R.layout.dialog_hint, null);
        setContentView (view);

        TextView tvTitle = (TextView) findViewById (R.id.tvTitle);
        TextView tvContent = (TextView) findViewById (R.id.tvContent);
        TextView tvConfirm = (TextView) findViewById (R.id.tvConfirm);
        TextView tvCancel = (TextView) findViewById (R.id.tvCancel);

        tvTitle.setText (title);
        tvContent.setText (content);
        tvConfirm.setText (confirmButtonText);
        tvCancel.setText (cancelButtonText);

        tvConfirm.setOnClickListener (new clickListener ());
        tvCancel.setOnClickListener (new clickListener ());

        Window dialogWindow = getWindow ();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes ();
        DisplayMetrics displayMetrics = context.getResources ().getDisplayMetrics ();
        lp.width = (int) (displayMetrics.widthPixels * 0.9);
        lp.height = (int) (displayMetrics.heightPixels * 0.5);
        dialogWindow.setGravity (Gravity.CENTER_VERTICAL);
        dialogWindow.setAttributes (lp);

    }

    public void setDialogClickListener(DialogClickListener dialogClickListener)
    {
        this.dialogClickListener = dialogClickListener;
    }

    private class clickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            switch (v.getId ())
            {
                case R.id.tvConfirm:
                    dialogClickListener.doConfirm ();
                    break;
                case R.id.tvCancel:
                    dialogClickListener.doCancel ();
                    break;
            }
        }
    }

    public interface DialogClickListener
    {
        void doConfirm();

        void doCancel();
    }
}
