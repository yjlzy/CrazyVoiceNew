package com.crazyvoice.activity;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.crazyvoice.app.R;
import com.crazyvoice.db.CrazyVoiceDB;
import com.crazyvoice.model.ServerInfor;
import com.crazyvoice.test.CreatServerRoomTest;
import com.crazyvoice.test.queryServerRoomTest;
import com.crazyvoice.util.ClientConServer;
import com.crazyvoice.util.SetServerInfor;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 登陆界面
 * 
 * @author yujie
 * 
 */
public class LogIn extends Activity implements OnClickListener {
	private EditText accountEditText;
	private EditText passwordEditText;
	private EditText serverIpEditText;
	private EditText serverPortEditText;
	private EditText serverNameEditText;
	private Button loginButton;
	private Button registButton;
	private Button forgetPwdButton;
	private Button userNameClear;
	private Button pwdClear;
	private Button pwsEye;
	private Button clearServerInfor;
	private Button enterServer;
	private Button setServerInfor;
	private RelativeLayout serverInforLayout;
	//根据编辑框变化从而做出的动作
	private TextWatcher username_watcher;
	private TextWatcher password_watcher; 
//	private Button testButton;
//	private Button testButton2;
	private boolean loginResult;
	private ServerInfor serverInfor;
//	private CrazyVoiceDB crazyVoiceDB;
//	private SetServerInfor setServer=new SetServerInfor();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setBar();
		setContentView(R.layout.activity_login);
//		crazyVoiceDB = CrazyVoiceDB.getInstance(this);
		init();
		initWatcher();
		accountEditText.addTextChangedListener(username_watcher);
		passwordEditText.addTextChangedListener(password_watcher);
	}

	/**
	 * 设置点击事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 登录按钮点击事件，目标是跳转到频道获取页面
		if (v.getId() == R.id.login) {
			String account = accountEditText.getText().toString().trim();
			String password = passwordEditText.getText().toString().trim();
			if (account.equals("") || password.equals("")) {
				Toast.makeText(LogIn.this,
						"UserName or PassWord should not be NULL",
						Toast.LENGTH_SHORT).show();
			} else {
				ClientConServer ccs = new ClientConServer(LogIn.this);
				loginResult = ccs.login(account, password);
				if (loginResult) {
					Toast.makeText(LogIn.this, "Sucessful", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LogIn.this, ChooseAreaActivity.class);
					//Bundle bundle=new Bundle();
					startActivity(intent);
				} else {
					Toast.makeText(LogIn.this, "Fail", Toast.LENGTH_SHORT).show();
				}
			}
		} else if (v.getId() == R.id.button_register) {// 注册按钮点击事件，跳转到注册页面
			Intent intent = new Intent();
			intent.setClass(LogIn.this, RegistActicity.class);
			startActivity(intent);
		}else if(v.getId() == R.id.button_forget_pwd){//忘记密码界面
//			Intent intent = new Intent();
//			intent.setClass(LogIn.this, queryServerRoomTest.class);
//			startActivity(intent);
		}else if(v.getId() == R.id.button_username_clear){//清除用户名
			accountEditText.setText(null);
		}else if(v.getId() == R.id.button_pwd_clear){//清除密码
			passwordEditText.setText(null);
		}else if(v.getId() == R.id.button_pwd_eye){//设置密码是否可见
			if(passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
				pwsEye.setBackgroundResource(R.drawable.button_eye);
			    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
			   }else{
				pwsEye.setBackgroundResource(R.drawable.button_eye_closed);
			    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
			   }
			passwordEditText.setSelection(passwordEditText.getText().toString().length());
		}
		//else if(v.getId()==R.id.button_set_serverinfor){
//			serverInfor=crazyVoiceDB.loadServerInfo();
//			//serverInfor=setServer.getServerInfor();
//			serverIpEditText.setText(serverInfor.getServerIp());
//			//你是不是在比如TextView的setText（）方法中向括号中填入了一个int类型的东西啊？
//			//我刚刚也遇到了这个问题，一顿好找，终于发现了。
//			//安卓对于数字很多时候当做资源的id而不仅仅是数字，比如这里的setText（）
//			serverPortEditText.setText(serverInfor.getPort());
//			serverNameEditText.setText(serverInfor.getServerName());
//			serverInforLayout.setVisibility(View.VISIBLE);
//		}else if(v.getId()==R.id.button_clear_server){
//			serverIpEditText.setText(null);
//			serverPortEditText.setText(null);
//			serverNameEditText.setText(null);
//		}else if(v.getId()==R.id.button_enter_server){
//			serverInfor.setServerIp(serverIpEditText.getText().toString().trim());
//			serverInfor.setPort(serverPortEditText.getText().toString().trim());
//			serverInfor.setServerName(serverNameEditText.getText().toString().trim());
//			crazyVoiceDB.saveServer(serverInfor);
//			serverInforLayout.setVisibility(View.INVISIBLE);
//		}
//		else if(v.getId() == R.id.Test2_button){
//			Intent intent = new Intent();
//			intent.setClass(LogIn.this, CreatServerRoomTest.class);
//			startActivity(intent);
//		}
		
	}

	/**
	 * 初始化组件
	 */
	public void init() {
		accountEditText = (EditText) findViewById(R.id.edit_username);
		passwordEditText = (EditText) findViewById(R.id.edit_password);
		loginButton = (Button) findViewById(R.id.login);
		registButton = (Button) findViewById(R.id.button_register);
		forgetPwdButton=(Button) findViewById(R.id.button_forget_pwd);
		userNameClear=(Button) findViewById(R.id.button_username_clear);
		pwdClear=(Button) findViewById(R.id.button_pwd_clear);
		pwsEye=(Button) findViewById(R.id.button_pwd_eye);
		serverIpEditText=(EditText) findViewById(R.id.edit_server_ip);
		serverPortEditText=(EditText) findViewById(R.id.edit_server_port);
		serverNameEditText=(EditText) findViewById(R.id.edit_server_name);
		clearServerInfor=(Button) findViewById(R.id.button_clear_server);
		enterServer=(Button) findViewById(R.id.button_enter_server);
		setServerInfor=(Button) findViewById(R.id.button_set_serverinfor);
		serverInforLayout=(RelativeLayout) findViewById(R.id.serverinfor_layout);
//		testButton=(Button) findViewById(R.id.Test_button);
//		testButton2=(Button) findViewById(R.id.Test2_button);

		loginButton.setOnClickListener(this);
		registButton.setOnClickListener(this);
		forgetPwdButton.setOnClickListener(this);
		userNameClear.setOnClickListener(this);
		pwdClear.setOnClickListener(this);
		pwsEye.setOnClickListener(this);
		clearServerInfor.setOnClickListener(this);
		enterServer.setOnClickListener(this);
		setServerInfor.setOnClickListener(this);
//		testButton.setOnClickListener(this);
//		testButton2.setOnClickListener(this);

		SmackAndroid.init(LogIn.this);// 初始化Asmack平台,必须的，否则会报错
	}
	
	 /**
	  * 用户名，密码输入控件公用这一个watcher
	  */
	private void initWatcher() {
		// TODO Auto-generated method stub
		username_watcher=new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				//passwordEditText.setText(null);
				if(s.toString().length()>0){
					userNameClear.setVisibility(View.VISIBLE);
				}else{
					userNameClear.setVisibility(View.INVISIBLE);
				}
			}
		};
		password_watcher=new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.toString().length()>0){
					pwdClear.setVisibility(View.VISIBLE);
				}else{
					pwdClear.setVisibility(View.INVISIBLE);
				}
			}
		};
	}
	/**
	 * 设置标题菜单栏
	 */
	private void setBar() {
		// TODO Auto-generated method stub
		ActionBar bar = getActionBar();
//		bar.setDisplayHomeAsUpEnabled(true);
		setTitle("吐槽星球-登录");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	/*
	 * public boolean connectServer(){
	 * 
	 * }
	 */

}
