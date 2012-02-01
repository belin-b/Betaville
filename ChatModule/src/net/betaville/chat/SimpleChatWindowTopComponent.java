/** Copyright (c) 2008-2012, Brooklyn eXperimental Media Center
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Brooklyn eXperimental Media Center nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Brooklyn eXperimental Media Center BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.betaville.chat;

import net.betaville.chat.api.XMPPMessenger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//net.betaville.chat//SimpleChatWindow//EN",
autostore = false)
@TopComponent.Description(preferredID = "SimpleChatWindowTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionID(category = "Window", id = "net.betaville.chat.SimpleChatWindowTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SimpleChatWindowAction",
preferredID = "SimpleChatWindowTopComponent")
@Messages({
    "CTL_SimpleChatWindowAction=SimpleChatWindow",
    "CTL_SimpleChatWindowTopComponent=SimpleChatWindow Window",
    "HINT_SimpleChatWindowTopComponent=This is a SimpleChatWindow window"
})
public final class SimpleChatWindowTopComponent extends TopComponent {

    public SimpleChatWindowTopComponent() {
        initComponents();
        setName(Bundle.CTL_SimpleChatWindowTopComponent());
        setToolTipText(Bundle.HINT_SimpleChatWindowTopComponent());

        // create the lookup
        InstanceContent content = new InstanceContent();
        Lookup dynamicLookup = new AbstractLookup(content);
        associateLookup(dynamicLookup);
        
        // create the messenger and add it to the lookup
        XMPPMessenger messenger = new XMPPMessenger();
        content.add(messenger);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        currentMessage = new javax.swing.JTextArea();
        sendMessasgeButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatHistory = new javax.swing.JTextArea();

        currentMessage.setColumns(20);
        currentMessage.setRows(5);
        jScrollPane1.setViewportView(currentMessage);

        org.openide.awt.Mnemonics.setLocalizedText(sendMessasgeButton, org.openide.util.NbBundle.getMessage(SimpleChatWindowTopComponent.class, "SimpleChatWindowTopComponent.sendMessasgeButton.text")); // NOI18N
        sendMessasgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessasgeButtonActionPerformed(evt);
            }
        });

        chatHistory.setColumns(20);
        chatHistory.setRows(5);
        jScrollPane2.setViewportView(chatHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendMessasgeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sendMessasgeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendMessasgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessasgeButtonActionPerformed
        // Attempt to send an XMPP message
        XMPPMessenger messenger = getLookup().lookup(XMPPMessenger.class);
        if(messenger.isLoggedIn() && !currentMessage.getText().isEmpty()){
            System.out.println("sending message");
            try {
                messenger.sendMessage("someone@somewhere.com", currentMessage.getText(), new MessageListener() {

                    @Override
                    public void processMessage(Chat chat, Message msg) {
                        StringBuilder sb = new StringBuilder(chatHistory.getText());
                        sb.append("<hr /><br />");
                        sb.append("<strong>");
                        sb.append(msg.getFrom());
                        sb.append("</strong>");
                        sb.append("\t-\t");
                        sb.append(msg.getBody());
                        chatHistory.setText(sb.toString());
                    }
                });
                System.out.println("message sent");
            } catch (XMPPException ex) {
                // flash error for message not being able to be sent
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_sendMessasgeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatHistory;
    private javax.swing.JTextArea currentMessage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sendMessasgeButton;
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
}
