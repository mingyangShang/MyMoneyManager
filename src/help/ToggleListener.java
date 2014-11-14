  
   package help;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.shang.manager.DisplayUtils;
import com.shang.manager.PreManager;
import com.shang.moneymanager.R;
public class ToggleListener implements OnCheckedChangeListener {
	private Context context;
	private String settingName;
	private ToggleButton toggle;
	private ImageButton toggle_Button;
	private final String start= "启动界面";
	private final String pass = "手势解锁";

	public ToggleListener(Context context, String settingName,
			ToggleButton toggle, ImageButton toggle_Button) {
		this.context = context;
		this.settingName = settingName;
		this.toggle = toggle;
		this.toggle_Button = toggle_Button;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// ��������
		if (start.equals(settingName)) {
			PreManager.set(context, PreManager.START, isChecked);
		} else if (pass.equals(settingName)) {
			PreManager.set(context, PreManager.PASS, isChecked);
		}
			/*
		} else if(receive.equals(settingName)){
			PreManager.set(context, PreManager.RECEIVE, isChecked);
		} else if(byOther.equals(settingName)){
			PreManager.set(context, PreManager.BYOTHER, isChecked);
		}*/
		// ���Ŷ���
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toggle_Button
				.getLayoutParams();
		if (isChecked) {
			// ����λ��
			params.addRule(RelativeLayout.ALIGN_RIGHT, -1);
			if (start.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_LEFT, R.id.toggle_start);
			} else if (pass.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_LEFT,R.id.toggle_pass);
			}  /*else if (receive.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_LEFT,R.id.toggle_receive);
			}  else if (byOther.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_LEFT,R.id.toggle_byOther);
			}  */
			toggle_Button.setLayoutParams(params);
			toggle_Button.setImageResource(R.drawable.progress_thumb_selector);
			toggle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			// ���Ŷ���
			TranslateAnimation animation = new TranslateAnimation(
					DisplayUtils.dip2px(context, 40), 0, 0, 0);
			animation.setDuration(200);
			toggle_Button.startAnimation(animation);
		} else {
			// ����λ��
			if (start.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.toggle_start);
			} else if (pass.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_RIGHT,R.id.toggle_pass);
			}/* else if (receive.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_RIGHT,R.id.toggle_receive);
			} else if (byOther.equals(settingName)) {
				params.addRule(RelativeLayout.ALIGN_RIGHT,R.id.toggle_byOther);
			}*/
			params.addRule(RelativeLayout.ALIGN_LEFT, -1);
			toggle_Button.setLayoutParams(params);
			toggle_Button
					.setImageResource(R.drawable.progress_thumb_off_selector);

			toggle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			// ���Ŷ���
			TranslateAnimation animation = new TranslateAnimation(
					DisplayUtils.dip2px(context, -40), 0, 0, 0);
			animation.setDuration(200);
			toggle_Button.startAnimation(animation);
		}
	}

}
