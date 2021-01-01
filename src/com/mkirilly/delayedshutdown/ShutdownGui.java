/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkirilly.delayedshutdown;

import java.awt.event.WindowEvent;

/**
 *
 * @author Miki
 */
public class ShutdownGui extends javax.swing.JFrame {

    private int minutes;
    private Shutdowner sd;
    /**
     * Creates new form ShutdownGui
     */
    public ShutdownGui() {
        initComponents();
        minutes = 30;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSliderMins = new javax.swing.JSlider();
        jTextFieldMins = new javax.swing.JTextField();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jCheckBoxHibernate = new javax.swing.JCheckBox();
        jCheckBoxWarn = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kikapcs");
        setResizable(false);

        jSliderMins.setValue(30);
        jSliderMins.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderMinsStateChanged(evt);
            }
        });

        jTextFieldMins.setEditable(false);
        jTextFieldMins.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldMins.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldMins.setText("30min");
        jTextFieldMins.setToolTipText("shutdown in 30 min");

        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonStop.setText("Stop");
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        jCheckBoxHibernate.setText("Hibernate");
        jCheckBoxHibernate.setToolTipText("hibernate instead of shutdown");

        jCheckBoxWarn.setText("Warn me");
        jCheckBoxWarn.setToolTipText("Warn me before shutting down");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonOk)
                .addGap(18, 18, 18)
                .addComponent(jButtonStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTextFieldMins)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBoxHibernate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxWarn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSliderMins, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxHibernate)
                    .addComponent(jCheckBoxWarn))
                .addGap(18, 18, 18)
                .addComponent(jSliderMins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldMins, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonStop))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        // start shutdown mechanism
        if (sd == null)
        {
            sd = new Shutdowner(this);
        } else {
            sd.stop();
        }

        try {
            sd.sdInMinutes(minutes,
                    jCheckBoxHibernate.isSelected(),
                    jCheckBoxWarn.isSelected());
        }
        catch (Exception e) {
        }
        
        // disable OK button, slider and change on settings
        jButtonOk.setEnabled(false);
        jSliderMins.setEnabled(false);
        jCheckBoxHibernate.setEnabled(false);
        jCheckBoxWarn.setEnabled(false);
        // enable the stop button
        jButtonStop.setEnabled(true);
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jSliderMinsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderMinsStateChanged
        
        // we must synchronize this because the timer job also touches
        // 'minutes' and executes setMinutesText()
        synchronized (this) {
            int minsSliderValue = jSliderMins.getValue();
            
            // change minutes' value based on the different scale of the slider
            if (minsSliderValue <= 30) {
                // don't change it, let's go with one minute per tick
                minutes = minsSliderValue;
            }
            else if (minsSliderValue <= 60) {
                // then 2 mins / tick
                minutes = 30 + (minsSliderValue - 30)*2;
            }
            else {
                // then 3 mins / tick
                minutes = 120 + (minsSliderValue - 60)*3;
            }

            setMinutesText();
        }
    }//GEN-LAST:event_jSliderMinsStateChanged

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // close the window
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        if (sd != null) {
            sd.stop();
        }
        // disable this button (Stop)
        jButtonStop.setEnabled(false);
        // enable the OK button to start a new countdown
        jButtonOk.setEnabled(true);
        jSliderMins.setEnabled(true);
        jCheckBoxHibernate.setEnabled(true);
        jCheckBoxWarn.setEnabled(true);
    }//GEN-LAST:event_jButtonStopActionPerformed

    /**
     * Update minute countdown
     * @param newMinutes new number of minutes until shutdown
     */
    public void minCountdown(int newMinutes) {
        // TODO syncronize

        if (newMinutes == 0) {
//            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else {
            // set slider
            int slideValue;
            if (newMinutes < 30) {
                slideValue = newMinutes;
            } else if (newMinutes < 90) {
                slideValue = 30 + (newMinutes-30)/2;
            } else {
                slideValue = 60 + (newMinutes-90)/3;
            }
            
            synchronized (this) {
                jSliderMins.setValue(slideValue);

                // set member
                minutes = newMinutes;
                // set display
                setMinutesText();
            }
        }
    }
    
    private void setMinutesText() {
        // Convert to hours + minutes.
        // Use integer division, it truncates.
        int hoursToDisplay = minutes/60;
        int minsToDisplay = minutes - hoursToDisplay*60;

        // create a string
        StringBuilder sb = new StringBuilder();
        if (hoursToDisplay > 0) {
            sb.append(hoursToDisplay).append("h ");
        }
        if ((minsToDisplay > 0) || (hoursToDisplay == 0))
        {
            sb.append(minsToDisplay).append("min");
        }
        jTextFieldMins.setText(sb.toString());
        
        jTextFieldMins.setToolTipText(sb.append(" until shutdown").toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JCheckBox jCheckBoxHibernate;
    private javax.swing.JCheckBox jCheckBoxWarn;
    private javax.swing.JSlider jSliderMins;
    private javax.swing.JTextField jTextFieldMins;
    // End of variables declaration//GEN-END:variables
}