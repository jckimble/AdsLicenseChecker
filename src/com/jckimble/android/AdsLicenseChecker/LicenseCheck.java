package com.jckimble.android.AdsLicenseChecker;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;

import android.app.Activity;
import android.provider.Settings.Secure;
import android.util.Log;

public class LicenseCheck implements LicenseCheckerCallback {
	private static final String TAG = "AdsLicenseChecker";
	private LicenseChecker checker;
	private LicenseCheckerCallback callback;
	protected LicenseCheck(){
		
	}
	/**
	 * Check if Application was bought from the android market.
	 * 
	 * @param activity The calling activity usually "this"
	 * @param SALT A static defined array of bytes
	 * @param Public_Key Your Android License Public Key
	 * @param callback Callback function when successful or failed
	 */
	public LicenseCheck(Activity activity,byte[] SALT,String Public_Key,LicenseCheckerCallback callback){
        String deviceId = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
		this.checker=new LicenseChecker(activity, new ServerManagedPolicy(activity,new AESObfuscator(SALT, activity.getPackageName(), deviceId)),Public_Key);
		this.callback=callback;
	}
	/**
	 * Run License Check 
	 */
	public void doCheck(){
		checker.checkAccess(callback);
	}
	/**
	 * This is defined here to save space. Innocent until proven guilty!
	 * @param policyCode Code Returned By LicenseChecker
	 */
	public void allow(int policyCode) {
	}
	/**
	 * Defined to save space shows in logcat. Means the developer screwed up!
 	 * @param policyCode Code Returned By LicenseChecker
	 */
	public void applicationError(int policyCode) {
		Log.d(TAG, "ApplicationError: "+Integer.toString(policyCode));
	}
	/**
	 * Application is not licensed by android market.
	 * @param policyCode Code Returned By LicenseChecker
	 */
	public void dontAllow(int policyCode) {
		if(policyCode==Policy.RETRY) doCheck();
	}
}
