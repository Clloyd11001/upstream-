package tables;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements a search-based table
 * using a list data structure.
 */
public class SearchTable extends Table {
	private List<List<Object>> list;

	/**
	 * Creates a table and initializes
	 * the data structure.
	 *
	 * @param tableName the table name
	 * @param columnNames the column names
	 * @param columnTypes the column types
	 * @param primaryIndex the primary index
	 */
	public SearchTable(String tableName, List<String> columnNames, List<String> columnTypes, int primaryIndex) {
		setTableName(tableName);
		setColumnNames(columnNames);
		setColumnTypes(columnTypes);
		setPrimaryIndex(primaryIndex);

		list = new LinkedList<>();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean put(List<Object> row) {
		Object key = row.get(getPrimaryIndex());

		if (key == null)
			throw new NullPointerException("Key field %d must not be null".formatted(getPrimaryIndex()));

		for (var i = 0; i < row.size(); i++) {
			var field = row.get(i);
			var type = getColumnTypes().get(i);

			if (field == null) {
				continue;
			}
			else if (type.equals("string")) {
				if (!(field instanceof String))
					throw new IllegalArgumentException("Field <%d> must be a string".formatted(i));
			}
			else if (type.equals("integer")) {
				if (!(field instanceof Integer))
					throw new IllegalArgumentException("Field <%d> must be an integer".formatted(i));
			}
			else if (type.equals("boolean")) {
				if (!(field instanceof Boolean))
					throw new IllegalArgumentException("Field <%d> must be a boolean".formatted(i));
			}
		}

		for (int i = 0; i < list.size(); i++) {
			List<Object> old = list.get(i);
			if (old.get(getPrimaryIndex()).equals(key)) {
				list.set(i, row);
				return true;
			}
		}
		list.add(row);
		return false;
	}

	@Override
	public boolean remove(Object key) {
		for (int i = 0; i < list.size(); i++) {
			List<Object> row = list.get(i);
			if (row.get(getPrimaryIndex()).equals(key)) {
				list.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Object> get(Object key) {
		for (int i = 0; i < list.size(); i++) {
			List<Object> row = list.get(i);
			if (row.get(getPrimaryIndex()).equals(key))
				return row;
		}
		return null;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public int capacity() {
		return size();
	}

	@Override
	public Iterator<List<Object>> iterator() {
		return new Iterator<>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < list.size();
			}

			@Override
			public List<Object> next() {
				return list.get(index++);
			}
		};
	}
}