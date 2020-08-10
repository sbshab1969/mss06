package acp;

import javax.swing.*;
import java.awt.event.*;

import acp.db.*;
import acp.utils.*;

public class Main extends JFrame {
  private static final long serialVersionUID = 1L;
  
  private static Main mainFrame = null;
  private static JDesktopPane desktop = new JDesktopPane();

  public Main() {
    super(Messages.getString("Title.Main") + " (6)");
    setContentPane(desktop);
    setSize(1200, 700);
    setLocationRelativeTo(null); // размещение по центру экрана
    setExtendedState(MAXIMIZED_BOTH);
    // desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
    // desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    MainMenu mainMenu = new MainMenu();
    setJMenuBar(mainMenu.createMenuBar());

//    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
  }

  private void formWindowClosing(WindowEvent evt) {
//    System.out.println("formWindowClosing");
    DbConnect.disconnect();
  }

  public static void setTitle() {
    mainFrame.setTitle(Messages.getString("Title.Main"));
  }

  public static JDesktopPane getDesktop() {
    return desktop;
  }

  private static void createAndShowGUI() {
    // --- Установка L&F перед созданием формы ---
    // JFrame.setDefaultLookAndFeelDecorated(true);
    // JDialog.setDefaultLookAndFeelDecorated(true);
    // Установка Look and Feel
    //try {
    //  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // default
    //  // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    //  // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
    //  // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    //  // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //} catch (Exception e) {
    //  e.printStackTrace();
    //}
    // System.out.println(UIManager.getSystemLookAndFeelClassName());

    // java.util.Locale.setDefault(java.util.Locale.US);
    mainFrame = new Main();
    
    // --- Установка L&F после создания формы ---
    // mainFrame.setUndecorated(true);
    // mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

    // Если есть установки после создания формы, то setVisible после
    mainFrame.setVisible(true);
  }

//  private static void logonDirect() {
//    String dbIndex = "0";
//    String dbName = "oracle1.xml";
//    Properties props = DbConnect.loadXmlProps(dbName);
//    props.setProperty(DbConnect.DB_INDEX, dbIndex);
//    props.setProperty(DbConnect.DB_NAME, dbName);
//    // ------------------------------------
//    boolean res = DbConnect.connect(props);
//    // ------------------------------------
//    if (res == true) {
//      DialogUtils.infoDialog(Messages.getString("Message.ConnectOk")); ;
//    } else {
//      DialogUtils.errorMsg(Messages.getString("Message.ConnectError"));
//    }
//  }

  private static void logonForm() {
    Logon logon = new Logon();
    boolean resInit = logon.initForm();
    if (resInit) {
      logon.showForm();
    }  
    logon = null;
  }

  private static void logon() {
//    logonDirect();
    logonForm();
  }

  public static void main(String[] args) {
    createAndShowGUI();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        logon();
      }
    });
  }
  
}
