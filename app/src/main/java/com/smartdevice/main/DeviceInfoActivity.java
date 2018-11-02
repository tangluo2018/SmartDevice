package com.smartdevice.main;

import com.smartdevice.mode.Device;
import com.smartdevice.mode.DeviceAttrView;
import com.smartdevice.mode.DeviceType;
import com.smartdevice.ui.CustLinearView;
import com.smartdevice.utils.CustActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeviceInfoActivity extends AppCompatActivity implements OnClickListener{
    
	private static final String TAG = DeviceInfoActivity.class.getSimpleName();
	private ImageView iconView;
	//private TextView nameView;
	private CustLinearView nameView;
	private TextView snView;
	private TextView typeView;
	private LinearLayout devInfoLayout;
	private int typeCode;
	DeviceAttrView attrView;
	private ImageView actionbarIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_info_layout); 
		iconView = (ImageView) findViewById(R.id.id_devinfo_icon);
		nameView = (CustLinearView) findViewById(R.id.id_devinfo_name);
		snView = (TextView) findViewById(R.id.id_deviceinfo_sn);
		typeView = (TextView) findViewById(R.id.id_deviceinfo_type);
		devInfoLayout = (LinearLayout) findViewById(R.id.id_devinfo_attr);
		Intent intent = this.getIntent();
		Device device = (Device)intent.getSerializableExtra("com.smartdevice.mode.Device");
		Log.i(TAG, "Device name " + device.getDeviceName());
		//Log.i(TAG, "Device base type id " + device.getDeviceType().getBaseTypeId());
		//Log.i(TAG, "Device type id " + device.getDeviceType().getTypeId());
		typeCode = device.getDeviceType().getTypeCode();
		setDeviceIcon(typeCode);
		if(device.getDeviceType().getTypeName() != null
				&& !device.getDeviceType().getTypeName().equals("") ){
			typeView.setText(device.getDeviceType().getTypeName());
		}
		
		nameView.setContent(device.getDeviceName());
		nameView.setContentColor(getResources().getColor(R.color.light_gray_20));
		snView.setText(device.getDeviceSN());
		attrView = new DeviceAttrView(this, device);
		ViewGroup viewGroup = attrView.getAttrViews();
		devInfoLayout.addView(viewGroup);
		
		CustActionBar actionBar = new CustActionBar(getSupportActionBar());
		actionBar.setTitle(R.string.device_info);
		actionbarIcon = actionBar.getIconView();
		actionbarIcon.setOnClickListener(this);
		
	}
	
	private void setDeviceIcon(int typeId){
		switch (typeId) {
		case DeviceType.DEVICE_TYPE_ADJUST_BRG_LIGHT:
		case DeviceType.DEVICE_TYPE_COLOR_TEMPERATURE_LIGHT:
			iconView.setImageResource(R.drawable.icon_light);
			break;
		case DeviceType.DEVICE_TYPE_SWITCH:
			iconView.setImageResource(R.drawable.icon_switcher);
			break;
		case DeviceType.DEVICE_TYPE_CURTAIN:
			iconView.setImageResource(R.drawable.icon_curtain);
			break;
		case DeviceType.DEVICE_TYPE_ELETRIC_METER:
			iconView.setImageResource(R.drawable.icon_eletric_meter);
			break;
		case DeviceType.DEVICE_TYPE_WATER_METER:
			iconView.setImageResource(R.drawable.icon_water_meter);
			break;
		case DeviceType.DEVICE_TYPE_HEAT_METER:
			iconView.setImageResource(R.drawable.icon_heat);
			break;
		case DeviceType.DEVICE_TYPE_SWITCH_SOCKET:
			iconView.setImageResource(R.drawable.icon_socket);
			break;
		case DeviceType.DEVICE_TYPE_GAS_METER:
			iconView.setImageResource(R.drawable.icon_gas_meter);
			break;
		case DeviceType.DEVICE_TYPE_BODY_INFARE:
			iconView.setImageResource(R.drawable.icon_body_infare);
			break;
		case DeviceType.DEVICE_TYPE_TEMPERATURE_HUMIDITY_SENSOR:
			iconView.setImageResource(R.drawable.icon_thermometer);
		    break;
		case DeviceType.DEVICE_TYPE_MAGNET:
			iconView.setImageResource(R.drawable.icon_magnatic);
			break;
		case DeviceType.DEVICE_TYPE_CAMERA:
			iconView.setImageResource(R.drawable.icon_camera);
			break;
		default:
			iconView.setImageResource(R.drawable.icon_unknow_device);
			break;
		}
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("com.smartdevice.mode.Device", attrView.getDeviceInfo());
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		Intent intent = new Intent();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("com.smartdevice.mode.Device", attrView.getDeviceInfo());
//		intent.putExtras(bundle);
//		setResult(RESULT_OK, intent);
//		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onClicked!");
		switch (v.getId()) {
		case R.id.id_actionbar_icon:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("com.smartdevice.mode.Device", attrView.getDeviceInfo());
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}
	}
	
	
}
