package acp.forms;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import acp.db.service.*;
import acp.forms.dm.*;
import acp.forms.frame.*;
import acp.forms.ui.*;
import acp.forms.utils.*;
import acp.utils.*;

public class XmlTableList extends FrameList {
  private static final long serialVersionUID = 1L;

  private ToptionManager tableManager;
  private SelectManager selectMng;
  private DmFormList dmForm;

  private JPanel pnlFilter_1 = new JPanel();
  private JPanel pnlFilter_2 = new JPanel();
  private JPanel pnlBtnFilter = new JPanel();

  private JLabel lblSource = new JLabel(Messages.getString("Column.SourceName"),
      JLabel.TRAILING);
  private JComboBox<CbClass> cbdbSource;
  private CbModelDb cbdbSourceModel;

  public XmlTableList(String keyTitle, String path, ArrayList<String> attrs) {
    setTitle(FieldConfig.getString(keyTitle));
    setSize(640, 480);
    setToCenter(); // метод из MyInternalFrame
    setMaximizable(true);
    setResizable(true);

    // Filter ---
    setFilterVisible(true);
    initFilter();

    // --- Table ---
    tableManager = new ToptionManager(path, attrs);
    selectMng = new SelectManager();
    dmForm = new DmFormList(selectMng);
    pnlTable.setModel(dmForm);
    String[] fieldnames = tableManager.getFieldnames();
    pnlTable.setHeaders(fieldnames);
//  pnlTable.setModePage(true);
//  pnlTable.setRecPerPage(3);

    // Buttons ---
    pnlBtnRecord.add(btnEdit);
    pnlBtnAct.add(btnRefresh);
  }

  private void initFilter() {
    pnlFilter.setBorder(new LineBorder(Color.BLACK));
    pnlFilter.setLayout(new BorderLayout());

    cbdbSourceModel = new CbModelDb();
    cbdbSourceModel.setNeedNullItem(true);
    cbdbSource = new JComboBox<CbClass>(cbdbSourceModel);
    lblSource.setLabelFor(cbdbSource);

    pnlFilter_1.setLayout(new SpringLayout());
    // pnlFilter_1.setBorder(new LineBorder(Color.BLACK));
    pnlFilter_1.add(lblSource);
    pnlFilter_1.add(cbdbSource);
    SpringUtilities.makeCompactGrid(pnlFilter_1, 1, 2, 8, 8, 8, 8);

    pnlBtnFilter.setLayout(new GridLayout(1, 2, 5, 5));
    pnlBtnFilter.add(btnFilter);
    pnlBtnFilter.add(btnFltClear);

    pnlFilter_2.setLayout(new FlowLayout());
    pnlFilter_2.add(pnlBtnFilter);

    pnlFilter.add(pnlFilter_1, BorderLayout.CENTER);
    pnlFilter.add(pnlFilter_2, BorderLayout.EAST);
  }
  
  protected void createTable(long src) {
    tableManager.createTable(src);
    initTable();
  }

  protected void initTable() {
    String query = tableManager.selectList();
    String queryCnt = tableManager.selectCount();
    selectMng.setQuery(query);
    selectMng.setQueryCnt(queryCnt);
    pnlTable.queryTable(pnlTable.NAV_FIRST);
  }

  public void initForm() {
    String queryCbdb = tableManager.selectSources();
    cbdbSourceModel.executeQuery(queryCbdb);
    cbdbSource.setSelectedIndex(-1);
    // -----------
    applyFilter();
    // -----------
  }

  protected void applyFilter() {
    int index = cbdbSource.getSelectedIndex();
    int keyInt = cbdbSourceModel.getKeyIntAt(index);
    createTable(keyInt);
  }
  
  protected void cancelFilter() {
    clearFilter();
    applyFilter();
  }

  protected void clearFilter() {
    cbdbSource.setSelectedIndex(-1);
  }

  protected void editRecord(int act, int recId) {
    XmlTableEdit xmlEdit = new XmlTableEdit(tableManager);
    // boolean resInit = false;
    boolean resInit = xmlEdit.initForm(act, recId);
    if (resInit) {
      xmlEdit.showForm();
      int resForm = xmlEdit.getResultForm();
      if (resForm == RES_OK) {
        pnlTable.refreshTable(pnlTable.NAV_CURRENT);
      }
    }
    xmlEdit = null;
  }
}
