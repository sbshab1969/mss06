package acp.forms;

import javax.swing.*;

import acp.db.domain.*;
import acp.db.service.*;
import acp.forms.frame.*;
import acp.forms.utils.*;
import acp.utils.*;

public class SourceEdit extends FrameEdit {
  private static final long serialVersionUID = 1L;

  private SourceManager tableManager;

  private JLabel lblName = new JLabel(Messages.getString("Column.Name"),
      JLabel.TRAILING);
  private JTextField txtName = new JTextField(20);

  public SourceEdit(SourceManager tblManager) {
    tableManager = tblManager;

    initPnlData();
    initFormNone();
    pack();
    setToCenter();
  }

  private void initPnlData() {
    pnlData.setLayout(new SpringLayout());
    pnlData.add(lblName);
    pnlData.add(txtName);
    lblName.setLabelFor(txtName);
    SpringUtilities.makeCompactGrid(pnlData, 1, 2, 10, 10, 10, 10);
  }
  
  protected void setEditableData(int act) {
    if (act == ACT_NEW) {
      txtName.setEditable(true);
    } else if (act == ACT_EDIT) {
      txtName.setEditable(true);
    } else {
      txtName.setEditable(false);
    }
  }

  protected void clearData() {
    txtName.setText("");
  }

  protected boolean fillData() {
    if (act == ACT_EDIT) {
      SourceClass recObj = tableManager.select(recId);
      if (recObj == null) {
        return false;
      }  
      // ------------------------------
      txtName.setText(recObj.getName());
      // ------------------------------
    }  
    return true;
  }

  protected boolean validateData() {
    String vName = txtName.getText();
    // --------------------
    if (vName.equals("")) {
      DialogUtils.errorMsg(Messages.getString("Message.IsEmpty") + ": "
          + Messages.getString("Column.Name"));
      return false;
    }
    // --------------------
    return true;
  }

  protected SourceClass getObj() {
    String vName = txtName.getText();
    // --------------------
    SourceClass formObj = new SourceClass();
    formObj.setId(recId);
    formObj.setName(vName);
    // --------------------
    return formObj;
  }

  protected int saveObj() {
    int res = -1;
    SourceClass formObj = getObj();
    if (act == ACT_NEW) {
      res = tableManager.insert(formObj);
    } else if (act == ACT_EDIT) {
      res = tableManager.update(formObj);
    }
    return res;
  }

}
