/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.betaville.usercontrol;

import edu.poly.bxmc.betaville.model.City;
import edu.poly.bxmc.betaville.model.Wormhole;
import edu.poly.bxmc.betaville.net.NetPool;
import edu.poly.bxmc.betaville.SceneScape;
import edu.poly.bxmc.betaville.SettingsPreferences;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//net.betaville.usercontrol//SelectCity//EN",
autostore = false)
@TopComponent.Description(preferredID = "SelectCityTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "net.betaville.usercontrol.SelectCityTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SelectCityAction",
preferredID = "SelectCityTopComponent")
@Messages({
    "CTL_SelectCityAction=SelectCity",
    "CTL_SelectCityTopComponent=SelectCity Window",
    "HINT_SelectCityTopComponent=This is a SelectCity window"
})
public final class SelectCityTopComponent extends TopComponent {

    private List<Wormhole> locations = null;

    public SelectCityTopComponent() {
	initComponents();
	setName(Bundle.CTL_SelectCityTopComponent());
	setToolTipText(Bundle.HINT_SelectCityTopComponent());
	putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        cityList = new javax.swing.JList();
        populateCityList();
        selectButton = new javax.swing.JButton();

        cityList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(cityList);

        org.openide.awt.Mnemonics.setLocalizedText(selectButton, org.openide.util.NbBundle.getMessage(SelectCityTopComponent.class, "SelectCityTopComponent.selectButton.text")); // NOI18N
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(selectButton)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
	// Set the currently selected City
	try {
	    Wormhole location = locations.get(cityList.getSelectedIndex());
	    String[] cityInfo = NetPool.getPool().getConnection().findCityByID(location.getCityID());
	    City city = new City(cityInfo[0], cityInfo[1], cityInfo[2]);
	    SceneScape.setUTMZone(location.getLocation().getLonZone(), location.getLocation().getLatZone());
	    SceneScape.addCityAndSetToCurrent(city);
	} catch (UnknownHostException ex) {
	    DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message("Could not connect to server"));
	    cityList.setModel(new DefaultListModel());
	} catch (IOException ex) {
	    DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message("Error retrieving cities city data from the server"));
	    cityList.setModel(new DefaultListModel());
	}
    }//GEN-LAST:event_selectButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList cityList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
	// TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
	// TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
	// better to version settings since initial version as advocated at
	// http://wiki.apidesign.org/wiki/PropertyFiles
	p.setProperty("version", "1.0");
	// TODO store your settings
    }

    void readProperties(java.util.Properties p) {
	String version = p.getProperty("version");
	// TODO read your settings according to their version
    }

    private void populateCityList() {
	try {
	    // Disable the select button
	    selectButton.setEnabled(false);

	    // get the wormholes from the server and put them into a list
	    locations = NetPool.getPool().getConnection().getAllWormholes();
	    DefaultListModel dlm = new DefaultListModel();

	    for (Wormhole location : locations) {
		String[] cityInfo = NetPool.getPool().getConnection().findCityByID(location.getCityID());
		dlm.addElement(((cityInfo != null) ? cityInfo[0] + " - " : "") + location.getName());
	    }

	    cityList.setModel(dlm);
	    cityList.addListSelectionListener(new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent lse) {
		    // enable the button whenever a city is selected
		    selectButton.setEnabled(true);
		}
	    });
	} catch (UnknownHostException ex) {
	    DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message("Could not connect to server"));
	    cityList.setModel(new DefaultListModel());
	} catch (IOException ex) {
	    DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message("Error retrieving cities from the server"));
	    cityList.setModel(new DefaultListModel());
	}
    }
}
