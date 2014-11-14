package help;


import android.app.Application;

import com.shang.manager.LockPatternUtils;

public class App extends Application {
	private static App mInstance;
	private LockPatternUtils mLockPatternUtils;

	public static App getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}
}
