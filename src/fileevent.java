
import java.io.Serializable;
public class fileevent implements Serializable {
		
	private static final long serialVersionUID = 1L;

	private String destinationDirectory;
	private String sourceDirectory;
	private String filename;
	private double fileSize;
	private byte[] fileData;
	private String status;
	private String size;

	public String getDestinationDirectory() {
	return destinationDirectory;
	}

	/*public void setDestinationDirectory(String destinationDirectory) {
	this.destinationDirectory = destinationDirectory;
	}*/


	public String getFilename() {
	return filename;
	}

	public void setFilename(String filename) {
	this.filename = filename;
	}

	public double getFileSize() {
	return fileSize;
	}

	public void setFileSize(double fileSize) {
	this.fileSize = fileSize;
	}
	public String getSizeInUnit() {
		return size;
		}

		public void setsizeInUnit(String Size) {
		this.size = Size;
		}

	public String getStatus() {
	return status;
	}

	public void setStatus(String status) {
	this.status = status;
	}


	}