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
package net.betaville.chat.api;

import org.jivesoftware.smack.*;

/**
 *
 * @author Skye Book
 */
public class XMPPMessenger {

    private Connection connection;
    private boolean loggedIn = false;

    public XMPPMessenger() {
    }

    public void login(String server, String user, String password) throws XMPPException {
        if (!isLoggedIn()) {
            ConnectionConfiguration config = new ConnectionConfiguration(server, 5222, "gmail.com");
            connection = new XMPPConnection(config);
            connection.connect();
            connection.login(user, password);
            loggedIn = true;
        }
    }

    public void logout() {
        if (isLoggedIn()) {
            connection.disconnect();
            loggedIn = false;
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void sendMessage(String recipient, String message, MessageListener messageListener) throws XMPPException {
        Chat chat = connection.getChatManager().createChat(recipient, messageListener);
        chat.sendMessage(message);
    }
}
