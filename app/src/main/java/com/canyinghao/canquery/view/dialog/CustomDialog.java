package com.canyinghao.canquery.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canquery.R;

public class CustomDialog extends Dialog {

	private String title;
	private String message;
	private String left;
	private String right;
	private View.OnClickListener leftClick;
	private View.OnClickListener rightClick;
	private TextView dialog_left;
	private TextView dialog_right;
	private TextView tv_dialog_title;
	private TextView tv_dialog_content;

	public CustomDialog(String title, String message, final Context context,
			String right, String left, final View.OnClickListener rightClick,
			final View.OnClickListener leftClick) {
		super(context, R.style.CustomDialog);
		this.title = title;
		this.message = message;
		this.left = left;
		this.right = right;
		this.leftClick = leftClick;
		this.rightClick = rightClick;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_custom_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		setCanceledOnTouchOutside(true);
		tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
		tv_dialog_content = (TextView) findViewById(R.id.tv_dialog_content);
		dialog_left = (TextView) findViewById(R.id.dialog_left);
		dialog_right = (TextView) findViewById(R.id.dialog_right);


		if (TextUtils.isEmpty(title)) {
			title = "温馨提示";
		}

		tv_dialog_title.setText(title);
		tv_dialog_content.setText(message);
		if (!TextUtils.isEmpty(left) && TextUtils.isEmpty(right)) {
			dialog_left.setVisibility(View.GONE);
			dialog_right.setVisibility(View.GONE);

		} else if (TextUtils.isEmpty(left) && TextUtils.isEmpty(right)) {
			dialog_left.setVisibility(View.GONE);
			dialog_right.setVisibility(View.GONE);


		} else if (!TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
			dialog_left.setText(left);
			dialog_right.setText(right);

		}

		View.OnClickListener clickListener = new View.OnClickListener() {

			@Override
			public void onClick(final  View v) {

                AnimeHepler.getInstance().startAnimation(getContext(),v,AnimeHepler.getInstance().animSmall2Big(),new AnimeHepler.OnAnimEnd() {
                    @Override
                    public void end() {
                        if (v == dialog_left) {
                            if (leftClick != null) {
                                leftClick.onClick(v);
                            }

                            dismiss();
                        } else if (v == dialog_right ) {
                            if (rightClick != null) {
                                rightClick.onClick(v);
                            }

                            dismiss();
                        }
                    }
                });


			}
		};

		dialog_left.setOnClickListener(clickListener);
		dialog_right.setOnClickListener(clickListener);


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK && isShowing()) {
				if (rightClick != null) {
					rightClick.onClick(dialog_right);
					return true;
				}

			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
