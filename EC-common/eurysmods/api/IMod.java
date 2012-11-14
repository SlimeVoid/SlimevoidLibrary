package eurysmods.api;

public interface IMod {
	public void initialize();

	public void load();

	public void addItems();

	public void addNames();

	public void addRecipes();

	public int configurationProperties();
}
