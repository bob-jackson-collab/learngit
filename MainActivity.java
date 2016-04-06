package com.xudan.school;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.xudan.model.Code;

public class MainActivity extends Activity implements OnClickListener {

	EditText edt1,edt2,edt3;
	ImageView imageView;
	ImageButton btn1;
	Button  btn2;
	String txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intt();
		start();
	}

	private void intt() {
		imageView = (ImageView) findViewById(R.id.img1);
		imageView.setImageBitmap(Code.getInstance().getBitmap());
		btn1 = (ImageButton) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		edt1=(EditText) findViewById(R.id.edt1);
		edt2=(EditText) findViewById(R.id.edt2);
		edt3=(EditText) findViewById(R.id.edt3);
	}

	private void start() {
		txt = Code.getInstance().getCode();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn1:
			imageView = (ImageView) findViewById(R.id.img1);
			imageView.setImageBitmap(Code.getInstance().getBitmap());
			break;
		case R.id.btn2:
//			if(TextUtils.isEmpty(edt1.getText())){
//				Toast.makeText(this, "请输入验证码!", 1).show();
//			}
//			else
				if(txt.equals(edt3.getText().toString())){
				Toast.makeText(this, "登陆成功", 1).show();
			}else {
				edt1.setText(null);
				Toast.makeText(this, "验证码错误,请重新输入验证码", 1).show();
				imageView = (ImageView) findViewById(R.id.img1);
				imageView.setImageBitmap(Code.getInstance().getBitmap());
				Log.i("555555555555", "6666666666");
			}
			break;

		default:
			break;
		}
	}
    
}
