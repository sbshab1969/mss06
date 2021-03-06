package acp.forms;

import java.awt.*;

import javax.swing.*;

import acp.db.service.*;
import acp.forms.frame.*;
import acp.utils.*;

public class ConfigXml extends FrameEdit {
  private static final long serialVersionUID = 1L;

  private ConfigManager tableManager;

  private JTextArea txtConf = new JTextArea();
  private JScrollPane txtView = new JScrollPane(txtConf);

  public ConfigXml(ConfigManager tblManager) {
    setSize(700, 500);
    setResizable(true);

    tableManager = tblManager;

    initPnlData();
    initFormNone();
//    pack();
    setToCenter();
  }

  private void initPnlData() {
    pnlData.setLayout(new BorderLayout());
    pnlData.add(txtView, BorderLayout.CENTER);
  }
  
  protected void initFormBefore() {
    setTitle(Messages.getString("Title.DirectEdit"));
  }

  protected void clearData() {
    txtConf.setText(null);
  }

  protected boolean fillData() {
    if (act == ACT_EDIT) {
      String txtCfg = tableManager.getCfgString(recId);
      txtConf.setText(txtCfg);
    }  
    return true;
  }

  protected int saveObj() {
    int res = -1;
    if (act == ACT_EDIT) {
      String txtCfg = txtConf.getText();
      res = tableManager.updateCfgStr(recId, txtCfg);
    }
    return res;
  }

}
