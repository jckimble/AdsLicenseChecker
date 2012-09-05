package com.jckimble.android.AdsLicenseChecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.android.vending.licensing.Policy;

public class LicenseCheckClose extends LicenseCheck {
	private Activity activity=null;
	private LicenseCheckClose parent;
	private int message;
	/**
	 * Check if Application was bought from the android market. Close application with warning.
 	 * @param activity The calling activity usually "this"
	 * @param SALT A static defined array of bytes
	 * @param Public_Key Your Android License Public Key
	 * @param message Message to display when fixing to close in resource "R.string.notlicensed"
	 */
	public LicenseCheckClose(Activity activity,byte[] SALT,String Public_Key,int message){
		super(activity,SALT,Public_Key,new LicenseCheckClose());
		this.activity=activity;
		this.parent=this;
		this.message=message;
	}
	protected LicenseCheckClose() {
	}
	/**
	 * {@inheritDoc}
	 */
	public void dontAllow(int policyCode) {
		super.dontAllow(policyCode);
		if(policyCode==Policy.NOT_LICENSED){
			AlertDialog.Builder builder= new AlertDialog.Builder(parent.activity);
			builder.setCancelable(false);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setMessage(parent.message);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					activity.finish();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

}
