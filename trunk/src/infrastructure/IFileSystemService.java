package infrastructure;

public interface IFileSystemService {

	boolean exists(String dataDirectory, String diagramName);

	void save(String fileName, String content);

}
