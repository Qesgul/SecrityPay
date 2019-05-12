package org.nupter.secritypay.bean;

public class SM2User {
	private String M;
	private String IDA;
	private String signature;
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

	public String getM() {
		return M;
	}

	public void setM(String m) {
		M = m;
	}

	public String getIDA() {
		return IDA;
	}

	public void setIDA(String IDA) {
		this.IDA = IDA;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
