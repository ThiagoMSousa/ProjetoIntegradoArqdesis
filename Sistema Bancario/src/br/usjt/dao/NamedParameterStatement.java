package br.usjt.dao;

import java.math.BigDecimal;
import java.nio.channels.ConnectionPendingException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * This class is copied from framework-pegasus
 * This class wraps around a {@link PreparedStatement} and allows the
 * programmer to set parameters by name instead
 * of by index.  This eliminates any confusion as to which parameter index
 * represents what.  This also means that
 * rearranging the SQL statement or adding a parameter doesn't involve
 * renumbering your indices.
 * Code such as this:
 * *
 * Connection con=getConnection();
 * String query="select * from my_table where name=? or address=?";
 * PreparedStatement p=con.prepareStatement(query);
 * p.setString(1, "bob");
 * p.setString(2, "123 terrace ct");
 * ResultSet rs=p.executeQuery();
 * *
 * can be replaced with:
 * *
 * Connection con=getConnection();
 * String query="select * from my_table where name=:name or address=:address";
 * NamedParameterStatement p=new NamedParameterStatement(con, query);
 * p.setString("name", "bob");
 * p.setString("address", "123 terrace ct");
 * ResultSet rs=p.executeQuery();
 */
public class NamedParameterStatement {
    /**
     * The statement this object is wrapping.
     */
    private PreparedStatement statement;

    /**
     * Maps parameter names to arrays of ints which are the parameter indices.
     */
    private Map<String, int[]> indexMap;

    private String query;
    private Integer options;
	private Connection connection;


    /**
     * Creates a NamedParameterStatement.  Wraps a call to
     * c.{@link Connection#prepareStatement(java.lang.String) prepareStatement}.
     *
     * @param connection the database connection
     * @param query      the parameterized query
     * @throws SQLException if the statement could not be created
     */
    public NamedParameterStatement(Connection connection){
    	this.connection = connection;
    }
    
    public NamedParameterStatement(Connection connection, String query) throws SQLException {
        indexMap = new HashMap<String, int[]>();
        String parsedQuery = parse(query, indexMap);
        statement = connection.prepareStatement(parsedQuery);
        this.query = query;
        this.options = null;
    }

    public NamedParameterStatement(Connection connection, String query, int options) throws SQLException {
        indexMap = new HashMap<String, int[]>();
        String parsedQuery = parse(query, indexMap);
        statement = connection.prepareStatement(parsedQuery, options);
        this.query = query;
        this.options = options;
    }
    
    /*
	 * Metodo que faz o parse das named queries 
	 */
    public boolean prepareNamedParameterStatement(String query){
    	try {
	    	indexMap = new HashMap<String, int[]>();
	        String parsedQuery = parse(query, indexMap);
			statement = getConnection().prepareStatement(parsedQuery);
	        this.query = query;
	        return true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void clean(){
    	
    }
    
    public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

    public String getQuery() {
        return query;
    }

    public Integer getOptions() {
        return options;
    }

    /**
     * Parses a query with named parameters.  The parameter-index mappings are
     * put into the map, and the
     * parsed query is returned.  DO NOT CALL FROM CLIENT CODE.  This
     * method is non-private so JUnit code can
     * test it.
     *
     * @param query    query to parse
     * @param paramMap map to hold parameter-index mappings
     * @return the parsed query
     */
    static final String parse(String query, Map<String, int[]> paramMap) {
        // I was originally using regular expressions, but they didn't work well
        // for ignoring parameter-like strings inside quotes.
        Map<String, List<Integer>> paramMapAux = new HashMap<String, List<Integer>>();
        int length = query.length();
        StringBuffer parsedQuery = new StringBuffer(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length &&
                        Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    c = '?'; // replace the parameter with a question mark
                    i += name.length(); // skip past the end if the parameter

                    List<Integer> indexList = paramMapAux.get(name);
                    if (indexList == null) {
                        indexList = new LinkedList<Integer>();
                        paramMapAux.put(name, indexList);
                    }
                    indexList.add(index);

                    index++;
                }
            }
            parsedQuery.append(c);
        }

        // replace the lists of Integer objects with arrays of ints
        for (Map.Entry<String, List<Integer>> entry : paramMapAux.entrySet()) {
            List<Integer> list = entry.getValue();
            int[] indexes = new int[list.size()];
            int i = 0;
            for (Integer x : list) {
                indexes[i++] = x;
            }
            paramMap.put(entry.getKey(), indexes);
        }

        return parsedQuery.toString();
    }


    /**
     * Returns the indexes for a parameter.
     *
     * @param name parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException if the parameter does not exist
     */
    private int[] getIndexes(String name) {
        int[] indexes = indexMap.get(name);
        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }
        return indexes;
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(String name, Object value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            statement.setObject(indexes[i], value);
        }
    }

    public void setObject(String name, Object value, int sqlType) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            statement.setObject(indexes[i], value, sqlType);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(String name, String value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            statement.setString(indexes[i], value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setInt(String name, int value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            statement.setInt(indexes[i], value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setLong(String name, long value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            statement.setLong(indexes[i], value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            // TODO: Con setTimestamp contra Oracle en algunos casos los queries se quedaban colgados ...
            //statement.setTimestamp(indexes[i], value);
            Date date = new Date(value.getTime());
            statement.setDate(indexes[i], date);
        }
    }
    
    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setBigDecimal(String name, BigDecimal value) throws SQLException {
        int[] indexes = getIndexes(name);
        for (int i = 0; i < indexes.length; i++) {
            // TODO: Con setTimestamp contra Oracle en algunos casos los queries se quedaban colgados ...
            //statement.setTimestamp(indexes[i], value);
            statement.setBigDecimal(indexes[i], value);
        }
    }


    /**
     * Returns the underlying statement.
     *
     * @return the statement
     */
    public PreparedStatement getStatement() {
        return statement;
    }


    /**
     * Executes the statement.
     *
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        return statement.execute();
    }


    /**
     * Executes the statement, which must be a query.
     *
     * @return the query results
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        if(statement.getMaxRows() >= 1000) {
            statement.setFetchSize(1000);
        } else {
            statement.setFetchSize(statement.getMaxRows());
        }
        return statement.executeQuery();
    }


    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     *
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }


    /**
     * Closes the statement.
     *
     * @throws SQLException if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException {
        statement.close();
    }


    /**
     * Adds the current set of parameters as a batch entry.
     *
     * @throws SQLException if something went wrong
     */
    public void addBatch() throws SQLException {
        statement.addBatch();
    }


    /**
     * Executes all of the batched statements.
     * *
     * See {@link Statement#executeBatch()} for details.
     *
     * @return update counts for each statement
     * @throws SQLException if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    public NamedParameterStatement clonePure() throws SQLException {
        return clonePure(getQuery());
    }

    public NamedParameterStatement clonePure(String query) throws SQLException {
        if (options != null) {
            return new NamedParameterStatement(getStatement().getConnection(), query, options);
        } else {
            return new NamedParameterStatement(getStatement().getConnection(), query);
        }
    }

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}