package com.samdasu.netty.server.daemon;

import com.samdasu.netty.server.StopServer;

public class ShutdownServer {

	public static void main(String args[]) throws InterruptedException {
		new StopServer().run();
	}
}
