package com.jckimble.android.AdsLicenseChecker;

import android.app.Activity;

import com.google.android.vending.licensing.Policy;

public class LicenseCheckAds extends LicenseCheck {
	private AdsFallback ads=null;
	private LicenseCheckAds parent;
	/**
	 * Check if Application was bought from the android market. Show ads with a fallback ad.
 	 * @param activity The calling activity usually "this"
	 * @param SALT A static defined array of bytes
	 * @param Public_Key Your Android License Public Key
	 * @param adId Ad Resource Id "R.id.admob"
	 * @param imageId Fallback Image Id "R.id.fallback"
	 */
	public LicenseCheckAds(Activity activity,byte[] SALT,String Public_Key,int adId,int imageId){
		super(activity,SALT,Public_Key,new LicenseCheckAds());
		this.ads=new AdsFallback(activity,adId,imageId);
		this.parent=this;
	}
	protected LicenseCheckAds() {
	}
	/**
	 * {@inheritDoc}
	 */
	public void dontAllow(int policyCode) {
		super.dontAllow(policyCode);
		if(policyCode==Policy.NOT_LICENSED) parent.ads.showAds();
	}

}
