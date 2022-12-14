package grade;

import org.junit.jupiter.api.BeforeAll;

import drivers.SQLError;
import tables.Table;

public class Module6 extends SQLModule {
	@BeforeAll
	public static void setup() {
		module_tag = "M6";

		query_data = new Object[][]{
			// PREREQUISITES
			{ Table.class, "CREATE TABLE m6_table (ps STRING PRIMARY, i INTEGER, b BOOLEAN)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"x\", null, true)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"xy\", 5, false)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"xx\", 4, true)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"y\", 1, null)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"yy\", 2, true)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"yx\", 3, false)", "prerequisite" },
			{ 1, "INSERT INTO m6_table VALUES (\"z\", null, null)", "prerequisite" },
			{ Table.class, "SHOW TABLE m6_table", "prerequisite" },
			{ Table.class, "SHOW TABLES", "prerequisite" },

			// STAR FORM
			{ Table.class, "SELECT * FROM m6_table", "star allowed" },
			{ Table.class, "select * from m6_table", "lower case keyword allowed" },
			{ Table.class, "SeLeCt * fRoM m6_table", "mixed case keyword allowed" },
			{ SQLError.class, "SELECT FROM m6_table", "star or columns required" },
			{ SQLError.class, "SELECT *, ps, i, b FROM m6_table", "star and columns forbidden" },

			// LONG FORM
			{ Table.class, "SELECT ps FROM m6_table", "1 column allowed" },
			{ Table.class, "SELECT ps, i, b FROM m6_table", "3 columns allowed" },
			{ Table.class, "select ps, i, b from m6_table", "lower case keyword allowed" },
			{ SQLError.class, "SELECT PS FROM m6_table", "case sensitive names required" },
			{ SQLError.class, "SELECT ps m6_table", "FROM keyword required" },
			{ SQLError.class, "ps FROM m6_table", "SELECT keyword required" },
			{ SQLError.class, "SELECT ps, i, b", "FROM keyword and table name required" },
			{ SQLError.class, "SELECT ps i b FROM m6_table", "commas between columns required" },

			// ALIASING
			{ Table.class, "SELECT ps AS primary FROM m6_table", "column aliasing allowed" },
			{ Table.class, "SELECT ps AS primary, i, b FROM m6_table", "partial aliasing allowed" },
			{ Table.class, "SELECT ps AS primary, i AS number, b AS flag FROM m6_table", "total aliasing allowed" },
			{ Table.class, "select ps as primary, i as number, b as flag from m6_table", "lower case keyword allowed" },
			{ Table.class, "SELECT ps, i AS number, b AS flag FROM m6_table", "partial aliasing allowed" },

			// REORDERING
			{ Table.class, "SELECT ps, b, i FROM m6_table", "reordering columns allowed" },
			{ Table.class, "SELECT i, b, ps FROM m6_table", "reordering columns allowed" },
			{ Table.class, "SELECT i AS number, b AS flag, ps AS primary FROM m6_table", "reordering columns with total aliasing allowed" },
			{ Table.class, "SELECT i AS number, b AS flag, ps FROM m6_table", "reordering columns with partial aliasing allowed" },

			// REPETITION
			{ Table.class, "SELECT ps, i AS number_1, i AS number_2 FROM m6_table", "repeating columns with unambiguous aliasing allowed" },
			{ Table.class, "SELECT ps, i, i AS i_copy FROM m6_table", "repeating columns with unambiguous aliasing allowed" },
			{ Table.class, "SELECT ps, i AS i_copy, i FROM m6_table", "repeating columns with unambiguous aliasing allowed" },
			{ Table.class, "SELECT b, b AS b_copy, ps FROM m6_table", "repeating columns with unambiguous aliasing allowed" },
			{ Table.class, "SELECT b AS b_copy, b, ps FROM m6_table", "repeating columns with unambiguous aliasing allowed" },
			{ SQLError.class, "SELECT ps, i, i FROM m6_table", "repeating columns with ambiguous aliasing forbidden" },
			{ SQLError.class, "SELECT ps, b, i, b FROM m6_table", "repeating columns with ambiguous aliasing forbidden" },
			{ SQLError.class, "SELECT ps, ps FROM m6_table", "repeating columns with ambiguous aliasing forbidden" },
			{ SQLError.class, "SELECT ps AS primary, ps AS primary FROM m6_table", "repeating columns with ambiguous aliasing forbidden" },
			{ SQLError.class, "SELECT i, b, ps AS primary, ps, ps FROM m6_table", "repeating columns with ambiguous aliasing forbidden" },

			// CROSS-ALIASING
			{ Table.class, "SELECT ps, i AS b, b AS i FROM m6_table", "cross-aliasing columns with unambiguous names allowed" },
			{ Table.class, "SELECT ps, i AS b, i AS i_copy, b AS i, b AS b_copy, ps AS ps_copy FROM m6_table", "cross-aliasing columns with unambiguous names" },
			{ Table.class, "SELECT i AS b, b AS ps, ps AS i FROM m6_table", "cross-aliasing columns with unambiguous names" },

			// PRIMARY COLUMN
			{ Table.class, "SELECT ps AS primary_1, ps AS primary_2 FROM m6_table", "repeating primary column with unambiguous total aliasing allowed" },
			{ Table.class, "SELECT ps AS primary_1, ps FROM m6_table", "repeating primary column with unambiguous partial aliasing allowed" },
			{ Table.class, "SELECT ps, ps AS primary_2 FROM m6_table", "repeating primary column with unambiguous partial aliasing allowed" },
			{ SQLError.class, "SELECT i, b FROM m6_table", "primary column required" },

			// STRING COMPARISONS
			{ Table.class, "SELECT * FROM m6_table WHERE ps = \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <> \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps < \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps > \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <= \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps >= \"y\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps = \"\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <> \"\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps < \"\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps > \"\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <= \"\"", "lexicographic string comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps >= \"\"", "lexicographic string comparison required" },

			// INTEGER COMPARISONS
			{ Table.class, "SELECT * FROM m6_table WHERE i = 3", "ascending integer comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i <> 3", "ascending integer comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i < 3", "ascending integer comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i > 3", "ascending integer comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i <= 3", "ascending integer comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i >= 3", "ascending integer comparison required" },

			// BOOLEAN COMPARISONS
			{ Table.class, "SELECT * FROM m6_table WHERE b = true", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b <> true", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b = false", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b <> false", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b < true", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b > false", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b <= true", "canonical boolean comparison required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b >= false", "canonical boolean comparison required" },

			// NULL COMPARISONS
			{ Table.class, "SELECT * FROM m6_table WHERE ps = null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <> null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i = null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i <> null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b = null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b <> null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps < null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps > null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps <= null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps >= null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i < null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i > null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i <= null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i >= null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b < null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b > null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b <= null", "always false comparison with null required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b >= null", "always false comparison with null required" },

			// MIXED TYPES
			{ Table.class, "SELECT * FROM m6_table WHERE ps = 3", "mixed type comparison as strings required" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps = true", "mixed type comparison as strings required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i = \"y\"", "mixed type comparison as strings required" },
			{ Table.class, "SELECT * FROM m6_table WHERE i = true", "mixed type comparison as strings required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b = \"y\"", "mixed type comparison as strings required" },
			{ Table.class, "SELECT * FROM m6_table WHERE b = 3", "mixed type comparison as strings required" },

			// WHERE CLAUSE
			{ Table.class, "SELECT * FROM m6_table  WHERE  ps = \"y\"", "excess internal whitespace allowed" },
			{ Table.class, "SELECT * FROM m6_table WHERE ps=\"y\"", "omitting whitespace around operator allowed" },
			{ Table.class, "SELECT b, i, ps FROM m6_table WHERE ps <> \"z\"", "reordering columns allowed" },
			{ Table.class, "SELECT ps AS s FROM m6_table WHERE ps <> \"z\"", "unaliased left hand side allowed" },
			{ SQLError.class, "SELECT * FROM m6_table WHERE x = 3", "existing left hand side required" },
			{ SQLError.class, "SELECT * FROM m6_table  i = 3", "WHERE keyword required" },
			{ SQLError.class, "SELECT * FROM m6_table WHERE  = 3", "left hand side required" },
			{ SQLError.class, "SELECT * FROM m6_table WHERE i = ", "right hand side required" },
			{ SQLError.class, "SELECT * FROM m6_table WHERE ps equals \"y\"", "undefined operator forbidden" },
			{ SQLError.class, "SELECT * FROM m6_table WHERE ps = asdf", "undefined type forbidden" },
			{ SQLError.class, "SELECT * FROM m6_tableWHEREps = \"y\"", "whitespace between keywords and names required" },
		};

		table_data = new Object[][]{
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xy", 5, false },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yy", 2, true },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "m6_table", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_tables", 3, 0, "table_name", "column_count", "row_count", "string", "integer", "integer", "m6_table", 3, 7 },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			null,
			null,
			{ "_select", 1, 0, "ps", "string", "x", "xx", "xy", "y", "yx", "yy", "z" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			null,
			null,
			null,
			null,
			null,
			{ "_select", 1, 0, "primary", "string", "x", "xx", "xy", "y", "yx", "yy", "z" },
			{ "_select", 3, 0, "primary", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "primary", "number", "flag", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "primary", "number", "flag", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "number", "flag", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "b", "i", "string", "boolean", "integer", "x", true, null, "xx", true, 4, "xy", false, 5, "y", null, 1, "yx", false, 3, "yy", true, 2, "z", null, null },
			{ "_select", 3, 2, "i", "b", "ps", "integer", "boolean", "string", null, true, "x", 4, true, "xx", 5, false, "xy", 1, null, "y", 3, false, "yx", 2, true, "yy", null, null, "z" },
			{ "_select", 3, 2, "number", "flag", "primary", "integer", "boolean", "string", null, true, "x", 4, true, "xx", 5, false, "xy", 1, null, "y", 3, false, "yx", 2, true, "yy", null, null, "z" },
			{ "_select", 3, 2, "number", "flag", "ps", "integer", "boolean", "string", null, true, "x", 4, true, "xx", 5, false, "xy", 1, null, "y", 3, false, "yx", 2, true, "yy", null, null, "z" },
			{ "_select", 3, 0, "ps", "number_1", "number_2", "string", "integer", "integer", "x", null, null, "xx", 4, 4, "xy", 5, 5, "y", 1, 1, "yx", 3, 3, "yy", 2, 2, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "i_copy", "string", "integer", "integer", "x", null, null, "xx", 4, 4, "xy", 5, 5, "y", 1, 1, "yx", 3, 3, "yy", 2, 2, "z", null, null },
			{ "_select", 3, 0, "ps", "i_copy", "i", "string", "integer", "integer", "x", null, null, "xx", 4, 4, "xy", 5, 5, "y", 1, 1, "yx", 3, 3, "yy", 2, 2, "z", null, null },
			{ "_select", 3, 2, "b", "b_copy", "ps", "boolean", "boolean", "string", true, true, "x", true, true, "xx", false, false, "xy", null, null, "y", false, false, "yx", true, true, "yy", null, null, "z" },
			{ "_select", 3, 2, "b_copy", "b", "ps", "boolean", "boolean", "string", true, true, "x", true, true, "xx", false, false, "xy", null, null, "y", false, false, "yx", true, true, "yy", null, null, "z" },
			null,
			null,
			null,
			null,
			null,
			{ "_select", 3, 0, "ps", "b", "i", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 6, 0, "ps", "b", "i_copy", "i", "b_copy", "ps_copy", "string", "integer", "integer", "boolean", "boolean", "string", "x", null, null, true, true, "x", "xx", 4, 4, true, true, "xx", "xy", 5, 5, false, false, "xy", "y", 1, 1, null, null, "y", "yx", 3, 3, false, false, "yx", "yy", 2, 2, true, true, "yy", "z", null, null, null, null, "z" },
			{ "_select", 3, 2, "b", "ps", "i", "integer", "boolean", "string", null, true, "x", 4, true, "xx", 5, false, "xy", 1, null, "y", 3, false, "yx", 2, true, "yy", null, null, "z" },
			{ "_select", 2, 0, "primary_1", "primary_2", "string", "string", "x", "x", "xx", "xx", "xy", "xy", "y", "y", "yx", "yx", "yy", "yy", "z", "z" },
			{ "_select", 2, 0, "primary_1", "ps", "string", "string", "x", "x", "xx", "xx", "xy", "xy", "y", "y", "yx", "yx", "yy", "yy", "z", "z" },
			{ "_select", 2, 0, "ps", "primary_2", "string", "string", "x", "x", "xx", "xx", "xy", "xy", "y", "y", "yx", "yx", "yy", "yy", "z", "z" },
			null,
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "y", 1, null, "yx", 3, false, "yy", 2, true, "z", null, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "yx", 3, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xx", 4, true, "xy", 5, false, "y", 1, null, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xx", 4, true, "xy", 5, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null, "yx", 3, false, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xx", 4, true, "xy", 5, false, "yx", 3, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xy", 5, false, "yx", 3, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xy", 5, false, "yx", 3, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "xy", 5, false, "yx", 3, false },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "yx", 3, false, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "x", null, true, "xx", 4, true, "xy", 5, false, "yx", 3, false, "yy", 2, true },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean" },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null },
			{ "_select", 3, 0, "ps", "i", "b", "string", "integer", "boolean", "y", 1, null },
			{ "_select", 3, 2, "b", "i", "ps", "boolean", "integer", "string", true, null, "x", true, 4, "xx", false, 5, "xy", null, 1, "y", false, 3, "yx", true, 2, "yy" },
			{ "_select", 1, 0, "s", "string", "x", "xx", "xy", "y", "yx", "yy" },
			null,
			null,
			null,
			null,
			null,
			null,
			null
		};
	}
}