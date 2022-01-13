package sk.hotelclientapplication.view;

import javax.swing.*;

public class ManagerView extends JFrame {

    private JTabbedPane jTabbedPane;
    private JPanel jPanelFirst;
    private JPanel jPanelSecond;
    private JPanel jPanelThird;

    public ManagerView() {

        this.setSize(1200, 1200);


        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelFirst = new javax.swing.JPanel();
        jPanelSecond = new javax.swing.JPanel();
        jPanelThird = new javax.swing.JPanel();


        HotelsView hotelsView = new HotelsView();
        jPanelFirst.setLayout(null);
        jPanelFirst.add(hotelsView);

        jTabbedPane.addTab("tab1", jPanelFirst);

        jPanelSecond.setLayout(null);
        jTabbedPane.addTab("tab2", jPanelSecond);

        jPanelThird.setLayout(null);
        jTabbedPane.addTab("tab3", jPanelThird);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane)
        );

        pack();


    }




}
