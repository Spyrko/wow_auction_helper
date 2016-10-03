package de.elderbyte.auctionhelper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Color;

import de.elderbyte.auctionhelper.gui.MainWindow;
import de.elderbyte.auctionhelper.model.Auction;
import de.elderbyte.auctionhelper.model.Auction.AuctionLength;
import de.elderbyte.auctionhelper.model.AuctionQuery;
import de.elderbyte.auctionhelper.model.AuctionQueryWrapper;

public class DataActualizer implements Runnable {
    int s = -1;
    private boolean fail = false;
    private MainWindow window;
    private String serverName;
    private String characterName;
    private long itemId;
    private String basic1 = "https://eu.api.battle.net/wow/auction/data/";
    private String basic2 = "?locale=de_DE&apikey=d2x44hvjy8hjpdx353rh4y5brh2uqfuc";
    private String basicUrl;
    private Map<Integer, String> itemNames = new HashMap<Integer, String>();
    private Map<AuctionLength, String> timeNames = new HashMap<AuctionLength, String>();

    public DataActualizer(MainWindow window) {
        this.window = window;
        characterName = window.getCharactername().getText();
        serverName = window.getServer().getText();
        basicUrl = basic1 + serverName + basic2;
        if (window.getItemId().getText().equals("")) {
            itemId = -1;
        } else {
            try {
                itemId = Long.parseLong(window.getItemId().getText());
            } catch (NumberFormatException e) {
                setAlertField("Item ID ungültig", -2);
                fail = true;
                return;
            }
        }

        fillMaps();
    }

