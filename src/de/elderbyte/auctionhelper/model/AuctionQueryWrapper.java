package de.elderbyte.auctionhelper.model;

public class AuctionQueryWrapper {
    private AuctionQuery auctionQuery;
    private QueryResult queryResult;

    public AuctionQuery getAuctionQuery() {
        return auctionQuery;
    }

    public void setAuctionQuery(AuctionQuery auctionQuery) {
        this.auctionQuery = auctionQuery;
    }

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

}
