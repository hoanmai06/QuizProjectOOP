package GUIs;

import Algorithms.read_docx;
import Algorithms.read_txt;
import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Question;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUI21 extends DefaultJFrame implements DropTargetListener {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JTabbedPane tabbedPane;
    private JComboBox categoryComboBox;
    private JCheckBox alsoShowQuestionsFromCheckBox;
    private JCheckBox alsoShowOldQuestionCheckBox;
    private JButton CREATENEWQUESTIONButton;
    private JTable questionTable;
    private JTextField categoryNameTextField;
    private JButton ADDCATEGORYButton;
    private JTextArea categoryInfoTextArea;
    private JButton CHOOSEAFILEButton;
    private JPanel dashedBorderPanel;
    private JTextField categoryIDNumberTextField;
    private JButton IMPORTButton;
    private JComboBox parentCategoryComboBox;
    private JLabel fileNameLabel;
    private JLabel dropfilename;
    private JLabel IT;
    private DropTarget dt;
    private String idFile = "";

    private String fileFormat = "";

    public GUI21(int width, int height) {
        super(width, height);
        categoryComboBox.setSelectedIndex(0);
        setContentPane(guiPanel);
        setVisible(true);

        dashedBorderPanel.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 7, 3));
        dt = new DropTarget(dashedBorderPanel, this);

        CHOOSEAFILEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog((Frame) null, "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
                // fd nay la mot trang so ra khi bam vao nut choose a file, chu khong phai la con tro tro toi file vua chon

                if (fd.getDirectory() != null) {
                    File selected = new File(fd.getDirectory());
                    idFile = selected.getAbsolutePath() + File.separator + fd.getFile();
                    dropfilename.setText(idFile);                                           // dong bo Jlabel ten file o DropFile area

                    String[] end = fd.getFile().split("\\.");
                    fileFormat = end[end.length - 1];

                    fileNameLabel.setText(fd.getFile());
                }
            }
        });
        IMPORTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileFormat.equals("txt") || fileFormat.equals("doc") || fileFormat.equals("docx")) {
                    ArrayList<Question> listQ = new ArrayList<>();
                    if(fileFormat.equals("txt") ) {
                        read_txt rt = new read_txt();
                        listQ = rt.readFromTXT(idFile);
                    }
                    if(fileFormat.equals("docx")) {
                        read_docx rd = new read_docx();
                        listQ = rd.readFromDOCX(idFile);
                    }
                    if(listQ.size()>0) {
                        for(Question q : listQ) {
                            CategoriesSingleton.getInstance().getCategories().get(categoryComboBox.getSelectedIndex()).addQuestion(q);
                        }
                        JFrame Sframe = new JFrame("SuccessMessage");
                        JOptionPane.showMessageDialog(Sframe, "Success "+listQ.size() +" questions", "Valid input", JOptionPane.INFORMATION_MESSAGE);

                        JFrame newGUI21 = new GUI21(getWidth(), getHeight());
                        dispose();
                    }
                }
                else {
                    JFrame FBframe = new JFrame("Format_Problems");
                    JOptionPane.showMessageDialog(FBframe, "Wrong Format", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        CREATENEWQUESTIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI32Add(1024, 768, categoryComboBox.getSelectedIndex());
            }
        });
        ADDCATEGORYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categoryNameTextField.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Category name is required.");
                else {
                    Category new_category = new Category();

                    new_category.setCategoryName(categoryNameTextField.getText());
                    new_category.setCategoryInfo(categoryInfoTextArea.getText());
                    new_category.setIdNumber(categoryIDNumberTextField.getText());

                    categoryNameTextField.setText("");
                    categoryInfoTextArea.setText("");
                    categoryIDNumberTextField.setText("");

                    CategoriesSingleton.getInstance().addCategory(new_category);
                    categoryComboBox.addItem(String.format("%s (0)", new_category.getCategoryName()));
                    JOptionPane.showMessageDialog(null, String.format("Added category \"%s\"", new_category.getCategoryName()));
                }
            }
        });
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = CategoriesSingleton.getInstance().getCategories().get(selectedIndex);

                QuestionTableModel questionTableModel = (QuestionTableModel) questionTable.getModel();
                questionTableModel.setRowCount(0);
                for (Question question : selectedCategory.getQuestions()) {
                    questionTableModel.addRow(question.getGUI21QuestionTableRow());
                }
            }
        });

        Action edit = new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                dispose();
                new GUI32Edit(getWidth(), getHeight(), categoryComboBox.getSelectedIndex(), row);
            }
        };

        new EditButtonColumn(questionTable, edit, 2);
        IT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new GUI11(getWidth(), getHeight());
                dispose();
            }
        });
    }

    public GUI21(int width, int height, int categoryIndex) {
        this(width, height);
        categoryComboBox.setSelectedIndex(categoryIndex);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null)
            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI21(1024, 768);
            }
        });
    }

    private void createUIComponents() {
        //categoryComboBox
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());

        // questionTable
        questionTable = new QuestionTable(CategoriesSingleton.getInstance().getCategories().get(0));

    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    java.util.List list = (List) tr.getTransferData(flavors[i]);
                    idFile = list.get(0) + "";
                    dropfilename.setText(idFile);
                    String[] last = idFile.split("\\.");
                    fileFormat = last[last.length-1];
                    dtde.dropComplete(true);

                    String[] idFileName = idFile.split("\\\\");                 // dong bo JLabel ten file o ChooseAfile
                    fileNameLabel.setText(idFileName[idFileName.length-1]);
                }
            }
            dtde.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }
}