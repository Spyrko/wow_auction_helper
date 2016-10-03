package de.elderbyte.auctionhelper.model;

public class Auction {
    public enum AuctionLength { VERY_LONG, LONG, SHORT, VERY_SHORT, MEDIUM };
	private long auc;
	private int item;
	private String owner;
	private String ownerRealm;
	private long bid;
	private long buyout;
	private int quantity;
	private AuctionLength timeLeft;
	private long rand;
	private long seed;
	private int context;
	
	
	public long getAuc() {
		return auc;
	}
	
	public void setAuc(long auc) {
		this.auc = auc;
	}
	
	public int getItem() {
		return item;
	}
	
	public void setItem(int item) {
		this.item = item;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwnerRealm() {
		return ownerRealm;
	}
	
	public void setOwnerRealm(String ownerRealm) {
		this.ownerRealm = ownerRealm;
	}
	
	public long getBid() {
		return bid;
	}
	
	public void setBid(long bid) {
		this.bid = bid;
	}
	
	public long getBuyout() {
		return buyout;
	}
	
	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public AuctionLength getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft(AuctionLength timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public long getRand() {
		return rand;
	}
	
	public void setRand(long rand) {
		this.rand = rand;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public int getContext() {
		return context;
	}
	
	public void setContext(int context) {
		this.context = context;
	}
}
