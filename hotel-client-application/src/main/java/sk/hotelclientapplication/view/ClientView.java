package sk.hotelclientapplication.view;

import javax.swing.*;
import java.io.IOException;

public class ClientView extends  JFrame{

    private JTabbedPane jTabbedPane;
    private JPanel jPanelFirst;
    private JPanel jPanelSecond;
    private JPanel jPanelThird;

    public ClientView() {

        this.setSize(1200, 1200);


        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelFirst = new javax.swing.JPanel();
        jPanelSecond = new javax.swing.JPanel();
        jPanelThird = new javax.swing.JPanel();


//        try {
//            jPanelFirst.add(new DetailsView());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        jTabbedPane.addTab("tab1", jPanelFirst);
//        jPanelFirst.setVisible(true);

        DetailsView detailsView = null;
        try {
            detailsView = new DetailsView();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }

        jTabbedPane.addTab("Detalji" , detailsView);

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