    @Override
    public void run() {
        // try {
        // Thread.sleep(3000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        if (fail)
            return;
        setAlertField("Updating", 0);
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                window.getTable().clearAll();
                window.getTable().setItemCount(0);
            }
        });

        AuctionQueryHelper aqh = new AuctionQueryHelper(basicUrl);
        AuctionQueryWrapper wrappedQuery;
        try {
           wrappedQuery = aqh.getAuctions();
        } catch (NullPointerException e) {
            fail = true;
            setAlertField("Servername Fehlerhaft", -2);
            return;
        }
        AuctionQuery query = wrappedQuery.getAuctionQuery();

        List<Auction> auctions;
        Map<Integer, Long> playerPrices = new HashMap<Integer, Long>();

        if (!characterName.equals("")) {
            if (itemId != -1) {
                for (Auction auc : query.getAuctions()) {
                    if (auc.getItem() == itemId) {
                        if (auc.getOwner().toLowerCase().equals(characterName.toLowerCase())
                                && auc.getOwnerRealm().toLowerCase().equals(serverName.toLowerCase())) {
                            if (!playerPrices.containsKey(auc.getItem())) {
                                playerPrices.put(auc.getItem(), auc.getBuyout()/auc.getQuantity());
                            } else {
                                if (playerPrices.get(auc.getItem()) > auc.getBuyout()/auc.getQuantity()) {
                                    playerPrices.put(auc.getItem(), auc.getBuyout()/auc.getQuantity());
                                }
                            }
                        }
                    }
                }
            } else {
                for (Auction auc : query.getAuctions()) {
                    if (auc.getOwner().toLowerCase().equals(characterName.toLowerCase())
                            && auc.getOwnerRealm().toLowerCase().equals(serverName.toLowerCase())) {
                        if (!playerPrices.containsKey(auc.getItem())) {
                            playerPrices.put(auc.getItem(), auc.getBuyout()/auc.getQuantity());
                        } else {
                            if (playerPrices.get(auc.getItem()) > auc.getBuyout()/auc.getQuantity()) {
                                playerPrices.put(auc.getItem(), auc.getBuyout()/auc.getQuantity());
                            }
                        }
                    }
                }
            }
            
            auctions = new ArrayList<Auction>();
            for (Auction auc : query.getAuctions()) {
                if (playerPrices.containsKey(auc.getItem()) && auc.getBuyout()/auc.getQuantity() < playerPrices.get(auc.getItem())) {
                    auctions.add(auc);
                }
            }
        } else {
            auctions = new ArrayList<Auction>();
            for (Auction auc : query.getAuctions()) {
                if (itemId == -1 || auc.getItem() == itemId)
                    auctions.add(auc);
            }
        }
        Map<Integer, Long> highestDifference = new HashMap<Integer, Long>();
        int lowPriceItemCount = 0;
        for (Auction auc : auctions) {
            lowPriceItemCount += auc.getQuantity();
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    String itemName = !itemNames.containsKey(auc.getItem()) ? auc.getItem() + ""
                            : itemNames.get(auc.getItem());
                    TableItem tableItem = new TableItem(window.getTable(), SWT.NONE);
                    long buyout = auc.getBuyout();
                    long difference = playerPrices.containsKey(auc.getItem())
                            ? playerPrices.get(auc.getItem()) - (auc.getBuyout()/auc.getQuantity()) : 0;
                    if(!highestDifference.containsKey(auc.getItem()) || highestDifference.get(auc.getItem()) < difference)
                    {
                        highestDifference.put(auc.getItem(), difference);
                    }
                            
                    tableItem.setText(new String[] { itemName + " (" + auc.getItem() + ")", auc.getOwner() + "-" + auc.getOwnerRealm(),
                            timeNames.get(auc.getTimeLeft()), auc.getQuantity() + "",
                            longToGold(buyout / auc.getQuantity()) + " (" + longToGold(buyout) + ")",
                            longToGold(difference) + " (" + longToGold(difference * auc.getQuantity()) + ") " + (difference * 100) / playerPrices.get(auc.getItem()) + "%"});
                }
            });
        }
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                LocalDateTime date = Instant.ofEpochMilli(wrappedQuery.getQueryResult().getFiles()[0].getLastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                String day = (date.getDayOfMonth() < 10 ? "0" : "") + date.getDayOfMonth();
                String month = (date.getMonthValue() < 10 ? "0" : "") + date.getMonthValue();
                String hour = (date.getHour() < 10 ? "0" : "") + date.getHour();
                String minute = (date.getMinute() < 10 ? "0" : "") + date.getMinute();
                String second = (date.getSecond() < 10 ? "0" : "") + date.getSecond();
                window.getDate().setText(day + "." + month + "." + date.getYear() + " " + hour + ":" + minute + ":" + second);
            }
        });
        
        boolean highDifference = false;
        for(int itemId : highestDifference.keySet())
        {
            highDifference = highDifference || (highestDifference.get(itemId) * 100) / playerPrices.get(itemId) > 10;
        }
        
        if (auctions.size() == 0 || characterName.equals(""))
            setAlertField("Keine Probleme", 1);       
        else if (highDifference || lowPriceItemCount > 200)
            setAlertField(auctions.size() + "x Unterboten (" + lowPriceItemCount + " Items)", -1);   
        else
            setAlertField(auctions.size() + "x Unterboten (" + lowPriceItemCount + " Items)", 0);
        Display.getDefault().timerExec(60000, this);
    }

    private String longToGold(long i) {
        String result = "";
        result += i / 10000;
        result += ",";
        if (((i / 10000) * 100) != 0)
            result += (i / 100) % ((i / 10000) * 100);
        else
            result += i / 100;

        return result;
    }

    private void setAlertField(String message, int mode) {
        Color color;
        switch (mode) {
        case 0:
            color = SWTResourceManager.getColor(SWT.COLOR_YELLOW);
            break;
        case 1:
            color = SWTResourceManager.getColor(SWT.COLOR_GREEN);
            break;
        case -1:
            color = SWTResourceManager.getColor(SWT.COLOR_RED);
            break;
        default:
            color = SWTResourceManager.getColor(SWT.COLOR_GRAY);
        }
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                window.getAlertBar().setBackground(color);
                window.getAlertBarText().setBackground(color);
                window.getAlertBarText().setText(message);
                window.getAlertBar().getParent().layout();
            }
        });
    }

    private void fillMaps() {
        timeNames.put(AuctionLength.VERY_LONG, "Sehr Lang");
        timeNames.put(AuctionLength.LONG, "Lang");
        timeNames.put(AuctionLength.VERY_SHORT, "Sehr Kurz");
        timeNames.put(AuctionLength.SHORT, "Kurz");
        timeNames.put(AuctionLength.MEDIUM, "Mittel");

        itemNames.put(124101, "Aethril");
        itemNames.put(124115, "Sturmschuppe");
        itemNames.put(124441, "Leylichtsplitter");
    }

    public void stop() {
        fail = true;
    }

}
