package acp.forms.dm;

import java.util.ArrayList;
import java.util.List;

import acp.db.service.SelectManager;
import acp.forms.frame.*;

public class DmFormList extends DmPanel {
  private static final long serialVersionUID = 1L;

  private SelectManager tblMng;
  List<String[]> cache;

  public DmFormList(SelectManager tbMng) {
    tblMng = tbMng;
    cache = new ArrayList<>();
  }

  public int getRowCount() {
    return cache.size();
  }

  public Object getValueAt(int row, int col) {
    return cache.get(row)[col];
  }

  public void calcPageCount() {
    setRecCount(tblMng.countRecords());
    super.calcPageCount();
  }

  public void fetchPage(int page) {
    int startRec = calcStartRec(page);
    cache = tblMng.queryPartMove(startRec,recPerPage);
    fireTableChanged(null);
  }

  public void queryPage() {
    calcPageCount();
    if (currPage > pageCount) {
      currPage = pageCount;
    }  
    int startRec = calcStartRec(currPage);
    // --------------------
    cache = tblMng.queryPartOpen(startRec,recPerPage);
    // --------------------
    if (headers == null) {
      setHeaders(tblMng.getHeaders());
    }
    fireTableChanged(null);
  }

  public void queryAll() {
    // --------------------
    cache = tblMng.queryAll();
    // --------------------
    if (headers == null) {
      setHeaders(tblMng.getHeaders());
    }
    fireTableChanged(null);
  }

  public void closeQuery() {
    tblMng.closeCursor();
  }

}
