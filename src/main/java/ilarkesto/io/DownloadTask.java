package ilarkesto.io;

import ilarkesto.base.Bytes;
import ilarkesto.concurrent.ATask;

public class DownloadTask extends ATask {

	private long totalBytes;
	private Bytes bytesDownloaded = new Bytes(0);

	// --- dependencies ---

	private String url;
	private String username;
	private String password;
	private String destinationPath;

	public DownloadTask(String url, String destinationPath) {
		this.url = url;
		this.destinationPath = destinationPath;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// --- ---

	@Override
	protected void perform() {
		IO.downloadUrlToFile(url, destinationPath, username, password, new Observer());
	}

	@Override
	public float getProgress() {
		long downloaded = bytesDownloaded.toLong();
		if (totalBytes > 0 && downloaded > 0) {
			return (float) downloaded / (float) totalBytes;
		} else {
			return super.getProgress();
		}
	}

	@Override
	public String getProgressMessage() {
		return url + " (" + bytesDownloaded.toRoundedString() + ")";
	}

	class Observer implements IO.CopyObserver {

		public void totalSizeDetermined(long bytes) {
			totalBytes = bytes;
		}

		public void dataCopied(long bytes) {
			bytesDownloaded = new Bytes(bytesDownloaded.toLong() + bytes);
		}

		public boolean isAbortRequested() {
			return DownloadTask.this.isAbortRequested();
		}

	}

}
