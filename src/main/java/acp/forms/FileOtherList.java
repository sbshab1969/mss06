package acp.forms;

import acp.db.service.*;
import acp.forms.dm.*;
import acp.forms.frame.*;
import acp.utils.*;

public class FileOtherList extends FrameList {
  private static final long serialVersionUID = 1L;
  private int fileId;

  private FileOtherManager tableManager;
  private SelectManager selectMng;
  private DmFormList dmForm;

  public FileOtherList(int file_id) {
    fileId = file_id;
    if (fileId > 0) {
      setTitle(Messages.getString("Title.AdvFileInfo"));
      setSize(1000, 500);
    } else {
      setTitle(Messages.getString("Title.OtherLogs"));
      setSize(1200, 650);
    }
    setToCenter();
    setMaximizable(true);
    setResizable(true);

    // Filter ---
    setFilterVisible(false);

    // Table ---
    tableManager = new FileOtherManager(fileId);
    selectMng = new SelectManager();
    dmForm = new DmFormList(selectMng);
    pnlTable.setModel(dmForm);
    String[] fieldnames = tableManager.getFieldnames();
    pnlTable.setHeaders(fieldnames);
    if (fileId > 0) {
      pnlTable.setModePage(false);
    } else {
      pnlTable.setModePage(true);
      pnlTable.setRecPerPage(30);
    }
  }

  protected void initTable() {
    String query = tableManager.selectList();
    String queryCnt = tableManager.selectCount();
    selectMng.setQuery(query);
    selectMng.setQueryCnt(queryCnt);
    pnlTable.queryTable(pnlTable.NAV_FIRST);
    if (fileId != 0) {
      pnlTable.selectRow(-1);
    }
  }

  public void initForm() {
    initTable();
  }
}
