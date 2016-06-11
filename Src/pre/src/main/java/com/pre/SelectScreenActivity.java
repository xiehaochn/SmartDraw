package com.pre;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

//只有在清单文件里面配置Activity，系统才能打开它
public class SelectScreenActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_activity);
	}

	public void apple(View view)
	{
		String str="apple";
		Uri data=Uri.parse(str);
		Intent result=new Intent(null,data);
		setResult(RESULT_OK, result);
		finish();
	}
	public void plane(View view)
	{
		String str="plane";
		Uri data=Uri.parse(str);
		Intent result=new Intent(null,data);
		setResult(RESULT_OK, result);
		finish();
	}
	//	public void monkey(View view)
	//	{
	//		String str="monkey";
	//		Uri data=Uri.parse(str);
	//		Intent result=new Intent(null,data);
	//		setResult(RESULT_OK, result);
	//		finish();
	//	}
	public void goose(View view)
	{
		String str="goose";
		Uri data=Uri.parse(str);
		Intent result=new Intent(null,data);
		setResult(RESULT_OK, result);
		finish();
	}
}
