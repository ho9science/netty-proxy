package com.samdasu.netty.server.daemon;

import com.samdasu.netty.server.SamdasuServer;

public class ServerLauncher {

	public static void main(String args[]) throws Exception {
		SamdasuServer server = new SamdasuServer();
		server.run();
	}
}
