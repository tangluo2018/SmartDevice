package com.smartdevice.ui;

import com.smartdevice.main.R;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustActionBar {

	private ActionBar actionBar;
	private ImageView iconView;
	private TextView iconNameView;
	private TextView titleView;
	private ImageView menuView;
	private int titleVisible = View.VISIBLE;
	private int iconVisible = View.VISIBLE;
	private int menuVisible = View.GONE;
	private int iconNameVisible = View.GONE;
	
	public CustActionBar(ActionBar actionBar){
		this.actionBar = actionBar;
		initActionBar();
	}
	
	public CustActionBar(ActionBar actionBar, String title){
		this(actionBar);
		titleView.setText(title);
	}
	
	public void initActionBar(){
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar_v2_layout);
		iconView = (ImageView) actionBar.getCustomView().findViewById(R.id.id_actionbar_v2_icon);
		iconNameView = (TextView) actionBar.getCustomView().findViewById(R.id.id_actionbar_v2_icon_name);
		titleView = (TextView) actionBar.getCustomView().findViewById(R.id.id_actionbar_v2_title);
		menuView = (ImageView) actionBar.getCustomView().findViewById(R.id.id_actionbar_v2_menu);
		iconNameView.setVisibility(iconNameVisible);
		menuView.setVisibility(menuVisible);
		//Modify on 2018/11/2
        //去掉两边间距
		Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0, 0);
        //去掉底部阴影
        if(Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
	}
	
	public void setTitle(int title){
		titleView.setText(title);
	}

	public TextView getTitleView() {
		return titleView;
	}

	public ImageView getMenuView() {
		return menuView;
	}
	
	public ImageView getIconView(){
		return iconView;
	}
	
	public void setTitleVisible(int visible){
		titleVisible = visible;
		titleView.setVisibility(titleVisible);
	}
	
	public void setIconVisible(int visible){
		iconVisible = visible;
		iconView.setVisibility(iconVisible);
	}
	
	public void setMenuVisible(int visible){
		menuView.setVisibility(visible);
	}
}
