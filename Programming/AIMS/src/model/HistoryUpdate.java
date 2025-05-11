package model;

import java.util.*;
 
public class HistoryUpdate {
    private int historyID;
    private Date time;
    private boolean isUpdatePrice;
    private int productID;

    // In‑memory store for all updates (shared across instances)
    private static final List<HistoryUpdate> STORE = new ArrayList<>();

    public HistoryUpdate() { }

    public HistoryUpdate(int historyID, Date time, boolean isUpdatePrice, int productID) {
        this.historyID      = historyID;
        this.time           = time;
        this.isUpdatePrice  = isUpdatePrice;
        this.productID      = productID;
    }

    public void recordUpdate(int productID, boolean isUpdatePrice) {
        HistoryUpdate h = new HistoryUpdate(
            STORE.size() + 1,
            new Date(),
            isUpdatePrice,
            productID
        );
        STORE.add(h);
    }

    public List<HistoryUpdate> getUpdateHistory(int productID) {
        List<HistoryUpdate> list = new ArrayList<>();
        for (HistoryUpdate h : STORE) {
            if (h.productID == productID) {
                list.add(h);
            }
        }
        return list;
    }

    public HistoryUpdate getLastUpdate(int productID) {
        for (int i = STORE.size() - 1; i >= 0; i--) {
            HistoryUpdate h = STORE.get(i);
            if (h.productID == productID) {
                return h;
            }
        }
        return null;
    }

    public void deleteHistory(int productID) {
        STORE.removeIf(h -> h.productID == productID);
    }

    public boolean isUpdatePrice() {
        return isUpdatePrice;
    }
}
