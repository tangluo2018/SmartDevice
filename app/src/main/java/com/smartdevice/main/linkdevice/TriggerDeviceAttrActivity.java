package com.smartdevice.main.linkdevice;

import com.smartdevice.main.R;
import com.smartdevice.mode.DeviceAttrType;
import com.smartdevice.mode.LinkDeviceAttribute;
import com.smartdevice.mode.LogicEntity;
import com.smartdevice.ui.CustActionBarV3;
import com.smartdevice.utils.CustUtils;
import com.smartdevice.utils.LogUtil;
import com.smartdevice.utils.ParamsUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TriggerDeviceAttrActivity extends AppCompatActivity {

	private LinkDeviceAttribute linkDeviceAttr;
	Spinner operatorSpinner;
	Spinner controlSpinner;
	EditText controlEditText;
	String[] openOperatorItem = {"="};
	String[] gradeOperatorItem = {"=","<",">","������"};
	String[] controlItem = {"�ر�", "��"};
	LinearLayout operatorLayout;
	LinearLayout valueLayout;
	int attrCode;
	String operatorValue = "";
	int controlValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trigger_attr_set_link_device_layout);
		init();
		TextView nameView = (TextView) findViewById(R.id.id_trigger_attr_set_name);
		operatorLayout = (LinearLayout) findViewById(R.id.id_trigger_attr_set_operator_ly);
		valueLayout = (LinearLayout) findViewById(R.id.id_trigger_attr_set_value_ly);
		Intent intent = getIntent();
		linkDeviceAttr = (LinkDeviceAttribute) intent.getSerializableExtra("com.smartdevice.mode.LinkDeviceAttribute");
		attrCode = linkDeviceAttr.getDeviceAttr().getAttrCode();
		nameView.setText(linkDeviceAttr.getDeviceAttr().getAttrName());
	    setOperatorView(attrCode);
	}
	
	private void init(){
		CustActionBarV3 actionBar = new CustActionBarV3(getSupportActionBar());
		actionBar.setTitle(R.string.link_device_trigger_set);
		actionBar.getIconView().setText(R.string.grobal_cancel);
		actionBar.getMenuView().setText(R.string.grobal_next_step);
		actionBar.getIconView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		actionBar.getMenuView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(attrCode == DeviceAttrType.ATTR_TYPE_BRIGHTNESS
						|| attrCode ==  DeviceAttrType.ATTR_TYPE_COLOR_TEMPER
						|| attrCode == DeviceAttrType.ATTR_TYPE_TEMPERATURE
						|| attrCode == DeviceAttrType.ATTR_TYPE_HUMIDITY){ 
					String textValue = controlEditText.getText().toString();
					if(textValue.equals("")){
						CustUtils.showToast(v.getContext(), R.string.link_device_attr_value_none);
						return;
					}else {
						controlValue = Integer.valueOf(textValue);
						operatorValue = gradeOperatorItem[operatorSpinner.getSelectedItemPosition()];
					}
				}else if (attrCode == DeviceAttrType.ATTR_TYPE_OPEN
						|| attrCode == DeviceAttrType.ATTR_TYPE_CURTAIN_STATE
						|| attrCode == DeviceAttrType.ATTR_TYPE_IS_ALARM){
					controlValue = controlSpinner.getSelectedItemPosition();
					operatorValue = "=";
					
				}else {
					//do...
					finish();
				}		
				LogUtil.i("TriggerDeviceAttrActivity", "operatorValue " + operatorValue
						+ "  controlValue " + controlValue);
				LogicEntity entity = new LogicEntity();
				entity.setGatewaySN(linkDeviceAttr.getGatewaySN());
				entity.setIfAttrCode(attrCode);
				entity.setIfTypeCode(linkDeviceAttr.getDeviceType().getTypeCode());
				entity.setIfAttrValue(String.valueOf(controlValue));
				entity.setIfOperateCode(operatorValue);
				entity.setIfDeviceName(linkDeviceAttr.getDeviceName());
				entity.setIfDeviceSN(linkDeviceAttr.getDeviceSN());
				Intent intent = new Intent(getApplicationContext(), CreateLinkDeviceActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("com.smartdevice.mode.LogicEntity", entity);
				intent.putExtras(bundle);
				intent.putExtra(ParamsUtils.LINK_DEVICE_ATTR_TYPE, ParamsUtils.LINK_DEVICE_ATTR_TRIGGER);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void setOperatorView(int attrCode){
		switch (attrCode) {
		case DeviceAttrType.ATTR_TYPE_OPEN:
		case DeviceAttrType.ATTR_TYPE_CURTAIN_STATE:
		case DeviceAttrType.ATTR_TYPE_IS_ALARM:
			operatorSpinner = new Spinner(this);
			ArrayAdapter<String> operatorStates = new ArrayAdapter<>
			           (this, android.R.layout.simple_spinner_item, openOperatorItem);
			operatorSpinner.setAdapter(operatorStates);
			operatorLayout.addView(operatorSpinner);
			controlSpinner = new Spinner(this);
			ArrayAdapter<String> controlStates = new ArrayAdapter<>
	           (this, android.R.layout.simple_spinner_item, controlItem);
	        controlSpinner.setAdapter(controlStates);
	        valueLayout.addView(controlSpinner);
			break;
		case DeviceAttrType.ATTR_TYPE_BRIGHTNESS:
		case DeviceAttrType.ATTR_TYPE_COLOR_TEMPER:
		case DeviceAttrType.ATTR_TYPE_HUMIDITY:
		case DeviceAttrType.ATTR_TYPE_TEMPERATURE:
			operatorSpinner = new Spinner(this);
			ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>
			           (this, android.R.layout.simple_spinner_item, gradeOperatorItem);
			operatorSpinner.setAdapter(gradeAdapter);
			operatorLayout.addView(operatorSpinner);
			controlEditText = new EditText(this);
			controlEditText.setSelected(false);
			controlEditText.setSingleLine();
			controlEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
			valueLayout.addView(controlEditText);
			break;
		default:
			break;
		}
	}
}
