package framework.processer;


public abstract class DataBaseProcessor {
	protected ISqlImpl sqlImpl;
	
	public DataBaseProcessor(ISqlImpl sqlImpl) {
		this.sqlImpl = sqlImpl;
	}
}
