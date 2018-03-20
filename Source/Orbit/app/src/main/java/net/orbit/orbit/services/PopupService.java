package net.orbit.orbit.services;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.orbit.orbit.R;

/**
 * Created by sristic on 3/19/18.
 */

public class PopupService {
    Context mContext;
    public PopupService(Context mContext) {
        this.mContext = mContext;
    }
    public void showPopup(String description){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_layout,null);

        //instantiate popup window
        final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //display the popup window
        popupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);
        TextView closePopupBtn = (TextView) customView.findViewById(R.id.closePopupBtn);
        TextView descText = (TextView) customView.findViewById(R.id.desc);
        if (!description.equals("")) {
            descText.setText(description);
        } else {
            descText.setText(R.string.no_description);
        }
        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
