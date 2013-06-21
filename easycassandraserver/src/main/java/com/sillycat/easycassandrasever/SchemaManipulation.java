package com.sillycat.easycassandrasever;

import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SuperColumnQuery;

/**
 * Creates a keyspace and adds a column family. This column family
 * contains an index on the column named 'birthdate' which is exptected
 * to be a Long.  
 * 
 * To run this example from maven:
 * mvn -e exec:java -Dexec.mainClass="com.riptano.cassandra.hector.example.SchemaManipulation"
 * 
 * @author zznate
 *
 */
public class SchemaManipulation {
    
    private static final String DYN_KEYSPACE = "Keyspace1";
    private static final String DYN_CF = "User1";
    private static final String CF_SUPER = "Super1";
    
    private static StringSerializer stringSerializer = StringSerializer.get();
    
    public static void main(String[] args) throws Exception {
        
        Cluster cluster = HFactory.getOrCreateCluster("TestCluster", "localhost:9160");
                
        try {
            if ( cluster.describeKeyspace(DYN_KEYSPACE) != null ) {
              cluster.dropKeyspace(DYN_KEYSPACE);
            }
            
            BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
            columnFamilyDefinition.setKeyspaceName(DYN_KEYSPACE);
            columnFamilyDefinition.setName(DYN_CF);
            
            columnFamilyDefinition.setKeyValidationClass("UTF8Type");
            columnFamilyDefinition.setComparatorType(ComparatorType.UTF8TYPE);
            columnFamilyDefinition.setDefaultValidationClass("UTF8Type");
            
            BasicColumnFamilyDefinition superCfDefinition = new BasicColumnFamilyDefinition();
            superCfDefinition.setKeyspaceName(DYN_KEYSPACE);
            superCfDefinition.setName(CF_SUPER);
            
            superCfDefinition.setColumnType(ColumnType.SUPER);
            superCfDefinition.setKeyValidationClass("UTF8Type");
            superCfDefinition.setComparatorType(ComparatorType.UTF8TYPE);
            superCfDefinition.setDefaultValidationClass("UTF8Type");
            
            ColumnFamilyDefinition cfDefStandard = new ThriftCfDef(columnFamilyDefinition);
            ColumnFamilyDefinition cfDefSuper = new ThriftCfDef(superCfDefinition);
            
            KeyspaceDefinition keyspaceDefinition = 
                    HFactory.createKeyspaceDefinition(DYN_KEYSPACE, "org.apache.cassandra.locator.SimpleStrategy", 
                        1, Arrays.asList(cfDefStandard,cfDefSuper));
                                               
            cluster.addKeyspace(keyspaceDefinition);
            
            // insert some data
            
            List<KeyspaceDefinition> keyspaces = cluster.describeKeyspaces();
            for (KeyspaceDefinition kd : keyspaces) {
                if ( kd.getName().equals(DYN_KEYSPACE) ) {
                    System.out.println("Name: " +kd.getName());
                    System.out.println("RF: " +kd.getReplicationFactor());
                    System.out.println("strategy class: " +kd.getStrategyClass());
                    List<ColumnFamilyDefinition> cfDefs = kd.getCfDefs();
                    for (ColumnFamilyDefinition def : cfDefs) {
                      System.out.println("  CF Type: " +def.getColumnType());
                      System.out.println("  CF Name: " +def.getName());
                      System.out.println("  CF Metadata: " +def.getColumnMetadata());  
                    }
                    
                    
                } 
            }
            
            Keyspace keyspaceOperator = HFactory.createKeyspace(DYN_KEYSPACE, cluster);
            
            Mutator<String> mutator1 = HFactory.createMutator(keyspaceOperator, StringSerializer.get());
            mutator1.insert("1", DYN_CF, HFactory.createStringColumn("First", "Carl"));
            mutator1.insert("1", DYN_CF, HFactory.createStringColumn("Last", "Luo"));
            
            ColumnQuery<String, String, String> columnQuery = HFactory.createStringColumnQuery(keyspaceOperator);
            //columnQuery.setColumnFamily(DYN_CF).setKey("1").setName("First");
            columnQuery.setColumnFamily(DYN_CF).setKey("1").setName("First");
            QueryResult<HColumn<String, String>> result1 = columnQuery.execute();
            
            System.out.println("Read HColumn from cassandra: " + result1.get());            
            System.out.println("Verify on CLI with:  get User1['1']; ");
            
            Mutator<String> mutator2 = HFactory.createMutator(keyspaceOperator, stringSerializer);
            mutator2.insert("1", CF_SUPER, HFactory.createSuperColumn("sillycat", 
                    Arrays.asList(HFactory.createStringColumn("First", "Carl"), HFactory.createStringColumn("Last", "Luo")), 
                    stringSerializer, stringSerializer, stringSerializer));
            
            SuperColumnQuery<String, String, String, String> superColumnQuery = 
                HFactory.createSuperColumnQuery(keyspaceOperator, stringSerializer, stringSerializer, 
                        stringSerializer, stringSerializer);
            superColumnQuery.setColumnFamily(CF_SUPER).setKey("1").setSuperName("sillycat");

            QueryResult<HSuperColumn<String, String, String>> result2 = superColumnQuery.execute();

            System.out.println("Read HSuperColumn from cassandra: " + result2.get());            
            System.out.println("Verify on CLI with:  get Super1['1']['sillycat']; ");
            
        } catch (HectorException he) {
            he.printStackTrace();
        }
        cluster.getConnectionManager().shutdown(); 
    }

}
