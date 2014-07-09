package co.minecc.client;

public interface MCCPullHandler {

	public void onSuccess(String[] data);
	public void onFailure(Exception e);
	
}
