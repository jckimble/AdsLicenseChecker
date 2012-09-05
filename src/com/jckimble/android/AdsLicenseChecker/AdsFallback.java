/**
 * @author James Kimble <jckimble601@gmail.com>
 */
package com.jckimble.android.AdsLicenseChecker;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class AdsFallback {
	private Activity myActivity=null;
	private int AdId=-1;
	private int ImageId=-1;
	private AdsFallback parent=null;
	/**
	 * Show ads with fallback ad
	 * @param activity The calling activity usually "this"
	 * @param adId Ad Resource Id "R.id.admob"
	 * @param imageId Fallback Image Id "R.id.fallback"
	 */
	public AdsFallback(Activity activity,int adId,int imageId){
		this.myActivity=activity;
		this.AdId=adId;
		this.ImageId=imageId;
		this.parent=this;
	}
	/**
	 * Run to show ads
	 */
	public void showAds(){
		if(myActivity.isFinishing()) return;
		AdView ads=(AdView)myActivity.findViewById(AdId);
		ImageView fallback=(ImageView)myActivity.findViewById(ImageId);
		if(ads.getVisibility()==View.GONE){
			ads.setVisibility(View.VISIBLE);
		}
		if(fallback.getVisibility()==View.VISIBLE){
			fallback.setVisibility(View.GONE);
		}
		ads.setAdListener(new FallbackAdListener());
	}
	private void fallbackAds(){
		if(myActivity.isFinishing()) return;
		AdView ads=(AdView)myActivity.findViewById(AdId);
		ImageView fallback=(ImageView)myActivity.findViewById(ImageId);
		if(fallback.getVisibility()==View.GONE){
			fallback.setVisibility(View.VISIBLE);
		}
		if(ads.getVisibility()==View.VISIBLE){
			ads.setVisibility(View.GONE);
		}
	}
	private class FallbackAdListener implements AdListener {
		public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
			if(ErrorCode.NETWORK_ERROR.equals(arg1)){
				parent.fallbackAds();
			}
		}

		public void onDismissScreen(Ad arg0) {
			
		}

		public void onLeaveApplication(Ad arg0) {
			
		}

		public void onPresentScreen(Ad arg0) {
			
		}

		public void onReceiveAd(Ad arg0) {
			parent.showAds();
		}

		
	}
}