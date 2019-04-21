package org.nupter.secritypay.bean;

public class SM2KeyString {
	/** 公钥 */
	private  String publicKeyStr;

	/** 私钥 */
	private String privateKeyStr;

	public String getPublicKeyStr() {
		return publicKeyStr;
	}

	public void setPublicKeyStr(String publicKeyStr) {
		this.publicKeyStr = publicKeyStr;
	}

	public String getPrivateKeyStr() {
		return privateKeyStr;
	}

	public void setPrivateKeyStr(String privateKeyStr) {
		this.privateKeyStr = privateKeyStr;
	}
}
