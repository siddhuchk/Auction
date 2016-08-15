package com.enterprise.adapter.spring.root;



import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;

/**
 * 
 * @author anuj.kumar2
 *
 */
/**
* Hibernate JPA adapter that adds supports for setting a non-default transaction isolation level.
*/
@SuppressWarnings("serial")
public class HibernateIsolationJpaDialect extends HibernateJpaDialect {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public TransactionDataWrapper beginTransaction(EntityManager entityManager, TransactionDefinition definition) throws SQLException {
		// Set transaction timeout
		if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT)
			this.getSession(entityManager).getTransaction().setTimeout(definition.getTimeout());
		
		// Set isolation level on the associated JDBC connection
		Integer oldIsolation = null;
		Connection connection = null;
		if (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
			connection = this.getJdbcConnection(entityManager, definition.isReadOnly()).getConnection();
			if (this.log.isTraceEnabled())
				this.log.trace("setting isolation level to " + definition.getIsolationLevel() + " on " + connection);
				oldIsolation = DataSourceUtils.prepareConnectionForTransaction(connection, definition);
			}
		
		// Start transaction
		entityManager.getTransaction().begin();
		
		// Prepare transaction
		return this.prepareTransaction(entityManager, definition, connection, oldIsolation);
	}
	
	/**
	 * Prepare transaction.
	 */
	protected TransactionDataWrapper prepareTransaction(EntityManager entityManager, TransactionDefinition definition, Connection connection, Integer oldIsolation) {
		
		// Do superclass preparation
		Object transactionData = this.prepareTransaction(entityManager, definition.isReadOnly(), definition.getName());
		
		// Wrap result
		return new TransactionDataWrapper(transactionData, connection, oldIsolation);
	}
	
	@Override
	public void cleanupTransaction(Object obj) {
		
		// Let superclass cleanup
		final TransactionDataWrapper wrapper = (TransactionDataWrapper)obj;
		super.cleanupTransaction(wrapper.getTransactionData());
		
		// Restore isolation level on the JDBC connection
		Connection connection = wrapper.getConnection();
		if (connection != null) {
			if (this.log.isTraceEnabled())
				this.log.trace("restoring isolation level to " + wrapper.getIsolation() + " on " + connection);
				DataSourceUtils.resetConnectionAfterTransaction(connection, wrapper.getIsolation());
		}
	}
	
	/**
	 * Wraps superclass transaction data and adds isolation level to restore.
	 */
	protected class TransactionDataWrapper {
		private final Object transactionData;
		private final Connection connection;
		private final Integer isolation;
		
		public TransactionDataWrapper(Object transactionData, Connection connection, Integer isolation) {
			this.transactionData = transactionData;
			this.connection = connection;
			this.isolation = isolation;
		}
		
		public Object getTransactionData() {
			return this.transactionData;
		}
		
		public Connection getConnection() {
			return this.connection;
		}
		
		public Integer getIsolation() {
			return this.isolation;
		}
	}
}