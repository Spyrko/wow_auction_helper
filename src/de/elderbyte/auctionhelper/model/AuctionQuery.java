package de.elderbyte.auctionhelper.model;

public class AuctionQuery {
	private Realm[] realms;
	private Auction[] auctions;
	
	
	public Realm[] getRealms() {
		return realms;
	}
	
	public void setRealms(Realm[] realms) {
		this.realms = realms;
	}
	
	public Auction[] getAuctions() {
		return auctions;
	}
	
	public void setAuctions(Auction[] auctions) {
		this.auctions = auctions;
	}
}
