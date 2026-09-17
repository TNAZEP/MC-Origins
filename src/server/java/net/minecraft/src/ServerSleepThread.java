package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ServerSleepThread extends Thread {
	final MinecraftServer mc;

	public ServerSleepThread(MinecraftServer var1) {
		this.mc = var1;
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(2147483647L);
			} catch (InterruptedException var2) {
			}
		}
	}
}
